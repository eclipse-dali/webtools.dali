/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * 
 */
public abstract class AbstractDeclarationAnnotationAdapter implements DeclarationAnnotationAdapter {
	private final String annotationName;


	// ********** constructors **********

	protected AbstractDeclarationAnnotationAdapter(String annotationName) {
		super();
		this.annotationName = annotationName;
	}


	// ********** DeclarationAnnotationAdapter implementation **********

	public MarkerAnnotation newMarkerAnnotation(ModifiedDeclaration declaration) {
		MarkerAnnotation annotation = this.newMarkerAnnotation(declaration.getAst());
		this.addAnnotationAndImport(declaration, annotation);
		return annotation;
	}

	public SingleMemberAnnotation newSingleMemberAnnotation(ModifiedDeclaration declaration) {
		SingleMemberAnnotation annotation = this.newSingleMemberAnnotation(declaration.getAst());
		this.addAnnotationAndImport(declaration, annotation);
		return annotation;
	}

	public NormalAnnotation newNormalAnnotation(ModifiedDeclaration declaration) {
		NormalAnnotation annotation = this.newNormalAnnotation(declaration.getAst());
		this.addAnnotationAndImport(declaration, annotation);
		return annotation;
	}

	/**
	 * Add the appropriate import statement, then shorten the annotation's
	 * name before adding it to the declaration.
	 */
	protected void addAnnotationAndImport(ModifiedDeclaration declaration, Annotation annotation) {
		annotation.setTypeName(declaration.getAst().newName(this.getSourceCodeAnnotationName(declaration)));
		this.addAnnotation(declaration, annotation);
	}

	/**
	 * Return the annotation's name as it can be used in source code;
	 * i.e. if we can add it to the compilation unit's imports, return the short
	 * name; if we can't (because of a collision), return the fully-qualified name.
	 * NB: an import may be added as a side-effect :-(
	 */
	protected String getSourceCodeAnnotationName(ModifiedDeclaration declaration) {
		return declaration.addImport(this.annotationName) ? this.getShortAnnotationName() : this.annotationName;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.annotationName);
	}

	/**
	 * Add the specified annotation to the specified declaration,
	 * replacing the original annotation if present.
	 */
	protected abstract void addAnnotation(ModifiedDeclaration declaration, Annotation annotation);


	// ********** public methods **********

	/**
	 * This is 'public' because we use it in CombinationIndexedDeclarationAnnotationAdapter
	 * to get the annotation name from a NestedIndexedDeclarationAnnotationAdapter.
	 */
	public String getAnnotationName() {
		return this.annotationName;
	}


	// ********** helper methods **********

	protected boolean nameMatches(ModifiedDeclaration declaration, Annotation annotation) {
		return this.nameMatches(declaration, annotation, this.annotationName);
	}

	protected boolean nameMatches(ModifiedDeclaration declaration, Annotation annotation, String name) {
		return declaration.annotationIsNamed(annotation, name);
	}

	protected MarkerAnnotation newMarkerAnnotation(AST ast) {
		return this.newMarkerAnnotation(ast, this.annotationName);
	}

	protected MarkerAnnotation newMarkerAnnotation(AST ast, String name) {
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newName(name));
		return annotation;
	}

	protected SingleMemberAnnotation newSingleMemberAnnotation(AST ast) {
		return this.newSingleMemberAnnotation(ast, this.annotationName);
	}

	protected SingleMemberAnnotation newSingleMemberAnnotation(AST ast, String name) {
		SingleMemberAnnotation annotation = ast.newSingleMemberAnnotation();
		annotation.setTypeName(ast.newName(name));
		return annotation;
	}

	protected NormalAnnotation newNormalAnnotation(AST ast) {
		return this.newNormalAnnotation(ast, this.annotationName);
	}

	protected NormalAnnotation newNormalAnnotation(AST ast, String name) {
		NormalAnnotation annotation = ast.newNormalAnnotation();
		annotation.setTypeName(ast.newName(name));
		return annotation;
	}

	protected String getShortAnnotationName() {
		return convertToShortName(this.annotationName);
	}
	
	protected static String convertToShortName(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}
	
	@SuppressWarnings("unchecked")
	protected List<MemberValuePair> values(NormalAnnotation na) {
		return na.values();
	}

}
