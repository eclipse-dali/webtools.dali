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
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlJavaTypeAdaptersAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlJavaTypeAdaptersAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdaptersAnnotation;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdaptersAnnotation
 */
public final class XmlJavaTypeAdaptersAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlJavaTypeAdaptersAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private XmlJavaTypeAdaptersAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlJavaTypeAdaptersAnnotation(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlJavaTypeAdaptersAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return XmlJavaTypeAdaptersAnnotation.ANNOTATION_NAME;
	}

}
