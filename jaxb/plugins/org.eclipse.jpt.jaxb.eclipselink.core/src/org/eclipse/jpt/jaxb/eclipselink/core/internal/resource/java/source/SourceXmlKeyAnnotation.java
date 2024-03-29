/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.source;

import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlKeyAnnotation;


public class SourceXmlKeyAnnotation
		extends SourceAnnotation
		implements XmlKeyAnnotation {

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_KEY);
	
	
	public SourceXmlKeyAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		this(parent, element, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(element, DECLARATION_ANNOTATION_ADAPTER));
	}
	
	public SourceXmlKeyAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
	}
	
	
	public String getAnnotationName() {
		return ELJaxb.XML_KEY;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getAnnotationName());
	}
}
