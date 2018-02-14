import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Teste de criptografia em Base64 com RSA
 */
public class Main {

    public static void main(String[] args) {

        try {
            KeyPairGenerator k = KeyPairGenerator.getInstance("RSA");
            k.initialize(1024);
            KeyPair kp = k.genKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();
            String pK = new String(Base64.encodeBase64(publicKey.getEncoded()));

            String pubKey = "-----BEGIN PUBLIC KEY-----\n" + pK + "-----END PUBLIC KEY-----\n";
            System.out.println(pubKey);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            String textoOriginal = "teste";

            byte[] bytesCriptografados = cipher.doFinal(textoOriginal.getBytes());
            String textoCriptografadoBase64 = new String(Base64.encodeBase64(bytesCriptografados));

            System.out.println("Texto criptografado em Base64: " + textoCriptografadoBase64);

            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytesDescriptografados = cipher.doFinal(Base64.decodeBase64(textoCriptografadoBase64.getBytes()));
            String textoDescriptogrado = new String(bytesDescriptografados);

            System.out.println("Texto descriptografado: " + textoDescriptogrado);
            System.out.println("Texto original: " + textoOriginal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
