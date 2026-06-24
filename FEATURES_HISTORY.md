# Historial de Features Implementadas — Chatrop

Este archivo registra las características principales añadidas y mejoradas en el clon de chat en tiempo real **Chatrop**, incluyendo enlaces a los archivos clave modificados.

---

## 1. Unificación de Lista de Conversaciones (Mensajes Directos y Grupos)
- **Descripción**: Rediseño del panel izquierdo para presentar en una lista unificada tanto los chats privados como los grupos de chat activos. Se ordenan de forma dinámica mostrando el preview del último mensaje recibido y la hora exacta.
- **Archivos Clave**:
  - [index.html](file:///Users/fjavp/dev/chatrop/src/main/resources/static/index.html) (Script JS para rendering unificado y ordenamiento)
  - [GetDirectChatsUseCase.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/messaging/application/usecase/GetDirectChatsUseCase.java)

## 2. Privacidad y Seguridad en Grupos
- **Descripción**: Eliminación del auto-join. Ahora un usuario solo puede ver los grupos de los que es miembro directo. Se implementaron en el backend verificaciones estrictas que arrojan excepciones de seguridad si alguien intenta acceder al historial de un grupo sin pertenecer al mismo.
- **Archivos Clave**:
  - [GetGroupsForUserUseCase.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/messaging/application/usecase/GetGroupsForUserUseCase.java)
  - [GetGroupHistoryUseCase.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/messaging/application/usecase/GetGroupHistoryUseCase.java)

## 3. Acciones de Grupo (Crear y Abandonar)
- **Descripción**:
  - **Creación**: Botón dedicado en el menú principal para añadir un nuevo grupo instantáneamente mediante una ventana de diálogo nativa.
  - **Abandono**: Opción para salir de un grupo con confirmación nativa (`confirm()`) y retorno directo a la lista (manual refresh).
- **Archivos Clave**:
  - [LeaveGroupUseCase.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/messaging/application/usecase/LeaveGroupUseCase.java)
  - [GroupController.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/messaging/infrastructure/adapter/input/rest/GroupController.java)
  - [index.html](file:///Users/fjavp/dev/chatrop/src/main/resources/static/index.html) (Acciones en la cabecera del chat y logout panel)

## 4. Contenerización Completa y Persistencia de Datos
- **Descripción**: Migración de la ejecución del entorno local a Docker/Docker Compose usando OrbStack. Se añadió persistencia explícita mapeando el directorio de datos de PostgreSQL en la raíz del espacio de trabajo y registrándolo en el control de versiones.
- **Archivos Clave**:
  - [Dockerfile](file:///Users/fjavp/dev/chatrop/Dockerfile)
  - [docker-compose.yml](file:///file:///Users/fjavp/dev/chatrop/docker-compose.yml)
  - [.gitignore](file:///Users/fjavp/dev/chatrop/.gitignore) (Ignora local data `/postgres-data/`)

## 5. Diseño Visual Pastel Blue y Adaptabilidad Móvil (UX)
- **Descripción**:
  - **Adaptación Teclado Móvil**: Integración de la API `visualViewport` en JS para redimensionar de forma dinámica el contenedor del chat, evitando que el teclado virtual empuje la cabecera o deforme el chat en celulares.
  - **Paleta de Colores**: Reemplazo de los tonos violeta/índigo por un estilo azul pastel fluido.
  - **Movimiento y Efectos**: Animaciones fluidas con curvas personalizadas (`cubic-bezier`) que aplican efectos rebote y elevación interactiva al pasar el mouse por las filas.
- **Archivos Clave**:
  - [index.html](file:///Users/fjavp/dev/chatrop/src/main/resources/static/index.html) (Viewport listeners y estilos CSS con variables dinámicas)

## 6. Particionamiento Mensual de la Tabla de Mensajes
- **Descripción**: Particionamiento de la tabla de base de datos `messages` mediante rangos mensuales usando la columna `timestamp`. Esto optimiza drásticamente las consultas y las operaciones de limpieza de históricos. Implementado dinámicamente en el arranque de Spring Boot antes de inicializar JPA.
- **Archivos Clave**:
  - [DatabasePartitionInitializer.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/infrastructure/config/DatabasePartitionInitializer.java) (Creación dinámica de particiones mensuales en ejecución)
  - [MessageId.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/messaging/infrastructure/adapter/output/persistence/entity/MessageId.java)
  - [MessageEntity.java](file:///Users/fjavp/dev/chatrop/src/main/java/com/chatrop/messaging/infrastructure/adapter/output/persistence/entity/MessageEntity.java) (Uso de clave compuesta requerida para el particionamiento)
