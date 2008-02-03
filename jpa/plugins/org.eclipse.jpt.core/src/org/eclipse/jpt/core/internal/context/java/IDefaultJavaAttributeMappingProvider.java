/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;


/**
 * Map a string key to an attribute mapping and its corresponding
 * Java annotation adapter.  
 */
public interface IDefaultJavaAttributeMappingProvider extends IJavaAttributeMappingProvider {

	/**
	 * Given the IJavaPersistentAttribute return whether the default mapping applies.
	 * This will be used to determine the default mapping in the case where no 
	 * mapping has been specified.
	 */
	boolean defaultApplies(IJavaPersistentAttribute persistentAttribute);

}
