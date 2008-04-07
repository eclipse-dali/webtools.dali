/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public abstract class AbstractResourceAnnotation<E extends Member>
	extends AbstractJavaResourceNode
	implements Annotation
{
	private final E member;

	private final DeclarationAnnotationAdapter daa;

	private final AnnotationAdapter annotationAdapter;
		
	protected AbstractResourceAnnotation(JavaResourceNode parent, E member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent);
		this.member = member;
		this.daa = daa;
		this.annotationAdapter = annotationAdapter;
	}
	
	public E getMember() {
		return this.member;
	}
	
	protected AbstractResourceAnnotation(JavaResourceNode parent, E member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}

	
	public AnnotationAdapter getAnnotationAdapter() {
		return this.annotationAdapter;
	}
	
	public org.eclipse.jdt.core.dom.Annotation getJdtAnnotation(CompilationUnit astRoot) {
		return getAnnotationAdapter().getAnnotation(astRoot);
	}
	
	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return this.daa;
	}

	public void removeAnnotation() {
		getAnnotationAdapter().removeAnnotation();
	}
	
	public void newAnnotation() {
		getAnnotationAdapter().newMarkerAnnotation();
	}
	
	public TextRange getTextRange(CompilationUnit astRoot) {
		return getAnnotationTextRange(astRoot);
	}
	
	protected TextRange getTextRange(ASTNode astNode) {
		return (astNode == null) ? null : new ASTNodeTextRange(astNode);
	}

	/**
	 * Return the text range corresponding to the specified annotation.
	 * If the annotation is missing, return null.
	 */
	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		return this.getTextRange(getAnnotation(astRoot));
	}
	
	protected org.eclipse.jdt.core.dom.Annotation getAnnotation(CompilationUnit astRoot) {
		return this.daa.getAnnotation(getMember().getModifiedDeclaration(astRoot));
		
	}

	protected Expression getAnnotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return adapter.getExpression(getMember().getModifiedDeclaration(astRoot));
	}

	protected TextRange getAnnotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return this.getTextRange(getAnnotationElementExpression(adapter, astRoot));

	}

	/**
	 * Convenience method. If the specified element is missing
	 * return the member's text range instead.
	 */
	protected TextRange getElementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, CompilationUnit astRoot) {
		return this.getElementTextRange(getAnnotationElementTextRange(elementAdapter, astRoot), astRoot);
	}
	
	/**
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected TextRange getElementTextRange(TextRange elementTextRange, CompilationUnit astRoot) {
		return (elementTextRange != null) ? elementTextRange : this.getTextRange(astRoot);
	}
	
	/**
	 * Convenience method. Return whether the specified position touches the element.
	 * Returns false if the element does not exist
	 */
	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.elementTouches(getAnnotationElementTextRange(elementAdapter, astRoot), pos);
	}
	
	/**
	 * Convenience method. Return whether element's text range is not
	 * null (meaning the element exists) and the specified position touches it.
	 */
	protected boolean elementTouches(TextRange elementTextRange, int pos) {
		return (elementTextRange != null) && elementTextRange.touches(pos);
	}

}
