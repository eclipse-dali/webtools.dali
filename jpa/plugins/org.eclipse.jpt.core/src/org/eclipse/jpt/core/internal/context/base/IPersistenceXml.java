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

import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;

public interface IPersistenceXml extends IJpaContextNode, IJpaStructureNode
{
	// **************** persistence *******************************************
	
	/**
	 * String constant associated with changes to the persistence property
	 */
	public final static String PERSISTENCE_PROPERTY = "persistence";
	
	/** 
	 * Return the content represented by the root of the persistence.xml file.
	 * This may be null.
	 */
	IPersistence getPersistence();
	
	/**
	 * Add a persistence node to the persistence.xml file and return the object 
	 * representing it.
	 * Throws {@link IllegalStateException} if a persistence node already exists.
	 */
	IPersistence addPersistence();
	
	/**
	 * Remove the persistence node from the persistence.xml file.
	 * Throws {@link IllegalStateException} if a persistence node does not exist.
	 */
	void removePersistence();
	
	
	// **************** updating **********************************************
	
	void initialize(PersistenceResource persistenceResource);
	
	void update(PersistenceResource persistenceResource);
}
