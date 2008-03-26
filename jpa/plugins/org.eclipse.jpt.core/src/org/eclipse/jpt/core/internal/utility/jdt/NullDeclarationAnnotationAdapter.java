/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

/**
 * Behaviorless implementation.
 */
public final class NullDeclarationAnnotationAdapter
	implements IndexedDeclarationAnnotationAdapter
{

	// singleton
	private static final NullDeclarationAnnotationAdapter INSTANCE = new NullDeclarationAnnotationAdapter();

	/**
	 * Return the singleton.
	 */
	public static IndexedDeclarationAnnotationAdapter instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullDeclarationAnnotationAdapter() {
		super();
	}


	// ********** DeclarationAnnotationAdapter implementation **********

	public Annotation getAnnotation(ModifiedDeclaration declaration) {
		return null;
	}

	public MarkerAnnotation newMarkerAnnotation(ModifiedDeclaration declaration) {
		return null;
	}

	public SingleMemberAnnotation newSingleMemberAnnotation(ModifiedDeclaration declaration) {
		return null;
	}

	public NormalAnnotation newNormalAnnotation(ModifiedDeclaration declaration) {
		return null;
	}

	public void removeAnnotation(ModifiedDeclaration declaration) {
		// do nothing
	}

	public ASTNode getAstNode(ModifiedDeclaration declaration) {
		return declaration.getDeclaration();
	}


	// ********** IndexedDeclarationAnnotationAdapter implementation **********

	public int getIndex() {
		return -1;
	}

	public void moveAnnotation(int newIndex, ModifiedDeclaration declaration) {
		// do nothing
	}

}
