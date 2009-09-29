/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;

/**
 * Common protocol for JPA objects that have a context, as opposed to
 * resource objects.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaContextNode
	extends JpaNode
{
	/**
	 * Return the resource type of the context node's resource.
	 */
	JpaResourceType getResourceType();
	
	/**
	 * Return the persistence unit if the context node is within a 
	 * persistence.xml context.  Otherwise return null.
	 */
	PersistenceUnit getPersistenceUnit();
	
	/**
	 * Return the mapping file root object if the context node is within a 
	 * mapping file context.  Otherwise return null.
	 */
	MappingFileRoot getMappingFileRoot();

	SchemaContainer getContextDefaultDbSchemaContainer();
	
	Catalog getContextDefaultDbCatalog();
	
	Schema getContextDefaultDbSchema();

	/**
	 * "Post update" is called once the JPA project "update" is complete.
	 * We use this to calculate (typically default) state that is dependent
	 * on the entity inheritance hierarchy (e.g. discriminator column name).
	 * Of course, if these settings change, yet another "update" will be
	 * triggered, followed by yet another "post update"; until the JPA
	 * project's state quiesces
	 */
	void postUpdate();
}
