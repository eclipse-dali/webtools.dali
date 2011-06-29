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
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;

/**
 * javax.xml.bind.annotation.XmlID
 */
public final class XmlIDAnnotationDefinition
		implements AnnotationDefinition {
	
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlIDAnnotationDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private XmlIDAnnotationDefinition() {
		super();
	}
	
	
	public String getAnnotationName() {
		return JAXB.XML_ID;
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlIDAnnotation(parent, annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlIDAnnotation(parent, jdtAnnotation);
	}
}
