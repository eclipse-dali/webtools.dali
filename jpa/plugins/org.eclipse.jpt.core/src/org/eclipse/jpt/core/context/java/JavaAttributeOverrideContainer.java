/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaAttributeOverrideContainer
	extends AttributeOverrideContainer, JavaOverrideContainer
{
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> attributeOverrides();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> specifiedAttributeOverrides();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeOverride> virtualAttributeOverrides();
	
	JavaAttributeOverride getAttributeOverrideNamed(String name);
	
	interface Owner extends AttributeOverrideContainer.Owner, JavaOverrideContainer.Owner
	{		
		//nothing yet
	}

}