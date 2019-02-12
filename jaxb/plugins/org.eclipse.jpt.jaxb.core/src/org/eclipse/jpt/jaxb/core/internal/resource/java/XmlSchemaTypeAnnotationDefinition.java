/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;


public class XmlSchemaTypeAnnotationDefinition
		implements NestableAnnotationDefinition {
	
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new XmlSchemaTypeAnnotationDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	private XmlSchemaTypeAnnotationDefinition() {
		super();
	}
	
	
	public String getNestableAnnotationName() {
		return JAXB.XML_SCHEMA_TYPE;
	}
	
	public String getContainerAnnotationName() {
		return JAXB.XML_SCHEMA_TYPES;
	}
	
	public String getElementName() {
		return JAXB.XML_SCHEMA_TYPES__VALUE;
	}
	
	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceXmlSchemaTypeAnnotation.buildSourceXmlSchemaTypeAnnotation(parent, annotatedElement, index);
	}
	
	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		throw new UnsupportedOperationException();
	}
}
