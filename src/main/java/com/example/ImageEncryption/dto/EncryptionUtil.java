package com.example.ImageEncryption.dto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

public class EncryptionUtil {
	
	// Convert SecretKey to String (Base64 encoded)
    public static String secretKeyToString(SecretKey secretKey) {
        byte[] encodedKey = secretKey.getEncoded();  // Get the raw bytes of the key
        return Base64.getEncoder().encodeToString(encodedKey);  // Encode to Base64 string
    }

    // Convert String (Base64 encoded) back to SecretKey
    public static SecretKey stringToSecretKey(String keyString, String algorithm) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);  // Decode from Base64 string to byte array
        return new SecretKeySpec(decodedKey, algorithm);  // Reconstruct the SecretKey
    }


    // Generate AES key
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // 128-bit AES key
        return keyGenerator.generateKey();
    }

    // Encrypt data using AES
    public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // Convert the byte array to Base64 string (for easy transmission)
    public static String encodeToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Decrypt data using AES
    public static byte[] decrypt(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    // Convert Base64 string back to byte array
    public static byte[] decodeFromBase64(String data) {
        return Base64.getDecoder().decode(data);
    }
}
