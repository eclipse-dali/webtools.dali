/*******************************************************************************
 *  Copyright (c) 2010, 2013  Oracle. All rights reserved.
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

import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;

/**
 * Represents a JAXB enum constant.  
 * (A constant inside an enum with either an explicit or implicit @XmlEnumValue annotation)
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
public interface JaxbEnumConstant
		extends JaxbContextNode {
	
	JavaResourceEnumConstant getResourceEnumConstant();

	/**
	 * Return the name of the enum constant. This will not change, a
	 * new JaxbEnumConstant will be built if the name changes.
	 */
	String getName();


	// ***** xml enum value *****
	
	/**
	 * Return the enum constant's value, whether specified or default.
	 */
	String getValue();
	
	/**
	 * The default value will be the name of the enum constant.
	 * It will be used if the XmlEnumValue annotation is null.
	 * @see getName()
	 */
	String getDefaultValue();
	
	String SPECIFIED_VALUE_PROPERTY = "specifiedValue"; //$NON-NLS-1$
	
	String getSpecifiedValue();
	
	void setSpecifiedValue(String value);	
}
