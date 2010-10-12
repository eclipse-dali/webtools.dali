/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryPrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.internal.resource.java.source.SourcePrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.PrimaryKeyJoinColumns
 */
public final class PrimaryKeyJoinColumnsAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new PrimaryKeyJoinColumnsAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private PrimaryKeyJoinColumnsAnnotationDefinition() {
		super();
	}

	public PrimaryKeyJoinColumnsAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourcePrimaryKeyJoinColumnsAnnotation((JavaResourcePersistentMember) parent, (Member) annotatedElement);
	}

	public PrimaryKeyJoinColumnsAnnotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryPrimaryKeyJoinColumnsAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME;
	}

}
