package mx.sos.era.easyretail.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoHelper {
    private static final SecretKey secretKey;
    private static final String KEY_FILE = "config/secret.key";

    static {
        try {
            Path path = Paths.get(KEY_FILE);
            if (Files.exists(path)) {
                byte[] decoded = Base64.getDecoder().decode(Files.readAllBytes(path));
                secretKey = new SecretKeySpec(decoded, "AES");
            }else{
                KeyGenerator generator = KeyGenerator.getInstance("AES");
                generator.init(128);
                secretKey = generator.generateKey();
                String encoded = Base64.getEncoder().encodeToString(secretKey.getEncoded());
                Files.createDirectories(path.getParent());
                Files.write(path, encoded.getBytes());
            }
        }catch (Exception e){
            throw new RuntimeException("Error inicializando clave AES",e);
        }
    }

    public static String encrypt(String plainText){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error encriptando",e);
        }
    }

    public static String decrypt(String encryptedText){
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[]decoded = Base64.getDecoder().decode(encryptedText);
            return new String(cipher.doFinal(decoded));
        }catch (Exception e){
            throw new RuntimeException("Error desencriptando", e);
        }
    }

    public static String getKeyBase64(){
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}