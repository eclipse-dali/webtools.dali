/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.binary.BinaryOneToMany2_0Annotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.source.SourceOneToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 *  OneToMany2_0AnnotationDefinition
 */
public final class OneToMany2_0AnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new OneToMany2_0AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OneToMany2_0AnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceOneToMany2_0Annotation((JavaResourcePersistentAttribute) parent, (Attribute) member);
	}

	public Annotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		return new NullOneToMany2_0Annotation((JavaResourcePersistentAttribute) parent);
	}

	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinaryOneToMany2_0Annotation((JavaResourcePersistentAttribute) parent, jdtAnnotation);
	}
	
	public String getAnnotationName() {
		return OneToManyAnnotation.ANNOTATION_NAME;
	}

}


