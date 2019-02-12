/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
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

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlTransformationAnnotation;


public class SourceXmlTransformationAnnotation
		extends SourceAnnotation
		implements XmlTransformationAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_TRANSFORMATION);
	
	private final DeclarationAnnotationElementAdapter<Boolean> optionalDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> optionalAdapter;
	private Boolean optional;
	private TextRange optionalTextRange;
	
	
	public SourceXmlTransformationAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		this(parent, element, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(element, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceXmlTransformationAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.optionalDeclarationAdapter = buildOptionalDeclarationAdapter(daa);
		this.optionalAdapter = buildShortCircuitBooleanElementAdapter(this.optionalDeclarationAdapter);
	}
	
	
	private DeclarationAnnotationElementAdapter<Boolean> buildOptionalDeclarationAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, ELJaxb.XML_TRANSFORMATION__OPTIONAL);
	}
	
	private AnnotationElementAdapter<Boolean> buildShortCircuitBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return ELJaxb.XML_TRANSFORMATION;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.optional = buildOptional(astAnnotation);
		this.optionalTextRange = this.buildOptionalTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncOptional(buildOptional(astAnnotation));
		this.optionalTextRange = this.buildOptionalTextRange(astAnnotation);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.optional);
	}
	
	
	// ********** optional
	
	public Boolean getOptional() {
		return this.optional;
	}
	
	public void setOptional(Boolean optional) {
		if (ObjectTools.notEquals(this.optional, optional)) {
			this.optional = optional;
			this.optionalAdapter.setValue(optional);
		}
	}
	
	private void syncOptional(Boolean astOptional) {
		Boolean old = this.optional;
		this.optional = astOptional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, this.optional);
	}
	
	private Boolean buildOptional(Annotation astAnnotation) {
		return this.optionalAdapter.getValue(astAnnotation);
	}

	public TextRange getOptionalTextRange() {
		return this.optionalTextRange;
	}

	private TextRange buildOptionalTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.optionalDeclarationAdapter, astAnnotation);
	}
}
