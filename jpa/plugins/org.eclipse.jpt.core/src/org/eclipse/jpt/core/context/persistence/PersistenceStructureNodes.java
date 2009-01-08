/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

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
public interface PersistenceStructureNodes 
{
	
	String PERSISTENCE_ID = 
		JptCorePlugin.PLUGIN_ID + ".persistence.persistence"; //$NON-NLS-1$
	
	String PERSISTENCE_UNIT_ID = 
		JptCorePlugin.PLUGIN_ID + ".persistence.persistenceUnit"; //$NON-NLS-1$
	
	String CLASS_REF_ID =
		JptCorePlugin.PLUGIN_ID + ".persistence.classRef"; //$NON-NLS-1$
	
	String MAPPING_FILE_REF_ID =
		JptCorePlugin.PLUGIN_ID + ".persistence.mappingFileRef"; //$NON-NLS-1$
}
