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

import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;

public abstract class AbstractAnnotationResource<E extends Member> extends AbstractResource<E> implements Annotation
{
	private DeclarationAnnotationAdapter daa;

	private AnnotationAdapter annotationAdapter;
		
	protected AbstractAnnotationResource(E member, JpaPlatform jpaPlatform, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(member, jpaPlatform);
		this.daa = daa;
		this.annotationAdapter = annotationAdapter;
	}
	
	protected AbstractAnnotationResource(E member, JpaPlatform jpaPlatform, DeclarationAnnotationAdapter daa) {
		this(member, jpaPlatform, daa, new MemberAnnotationAdapter(member, daa));
	}

	
	public AnnotationAdapter getAnnotationAdapter() {
		return this.annotationAdapter;
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
}
