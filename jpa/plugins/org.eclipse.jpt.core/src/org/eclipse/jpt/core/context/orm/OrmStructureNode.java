/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmStructureNode 
{
	/**
	 * Return the content type of the node's ORM.
	 * This is used to find the appropriate provider when building ORM type
	 * mappings (they can be ORM-specific) for the persistent type.
	 */
	IContentType getContentType();
	
	String ENTITY_MAPPINGS_ID = JptCorePlugin.PLUGIN_ID + ".orm.entityMappings"; //$NON-NLS-1$
	
	String PERSISTENT_TYPE_ID = JptCorePlugin.PLUGIN_ID + ".orm.persistentType"; //$NON-NLS-1$
	
	String PERSISTENT_ATTRIBUTE_ID = JptCorePlugin.PLUGIN_ID + ".orm.persistentAttribute"; //$NON-NLS-1$

}
