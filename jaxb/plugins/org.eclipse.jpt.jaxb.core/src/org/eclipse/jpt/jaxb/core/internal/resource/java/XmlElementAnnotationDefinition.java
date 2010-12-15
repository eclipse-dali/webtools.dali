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
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlElement
 */
public final class XmlElementAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new XmlElementAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private XmlElementAnnotationDefinition() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceXmlElementAnnotation.buildSourceXmlElementAnnotation((JavaResourceField) parent, (Attribute) annotatedElement, index);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlElementAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return XmlElementAnnotation.ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return JAXB.XML_ELEMENT;
	}

	public String getContainerAnnotationName() {
		return JAXB.XML_ELEMENTS;
	}

	public String getElementName() {
		return JAXB.XML_ELEMENTS__VALUE;
	}
}
