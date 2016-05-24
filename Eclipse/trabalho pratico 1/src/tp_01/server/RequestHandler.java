package tp_01.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
class RequestHandler extends Thread {

    private Socket connection;
  
    public RequestHandler(Socket connection) {
        this.connection = connection;
    }


    public void run() {

        BufferedReader is = null;
        PrintWriter os    = null;

        try {
            // circuito virtual estabelecido: socket cliente na variavel newSock
            System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

            is = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            os = new PrintWriter(connection.getOutputStream(), true);

            String inputLine = is.readLine(); 
            
            getMenu();

            System.out.println("Recebi -> " + inputLine);

            os.println("@" + inputLine.toUpperCase());
        }
        catch (IOException e) {
            System.err.println("erro na liga�ao " + connection + ": " + e.getMessage());
        }
        finally {
            // garantir que o socket � fechado
            try {
                if (is != null) is.close();  
                if (os != null) os.close();
                if (connection != null) connection.close();                    
            } catch (IOException e) { }
        }
    } // end run
    
    public String getMenu() {
    	
    	try {
	    	
	    	//Get XML dataBase
	    	File xmlFile = new File("D:/LEIM/4� Semestre/ICD/Eclipse/trabalho pratico 1/bin/tp_01/database/restaurante.xml");
	    	//Create document builder
	    	//TODO Validate xsd
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
		    doc.getDocumentElement().normalize();		
	        System.out.println("Root element :"+doc.getDocumentElement().getNodeName());
	        NodeList nodeList = doc.getElementsByTagName("ementa");
	        System.out.println("----------------------------");
	        	for (int index = 0; index < nodeList.getLength(); index++) {
		            Node nEmenta = nodeList.item(index);
		            System.out.println("\nCurrent Element :"+nEmenta.getNodeName());
		            if (nEmenta.getNodeType() == Node.ELEMENT_NODE) {
		               Element  e_Ementa = (Element) nEmenta;
		               NodeList nl_Serve = e_Ementa.getChildNodes();
		               for (int index2=0; index2 < nl_Serve.getLength(); index2++) {
		            	   Node n_Serve = nl_Serve.item(index2);
		            	   if (n_Serve.getNodeType() == Node.ELEMENT_NODE) {
		            		   Element e_Serve = (Element) n_Serve;
		            		   System.out.println("Pre�o: "+e_Serve.getAttribute("pre�o"));
		            		   NodeList nl_Item = doc.getElementsByTagName("item");
		            		   for (int index3=0; index3 < nl_Item.getLength(); index3++) {
				            	   Node n_Item = nl_Item.item(index3);
				            	   if (n_Item.getNodeType() == Node.ELEMENT_NODE) {
				            		   Element e_Item = (Element) n_Item;
				            		   if (e_Item.getAttribute("iditem").equals(e_Serve.getAttribute("iditem"))) {
				            			   	System.out.println(e_Item.getElementsByTagName("pt-pt").item(0).getTextContent());
				            			   	NodeList nl_ingred = doc.getElementsByTagName("ingrediente");
				            			   	for(int index4=0; index4 < nl_ingred.getLength(); index4++){
				            			   		Node n_ingred = nl_ingred.item(index4);
				            			   		if(n_ingred.getNodeType() == Node.ELEMENT_NODE){
				     		            		   Element e_ingred = (Element) n_ingred;
				     		            		   System.out.println(e_ingred.getChildNodes().getLength());
				     		            		   //TODO Temos 2 elementos com o nome ingrediente 
//				     		            		   if(e_ingred.getAttribute("idingrediente").equals(((Element)e_Item.getElementsByTagName("ingrediente").item(0)).getAttribute("idingrediente"))){
//							            			   System.out.println(e_ingred.getChildNodes().getLength());
//				     		            			   System.out.println(e_ingred.getElementsByTagName("fr-fr").item(0).getTextContent());
//				     		            		   }
				            			   		}
				            			   	}
				            		   }
				            	   }
		            		   }
		            	   }
	            	   }
		            }
	        	}
					
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	return null;
    }
    
    

} // end HandleConnectionThread
