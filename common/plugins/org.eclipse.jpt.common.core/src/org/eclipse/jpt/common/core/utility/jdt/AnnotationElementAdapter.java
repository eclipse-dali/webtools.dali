/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
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
	 * If the compilation unit is available, {@link #getValue(CompilationUnit)}
	 * might be more performant.
	 * If the annotation is available, {@link #getValue(Annotation)} might
	 * be more performant.
	 * @see #getValue(CompilationUnit)
	 * @see #getValue(Annotation)
	 */
	T getValue();

	/**
	 * Given the specified compilation unit, return the value of the
	 * adapter's annotation element.
	 * Return null if the element is not present.
	 * If the annotation is available, {@link #getValue(Annotation)} might
	 * be more performant.
	 * @see #getValue()
	 * @see #getValue(Annotation)
	 */
	T getValue(CompilationUnit astRoot);
	
	/**
	 * Given the specified annotation, return the value of the
	 * adapter's annotation element.
	 * Return null if the element is not present.
	 * @see #getValue()
	 */
	T getValue(Annotation astAnnotation);

	/**
	 * Set the value of the adapter's annotation element.
	 * Setting the value of the element to null will cause
	 * the element to be removed from its annotation.
	 */
	void setValue(T value);

	/**
	 * Given the specified compilation unit, return the expression value of the
	 * adapter's annotation element.
	 * Return null if the element is not present.
	 * If the annotation is available, {@link #getExpression(Annotation)} might
	 * be more performant
	 * @see #getExpression(Annotation)
	 */
	Expression getExpression(CompilationUnit astRoot);
	
	/**
	 * Given the specified compilation unit, return the expression value of the
	 * adapter's annotation element.
	 * Return null if the element is not present.
	 * @see #getExpression(CompilationUnit)
	 */
	Expression getExpression(Annotation astAnnotation);

	/**
	 * Return the AST node corresponding to the element.
	 * If the element is missing, return the annotation's node.
	 */
	ASTNode getAstNode(CompilationUnit astRoot);

}
