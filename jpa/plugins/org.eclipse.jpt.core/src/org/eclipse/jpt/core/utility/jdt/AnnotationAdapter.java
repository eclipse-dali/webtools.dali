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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Adapt a Java annotation with a simple-to-use interface.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface AnnotationAdapter {

	/**
	 * Given the specified compilation unit, return the value of the
	 * adapter's annotation.
	 * @see #getAnnotation()
	 */
	Annotation getAnnotation(CompilationUnit astRoot);

	/**
	 * Build a new marker annotation, replacing the original annotation if present.
	 */
	void newMarkerAnnotation();

	/**
	 * Build a new single member annotation, replacing the original annotation if present.
	 */
	void newSingleMemberAnnotation();

	/**
	 * Build a new normal annotation, replacing the original annotation if present.
	 */
	void newNormalAnnotation();

	/**
	 * Remove the annotation.
	 */
	void removeAnnotation();

	/**
	 * Return the AST node corresponding to the annotation.
	 * If the annotation is missing, return the annotation's parent node.
	 * @see #getAstNode()
	 */
	ASTNode getAstNode(CompilationUnit astRoot);

}
