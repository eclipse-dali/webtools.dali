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
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;

public interface IPersistenceXml extends IJpaContextNode
{
	void initialize(PersistenceResourceModel persistenceResource);

	// **************** persistence *******************************************
	
	IPersistence getPersistence();
	
	void setPersistence(IPersistence persistence);
	
	public final static String PERSISTENCE_PROPERTY = "persistence";
	
	
	// **************** updating **********************************************
	
	void update(PersistenceResourceModel persistenceResource);
}
