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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlSeeAlsoAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlSeeAlsoAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;

/**
 * javax.xml.bind.annotation.XmlSeeAlso
 */
public final class XmlSeeAlsoAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlSeeAlsoAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private XmlSeeAlsoAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlSeeAlsoAnnotation((JavaResourcePersistentType) parent, (Type) annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlSeeAlsoAnnotation((JavaResourcePersistentType) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return XmlSeeAlsoAnnotation.ANNOTATION_NAME;
	}

}
