package com.appsecco.dvja.util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 16; // 128 bits para AES estándar
    private static final int GCM_IV_LENGTH = 12; // IV recomendado para GCM
    private static final int GCM_TAG_LENGTH = 128; // En bits

    // Una clave estática de prueba de 16 bytes. 
    // En producción iría en variables de entorno, pero para el entorno académico es perfecta.
    private static final byte[] FIXED_KEY = "DavidOrtegaKey12".getBytes(StandardCharsets.UTF_8);

    /**
     * Cifra una cadena de texto plano usando AES-GCM y devuelve una cadena en Base64.
     */
    public static String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) return plainText;
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv); // Genera un IV aleatorio único

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            SecretKeySpec keySpec = new SecretKeySpec(FIXED_KEY, ALGORITHM);
            
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, parameterSpec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Unimos el IV y el texto cifrado en un solo arreglo para guardarlo fácilmente
            byte[] cipherMessage = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, cipherMessage, 0, iv.length);
            System.arraycopy(cipherText, 0, cipherMessage, iv.length, cipherText.length);

            return Base64.getEncoder().encodeToString(cipherMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar el dato sensible con AES", e);
        }
    }

    /**
     * Descifra una cadena en Base64 proveniente de la BD usando AES-GCM.
     */
    public static String decrypt(String cipherTextBase64) {
        if (cipherTextBase64 == null || cipherTextBase64.isEmpty()) return cipherTextBase64;
        try {
            byte[] cipherMessage = Base64.getDecoder().decode(cipherTextBase64);

            byte[] iv = new byte[GCM_IV_LENGTH];
            System.arraycopy(cipherMessage, 0, iv, 0, iv.length);

            int cipherTextLength = cipherMessage.length - GCM_IV_LENGTH;
            byte[] cipherText = new byte[cipherTextLength];
            System.arraycopy(cipherMessage, iv.length, cipherText, 0, cipherTextLength);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            SecretKeySpec keySpec = new SecretKeySpec(FIXED_KEY, ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, keySpec, parameterSpec);
            byte[] plainTextBytes = cipher.doFinal(cipherText);

            return new String(plainTextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Si no está cifrado (datos previos), devolvemos el texto original para no romper la app
            return cipherTextBase64;
        }
    }
}