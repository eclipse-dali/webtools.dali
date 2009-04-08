/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.binary.BinaryExistenceCheckingAnnotation;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.source.SourceExistenceCheckingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ExistenceCheckingAnnotation;

/**
 * org.eclipse.persistence.annotations.ExistenceChecking
 */
public class ExistenceCheckingAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new ExistenceCheckingAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private ExistenceCheckingAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceExistenceCheckingAnnotation((JavaResourcePersistentType) parent, (Type) member);
	}

	public Annotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinaryExistenceCheckingAnnotation((JavaResourcePersistentType) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ExistenceCheckingAnnotation.ANNOTATION_NAME;
	}

}
