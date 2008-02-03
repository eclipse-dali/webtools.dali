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
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.OrmResource;

public interface OrmXml extends IJpaContextNode
{
	// **************** persistence *******************************************
	
	/**
	 * String constant associated with changes to the entity-mappings property
	 */
	public final static String ENTITY_MAPPINGS_PROPERTY = "entityMappingsProperty";
	
	/** 
	 * Return the content represented by the root of the orm.xml file.
	 * This may be null.
	 */
	EntityMappings getEntityMappings();
	
	/**
	 * Add a entity-mappings node to the orm.xml file and return the object 
	 * representing it.
	 * Throws {@link IllegalStateException} if a entity-mappings node already exists.
	 */
	EntityMappings addEntityMappings();
	
	/**
	 * Remove the entity-mappings node from the orm.xml file.
	 * Throws {@link IllegalStateException} if a persistence node does not exist.
	 */
	void removeEntityMappings();
	
	PersistenceUnitDefaults persistenceUnitDefaults();
	
	/**
	 * Return the XmlPersistentType listed in this mapping file
	 * with the given fullyQualifiedTypeName.  Return null if non exists.
	 */
	XmlPersistentType persistentTypeFor(String fullyQualifiedTypeName);

	// **************** updating **********************************************
	
	void initialize(OrmResource ormResource);
	
	void update(OrmResource ormResource);
}
