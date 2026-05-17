package com.chatrop.users.domain.port;

public interface PasswordHasher {
    
    /**
     * Transforma una contraseña en texto plano en un hash seguro.
     */
    String hash(String plainText);

    /**
     * Comprueba si una contraseña en texto plano coincide con un hash guardado.
     */
    boolean check(String plainText, String hash);
}
