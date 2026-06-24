package com.chatrop.infrastructure.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component("databasePartitionInitializer")
public class DatabasePartitionInitializer implements InitializingBean {

    private final JdbcTemplate jdbcTemplate;

    public DatabasePartitionInitializer(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Running DatabasePartitionInitializer to ensure partitioned 'messages' table...");
        
        // 1. Check if the table "messages" exists and if it is partitioned
        List<String> relkinds = jdbcTemplate.query(
                "SELECT relkind::text FROM pg_class WHERE relname = 'messages'",
                (rs, rowNum) -> rs.getString(1)
        );

        boolean needsRecreation = false;
        if (!relkinds.isEmpty()) {
            String relkind = relkinds.get(0);
            if (!"p".equalsIgnoreCase(relkind)) {
                // Table exists but is not partitioned, we need to drop it and recreate it partitioned
                needsRecreation = true;
            }
        } else {
            // Table doesn't exist at all
            needsRecreation = true;
        }

        if (needsRecreation) {
            System.out.println("Recreating 'messages' table as a partitioned table...");
            jdbcTemplate.execute("DROP TABLE IF EXISTS messages CASCADE");
            jdbcTemplate.execute(
                    "CREATE TABLE messages (" +
                    "    id UUID NOT NULL," +
                    "    sender_id VARCHAR(255)," +
                    "    sender_email VARCHAR(255)," +
                    "    receiver_id VARCHAR(255)," +
                    "    receiver_email VARCHAR(255)," +
                    "    group_id VARCHAR(255)," +
                    "    content VARCHAR(255)," +
                    "    timestamp TIMESTAMP NOT NULL," +
                    "    PRIMARY KEY (id, timestamp)" +
                    ") PARTITION BY RANGE (timestamp)"
            );
        }

        // 2. Generate partitions dynamically for previous month, current month, and next 3 months
        LocalDate now = LocalDate.now();
        for (int i = -1; i <= 3; i++) {
            LocalDate targetMonth = now.plusMonths(i);
            LocalDate start = targetMonth.withDayOfMonth(1);
            LocalDate end = targetMonth.plusMonths(1).withDayOfMonth(1);

            String partitionName = String.format("messages_y%dm%02d", start.getYear(), start.getMonthValue());
            String startStr = start.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String endStr = end.format(DateTimeFormatter.ISO_LOCAL_DATE);

            String ddl = String.format(
                    "CREATE TABLE IF NOT EXISTS %s PARTITION OF messages FOR VALUES FROM ('%s') TO ('%s')",
                    partitionName, startStr, endStr
            );
            System.out.println("Ensuring partition: " + partitionName);
            jdbcTemplate.execute(ddl);
        }

        // 3. Create composite index on parent partitioned table to automatically propagate to all partitions
        System.out.println("Ensuring composite index idx_messages_group_timestamp exists...");
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_messages_group_timestamp ON messages (group_id, timestamp ASC)");

        System.out.println("DatabasePartitionInitializer completed successfully.");
    }
}

@org.springframework.context.annotation.Configuration
class JpaDependsOnConfig {
    @org.springframework.context.annotation.Bean
    public static org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryDependsOnPostProcessor entityManagerFactoryDependsOnPostProcessor() {
        return new org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryDependsOnPostProcessor("databasePartitionInitializer");
    }
}
