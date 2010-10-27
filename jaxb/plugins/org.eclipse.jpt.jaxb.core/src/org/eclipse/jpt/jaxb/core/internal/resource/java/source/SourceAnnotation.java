/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;

/**
 * some common state and behavior for Java source annotations;
 * and lots of convenience methods
 */
public abstract class SourceAnnotation<A extends AnnotatedElement>
	extends SourceNode
	implements Annotation
{
	protected final A annotatedElement;

	protected final DeclarationAnnotationAdapter daa;

	protected final AnnotationAdapter annotationAdapter;


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


	// ********** convenience methods **********

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

}
