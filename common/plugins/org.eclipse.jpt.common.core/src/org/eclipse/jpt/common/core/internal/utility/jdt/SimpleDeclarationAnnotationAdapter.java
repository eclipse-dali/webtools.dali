/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;

/**
 * Manipulate an annotation with a specific name, e.g.
 * <pre>
 *     &#64;Foo
 *     private int id;
 * </pre>
 * 
 * NB:
 * If the declaration contains more than one annotation with the same
 * name, the adapter will correspond to the first annotation with the specified
 * name. (Also note that the compiler will not allow a declaration to be
 * modified by multiple annotations with the same name, i.e. of the same type;
 * so if there *are* multiple annotations of the same type, there are bigger
 * problems to worry about than which annotation the adapter manipulates.)
 */
public class SimpleDeclarationAnnotationAdapter extends AbstractDeclarationAnnotationAdapter {


	// ********** constructors **********

	public SimpleDeclarationAnnotationAdapter(String annotationName) {
		super(annotationName);
	}


	// ********** DeclarationAnnotationAdapter implementation **********

	public Annotation getAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(this.getAnnotationName());
	}

	public void removeAnnotation(ModifiedDeclaration declaration) {
		declaration.removeAnnotationNamed(this.getAnnotationName());
	}

	@Override
	protected void addAnnotation(ModifiedDeclaration declaration, Annotation annotation) {
		declaration.replaceAnnotationNamed(this.getAnnotationName(), annotation);
	}

	public ASTNode getAstNode(ModifiedDeclaration declaration) {
		// if the annotation is missing, return the declaration
		Annotation annotation = this.getAnnotation(declaration);
		return (annotation != null) ? annotation : declaration.getDeclaration();
	}

}
