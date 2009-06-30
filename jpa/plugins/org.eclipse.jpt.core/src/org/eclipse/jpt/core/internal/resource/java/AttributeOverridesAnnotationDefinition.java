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
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAttributeOverridesAnnotation;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.AttributeOverrides
 */
public final class AttributeOverridesAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new AttributeOverridesAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private AttributeOverridesAnnotationDefinition() {
		super();
	}

	public AttributeOverridesAnnotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceAttributeOverridesAnnotation(parent, member);
	}

	public AttributeOverridesAnnotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinaryAttributeOverridesAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return AttributeOverridesAnnotation.ANNOTATION_NAME;
	}

}
