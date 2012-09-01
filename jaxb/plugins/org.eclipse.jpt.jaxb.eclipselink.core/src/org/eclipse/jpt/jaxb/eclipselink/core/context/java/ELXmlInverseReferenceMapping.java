package org.eclipse.jpt.jaxb.eclipselink.core.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;

/**
 * Represents an Oxm XmlInverseReference attribute mapping in either
 * java annotations or oxm file.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface ELXmlInverseReferenceMapping
		extends JaxbAttributeMapping {
	
	// ***** mappedBy *****
	
	/**
	 * String associated with changes to the mappedBy property
	 */
	String MAPPED_BY_PROPERTY = "mappedBy";  ///$NON-NLS-1$
	
	/**
	 * Return the mappedBy property value.
	 * A null indicates it is not specified.
	 */
	String getMappedBy();
	
	/**
	 * Set the mappedBy property value.
	 * Setting to null will unspecify it.
	 */
	void setMappedBy(String mappedBy);
}
