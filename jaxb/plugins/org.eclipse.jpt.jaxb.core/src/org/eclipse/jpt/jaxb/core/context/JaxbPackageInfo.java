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
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
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
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPackageInfo
		extends JavaContextNode, XmlAccessTypeHolder, XmlAccessOrderHolder {
	
	JavaResourcePackage getResourcePackage();

	JaxbPackage getParent();

	// ********** xml schema **********

	/**
	 * Return the XML schema for this package info, this will not be null.
	 */
	XmlSchema getXmlSchema();


	// ********** xml schema types **********

	ListIterable<XmlSchemaType> getXmlSchemaTypes();
	int getXmlSchemaTypesSize();
	XmlSchemaType addXmlSchemaType(int index);
	void removeXmlSchemaType(int index);
	void removeXmlSchemaType(XmlSchemaType xmlSchemaType);
	void moveXmlSchemaType(int targetIndex, int sourceIndex);
		String XML_SCHEMA_TYPES_LIST = "xmlSchemaTypes"; //$NON-NLS-1$

	
	// ********** xml java type adapters **********

	ListIterable<XmlJavaTypeAdapter> getXmlJavaTypeAdapters();
	int getXmlJavaTypeAdaptersSize();
	XmlJavaTypeAdapter addXmlJavaTypeAdapter(int index);
	void removeXmlJavaTypeAdapter(int index);
	void removeXmlJavaTypeAdapter(XmlJavaTypeAdapter xmlJavaTypeAdapter);
	void moveXmlJavaTypeAdapter(int targetIndex, int sourceIndex);
		String XML_JAVA_TYPE_ADAPTERS_LIST = "xmlJavaTypeAdapters"; //$NON-NLS-1$


	// **************** validation ********************************************
	
	/**
	 * Add validation messages to the specified list.
	 */
	void validate(List<IMessage> messages, IReporter reporter);

}
