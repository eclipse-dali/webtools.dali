/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

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
		MarkerAnnotation annotation = this.newMarkerAnnotation(declaration.ast());
		this.addAnnotationAndImport(declaration, annotation);
		return annotation;
	}

	public SingleMemberAnnotation newSingleMemberAnnotation(ModifiedDeclaration declaration) {
		SingleMemberAnnotation annotation = this.newSingleMemberAnnotation(declaration.ast());
		this.addAnnotationAndImport(declaration, annotation);
		return annotation;
	}

	public NormalAnnotation newNormalAnnotation(ModifiedDeclaration declaration) {
		NormalAnnotation annotation = this.newNormalAnnotation(declaration.ast());
		this.addAnnotationAndImport(declaration, annotation);
		return annotation;
	}

	/**
	 * Add the appropriate import statement, then shorten the annotation's
	 * name before adding it to the declaration.
	 */
	protected void addAnnotationAndImport(ModifiedDeclaration declaration, Annotation annotation) {
		declaration.addImport(this.annotationName);
		annotation.setTypeName(declaration.ast().newName(this.shortAnnotationName()));
		this.addAnnotation(declaration, annotation);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.annotationName);
	}


	// ********** abstract methods **********

	public abstract Annotation annotation(ModifiedDeclaration declaration);

	public abstract void removeAnnotation(ModifiedDeclaration declaration);

	public abstract ASTNode astNode(ModifiedDeclaration declaration);

	/**
	 * Add the specified annotation to the specified declaration,
	 * replacing the original annotation if present.
	 */
	protected abstract void addAnnotation(ModifiedDeclaration declaration, Annotation annotation);


	// ********** public methods **********

	public String annotationName() {
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

	protected String shortAnnotationName() {
		return this.shortName(this.annotationName);
	}
	
	protected String shortName(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}
	
	@SuppressWarnings("unchecked")
	protected List<MemberValuePair> values(NormalAnnotation na) {
		return na.values();
	}

}
