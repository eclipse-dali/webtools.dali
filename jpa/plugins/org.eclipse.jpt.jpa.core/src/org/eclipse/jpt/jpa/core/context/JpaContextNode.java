/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.JptResourceTypeReference;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * Common protocol for JPA objects that have a context, as opposed to
 * resource objects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JpaContextNode
	extends JpaNode, JptResourceTypeReference
{
	/**
	 * Return the persistence unit if the context node is within a 
	 * persistence unit. Otherwise throw an exception.
	 */
	PersistenceUnit getPersistenceUnit();

	/**
	 * Return the mapping file root if the context node is within a 
	 * mapping file. Otherwise throw an exception.
	 */
	MappingFile.Root getMappingFileRoot();


	// ********** database stuff **********

	SchemaContainer getContextDefaultDbSchemaContainer();

	Catalog getContextDefaultDbCatalog();

	Schema getContextDefaultDbSchema();


	// ********** synchronize/update **********

	/**
	 * The resource model has changed; synchronize the context model with it.
	 * This will probably trigger a call to {@link #update()}.
	 */
	void synchronizeWithResourceModel();

	/**
	 * Some non-trivial state in the JPA project has changed; update the
	 * parts of the context node that are dependent on yet other parts of the
	 * node's JPA project.
	 * If the dependent state changes also, yet another <em>update</em> will be
	 * triggered, possibly followed by yet more <em>updates</em>; until the JPA
	 * project's state quiesces.
	 */
	void update();
}
