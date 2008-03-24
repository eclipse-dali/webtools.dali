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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Adapt a Java annotation element with a simple-to-use interface.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface AnnotationElementAdapter<T> {

	/**
	 * Return the value of the adapter's annotation element.
	 * Return null if the element is not present.
	 * If the compilation unit is available, #value(CompilationUnit)
	 * might be more performant.
	 * @see #value(org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	T value();

	/**
	 * Given the specified compilation unit, return the value of the
	 * adapter's annotation element.
	 * Return null if the element is not present.
	 * @see #value()
	 */
	T value(CompilationUnit astRoot);

	/**
	 * Set the value of the adapter's annotation element.
	 * Setting the value of the element to null will cause
	 * the element to be removed from its annotation.
	 */
	void setValue(T value);

	/**
	 * Return the expression value of the adapter's annotation element.
	 * Return null if the element is not present.
	 * If the compilation unit is available, #expression(CompilationUnit)
	 * might be more performant.
	 * @see #expression(org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	Expression expression();

	/**
	 * Given the specified compilation unit, return the expression value of the
	 * adapter's annotation element.
	 * Return null if the element is not present.
	 * @see #expression()
	 */
	Expression expression(CompilationUnit astRoot);

	/**
	 * Return the AST node corresponding to the element.
	 * If the element is missing, return the annotation's node.
	 * If the compilation unit is available, #astNode(CompilationUnit)
	 * might be more performant.
	 * @see #astNode(org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	ASTNode astNode();

	/**
	 * Return the AST node corresponding to the element.
	 * If the element is missing, return the annotation's node.
	 * @see #astNode()
	 */
	ASTNode astNode(CompilationUnit astRoot);

}
