/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility.jdt;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Dali manipulates annotations on members (types, fields, and methods).
 * This interface simplifies those manipulations.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface Member {

	/**
	 * Return the member's body declaration from the specified AST.
	 * This can be null if the member is no longer present in the AST
	 * because the source has been changed in another thread.
	 */
	BodyDeclaration getBodyDeclaration(CompilationUnit astRoot);

	/**
	 * Return the member's binding from the specified AST.
	 */
	IBinding getBinding(CompilationUnit astRoot);

	/**
	 * Return the member's "modified" declaration from the specified AST.
	 */
	ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot);

	/**
	 * Return the member's "modified" declaration from a newly-generated AST.
	 */
	ModifiedDeclaration getModifiedDeclaration();

	/**
	 * Return whether the attribute is a persistable field or property getter.
	 */
	boolean isPersistable(CompilationUnit astRoot);

	/**
	 * Return whether the member matches the specified member
	 * and occurrence.
	 */
	boolean matches(String memberName, int occurrence);

	/**
	 * Return the member's name text range from the specified AST.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);

	/**
	 * Edit the member's declaration using the specified editor.
	 */
	void edit(Editor editor);


	// ********** "editor" interface **********

	/**
	 * This interface defines a callback that is invoked when the member's
	 * compilation unit/AST is in a state to be manipulated.
	 */
	public interface Editor {

		/**
		 * Edit the specified declaration. Any changes made to the declaration
		 * will be captured and applied to the member's compilation unit.
		 */
		void edit(ModifiedDeclaration declaration);

	}

}
