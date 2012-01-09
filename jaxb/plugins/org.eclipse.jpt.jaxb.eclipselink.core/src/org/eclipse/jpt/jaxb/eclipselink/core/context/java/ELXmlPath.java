/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.java;


public interface ELXmlPath {
	
	// ***** value *****
	
	/**
	 * String associated with changes to the value property
	 */
	String VALUE_PROPERTY = "value";  ///$NON-NLS-1$
	
	/**
	 * Return the value property value.
	 * A null indicates it is not specified.
	 */
	String getValue();
	
	/**
	 * Set the value property value.
	 * Null unspecifies the value.
	 */
	void setValue(String value);
}
