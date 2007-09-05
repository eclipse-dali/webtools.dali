/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * Map a string key to an attribute mapping and its corresponding
 * Java annotation adapter.  
 */
public interface IDefaultJavaAttributeMappingProvider extends IJavaAttributeMappingProvider {

	/**
	 * Given the Attribute and DefaultContext return whether the default mapping applies.
	 * This will be used to determine the default mapping in the case where no 
	 * mapping has been specified.
	 * @param attribute
	 * @param defaultsContext
	 * @return
	 */
	boolean defaultApplies(Attribute attribute, DefaultsContext defaultsContext);

}
