/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlInverseReferenceAnnotation;


public class SourceXmlInverseReferenceAnnotation
		extends SourceAnnotation
		implements XmlInverseReferenceAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_INVERSE_REFERENCE);
	
	private final DeclarationAnnotationElementAdapter<String> mappedByDeclarationAdapter;
	private final AnnotationElementAdapter<String> mappedByAdapter;
	private String mappedBy;
	private TextRange mappedByTextRange;
	
	
	public SourceXmlInverseReferenceAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		this(parent, element, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(element, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceXmlInverseReferenceAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.mappedByDeclarationAdapter = buildMappedByDeclarationAdapter(daa);
		this.mappedByAdapter = buildMappedByAdapter(this.mappedByDeclarationAdapter);
	}
	
	
	private DeclarationAnnotationElementAdapter<String> buildMappedByDeclarationAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(daa, ELJaxb.XML_INVERSE_REFERENCE__MAPPED_BY, StringExpressionConverter.instance());
	}
	
	private AnnotationElementAdapter<String> buildMappedByAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return ELJaxb.XML_INVERSE_REFERENCE;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.mappedBy = buildMappedBy(astAnnotation);
		this.mappedByTextRange = this.buildMappedByTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncMappedBy(buildMappedBy(astAnnotation));
		this.mappedByTextRange = this.buildMappedByTextRange(astAnnotation);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.mappedBy);
	}
	
	
	// ***** mappedBy *****
	
	public String getMappedBy() {
		return this.mappedBy;
	}
	
	public void setMappedBy(String mappedBy) {
		if (attributeValueHasChanged(this.mappedBy, mappedBy)) {
			this.mappedBy = mappedBy;
			this.mappedByAdapter.setValue(mappedBy);
		}
	}
	
	private void syncMappedBy(String astMappedBy) {
		String old = this.mappedBy;
		this.mappedBy = astMappedBy;
		this.firePropertyChanged(MAPPED_BY_PROPERTY, old, this.mappedBy);
	}
	
	private String buildMappedBy(Annotation astAnnotation) {
		return this.mappedByAdapter.getValue(astAnnotation);
	}

	public TextRange getMappedByTextRange() {
		return this.mappedByTextRange;
	}

	private TextRange buildMappedByTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.mappedByDeclarationAdapter, astAnnotation);
	}
	
	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.mappedByDeclarationAdapter, pos, astRoot);
	}
}
