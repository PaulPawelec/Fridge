package com.example.fridge;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import android.util.Base64;

/**
 * Source: https://gist.github.com/berlinbrown/4171730
 * Use encryption algorithm with DESede Cipher and Base64/MD5 Base64 encryption for Java.
 */
public class Encryption {

    /*
     * Output:
     * Encrypted Data : fxqeTkmii3BzPEEXTlhfMKeC4AZRAaBV1gAAPiROEyo=
     * Vl3P0kxGTj5phbg+S9C/mQ==
     * Decrypted Data: MyData=1&endt=1354221317
     */

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    private static final String ENCRYPTION_KEY = "YNYNWKLIJLKJFJALJLAJFJFJY";

 /**   public static void main(final String [] args) {
        final Encryption e = new Encryption();
        String o [] = e.encrypt("MyData=1");
        System.out.println("Encrypted Data : " + o[0]);
        System.out.println(o[1]);
    }
    **/
    public String encrypt(final String plainText) {
        try {
            final String param1 = plainText;
            final Encrypter encrypter = new Encrypter("DESede", ENCRYPTION_KEY);
            final String encryptedParam1 = encrypter.encrypt(param1);
            // Use a one-way hash, we cannot determine the original message but
            // we can verify against the original message.
            //final String param2hashed = this.makeHash(param1);
            return encryptedParam1;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Encrypter {
        public static final String ENC_DEFAULT_KEY = "YUNWEUYSKHWKHFABCUEKWYRNUI";
        public static final String DES_ENCRYPTION_SCHEME = "DES";
        public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
        private KeySpec keySpec;
        private SecretKeyFactory keyFactory;
        private Cipher cipher;
        private static final String ENCODING = "UTF8";

        public Encrypter(String encryptionScheme) {
            this(encryptionScheme, ENC_DEFAULT_KEY);
        }

        public Encrypter(String encryptionScheme, String encryptionKey) {
            if (encryptionKey == null)
                throw new IllegalArgumentException("encryption key was invalid");
            try {
                final byte[] keyAsBytes = encryptionKey.getBytes(ENCODING);
                if (encryptionScheme.equals(DESEDE_ENCRYPTION_SCHEME)) {
                    keySpec = new DESedeKeySpec(keyAsBytes);
                } else if (encryptionScheme.equals(DES_ENCRYPTION_SCHEME)) {
                    keySpec = new DESKeySpec(keyAsBytes);
                } else {
                    throw new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme);
                }
                keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
                cipher = Cipher.getInstance(encryptionScheme);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String encrypt(String decrstr) {
            if (decrstr == null || decrstr.trim().length() == 0) {
                throw new IllegalArgumentException("unencrypted string was null or empty");
            }
            try {
                SecretKey key = keyFactory.generateSecret(keySpec);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] cleartext = decrstr.getBytes(ENCODING);
                byte[] ciphertext = cipher.doFinal(cleartext);
                return Base64.encodeToString(ciphertext,Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}