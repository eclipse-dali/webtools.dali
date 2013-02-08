/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum;

public interface OxmXmlEnum
		extends OxmTypeMapping, JaxbEnumMapping {
	
	public EXmlEnum getETypeMapping();
	
	public JavaEnum getJavaType();
	
	
	// ***** java enum  *****
	
	/**
	 * String associated with changes to the "specifiedJavaEnum" property
	 */
	String SPECIFIED_JAVA_ENUM_PROPERTY = "specifiedJavaEnum"; //$NON-NLS-1$
	
	/**
	 * Return the java enum specified in source
	 */
	String getSpecifiedJavaEnum();
	
	/**
	 * Set the java enum specified in source
	 */
	void setSpecifiedJavaEnum(String javaEnum);
	
	
	// ***** value *****
	
	final String DEFAULT_VALUE_PROPERTY = "defaultValue";  //$NON-NLS-1$
	
	String getDefaultValue();
}
