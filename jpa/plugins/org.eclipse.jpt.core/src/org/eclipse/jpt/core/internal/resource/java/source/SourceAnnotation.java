/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;

/**
 * some common state and behavior for Java source annotations;
 * and lots of convenience methods
 */
public abstract class SourceAnnotation<A extends AnnotatedElement>
	extends SourceNode
	implements Annotation
{
	protected final A annotatedElement;

	// TODO - make 'final' if we start using combination annotation adapters(?)
	protected DeclarationAnnotationAdapter daa;

	// TODO - make 'final' if we start using combination annotation adapters(?)
	protected AnnotationAdapter annotationAdapter;


	/**
	 * constructor for straight member annotation
	 */
	protected SourceAnnotation(JavaResourceNode parent, A annotatedElement, DeclarationAnnotationAdapter daa) {
		this(parent, annotatedElement, daa, new ElementAnnotationAdapter(annotatedElement, daa));
	}

	/**
	 * constructor for nested annotation (typically)
	 */
	protected SourceAnnotation(JavaResourceNode parent, A annotatedElement, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent);
		this.annotatedElement = annotatedElement;
		this.daa = daa;
		this.annotationAdapter = annotationAdapter;
	}


	// ********** JavaResourceNode implementation **********

	public TextRange getTextRange(CompilationUnit astRoot) {
		return this.getAnnotationTextRange(astRoot);
	}


	// ********** Annotation implementation **********

	public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	public void newAnnotation() {
		this.annotationAdapter.newMarkerAnnotation();
	}

	public void removeAnnotation() {
		this.annotationAdapter.removeAnnotation();
	}

	public boolean isUnset() {
		return true;
	}


	// ********** convenience methods **********

	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}

	protected IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}

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

	/**
	 * Return the text range corresponding to the annotation.
	 * If the annotation is missing, return null.
	 */
	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getAstAnnotation(astRoot));
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
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected TextRange getElementTextRange(TextRange elementTextRange, CompilationUnit astRoot) {
		return (elementTextRange != null) ? elementTextRange : this.getAnnotationTextRange(astRoot);
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
		return this.getTextRange(this.getAnnotationElementExpression(adapter, astRoot));
	}

	/**
	 * Return the specified AST DOM element.
	 */
	protected Expression getAnnotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return adapter.getExpression(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	/**
	 * Return the text range corresponding to the specified AST node.
	 * If the AST node is null, return null.
	 */
	protected TextRange getTextRange(ASTNode astNode) {
		return (astNode == null) ? null : new ASTNodeTextRange(astNode);
	}

	/**
	 * Convenience implementation of
	 * {@link org.eclipse.jpt.core.resource.java.NestableAnnotation#convertToNested(ContainerAnnotation, DeclarationAnnotationAdapter, int)}
	 * used by subclasses.
	 */
	public void convertToNested(ContainerAnnotation<? extends NestableAnnotation> containerAnnotation, DeclarationAnnotationAdapter containerAnnotationAdapter, int index) {
		Map<String, Object> map = this.buildState();
		this.removeAnnotation();  // this annotation has already been removed from the model
		IndexedDeclarationAnnotationAdapter idaa = this.buildNestedDeclarationAnnotationAdapter(containerAnnotationAdapter, index);
		this.daa = idaa;
		this.annotationAdapter = new ElementIndexedAnnotationAdapter(this.annotatedElement, idaa);
		this.rebuildAdapters();
		containerAnnotation.addNestedAnnotation(index, (NestableAnnotation) this);
		this.newAnnotation();
		this.restoreFrom(map);
	}

	/**
	 * Convenience implementation of
	 * {@link org.eclipse.jpt.core.resource.java.NestableAnnotation#convertToStandAlone()}
	 * used by subclasses.
	 */
	public void convertToStandAlone() {
		Map<String, Object> map = this.buildState();
		this.removeAnnotation();  // this annotation has already been removed from the model
		this.daa = new SimpleDeclarationAnnotationAdapter(this.getAnnotationName());
		this.annotationAdapter = new ElementAnnotationAdapter(this.annotatedElement, this.daa);
		this.rebuildAdapters();
		this.getParent().addStandAloneAnnotation((NestableAnnotation) this);
		this.newAnnotation();
		this.restoreFrom(map);
	}

	private Map<String, Object> buildState() {
		Map<String, Object> map = new HashMap<String, Object>();
		this.storeOn(map);
		return map;
	}

	protected void rebuildAdapters() {
		// this is only needed by nestable annotations
	}

	public void storeOn(Map<String, Object> map) {
		// this is only needed by nestable annotations
	}

	public void restoreFrom(Map<String, Object> map) {
		// this is only needed by nestable annotations
	}

	protected List<Map<String, Object>> buildStateList(int initialCapacity) {
		return (initialCapacity == 0) ?
				Collections.<Map<String, Object>>emptyList() :
				new ArrayList<Map<String, Object>>(initialCapacity);
	}

	protected IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter containerAnnotationAdapter, int index) {
		return buildNestedDeclarationAnnotationAdapter(index, containerAnnotationAdapter, this.getAnnotationName());
	}

	protected static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter containerAnnotationAdapter, String annotationName) {
		return new NestedIndexedDeclarationAnnotationAdapter(containerAnnotationAdapter, index, annotationName);
	}
}
