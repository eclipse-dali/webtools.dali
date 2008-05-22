/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.BaseEmbeddedMapping;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaBaseEmbeddedMapping extends JavaAttributeMapping, BaseEmbeddedMapping
{
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> attributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> virtualAttributeOverrides();
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> specifiedAttributeOverrides();
	
	JavaAttributeOverride getAttributeOverrideNamed(String name);

}
