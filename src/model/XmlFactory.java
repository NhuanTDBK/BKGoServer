package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mydropbox.MyDropboxSwing;

import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import controller.ServerUtil;

public class XmlFactory {
	public DomRepresentation dom;
	public SimpleDateFormat format;
	public XmlFactory() {
		dom = MyDropboxSwing.dom;
		format = new  SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	}
	public XmlFactory(DomRepresentation dom) {
		this.dom = dom;
		format = new  SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	}
	public int getFileIdByFileName(String fileName) {
		int fileId = 0;
		String xpath = "//File[@name='" + fileName + "']";
		try {
			Node fileNode = dom.getNode(xpath);
			
			String idStr = fileNode.getAttributes().getNamedItem("id")
					.getNodeValue();
			fileId = Integer.parseInt(idStr);
		} catch (DOMException doms) {
			doms.printStackTrace();
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
		}
		return fileId;
	}
	public void insertFileNode(FileChange fileChange)
	{
		try {
			Document doc = dom.getDocument();
			Element fileNode = null;
			if(fileChange.getIsFile()==Constants.IS_FILE)
			{
				fileNode = doc.createElement("File");
				fileNode.setAttribute("id",Integer.toString(fileChange.getFileId()));
				fileNode.setAttribute("name", fileChange.getFileName());
				String filePath = MyDropboxSwing.urls+"/"+fileChange.getFileName();
				File file = new File(filePath);
				fileNode.setAttribute("signature", ServerUtil.encryptFile(new FileInputStream(filePath)));
				Date date = new Date(file.lastModified());
				fileNode.setAttribute("lastModified",format.format(date));
				fileNode.setAttribute("size",String.valueOf(file.length() / 1024.0));
			}
			else 
			{
				fileNode = doc.createElement("Directory");
				fileNode.setAttribute("name", fileChange.getFileName());
			}
			Node root = doc.getFirstChild();
			root.appendChild(fileNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void deleteNodeByFileId(int fileId)
	{
		try {
			Document doc = dom.getDocument();
			Node fileNode = doc.getElementById(Integer.toString(fileId));
			Node root = doc.getFirstChild();
			root.removeChild(fileNode);
		} catch (IOException |DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveFileXml(Document doc)
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			doc.setXmlStandalone(true);
			DOMSource source = new DOMSource(doc);
			File out_put = new File("xml_demo.xml");
			out_put.createNewFile();
			StreamResult result = new StreamResult(out_put);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File saved!");

	}
//	public static void main(String[] args) {
//		XMLFactory fac = new XMLFactory();
//		System.out.println(fac.getFileIdByFileName("new_name.js"));
//	}
}