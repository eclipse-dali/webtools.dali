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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryXmlElementDeclAnnotation;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlElementDeclAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementDecl
 */
public final class XmlElementDeclAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlElementDeclAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private XmlElementDeclAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlElementDeclAnnotation((JavaResourcePersistentAttribute) parent, (MethodAttribute) annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlElementDeclAnnotation((JavaResourcePersistentAttribute) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return XmlElementDeclAnnotation.ANNOTATION_NAME;
	}

}
