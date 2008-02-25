/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;

public abstract class AbstractAnnotationResource<E extends Member> extends AbstractMemberResource<E> 
	implements Annotation
{
	private final DeclarationAnnotationAdapter daa;

	private final AnnotationAdapter annotationAdapter;
		
	protected AbstractAnnotationResource(JavaResourceNode parent, E member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member);
		this.daa = daa;
		this.annotationAdapter = annotationAdapter;
	}
	
	protected AbstractAnnotationResource(JavaResourceNode parent, E member, DeclarationAnnotationAdapter daa) {
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
}
