/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IPersistentType;

public interface DefaultsContext
{
	/**
	 * Return the default for the given key.  Se BaseJpaPlatform 
	 * for the defaults supported by it.
	 * @param key
	 * @return
	 */
	Object getDefault(String key);
	
	/**
	 * Return the IPersistentType with the given name found in the scope
	 * of the persistence unit
	 * @param fullyQualifiedTypeName
	 * @return
	 */
	IPersistentType persistentType(String fullyQualifiedTypeName);
	
	/**
	 * Return null in instances where the scope is not within a PersistentType.
	 * Otherwise return the CompilationUnit for the persistentType so that we
	 * don't have to build this everything.  It is costly to build and resolve bindings.
	 * @return
	 */
	CompilationUnit astRoot();
}
