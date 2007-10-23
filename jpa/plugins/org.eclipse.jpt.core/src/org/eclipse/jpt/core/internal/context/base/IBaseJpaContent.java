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

import org.eclipse.jpt.core.internal.IContextModel;

public interface IBaseJpaContent extends IJpaContextNode, IContextModel
{
	// **************** persistence xml ***************************************
	
	IPersistenceXml getPersistenceXml();
	
	void setPersistenceXml(IPersistenceXml persistenceXml);
	
	public final static String PERSISTENCE_XML_PROPERTY = "persistenceXml";
}
