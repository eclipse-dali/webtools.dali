/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;

/**
 * JPA <code>orm.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmXml
	extends XmlContextNode, MappingFile
{
	/**
	 * covariant override
	 */
	MappingFileRef getParent();


	// ********** entity mappings **********

	/**
	 * String constant associated with changes to the entity-mappings property
	 */
	public final static String ENTITY_MAPPINGS_PROPERTY = "entityMappings"; //$NON-NLS-1$

	/**
	 * Return the content represented by the root of the orm.xml file.
	 * This may be null.
	 */
	EntityMappings getEntityMappings();
}
