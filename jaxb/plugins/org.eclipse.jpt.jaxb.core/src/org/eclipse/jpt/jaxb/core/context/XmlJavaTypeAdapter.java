/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

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
public interface XmlJavaTypeAdapter
	extends 
		JaxbContextNode
{

	XmlJavaTypeAdapterAnnotation getResourceXmlJavaTypeAdapter();

	/**************** value *****************/

	String getValue();

	void setValue(String value);
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$

	/**************** type *****************/

	String getType();

	String getSpecifiedType();
	void setSpecifiedType(String type);
		String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$

	String getDefaultType();
		String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
		String DEFAULT_TYPE = "javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT"; //$NON-NLS-1$
}
