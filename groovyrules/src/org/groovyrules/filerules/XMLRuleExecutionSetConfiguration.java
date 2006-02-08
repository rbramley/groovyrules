package org.groovyrules.filerules;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Loads configuration for a <tt>RuleExecutionSet</tt>, from an XML
 * configuration file. Each rule in the set is defined in an individual
 * <tt>.groovyrule</tt> file.
 * <p>
 * This uses a simple <tt>DocumentBuilder</tt> to parse the XML configuration.
 * 
 * @author Rob Newsome
 */
public class XMLRuleExecutionSetConfiguration {

	private String name;

	private String description;

	private String ruleRoot;

	// List of Strings
	private List ruleFiles;

	public XMLRuleExecutionSetConfiguration(InputStream inStream) {

		try {
			
			Document configurationDoc = null;

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			configurationDoc = db.parse(inStream);
			inStream.close();

			// This is the <ruleexecutionset> doc
			Element documentElement = configurationDoc.getDocumentElement();

			parseConfiguration(documentElement);

		} catch (Exception e) {
			throw new RuntimeException(
					"RuleExecutionSetConfiguration unable to parse input stream to XML document",
					e);
		}

	}
	
	public XMLRuleExecutionSetConfiguration(Reader reader) {
		
		try {
			
			Document configurationDoc = null;

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			configurationDoc = db.parse(new InputSource(reader));
			reader.close();

			// This is the <ruleexecutionset> doc
			Element documentElement = configurationDoc.getDocumentElement();

			parseConfiguration(documentElement);

		} catch (Exception e) {
			throw new RuntimeException(
					"RuleExecutionSetConfiguration unable to parse reader to XML document",
					e);
		}
		
	}
	

	public XMLRuleExecutionSetConfiguration(Element documentElement) {

		parseConfiguration(documentElement);

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

	private final void parseConfiguration(Element documentElement) {

		try {

			// Create a list for the rules
			ruleFiles = new ArrayList();

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
						for (int j = 0; j < ruleNodeList.getLength(); j++) {
							Node ruleNode = ruleNodeList.item(j);
							if (ruleNode.getNodeName().equals("rule")) {
								String rule = ruleNode.getFirstChild()
										.getNodeValue();
								ruleFiles.add(rule);
							}
						}
					} else {
						// Unknown config node - ignore
					}
				}
			}
		} catch (Exception ex) {

			throw new RuntimeException(
					"Unable to parse configuration XML as a valid RuleExecutionSet configuration",
					ex);

		}
	}

}