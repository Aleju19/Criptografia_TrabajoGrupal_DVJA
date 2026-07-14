package com.appsecco.dvja.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    // Nivel de costo de procesamiento estándar de la industria (12 iteraciones)
    private static final int LOG_ROUNDS = 12;

    /**
     * Toma la contraseña plana que ingresa un usuario al registrarse y la devuelve hasheada con Salt dinámico.
     */
    public static String hashPassword(String plainTextPassword) {
        if (plainTextPassword == null || plainTextPassword.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Valida si el texto plano ingresado en el Login coincide con el hash seguro guardado en la Base de Datos.
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (plainTextPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainTextPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Maneja fallos en caso de interactuar con registros remanentes no migrados o corruptos
            return false;
        }
    }
}