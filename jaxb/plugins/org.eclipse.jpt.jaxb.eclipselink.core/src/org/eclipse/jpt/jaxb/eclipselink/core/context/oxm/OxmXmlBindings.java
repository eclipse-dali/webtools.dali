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

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlAccessOrderHolder;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlAccessTypeHolder;

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
		extends JaxbContextNode, ELXmlAccessTypeHolder, ELXmlAccessOrderHolder {
	
	// ***** mapping metadata complete *****
	
	final static String XML_MAPPING_METADATA_COMPLETE_PROPERTY = "xmlMappingMetadataComplete"; //$NON-NLS-1$
	
	boolean isXmlMappingMetadataComplete();
	
	void setXmlMappingMetadataComplete(boolean newValue);
	
	
	// ***** package name *****
	
	final static String PACKAGE_NAME_PROPERTY = "packageName"; //$NON-NLS-1$
	
	String getPackageName();
	
	void setPackageName(String packageName);
	
	/**
	 * Return a qualified name for the given child type
	 */
	String getQualifiedName(String childTypeName);
	
	
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
}
