/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;


/**
 * Represents a JAXB persistent enum.  
 * (A enum with either an explicit or implicit @XmlEnum annotation)
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
public interface JaxbPersistentEnum
		extends JaxbPersistentType {

	/**
	 * covariant override
	 */
	JavaResourceEnum getJavaResourceType();

	/**************** enum type *****************/

	/**
	 * enum type corresponds to the XmlEnum annotation value element
	 */
	String getEnumType();
	void setEnumType(String enumType);
	String ENUM_TYPE_PROPERTY = "enumType"; //$NON-NLS-1$
		String DEFAULT_ENUM_TYPE = "java.lang.String"; //$NON-NLS-1$


	/********** enum constants **********/

	Iterable<JaxbEnumConstant> getEnumConstants();
	int getEnumConstantsSize();
		String ENUM_CONSTANTS_COLLECTION = "enumConstants"; //$NON-NLS-1$

}
