/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;

/**
 * Behaviorless implementation.
 */
public class NullDeclarationAnnotationElementAdapter<T>
	implements DeclarationAnnotationElementAdapter<T>
{

	// singleton
	@SuppressWarnings("rawtypes")
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

	public T getValue(Annotation astAnnotation) {
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

	public Expression getExpression(Annotation astAnnotation) {
		return null;
	}
}
