/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Provide clients with a pluggable way to manipulate an
 * annotation element modifying a "declaration".
 * T is the type of the object to be passed to and returned by the adapter.
 */
public interface DeclarationAnnotationElementAdapter<T> {

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
	Expression expression(ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, return the AST node
	 * corresponding to the element's value.
	 * If the element is missing, return the annotation's node.
	 */
	ASTNode astNode(ModifiedDeclaration declaration);

}
