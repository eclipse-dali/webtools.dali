/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrderHolder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessTypeHolder;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface OxmXmlBindings
		extends JaxbContextNode, XmlAccessTypeHolder, XmlAccessOrderHolder {
	
	EXmlBindings getEXmlBindings();
	
	OxmFile getOxmFile();
	
	
	// ***** mapping metadata complete *****
	
	final static String XML_MAPPING_METADATA_COMPLETE_PROPERTY = "xmlMappingMetadataComplete"; //$NON-NLS-1$
	
	boolean isXmlMappingMetadataComplete();
	
	void setXmlMappingMetadataComplete(boolean newValue);
	
	
	// ***** package name *****
	
	/** string associated with specifiedPackageName property */
	final static String SPECIFIED_PACKAGE_NAME_PROPERTY = "specifiedPackageName"; //$NON-NLS-1$
	
	/** return the package name specified on the xml-bindings node */
	String getSpecifiedPackageName();
	
	/** set the package name on the xml-bindings node */
	void setSpecifiedPackageName(String packageName);
	
	/** string associated with impliedPackageName property */
	final static String IMPLIED_PACKAGE_NAME_PROPERTY = "impliedPackageName"; //$NON-NLS-1$
	
	/** return the package name implied by querying java types included in this document */
	String getImpliedPackageName();
	
	/** return the specified package name if specified, otherwise the implied package name */
	String getPackageName();
	
	/** Return a qualified name for the given (qualified or unqualified) child type name, 
	 *  prepending a package name if applicable */
	String getQualifiedName(String childTypeName);
	
	
	// ***** xml schema *****
	
	OxmXmlSchema getXmlSchema();
	
	
	// ***** java types *****
	
	final static String JAVA_TYPES_LIST = "javaTypes"; //$NON-NLS-1$
	
	ListIterable<OxmJavaType> getJavaTypes();
	
	int getJavaTypesSize();
	
	OxmJavaType getJavaType(int index);
	
	OxmJavaType addJavaType(int index);
	
	void removeJavaType(int index);
	
	/**
	 * Return the first java type with the given qualified name
	 */
	OxmJavaType getJavaType(String qualifiedName);
	
	
	// ***** misc *****
	
	Iterable<OxmTypeMapping> getTypeMappings();
	
	/**
	 * Return *first* type mapping with the given name
	 */
	OxmTypeMapping getTypeMapping(String typeName);
}
