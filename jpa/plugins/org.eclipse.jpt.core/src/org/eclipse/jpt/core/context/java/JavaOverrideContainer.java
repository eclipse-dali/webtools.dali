/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.OverrideContainer;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaOverrideContainer
	extends
		OverrideContainer,
		JavaJpaContextNode
{
	
	void initialize(JavaResourcePersistentMember jrpm);
	
	/**
	 * Update the JavaAttributeOverrideContainer context model object to match the JavaResourcePersistentMember 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(JavaResourcePersistentMember jrpm);
	
	interface Owner extends OverrideContainer.Owner
	{		
		TextRange getValidationTextRange(CompilationUnit astRoot);

		/**
		 * This is necessary for JPA 2.0. Return a prefix (ending in '.') that is allowed to be appended to the override name.
		 * Return null if no prefix is supported. "map." and "key." are the prefixes supported in JPA 2.0.
		 */
		String getPrefix();

		/**
		 * This is necessary for JPA 2.0 where Override annotation can have a prefix that distinguishes them.
		 * Return whether the given overrideName that might have a prefix is relevant to this particular override
		 * container.  "map." and "key." are the prefixes supported in JPA 2.0.
		 */
		boolean isRelevant(String overrideName);
	}

}