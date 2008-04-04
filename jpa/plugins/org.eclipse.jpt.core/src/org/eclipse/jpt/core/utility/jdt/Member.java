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

import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jpt.core.utility.TextRange;

/**
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

	boolean wraps(IMember member);

	Type getTopLevelDeclaringType();

	CompilationUnit getAstRoot();

	/**
	 * Return the member's body declaration.
	 */
	BodyDeclaration getBodyDeclaration(CompilationUnit astRoot);

	IBinding getBinding(CompilationUnit astRoot);

	ModifiedDeclaration getModifiedDeclaration();

	ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot);

	TextRange getTextRange(CompilationUnit astRoot);

	TextRange getNameTextRange(CompilationUnit astRoot);

	TextRange getAnnotationTextRange(DeclarationAnnotationAdapter adapter, CompilationUnit astRoot);

	TextRange getAnnotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot);

	void newMarkerAnnotation(DeclarationAnnotationAdapter adapter);

	void edit(Editor editor);


	// ********** "editor" interface **********

	/**
	 * This interface defines a callback that is invoked when the member's
	 * compilation unit is in a state to be manipulated.
	 */
	public interface Editor {

		/**
		 * Edit the specified declaration.
		 */
		void edit(ModifiedDeclaration declaration);

	}

}
