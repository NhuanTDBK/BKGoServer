package service;

import java.io.IOException;

import mydropbox.MyDropboxSwing;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.restlet.Request;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class DeleteService {
	public static void deleteFileById(int fileId)
	{
		String URL = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/file/"+fileId;
		HttpDelete deleteHTTP = new HttpDelete(URL);
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = client.execute(deleteHTTP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void deleteFileByFileName(String fileName)
	{
		String URL = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/file/"+fileName;
		DomRepresentation dom=null;
		try {
			dom = new DomRepresentation();
			Document doc = dom.getDocument();
			Node fileDeleteNode = doc.createElement("File");
			
			Node fileNameNode = doc.createElement("FileName");
			fileNameNode.setTextContent(fileName);
			fileDeleteNode.appendChild(fileNameNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientResource client = new ClientResource(URL);
		try{
			client.delete();
			System.out.println("Delete thanh cong");
		}
		catch(Exception ex)
		{
			System.out.println("Delete loi");
			ex.printStackTrace();
		}
	}
	public static void deleteFileByFileName(String fileName,String tid)
	{
		String URL = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/file/"+fileName+"@"+tid;
		ClientResource client = new ClientResource(URL);
		try{
			client.delete();
			System.out.println("Delete thanh cong");
		}
		catch(Exception ex)
		{
			System.out.println("Delete loi");
			ex.printStackTrace();
		}
	}
	public static void deleteFileByFileName(int fileId,String tid)
	{
		String URL = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/file/"+fileId+"@"+tid;
		ClientResource client = new ClientResource(URL);
		try{
			client.delete();
			System.out.println("Delete thanh cong");
		}
		catch(Exception ex)
		{
			System.out.println("Delete loi");
			ex.printStackTrace();
		}
	}
//	public static void main(String [] args)
//	{
//		deleteFileByFileName(60, "2");
//	}
}