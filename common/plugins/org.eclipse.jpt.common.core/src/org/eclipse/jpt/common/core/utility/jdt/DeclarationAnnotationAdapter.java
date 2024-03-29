/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.jdt;

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
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
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
	ASTNode getAstNode(ModifiedDeclaration declaration);

}
