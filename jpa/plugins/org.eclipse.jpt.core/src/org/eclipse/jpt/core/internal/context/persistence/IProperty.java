/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperty;

public interface IProperty extends IJpaContextNode
{
	// **************** name ***************************************************
	
	final static String NAME_PROPERTY = "name";
	
	String getName();
	
	void setName(String name);
	
	
	// **************** value **************************************************
	
	final static String VALUE_PROPERTY = "value";
	
	void setValue(String value);
	
	String getValue();
	
	
	// **************** updating ***********************************************
	
	void initialize(XmlProperty property);
	
	void update(XmlProperty property);
}
