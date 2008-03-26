/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

/**
 * Behaviorless implementation.
 */
public class NullDeclarationAnnotationElementAdapter<T>
	implements DeclarationAnnotationElementAdapter<T>
{

	// singleton
	@SuppressWarnings("unchecked")
	private static final DeclarationAnnotationElementAdapter INSTANCE
			= new NullDeclarationAnnotationElementAdapter();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <S> DeclarationAnnotationElementAdapter<S> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NullDeclarationAnnotationElementAdapter() {
		super();
	}

	public T getValue(ModifiedDeclaration declaration) {
		return null;
	}

	public void setValue(T value, ModifiedDeclaration declaration) {
		// do nothing
	}

	public ASTNode getAstNode(ModifiedDeclaration declaration) {
		return declaration.getDeclaration();
	}

	public Expression getExpression(ModifiedDeclaration declaration) {
		return null;
	}

}
