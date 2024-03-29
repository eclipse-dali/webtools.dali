/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlEnumAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlEnumAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;

/**
 * javax.xml.bind.annotation.XmlEnum
 */
public final class XmlEnumAnnotationDefinition
		implements AnnotationDefinition {
	
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlEnumAnnotationDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private XmlEnumAnnotationDefinition() {
		super();
	}
	
	
	public String getAnnotationName() {
		return JAXB.XML_ENUM;
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlEnumAnnotation(parent, annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		return new NullXmlEnumAnnotation(parent);
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlEnumAnnotation(parent, jdtAnnotation);
	}
}
