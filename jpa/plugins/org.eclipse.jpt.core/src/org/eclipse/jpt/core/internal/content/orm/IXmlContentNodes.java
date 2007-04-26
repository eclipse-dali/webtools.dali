/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.jpt.core.internal.JptCorePlugin;

public interface IXmlContentNodes 
{
	public static final String XML_ROOT_ID =
		JptCorePlugin.PLUGIN_ID + ".xml.Root";
	
	public static final String ENTITY_MAPPINGS_ID = 
		JptCorePlugin.PLUGIN_ID + ".xml.entityMappings";
	
	public static final String PERSISTENT_TYPE_ID = 
		JptCorePlugin.PLUGIN_ID + ".xml.persistentType";
	
	public static final String PERSISTENT_ATTRIBUTE_ID =
		JptCorePlugin.PLUGIN_ID + ".xml.persistentAttribute";
}
