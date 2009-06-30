/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt2_0.core.internal.resource.java.binary.BinaryAssociationOverrides2_0Annotation;
import org.eclipse.jpt2_0.core.internal.resource.java.source.SourceAssociationOverrides2_0Annotation;

/**
 * javax.persistence.AssociationOverrides
 */
public final class AssociationOverrides2_0AnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new AssociationOverrides2_0AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private AssociationOverrides2_0AnnotationDefinition() {
		super();
	}

	public AssociationOverridesAnnotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceAssociationOverrides2_0Annotation(parent, member);
	}

	public AssociationOverridesAnnotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinaryAssociationOverrides2_0Annotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return AssociationOverridesAnnotation.ANNOTATION_NAME;
	}

}
