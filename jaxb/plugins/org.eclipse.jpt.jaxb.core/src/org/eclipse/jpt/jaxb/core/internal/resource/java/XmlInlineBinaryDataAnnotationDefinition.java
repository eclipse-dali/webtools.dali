/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlInlineBinaryDataAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlInlineBinaryDataAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlInlineBinaryDataAnnotation;

/**
 * javax.xml.bind.annotation.XmlInlineBinaryData
 */
public final class XmlInlineBinaryDataAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlInlineBinaryDataAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private XmlInlineBinaryDataAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlInlineBinaryDataAnnotation((JavaResourcePersistentMember) parent, (Member) annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlInlineBinaryDataAnnotation((JavaResourcePersistentMember) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return XmlInlineBinaryDataAnnotation.ANNOTATION_NAME;
	}

}
