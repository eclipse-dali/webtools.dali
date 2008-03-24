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

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;

/**
 * Define a wrapper that provides a common protocol for all the various AST
 * declarations that have modifiers (i.e. there are a number of AST node
 * classes that implement the method #modifiers(), but they do not implement
 * a common interface):
 *     BodyDeclaration
 *     SingleVariableDeclaration
 *     VariableDeclarationExpression
 *     VariableDeclarationStatement
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface ModifiedDeclaration {

	/**
	 * Return the "declaration" AST node.
	 */
	ASTNode getDeclaration();

	/**
	 * Return the "declaration" AST.
	 */
	AST getAST();

	/**
	 * Return the *first* annotation with the specified name.
	 * Return null if the declaration has no such annotation.
	 */
	Annotation getAnnotationNamed(String annotationName);

	/**
	 * Remove the *first* annotation with the specified name from the declaration.
	 */
	void removeAnnotationNamed(String annotationName);

	/**
	 * Replace the specified old annotation with the specified new annotation.
	 * If there is no annotation with the specified name, simply add the new
	 * annotation to the declaration's modifiers.
	 */
	void replaceAnnotationNamed(String oldAnnotationName, Annotation newAnnotation);

	/**
	 * Add the specified import to the declaration's compilation unit.
	 */
	void addImport(String importName);

	/**
	 * Add the specified static import to the declaration's compilation unit.
	 */
	void addStaticImport(String importName);

	/**
	 * Add the specified import to the declaration's compilation unit.
	 */
	void addImport(String importName, boolean static_);

	/**
	 * Return whether the specified annotation has the specified name.
	 */
	boolean annotationIsNamed(Annotation annotation, String name);

}
