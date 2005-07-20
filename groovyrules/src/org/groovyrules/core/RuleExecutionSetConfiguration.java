package org.groovyrules.core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Loads configuration for a <tt>RuleExecutionSet</tt>, from an XML 
 * configuration file. Each rule in the set is defined in an 
 * individual <tt>.groovyrule</tt> file. 
 * <p>
 * This uses a simple <tt>DocumentBuilder</tt> to parse the XML
 * configuration.
 * 
 * @author Rob Newsome
 */
public class RuleExecutionSetConfiguration {

	private String name;
	private String description;
	private String ruleRoot;
	// List of Strings
	private List ruleFiles;
	
	public RuleExecutionSetConfiguration(InputStream inStream) {
		parseConfiguration(inStream);
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getRuleRoot() {
		return ruleRoot;
	}
	
	public List getRuleFiles() {
		return ruleFiles;
	}
	
	private final void parseConfiguration(InputStream inStream) {

		try {

			Document configurationDoc = null;

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			configurationDoc = db.parse(inStream);
			inStream.close();

			// Create a list for the rules
			ruleFiles = new ArrayList();
			
			// This is the <ruleexecutionset> doc
			Element documentElement = configurationDoc.getDocumentElement();
			NodeList confNodeList = documentElement.getChildNodes();

			for (int i = 0; i < confNodeList.getLength(); i++) {
				
				Node childNode = confNodeList.item(i);
				if (childNode != null) {
					String nodeName = childNode.getNodeName();

					if (nodeName.equals("name")) {
						name = childNode.getFirstChild().getNodeValue();
					} else if (nodeName.equals("description")) {
						description = childNode.getFirstChild().getNodeValue();
					} else if (nodeName.equals("ruleroot")) {
						ruleRoot = childNode.getFirstChild().getNodeValue();
					} else if (nodeName.equals("rules")) {
						// Now get all the rules...
						NodeList ruleNodeList = childNode.getChildNodes();
						for(int j=0; j<ruleNodeList.getLength(); j++) {
							Node ruleNode = ruleNodeList.item(j);
							if(ruleNode.getNodeName().equals("rule")) {
								String rule = ruleNode.getFirstChild().getNodeValue();
								ruleFiles.add(rule);
							}
						}	
					} else {
						// Unknown config node - ignore
					}
				}
			}
		} catch (Exception ex) {
			// @ TODO 
			throw new RuntimeException(ex);
		}
	}
	
}
