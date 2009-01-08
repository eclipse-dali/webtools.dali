/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Property extends XmlContextNode
{
	// **************** name ***************************************************
	
	final static String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	String getName();
	
	void setName(String name);
	
	
	// **************** value **************************************************
	
	final static String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	void setValue(String value);
	
	String getValue();
	
	
	// **************** updating ***********************************************
		
	/**
	 * Update the Property context model object to match the XmlProperty 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlProperty property);
}
