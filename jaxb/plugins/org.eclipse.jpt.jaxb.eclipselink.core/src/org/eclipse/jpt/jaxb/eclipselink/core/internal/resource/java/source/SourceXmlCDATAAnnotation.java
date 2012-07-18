/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlCDATAAnnotation;


public class SourceXmlCDATAAnnotation
		extends SourceAnnotation
		implements XmlCDATAAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_CDATA);
	
	
	public SourceXmlCDATAAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		this(parent, element, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(element, DECLARATION_ANNOTATION_ADAPTER));
	}
	
	public SourceXmlCDATAAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
	}
	
	
	public String getAnnotationName() {
		return ELJaxb.XML_CDATA;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		//no-op
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		//no-op
	}
	
	@Override
	public void toString(StringBuilder sb) {}
}
