/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jpt.core.JptCorePlugin;

public interface PersistenceStructureNodes 
{
	
	String PERSISTENCE_ID = 
		JptCorePlugin.PLUGIN_ID + ".persistence.persistence";
	
	String PERSISTENCE_UNIT_ID = 
		JptCorePlugin.PLUGIN_ID + ".persistence.persistenceUnit";
	
	String CLASS_REF_ID =
		JptCorePlugin.PLUGIN_ID + ".persistence.classRef";
	
	String MAPPING_FILE_REF_ID =
		JptCorePlugin.PLUGIN_ID + ".persistence.mappingFileRef";
}
