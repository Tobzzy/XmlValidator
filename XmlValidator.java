/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toyib;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author AHMED
 */
public class XmlValidator {
    
    public static void main(String[] args) {
        
        String xmlFileName = "C:\\Users\\OMITOBISAM\\Desktop\\books.xml";
        String xsdFileName = "C:\\Users\\OMITOBISAM\\Desktop\\books.xsd";
        try {
            File xsdFile = new File(xsdFileName);
            File xmlFile = new File(xmlFileName);
            Source xmlSourceFile = new StreamSource(xmlFile);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlSourceFile);
            
            System.out.println("'"+ xmlFileName + "' is valid");
            
           DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

	Document doc = dBuilder.parse(xmlFile);

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	if (doc.hasChildNodes()) {

		printNote(doc.getChildNodes());

	}
        } catch(org.xml.sax.SAXParseException spex) {
            System.out.println("'"+ xmlFileName + "' is not valid");
            //Logger.getLogger(XmlValidator.class.getName()).log(Level.SEVERE, null, spex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(XmlValidator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XmlValidator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlValidator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
  private static void printNote(NodeList nodeList) {

    for (int count = 0; count < nodeList.getLength(); count++) {

	Node tempNode = nodeList.item(count);

	// make sure it's element node.
	if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

		// get node name and value
		System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
		System.out.println("Node Value =" + tempNode.getTextContent());

		if (tempNode.hasAttributes()) {

			// get attributes names and values
			NamedNodeMap nodeMap = tempNode.getAttributes();

			for (int i = 0; i < nodeMap.getLength(); i++) {

				Node node = nodeMap.item(i);
				System.out.println("attr name : " + node.getNodeName());
				System.out.println("attr value : " + node.getNodeValue());

			}

		}

		if (tempNode.hasChildNodes()) {
			// loop again if has child nodes
			printNote(tempNode.getChildNodes());
		}

		System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
	}

    }

  }

}
