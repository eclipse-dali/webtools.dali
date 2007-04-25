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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;

/**
 * Provide clients with a pluggable way to manipulate an
 * annotation modifying a "declaration".
 * 
 * TODO specify how multiple annotations with the same name are to be handled
 */
public interface DeclarationAnnotationAdapter {

	/**
	 * Given the specified declaration, return the annotation.
	 */
	Annotation getAnnotation(ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, modify it with
	 * a new marker annotation, replacing the original annotation if present.
	 * Return the new annotation.
	 */
	MarkerAnnotation newMarkerAnnotation(ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, modify it with
	 * a new single member annotation, replacing the original annotation if present.
	 * Return the new annotation.
	 */
	SingleMemberAnnotation newSingleMemberAnnotation(ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, modify it with
	 * a new normal annotation, replacing the original annotation if present.
	 * Return the new annotation.
	 */
	NormalAnnotation newNormalAnnotation(ModifiedDeclaration declaration);

	/**
	 * Remove the annotation from the specified declaration.
	 */
	void removeAnnotation(ModifiedDeclaration declaration);

	/**
	 * Given the specified declaration, return the AST node
	 * corresponding to the annotation.
	 * If the annotation is missing, return its parent node.
	 */
	ASTNode astNode(ModifiedDeclaration declaration);

}
