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

import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.xsd.util.XSDUtil;

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
public interface XmlSchemaType
		extends JavaContextNode {
	
	String DEFAULT_NAMESPACE = XSDUtil.SCHEMA_FOR_SCHEMA_URI_2001;
	
	XmlSchemaTypeAnnotation getXmlSchemaTypeAnnotation();
	
	JaxbSchemaComponentRef getSchemaTypeRef();
	
	
	/**************** type *****************/
	
	/**
	 * Corresponds to the XmlSchemaType annotation 'type' element
	 */
	String getType();
	void setType(String type);
		String TYPE_PROPERTY = "type"; //$NON-NLS-1$
		String DEFAULT_TYPE = "javax.xml.bind.annotation.XmlSchemaType.DEFAULT"; //$NON-NLS-1$
}
