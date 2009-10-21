/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.binary.BinaryCacheable2_0Annotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.source.SourceCacheable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.Cacheable2_0Annotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.AssociationOverride
 */
public final class Cacheable2_0AnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new Cacheable2_0AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Cacheable2_0AnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceCacheable2_0Annotation((JavaResourcePersistentType) parent, (Type) member);
	}

	public Annotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinaryCacheable2_0Annotation((JavaResourcePersistentType) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return Cacheable2_0Annotation.ANNOTATION_NAME;
	}

}
