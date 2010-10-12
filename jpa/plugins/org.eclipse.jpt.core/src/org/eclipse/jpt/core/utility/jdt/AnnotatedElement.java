/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Dali manipulates annotations on elements (packages, types, fields, and methods).
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
public interface AnnotatedElement {

	/**
	 * Return the annotated element's body declaration from the specified AST.
	 * This can be null if the annotated element is no longer present in the AST
	 * because the source has been changed in another thread.
	 */
	ASTNode getBodyDeclaration(CompilationUnit astRoot);

	/**
	 * Return the annotated element's binding from the specified AST.
	 */
	IBinding getBinding(CompilationUnit astRoot);

	/**
	 * Return the annotated element's "modified" declaration from the specified AST.
	 */
	ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot);

	/**
	 * Return the annotated element's "modified" declaration from a newly-generated AST.
	 */
	ModifiedDeclaration getModifiedDeclaration();

	/**
	 * Return the annotated element's name text range from the specified AST.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);

	/**
	 * Edit the annotated element's declaration using the specified editor.
	 */
	void edit(Editor editor);


	// ********** "editor" interface **********

	/**
	 * This interface defines a callback that is invoked when the annotated element's
	 * compilation unit/AST is in a state to be manipulated.
	 */
	public interface Editor {

		/**
		 * Edit the specified declaration. Any changes made to the declaration
		 * will be captured and applied to the annotated element's compilation unit.
		 */
		void edit(ModifiedDeclaration declaration);

	}

}
