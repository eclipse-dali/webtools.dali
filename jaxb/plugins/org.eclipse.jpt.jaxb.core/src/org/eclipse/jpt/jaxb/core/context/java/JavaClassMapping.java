package org.eclipse.jpt.jaxb.core.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;

public interface JavaClassMapping
		extends JavaTypeMapping, JaxbClassMapping {
	
	// ***** overrides *****
	
	JavaClass getJavaType();
	
	
	// ***** attributes *****
	
	/** string associated with changes to the collection of attributes on this class mapping */
	String ATTRIBUTES_COLLECTION = "attributes"; //$NON-NLS-1$
	
	/** string associated with changes to the collection of included attributes on this class mapping */
	String INCLUDED_ATTRIBUTES_COLLECTION = "includedAttributes"; //$NON-NLS-1$
}
