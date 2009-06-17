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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt2_0.core.internal.resource.java.binary.BinaryAccessAnnotation;
import org.eclipse.jpt2_0.core.internal.resource.java.source.SourceAccessAnnotation;
import org.eclipse.jpt2_0.core.resource.java.AccessAnnotation;

/**
 * javax.persistence.Access
 */
public class AccessAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new AccessAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private AccessAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceAccessAnnotation(parent, member);
	}

	public Annotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		return new NullAccessAnnotation(parent);
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinaryAccessAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return AccessAnnotation.ANNOTATION_NAME;
	}

}
