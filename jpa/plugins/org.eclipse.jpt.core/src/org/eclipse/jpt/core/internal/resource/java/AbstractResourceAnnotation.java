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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
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
	
	public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
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
	
	public TextRange textRange(CompilationUnit astRoot) {
		return getMember().annotationTextRange(this.daa, astRoot);
	}

	/**
	 * Convenience method. If the specified element is missing
	 * return the member's text range instead.
	 */
	protected TextRange elementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, CompilationUnit astRoot) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter, astRoot), astRoot);
	}
	
	/**
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected TextRange elementTextRange(TextRange elementTextRange, CompilationUnit astRoot) {
		return (elementTextRange != null) ? elementTextRange : this.textRange(astRoot);
	}
	
	/**
	 * Convenience method. Return whether the specified position touches the element.
	 * Returns false if the element does not exist
	 */
	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.member.annotationElementTextRange(elementAdapter, astRoot), pos);
	}
	
	/**
	 * Convenience method. Return whether element's text range is not
	 * null (meaning the element exists) and the specified position touches it.
	 */
	protected boolean elementTouches(TextRange elementTextRange, int pos) {
		return (elementTextRange != null) && elementTextRange.touches(pos);
	}

}
