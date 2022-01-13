package com.example.mediacommunity.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigTest {
    @Test
    public void jasypt_encrypt_decrypt_test() {
        String plainText = "hello world";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("password");
        jasypt.setAlgorithm("PBEWithMD5AndDES");

        String encryptedText = jasypt.encrypt(plainText);
        String decryptedText = jasypt.decrypt(encryptedText);

        System.out.println("encryptedText = " + encryptedText);

        Assertions.assertThat(plainText).isEqualTo(decryptedText);
    }
}