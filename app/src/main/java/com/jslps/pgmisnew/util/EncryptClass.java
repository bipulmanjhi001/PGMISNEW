package com.jslps.pgmisnew.util;

import se.simbio.encryption.Encryption;

public class EncryptClass {
    private String textToEncryptorDecrypt;
    private String method;

    public EncryptClass(String textToEncryptorDecrypt, String method) {
        this.textToEncryptorDecrypt = textToEncryptorDecrypt;
        this.method = method;
    }

    public String Encrypt() {
        String key = "PgMis";
        String salt = "MICROWARE";
        byte[] iv = new byte[16];
        String encrypted = null;
        Encryption encryption = Encryption.getDefault(key, salt, iv);

        if(method.equals("encrypt")){
            encrypted = encryption.encryptOrNull(textToEncryptorDecrypt);
            return encrypted;
        }else{
            String decrypted = encryption.decryptOrNull(textToEncryptorDecrypt);
            return decrypted;
        }
    }
}
