/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Java source code or binary field
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface JavaResourceField
	extends JavaResourceAttribute
{
	/**
	 * The [source] field must be sychronized with both the ASTFieldDeclaration and the
	 * VariableDeclarationFragment.
	 * This is to handle multiple fields declared in a single statement:
	 * 		private int foo, bar;
	 * The FieldDeclaration is the ASTNode that has the annotations on it.
	 * The VariableDeclarationFragment contains the name and return the
	 * IVariableBinding for the particular field.
	 */
	void synchronizeWith(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration);

	/**
	 * Resolve type information that could be dependent on changes elsewhere
	 * in the workspace.
	 */
	void resolveTypes(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration);

	/**
	 * Field is (annotated) or (non-static and non-transient);
	 */
	@SuppressWarnings("unchecked")
	Predicate<JavaResourceField> IS_RELEVANT_FOR_FIELD_ACCESS = 
			PredicateTools.or(
				IS_ANNOTATED,
				PredicateTools.and(
						PredicateTools.not(IS_TRANSIENT),
						PredicateTools.not(IS_STATIC)
				)
			);

	/**
	 * Field is (annotated) or (public and non-static and non-transient);
	 */
	@SuppressWarnings("unchecked")
	Predicate<JavaResourceField> IS_RELEVANT_FOR_PUBLIC_MEMBER_ACCESS = 
			PredicateTools.or(
				IS_ANNOTATED,
				PredicateTools.and(
						IS_PUBLIC,
						PredicateTools.not(IS_TRANSIENT),
						PredicateTools.not(IS_STATIC)
				)
			);
}
