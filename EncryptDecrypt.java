package p4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

public class EncryptDecrypt {
	
	public static String getKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecretKey k = keyGenerator.generateKey();
		String encodedKey = Base64.getEncoder().encodeToString(k.getEncoded());
		return encodedKey;
        
	}
	
	private static SecretKey decodeKey(String encodedKey) {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		// rebuild key using SecretKeySpec
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		
		return originalKey;
	}
	
	/*Encrypt file*/
	public static void encrypt(File file, String fileName, String encodedKey) throws Exception {
		
		//decode key
		SecretKey secret = null;
		try {
			secret = decodeKey(encodedKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid");
			System.exit(255);
		}
		
		//get file to byte array
		byte[] fileArray = fileToByte(file);
		
		//create cipher object
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        
        //Initialize cipher to encryption mode
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        
        //encrypt byte array and save to file
        byte[] encryptedText = cipher.doFinal(fileArray);
        byteToFile(fileName, encryptedText);

    }
	/*Decrypt File*/
	public static void decrypt(File file, String fileName, String encodedKey) throws Exception {
		
		//decode key
		SecretKey secret = null;
		try {
			secret = decodeKey(encodedKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid");
			System.exit(255);
		}
				
		//get file to byte array
		byte[] fileArray = fileToByte(file);
		
		//create cipher object
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        
        //Initialize cipher to encryption mode
        cipher.init(Cipher.DECRYPT_MODE, secret);
        
        //Decrypt byte array and save file
        byte[] decryptedText = cipher.doFinal(fileArray);
        byteToFile(fileName, decryptedText);

    }
	
	public static String makeMac(byte[] msg, String encodedKey) throws Exception
	{
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(decodeKey(encodedKey));
		return new String (mac.doFinal(msg));
	}
	
	/*read file to byte array - used in encrypt/decrypt methods*/
	public static byte[] fileToByte(File file)
	        throws IOException
	    {
	        // read from a file
	        FileInputStream fis = new FileInputStream(file);
	  
	        // create byte array
	        byte[] arr = new byte[(int)file.length()];
	  
	        // Read and close file input
	        fis.read(arr);
	        fis.close();
	  
	        // Returning above byte array
	        return arr;
	    }
	
	/*Write byte array back to file*/
	public static void byteToFile(String path, byte [] data) throws IOException {

        try(FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(data);
        }

    }
	
}
