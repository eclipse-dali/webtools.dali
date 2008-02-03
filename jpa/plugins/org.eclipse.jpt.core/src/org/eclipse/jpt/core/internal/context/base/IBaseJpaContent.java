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
	
	/**
	 * String constant associated with changes to the persistenceXml property
	 */
	public final static String PERSISTENCE_XML_PROPERTY = "persistenceXml";
	
	/** 
	 * Return the content represented by the persistence.xml file associated with 
	 * this project.
	 * This may be null. 
	 */
	IPersistenceXml getPersistenceXml();
	
	/**
	 * Add a persistence.xml file to this content and return the content associated
	 * with it.
	 * Throws {@link IllegalStateException} if a persistence.xml already exists.
	 */
	IPersistenceXml addPersistenceXml();
	
	/**
	 * Remove the persistence.xml file from this content.
	 * Throws {@link IllegalStateException} if a persistence.xml does not exist.
	 */
	void removePersistenceXml();
}
