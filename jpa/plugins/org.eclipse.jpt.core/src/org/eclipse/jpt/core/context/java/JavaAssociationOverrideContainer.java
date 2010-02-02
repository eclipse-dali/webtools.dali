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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaAssociationOverrideContainer extends AssociationOverrideContainer, JavaJpaContextNode
{
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> associationOverrides();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> specifiedAssociationOverrides();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAssociationOverride> virtualAssociationOverrides();
	
	JavaAssociationOverride getAssociationOverrideNamed(String name);

	void initialize(JavaResourcePersistentMember jrpm);
	
	/**
	 * Update the JavaAssociationOverrideContainer context model object to match the JavaResourcePersistentMember 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(JavaResourcePersistentMember jrpm);
	
	interface Owner extends AssociationOverrideContainer.Owner
	{		
		TextRange getValidationTextRange(CompilationUnit astRoot);
	}


}