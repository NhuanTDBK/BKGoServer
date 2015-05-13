package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ServerUtil {
	public static String encryptMessage(String input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(input.getBytes());
		byte[] output = md.digest();
		return bytesToHex(output);
	}
	public static String encryptFile(FileInputStream input) throws NoSuchAlgorithmException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte [] dataToRead = new byte [1024];
		int nRead = 0;
		while((nRead=input.read(dataToRead))!=-1)
		{
			md.update(dataToRead,0,nRead);
		}
		byte[] output = md.digest();
		return bytesToHex(output);
	}
	public static String bytesToHex(byte[] b) {

	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	       buf.append(Integer.toHexString(0xFF&b[j]));
	      }
	      return buf.toString();
	 }
	public static ArrayList<String>getToken(String element)
	{
		ArrayList<String> lstToken = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(element, Constants.DELIMITER);
		while(token.hasMoreTokens())
		{
			lstToken.add(token.nextToken());
		}
		return lstToken;
	}
}
