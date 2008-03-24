/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Adapt a "boolean" Java annotation with a simple-to-use interface.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface BooleanAnnotationAdapter {

	/**
	 * Return the "boolean" value of the adapter's annotation.
	 * Return true if the annotation is present; otherwise return false.
	 * If the compilation unit is available, #getValue(CompilationUnit)
	 * might be more performant.
	 * @see #getValue(org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	boolean value();

	/**
	 * Given the specified compilation unit, return the "boolean" value of the
	 * adapter's annotation.
	 * Return true if the annotation is present; otherwise return false.
	 * @see #getValue()
	 */
	boolean value(CompilationUnit astRoot);

	/**
	 * Set the "boolean" value of the adapter's annotation.
	 * Setting the value to true will cause the annotation to be added
	 * to its declaration; setting it to false will cause the annotation
	 * to be removed.
	 */
	void setValue(boolean value);

	/**
	 * Return the value of the adapter's annotation.
	 * If the compilation unit is available, #annotation(CompilationUnit)
	 * might be more performant.
	 * @see #annotation(org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	Annotation annotation();

	/**
	 * Given the specified compilation unit, return the value of the
	 * adapter's annotation.
	 * @see #annotation()
	 */
	Annotation annotation(CompilationUnit astRoot);

	/**
	 * Return the AST node corresponding to the annotation.
	 * If the annotation is missing, return the annotation's parent's node.
	 * If the compilation unit is available, #astNode(CompilationUnit)
	 * might be more performant.
	 * @see #astNode(org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	ASTNode astNode();

	/**
	 * Return the AST node corresponding to the annotation.
	 * If the annotation is missing, return the annotation's parent node.
	 * @see #astNode()
	 */
	ASTNode astNode(CompilationUnit astRoot);

}
