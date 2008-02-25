/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.JptCorePlugin;

public interface JavaStructureNodes 
{
	String COMPILATION_UNIT_ID = 
		JptCorePlugin.PLUGIN_ID + ".java.compilationUnit";
	
	String PERSISTENT_TYPE_ID = 
		JptCorePlugin.PLUGIN_ID + ".java.persistentType";
	
	String PERSISTENT_ATTRIBUTE_ID = 
		JptCorePlugin.PLUGIN_ID + ".java.persistentAttribute";
}
