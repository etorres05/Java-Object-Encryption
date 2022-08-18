package p4;
import javax.crypto.*;

import java.lang.Exception;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.IOException;

/**
 * Initialize gradebook with specified name and generate a key.
 */
public class setup{
	
  /* test whether the file exists */
  private static boolean file_test(String filename) {
    //TODO complete
	File f = new File(filename);
    return (f.isFile() || f.exists());
  }

  public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
    String key;

    if (args.length < 2) {
      System.out.println("Usage: setup <logfile pathname>");
      System.exit(1);
    }

  /* test if file exists */
    String file = args[1];
	if(file_test(file)) {
		System.out.println("invalid");
		System.exit(255);
	}	
	
	/*test that filename contains only appropriate characters*/
	if (!file.matches("[A-Za-z0-9_.]+")) {
		System.out.println("invalid");
		System.exit(255);
	}
	
	/*get secret key*/
    key = EncryptDecrypt.getKey();
    
    /*Create Gradebook object*/
    Gradebook gb = new Gradebook(file);
    objectReadWrite.dataOut(gb, file);
    byte[] macM = objectReadWrite.readLine(file);
	try {
		String macFinal = EncryptDecrypt.makeMac(macM, key);
		objectReadWrite.dataOut(gb, macFinal, file);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("invalid");
		System.exit(255);
	}
    File objFile = new File(file);
    
    //Encrypt gradebook
    try {
		EncryptDecrypt.encrypt(objFile, file, key);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("invalid");
		System.exit(255);
	}
    
    System.out.println("Key is: " + key);

    return;
  }
  
}
