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

import java.util.Collections;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
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

	protected TextRange annotationTextRange;

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

	public TextRange getTextRange() {
		return this.getAnnotationTextRange();
	}


	// ********** Annotation implementation **********

	public void initialize(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		this.annotationTextRange = this.buildTextRange(astAnnotation);
		initialize((CompilationUnit) astAnnotation.getRoot());
	}

	public void synchronizeWith(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		this.annotationTextRange = this.buildTextRange(astAnnotation);
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
	protected TextRange getAnnotationTextRange() {
		return this.annotationTextRange;
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
	protected TextRange getElementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return this.getElementTextRange(this.getAnnotationElementTextRange(elementAdapter, astAnnotation), astAnnotation);
	}

	/**
	 * Convenience method.
	 * Return the list of text ranges corresponding to the specified element.
	 * If the specified element is missing, return the annotation's text range instead.
	 */
	protected List<TextRange> getElementTextRanges(DeclarationAnnotationElementAdapter<?> elementAdapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return this.getElementTextRanges(this.getAnnotationElementTextRanges(elementAdapter, astAnnotation), astAnnotation);
	}

	/**
	 * Convenience method.
	 * Return the text range corresponding to the specified element at the given index.
	 * If the specified element is missing, return the annotation's text range instead.
	 */
	protected TextRange getElementTextRange(IndexedDeclarationAnnotationElementAdapter<?> elementAdapter, int index, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return this.getElementTextRange(this.getAnnotationElementTextRange(elementAdapter, index, astAnnotation), astAnnotation);
	}

	/**
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected TextRange getElementTextRange(TextRange elementTextRange, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return (elementTextRange != null) ? elementTextRange : this.getAnnotationTextRange(astAnnotation);
	}

	/**
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected List<TextRange> getElementTextRanges(List<TextRange> elementTextRanges, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return !elementTextRanges.isEmpty() ? elementTextRanges : Collections.singletonList(this.getAnnotationTextRange(astAnnotation));
	}

	/**
	 * Convenience method. Return whether the specified text range is not
	 * null (meaning the corresponding AST node exists) and the specified position touches it.
	 */
	protected boolean textRangeTouches(TextRange textRange, int pos) {
		return (textRange != null) && textRange.touches(pos);
	}

	protected TextRange getAnnotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return (astAnnotation == null) ? null : this.buildTextRange(this.getAnnotationElementExpression(adapter, astAnnotation));
	}

	protected List<TextRange> getAnnotationElementTextRanges(DeclarationAnnotationElementAdapter<?> adapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return (astAnnotation == null) ? Collections.<TextRange>emptyList() : this.buildTextRanges(this.getAnnotationElementExpression(adapter, astAnnotation));
	}

	protected TextRange getAnnotationElementTextRange(IndexedDeclarationAnnotationElementAdapter<?> adapter, int index, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return (astAnnotation == null) ? null : this.buildTextRange(this.getAnnotationElementExpression(adapter, index, astAnnotation));
	}

	/**
	 * Return the specified AST DOM element.
	 */
	protected Expression getAnnotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return adapter.getExpression(astAnnotation);
	}

	/**
	 * Return the specified AST DOM element.
	 */
	protected Expression getAnnotationElementExpression(IndexedDeclarationAnnotationElementAdapter<?> adapter, int index, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		return adapter.selectExpression(astAnnotation, index);
	}

	/**
	 * Return the text range corresponding to the specified AST node.
	 * Return <code>null</code> if the AST node is <code>null</code>.
	 */
	protected TextRange buildTextRange(ASTNode astNode) {
		return (astNode == null) ? null : ASTTools.buildTextRange(astNode);
	}

	/**
	 * Return the list of text ranges corresponding to the specified AST node.
	 * Return an empty list if the AST node is <code>null</code>.
	 */
	protected List<TextRange> buildTextRanges(ASTNode astNode) {
		return (astNode == null) ? Collections.<TextRange>emptyList() : ASTTools.buildTextRanges(astNode);
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
