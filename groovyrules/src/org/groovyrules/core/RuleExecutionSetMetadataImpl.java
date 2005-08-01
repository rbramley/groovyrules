package org.groovyrules.core;

import javax.rules.RuleExecutionSetMetadata;

/**
 * Implementation of metadata for a RuleExecutionSet. This is
 * currently very limited.
 * 
 * @author Rob Newsome
 */
public class RuleExecutionSetMetadataImpl implements RuleExecutionSetMetadata {

	private String name;
	private String description;
	private String uri;
	
	RuleExecutionSetMetadataImpl(String name, String description, String uri) {
		this.name = name;
		this.description = description;
		this.uri = uri;
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleExecutionSetMetadata#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see javax.rules.RuleExecutionSetMetadata#getName()
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see javax.rules.RuleExecutionSetMetadata#getUri()
	 */
	public String getUri() {
		return uri;
	}
	
}
