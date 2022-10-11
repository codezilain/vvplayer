package co.virendra.smart.vvplayer.manager;

import android.content.Context;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import co.virendra.smart.vvplayer.detail.CryptoDetail;

/**
 * Created by Virenddra on 10/10/2022
 */

public class CryptoManager
{
    private static String BASIC_CIPHER_KEY="Virendra*76ERDF$";
    public static final String AES_ALGORITHM = "AES";
    public static final String AES_TRANSFORMATION = "AES/CTR/NoPadding";

    private static CryptoDetail cryptoDetail;

    public static CryptoDetail getInstance(Context context)
    {
        if(cryptoDetail == null)
        {
            cryptoDetail = new CryptoDetail();
            try {
                byte[] key;
                if(PrefManager.getInstance(context).getCryptoKey().isEmpty())
                {
                    key = CryptoManager.secretToken();
                    String hex =  Base64.encodeToString(key, Base64.URL_SAFE);
                    PrefManager.getInstance(context).setCryptoKey(hex);
                }else
                {
                    key = Base64.decode(PrefManager.getInstance(context).getCryptoKey(), Base64.URL_SAFE);
                }
                SecretKeySpec  mSecretKeySpec = new SecretKeySpec(key, AES_ALGORITHM);
                IvParameterSpec mIvParameterSpec = new IvParameterSpec(key);

                Cipher encryptCipher = Cipher.getInstance(AES_TRANSFORMATION);
                encryptCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, mIvParameterSpec);

                Cipher decryptCipher = Cipher.getInstance(AES_TRANSFORMATION);
                decryptCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec, mIvParameterSpec);

                cryptoDetail.setSecretKeySpec(mSecretKeySpec);
                cryptoDetail.setIvParameterSpec(mIvParameterSpec);
                cryptoDetail.setEncryptCipher(encryptCipher);
                cryptoDetail.setDecryptCipher(decryptCipher);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return cryptoDetail;
    }

    /**
     * Get the device id as scret key
     * important this is just for sample purpose yoy can make you own logic for this
     * */
    public static byte[] secretToken()
    {
        byte[] byteArray = BASIC_CIPHER_KEY.getBytes();
        System.out.println(byteArray.length);
        System.out.println(byteArray.toString());
        return byteArray;
    }

}
