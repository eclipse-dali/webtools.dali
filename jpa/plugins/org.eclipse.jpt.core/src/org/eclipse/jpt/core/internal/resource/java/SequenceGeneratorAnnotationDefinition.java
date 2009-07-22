/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.jpa1.resource.java.binary.BinarySequenceGenerator1_0Annotation;
import org.eclipse.jpt.core.internal.jpa1.resource.java.source.SourceSequenceGenerator1_0Annotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.SequenceGenerator
 */
public final class SequenceGeneratorAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new SequenceGeneratorAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	protected SequenceGeneratorAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceSequenceGenerator1_0Annotation(parent, member);
	}

	public Annotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinarySequenceGenerator1_0Annotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return SequenceGeneratorAnnotation.ANNOTATION_NAME;
	}

}
