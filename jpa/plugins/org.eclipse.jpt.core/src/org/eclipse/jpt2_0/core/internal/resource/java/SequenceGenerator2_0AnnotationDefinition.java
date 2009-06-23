/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt2_0.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.resource.java.SequenceGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt2_0.core.internal.resource.java.binary.BinarySequenceGenerator2_0Annotation;
import org.eclipse.jpt2_0.core.internal.resource.java.source.SourceSequenceGenerator2_0Annotation;

/**
 *  SequenceGenerator2_0AnnotationDefinition
 */
public class SequenceGenerator2_0AnnotationDefinition
	extends SequenceGeneratorAnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new SequenceGenerator2_0AnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private SequenceGenerator2_0AnnotationDefinition() {
		super();
	}

	@Override
	public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceSequenceGenerator2_0Annotation(parent, member);
	}

	@Override
	public Annotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinarySequenceGenerator2_0Annotation(parent, jdtAnnotation);
	}
}
