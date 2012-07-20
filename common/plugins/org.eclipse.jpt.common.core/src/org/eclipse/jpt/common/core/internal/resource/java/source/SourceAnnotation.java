/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationElementAdapter;

/**
 * some common state and behavior for Java source annotations;
 * and lots of convenience methods
 */
public abstract class SourceAnnotation
	extends SourceNode
	implements Annotation
{
	protected final AnnotatedElement annotatedElement;

	protected final DeclarationAnnotationAdapter daa;

	protected final AnnotationAdapter annotationAdapter;


	/**
	 * constructor for straight member annotation
	 */
	protected SourceAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	/**
	 * constructor for nested annotation (typically)
	 */
	protected SourceAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent);
		this.annotatedElement = element;
		this.daa = daa;
		this.annotationAdapter = annotationAdapter;
	}


	// ********** JavaResourceNode implementation **********

	public TextRange getTextRange(CompilationUnit astRoot) {
		return this.getAnnotationTextRange(astRoot);
	}


	// ********** Annotation implementation **********

	public void initialize(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		initialize((CompilationUnit) astAnnotation.getRoot());
	}

	public void synchronizeWith(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		synchronizeWith((CompilationUnit) astAnnotation.getRoot());
	}

	public void initialize(CompilationUnit astRoot) {
		//use the more performant initialize(Annotation)
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		//use the more performant synchronizeWith(Annotation)
	}

	public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	public void newAnnotation() {
		this.initialize(this.annotationAdapter.newMarkerAnnotation());
	}

	public void removeAnnotation() {
		this.annotationAdapter.removeAnnotation();
	}

	public boolean isUnset() {
		return true;
	}


	// ********** convenience methods **********

	protected DeclarationAnnotationElementAdapter<String> buildStringElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, elementName);
	}

	protected DeclarationAnnotationElementAdapter<Boolean> buildBooleanElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(this.daa, elementName);
	}

	protected DeclarationAnnotationElementAdapter<Integer> buildIntegerElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forNumbers(this.daa, elementName);
	}

	protected AnnotationElementAdapter<String> buildStringElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	protected AnnotationElementAdapter<Boolean> buildBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}

	protected AnnotationElementAdapter<Integer> buildIntegerElementAdapter(DeclarationAnnotationElementAdapter<Integer> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Integer>(this.annotatedElement, daea);
	}

	protected CompilationUnit buildASTRoot() {
		return this.getJavaResourceCompilationUnit().buildASTRoot();
	}

	/**
	 * Return the text range corresponding to the annotation.
	 * If the annotation is missing, return <code>null</code>.
	 */
	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		// the AST is null for virtual Java attributes
		// TODO remove the AST null check once we start storing text ranges
		// in the resource model
		return (astRoot == null) ? null : this.buildTextRange(this.getAstAnnotation(astRoot));
	}

	/**
	 * Return the text range corresponding to the annotation.
	 * If the annotation is missing, return <code>null</code>.
	 */
	protected TextRange getAnnotationTextRange(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return this.buildTextRange(astAnnotation);
	}

	/**
	 * Convenience method.
	 * Return the text range corresponding to the specified element.
	 * If the specified element is missing, return the annotation's text range instead.
	 */
	protected TextRange getElementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, CompilationUnit astRoot) {
		return this.getElementTextRange(this.getAnnotationElementTextRange(elementAdapter, astRoot), astRoot);
	}

	/**
	 * Convenience method.
	 * Return the text range corresponding to the specified element.
	 * If the specified element is missing, return the annotation's text range instead.
	 */
	protected TextRange getElementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return this.getElementTextRange(this.getAnnotationElementTextRange(elementAdapter, astAnnotation), astAnnotation);
	}

	/**
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected TextRange getElementTextRange(TextRange elementTextRange, CompilationUnit astRoot) {
		return (elementTextRange != null) ? elementTextRange : this.getAnnotationTextRange(astRoot);
	}

	/**
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected TextRange getElementTextRange(TextRange elementTextRange, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return (elementTextRange != null) ? elementTextRange : this.getAnnotationTextRange(astAnnotation);
	}

	/**
	 * Convenience method. Return whether the specified position exists and
	 * touches the specified element.
	 */
	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.textRangeTouches(this.getAnnotationElementTextRange(elementAdapter, astRoot), pos);
	}

	/**
	 * Convenience method. Return whether the specified text range is not
	 * null (meaning the corresponding AST node exists) and the specified position touches it.
	 */
	protected boolean textRangeTouches(TextRange textRange, int pos) {
		return (textRange != null) && textRange.touches(pos);
	}

	/**
	 * Return the text range corresponding to the specified element.
	 * If the element is missing, return null.
	 */
	protected TextRange getAnnotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		// the AST is null for virtual Java attributes
		// TODO remove the AST null check once we start storing text ranges
		// in the resource model
		return (astRoot == null) ? null : this.buildTextRange(this.getAnnotationElementExpression(adapter, astRoot));
	}

	protected TextRange getAnnotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return (astAnnotation == null) ? null : this.buildTextRange(this.getAnnotationElementExpression(adapter, astAnnotation));
	}

	/**
	 * Return the specified AST DOM element.
	 */
	protected Expression getAnnotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return adapter.getExpression(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	/**
	 * Return the specified AST DOM element.
	 */
	protected Expression getAnnotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return adapter.getExpression(astAnnotation);
	}

	/**
	 * Return the text range corresponding to the expression at the specified
	 * index of the element's array.
	 * @exception ArrayIndexOutOfBoundsException if the index is out of range
	 */
	protected TextRange selectAnnotationElementTextRange(IndexedDeclarationAnnotationElementAdapter<?> adapter, int index, CompilationUnit astRoot) {
		// the AST is null for virtual Java attributes
		// TODO remove the AST null check once we start storing text ranges
		// in the resource model
		return (astRoot == null) ? null : this.buildTextRange(this.selectAnnotationElementExpression(adapter, index, astRoot));
	}

	/**
	 * Return the expression at the specified index of the element's array.
	 */
	protected Expression selectAnnotationElementExpression(IndexedDeclarationAnnotationElementAdapter<?> adapter, int index, CompilationUnit astRoot) {
		return adapter.selectExpression(this.annotatedElement.getModifiedDeclaration(astRoot), index);
	}

	/**
	 * Return the text range corresponding to the specified AST node.
	 * Return <code>null</code> if the AST node is <code>null</code>.
	 */
	protected TextRange buildTextRange(ASTNode astNode) {
		return (astNode == null) ? null : ASTTools.buildTextRange(astNode);
	}


	// ********** NestableAnnotation implementation **********

	/**
	 * Convenience implementation of method from {@link NestableAnnotation} interface
	 * for subclasses.
	 */
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}

	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}
}
