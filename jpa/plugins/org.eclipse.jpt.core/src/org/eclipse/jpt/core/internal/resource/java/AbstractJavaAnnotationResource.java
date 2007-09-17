package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;

public abstract class AbstractJavaAnnotationResource<E extends Member> extends AbstractJavaResource<E> implements JavaTypeAnnotation
{
	private DeclarationAnnotationAdapter daa;

	private AnnotationAdapter annotationAdapter;
		
	protected AbstractJavaAnnotationResource(E member, JpaPlatform jpaPlatform, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(member, jpaPlatform);
		this.daa = daa;
		this.annotationAdapter = annotationAdapter;
	}
	
	protected AbstractJavaAnnotationResource(E member, JpaPlatform jpaPlatform, DeclarationAnnotationAdapter daa) {
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
