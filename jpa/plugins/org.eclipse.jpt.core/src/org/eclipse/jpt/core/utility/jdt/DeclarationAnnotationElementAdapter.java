/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Provide clients with a pluggable way to manipulate an
 * annotation element modifying a "declaration".
 * T is the type of the object to be passed to and returned by the adapter.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface DeclarationAnnotationElementAdapter<T> {

	/**
	 * Reduce the number of NLS warnings.
	 */
	String VALUE = "value"; //$NON-NLS-1$

	/**
	 * Given the specified declaration, return the value of the
	 * annotation element. Return null or an empty array
	 * if the element is not present.
	 */
	T getValue(ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, set the value of the
	 * annotation element. Setting the value of the element
	 * to null will cause the element to be removed from its
	 * annotation.
	 */
	void setValue(T value, ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, return the element's value expression.
	 * Return null if the element is not present.
	 */
	Expression getExpression(ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, return the AST node
	 * corresponding to the element's value.
	 * If the element is missing, return the annotation's node.
	 */
	ASTNode getAstNode(ModifiedDeclaration declaration);

}
