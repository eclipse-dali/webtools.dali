/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * 
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.0
 */
public interface JaxbPackageInfo
		extends JavaContextNode, XmlAccessTypeHolder, XmlAccessOrderHolder {
	
	JavaResourcePackage getResourcePackage();
	
	JaxbPackage getJaxbPackage();
	
	
	// ***** XmlSchema *****
	
	/**
	 * Return the XML schema for this package info, this will not be null.
	 */
	XmlSchema getXmlSchema();
	
	
	// ***** XmlSchemaTypes *****
	
	String XML_SCHEMA_TYPES_LIST = "xmlSchemaTypes"; //$NON-NLS-1$
	
	ListIterable<XmlSchemaType> getXmlSchemaTypes();
	
	int getXmlSchemaTypesSize();
	
	XmlSchemaType addXmlSchemaType(int index);
	
	void removeXmlSchemaType(int index);
	
	void removeXmlSchemaType(XmlSchemaType xmlSchemaType);
	
	void moveXmlSchemaType(int targetIndex, int sourceIndex);
		
	
	// ***** XmlJavaTypeAdapters ******
	
	String XML_JAVA_TYPE_ADAPTERS_LIST = "xmlJavaTypeAdapters"; //$NON-NLS-1$
	
	ListIterable<XmlJavaTypeAdapter> getXmlJavaTypeAdapters();
	
	int getXmlJavaTypeAdaptersSize();
	
	XmlJavaTypeAdapter addXmlJavaTypeAdapter(int index);
	
	void removeXmlJavaTypeAdapter(int index);
	
	void removeXmlJavaTypeAdapter(XmlJavaTypeAdapter xmlJavaTypeAdapter);
	
	void moveXmlJavaTypeAdapter(int targetIndex, int sourceIndex);
	
	/**
	 * return an {@link XmlJavaTypeAdapter} for the given bound type name, if one exists
	 */
	XmlJavaTypeAdapter getXmlJavaTypeAdapter(String boundTypeName);
	
	
	// ***** misc *****
	
	String getNamespaceForPrefix(String prefix);
	
	
	// ***** validation *****
	
	/**
	 * Add validation messages to the specified list.
	 */
	void validate(List<IMessage> messages, IReporter reporter);
}
