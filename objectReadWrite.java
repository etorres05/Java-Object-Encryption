package p4;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;

public class objectReadWrite {
	
	/*Export an object to a file*/
	public static void dataOut(Gradebook gb, String filename)throws IOException{
		FileOutputStream fileOut = new FileOutputStream(new File(filename));
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		
		// Write object to file
		objOut.writeObject(gb);
		
		//close streams
		objOut.close();
		fileOut.close();
	}
	
	public static void dataOut(Gradebook gb, String mac, String filename)
			throws IOException{
		FileOutputStream fileOut = new FileOutputStream(new File(filename));
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		
		// Write object to file
		objOut.writeObject(gb);
		objOut.writeObject(mac);
		
		//close streams
		objOut.close();
		fileOut.close();
	}

	/*Read an object from a file*/
	public static ObjectInputStream dataIn(String filename) throws ClassNotFoundException, IOException{
		FileInputStream fi = new FileInputStream(new File(filename));
		ObjectInputStream oi = new ObjectInputStream(fi);
		return oi;
	}
	
	public static byte[] readLine(String filename) throws IOException
	{
		BufferedReader buf = new BufferedReader(new FileReader(filename));
		String s = buf.readLine();
		buf.close();
		return s.getBytes();
	}
	
}
