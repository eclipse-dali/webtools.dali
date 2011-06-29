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
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;

/**
 * javax.xml.bind.annotation.XmlType
 */
public final class XmlTypeAnnotationDefinition
		implements AnnotationDefinition {
	
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlTypeAnnotationDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private XmlTypeAnnotationDefinition() {
		super();
	}
	
	
	public String getAnnotationName() {
		return JAXB.XML_TYPE;
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlTypeAnnotation(parent, annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		return new NullXmlTypeAnnotation(parent);
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlTypeAnnotation(parent, jdtAnnotation);
	}
}
