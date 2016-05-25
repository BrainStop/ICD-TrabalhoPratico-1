package tp_01.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
class RequestHandler extends Thread {

    private Socket connection;
    BufferedReader is = null;
    PrintWriter os    = null;

  
    public RequestHandler(Socket connection) {
        this.connection = connection;
    }
   
    public void run() {


        try {
            // circuito virtual estabelecido: socket cliente na variavel newSock
            System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

            is = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            os = new PrintWriter(connection.getOutputStream(), true);

            String inputLine = is.readLine(); 
                        
            System.out.println("Recebi -> " + inputLine);

            os.println(getMenu("pt-pt"));
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
    
    public String getMenu(String ling) {
    	if(!ling.equals("pt-pt") && !ling.equals("en-uk") && !ling.equals("fr-fr")) {
    		return "Error 'ling' variable invalid value: "+ling;
    	}
    	

    	String result = "";
    	try {
	    	//Get XML dataBase
    		//TODO Make relative path
	    	File xmlFile = new File("D:/LEIM/4� Semestre/ICD/Eclipse/trabalho pratico 1/bin/tp_01/database/restaurante.xml");
	    	//Create document builder
	    	//TODO Validate xsd
	    	String[] modoTipo = getMenuType();
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
		    doc.getDocumentElement().normalize();
	        NodeList nodeList = doc.getElementsByTagName("ementa");
	        	for (int index = 0; index < nodeList.getLength(); index++) {
		            Node nEmenta = nodeList.item(index);
		            result += "############################################";
		            if (nEmenta.getNodeType() == Node.ELEMENT_NODE) {
		               Element  e_Ementa = (Element) nEmenta;
		               System.out.println(e_Ementa.getAttribute("modo")+" "+modoTipo[1]+"\n"+e_Ementa.getAttribute("tipo")+" "+modoTipo[0]);
		               if (e_Ementa.getAttribute("modo").equals(modoTipo[1]) &&
	            		   e_Ementa.getAttribute("tipo").equals(modoTipo[0])) {
		            	   System.out.println("entrou");
			               NodeList nl_Serve = e_Ementa.getChildNodes();
			               for (int index2=0; index2 < nl_Serve.getLength(); index2++) {
			            	   Node n_Serve = nl_Serve.item(index2);
			            	   if (n_Serve.getNodeType() == Node.ELEMENT_NODE) {
			            		   Element e_Serve = (Element) n_Serve;
			            		   NodeList nl_Item = doc.getElementsByTagName("item");
			            		   for (int index3=0; index3 < nl_Item.getLength(); index3++) {
					            	   Node n_Item = nl_Item.item(index3);
					            	   if (n_Item.getNodeType() == Node.ELEMENT_NODE) {
					            		   Element e_Item = (Element) n_Item;
					            		   if (e_Item.getAttribute("iditem").equals(e_Serve.getAttribute("iditem"))) {
					            			   result += "Prato: "+e_Item.getElementsByTagName(ling).item(0).getTextContent();
					            			   	NodeList nl_IngrRef = e_Item.getElementsByTagName("ingrediente");
					            			   	for(int index4=0; index4 < nl_IngrRef.getLength(); index4++){
					            			   		Node n_IngrRef = nl_IngrRef.item(index4);
					            			   		if(n_IngrRef.getNodeType() == Node.ELEMENT_NODE){
					            			   			Element e_IngrRef = (Element) n_IngrRef;
					            			   			NodeList nl_IngrID = doc.getElementsByTagName("ingredientes").item(0).getChildNodes();
					            			   			for(int index5=0; index5 < nl_IngrID.getLength(); index5++) {
					            			   				Node n_IngrID = nl_IngrID.item(index5);
					            			   				if(n_IngrID.getNodeType() == Node.ELEMENT_NODE) {
					            			   					Element e_IngrID = (Element) n_IngrID;
					            			   					if(e_IngrID.getAttribute("idingrediente").equals(e_IngrRef.getAttribute("idingrediente"))) {
					            			   						result += "Ingredientes: "+e_IngrID.getElementsByTagName(ling).item(0).getTextContent();
					            			   					}
					            			   				}
					            			   			}
					            			   		}
					            			   	}
					            		   }
					            	   }
			            		   }
			            		   result += "Pre�o: "+e_Serve.getAttribute("pre�o");
			            		   result += "----------";
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
    	//TODO introduzir new line na string
    	return result;
    }
    
    private String[] getMenuType() {
    	String[][] dates = {{"1","1"},{"3","25"},{"3","27"},{"4","25"},
    		 	{"5","1"},{"5","26"},{"6","10"},{"8","15"},{"10","5"},
    			{"11","1"},{"12","1"},{"12","8"},{"12","25"}};
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd");
    	Calendar     calendar = Calendar.getInstance();
    	String  currentDate[] = dateFormat.format(calendar.getTime()).split("/");
    	int dayOfWeek         = calendar.get(Calendar.DAY_OF_WEEK);
    	DateFormat timeFormat = new SimpleDateFormat("HH");
    	System.out.println(timeFormat.format(calendar.getTime()));
    	int time = Integer.parseInt(timeFormat.format(calendar.getTime()));
    	String[] result = new String[2];
    	if( time > 15 )
    		result[0] = "jantar";
    	else
    		result[0] = "almo�o";
    	
    	if(dayOfWeek == 1 || dayOfWeek == 7)
    		result[1] = "fds-feriado";
    	else
    		result[1] = "dia�til";
 
    	for (String[] d : dates) {
    		if (d.equals(currentDate))
    			result[1] = "fds-feriado";
    	}
    	return result;
    }

} // end HandleConnectionThread
