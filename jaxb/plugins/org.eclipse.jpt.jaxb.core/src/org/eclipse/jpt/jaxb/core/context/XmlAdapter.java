/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;

/**
 * Represents a java class used as an XmlAdapter
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
public interface XmlAdapter 
		extends JaxbContextNode {
	
	static String MARSHAL_METHOD_NAME = "marshal";  //$NON-NLS-1$
	
	
	// ***** bound type *****
	
	/**
	 * String associated with changes to the "boundType" property
	 */
	static String BOUND_TYPE_PROPERTY = "boundType";  //$NON-NLS-1$
	
	/**
	 * Return the fully qualified bound type name (the type used in the java model)
	 */
	String getBoundType();
	
	
	// ***** value type *****
	
	/**
	 * String associated with changes to the "valueType" property
	 */
	static String VALUE_TYPE_PROPERTY = "valueType";  //$NON-NLS-1$
	
	/**
	 * Return the fully qualified value type name (the type that corresponds to the xml schema)
	 */
	String getValueType();
	
	
	// ***** misc *****
	
	JavaResourceType getJavaResourceType();
}
