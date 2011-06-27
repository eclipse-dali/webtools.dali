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
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapterAnnotation
 */
public final class XmlJavaTypeAdapterAnnotationDefinition
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new XmlJavaTypeAdapterAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private XmlJavaTypeAdapterAnnotationDefinition() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceXmlJavaTypeAdapterAnnotation.buildSourceXmlJavaTypeAdapterAnnotation(parent, annotatedElement, index);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlJavaTypeAdapterAnnotation(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return JAXB.XML_JAVA_TYPE_ADAPTER;
	}

	public String getContainerAnnotationName() {
		return JAXB.XML_JAVA_TYPE_ADAPTERS;
	}

	public String getElementName() {
		return JAXB.XML_JAVA_TYPE_ADAPTERS__VALUE;
	}
}
