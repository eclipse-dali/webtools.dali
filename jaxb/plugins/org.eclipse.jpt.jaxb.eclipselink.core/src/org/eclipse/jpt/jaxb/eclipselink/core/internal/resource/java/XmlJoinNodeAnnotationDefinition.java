/*******************************************************************************
 * Copyright (c) 2012, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.binary.BinaryXmlJoinNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.source.SourceXmlJoinNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class XmlJoinNodeAnnotationDefinition
		implements NestableAnnotationDefinition {
	
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new XmlJoinNodeAnnotationDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	private XmlJoinNodeAnnotationDefinition() {
		super();
	}
	
	
	public String getNestableAnnotationName() {
		return ELJaxb.XML_JOIN_NODE;
	}
	
	public String getContainerAnnotationName() {
		return ELJaxb.XML_JOIN_NODES;
	}
	
	public String getElementName() {
		return ELJaxb.XML_JOIN_NODES__VALUE;
	}
	
	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceXmlJoinNodeAnnotation.buildSourceXmlJoinNodeAnnotation(parent, annotatedElement, index);
	}
	
	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryXmlJoinNodeAnnotation(parent, jdtAnnotation);
	}
}
