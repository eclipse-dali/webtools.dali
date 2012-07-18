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
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;


public class SourceXmlPathAnnotation
		extends SourceAnnotation
		implements XmlPathAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = 
			new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_PATH);
	public static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = 
			new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_PATHS);
	
	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	
	
	public static SourceXmlPathAnnotation buildSourceXmlPathAnnotation(
			JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		
		IndexedDeclarationAnnotationAdapter idaa = buildXmlPathDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildXmlPathAnnotationAdapter(annotatedElement, idaa);
		return new SourceXmlPathAnnotation(parent, annotatedElement, idaa, iaa);
	}
	
	
	private SourceXmlPathAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		
		super(parent, annotatedElement, daa, annotationAdapter);
		this.valueDeclarationAdapter = buildValueAdapter(daa);
		this.valueAdapter = this.buildAnnotationElementAdapter(this.valueDeclarationAdapter);
	}
	
	
	private DeclarationAnnotationElementAdapter<String> buildValueAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, ELJaxb.XML_PATH__VALUE, StringExpressionConverter.instance());
	}
	
	private DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}
	
	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(
			DeclarationAnnotationElementAdapter<String> daea) {
		
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return ELJaxb.XML_PATH;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.value = buildValue(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncValue(buildValue(astAnnotation));
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
	
	
	// ***** value *****
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(value);
		}
	}
	
	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}
	
	private String buildValue(Annotation astAnnotation) {
		return this.valueAdapter.getValue(astAnnotation);
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}
	
	public boolean valueTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.valueDeclarationAdapter, pos, astRoot);
	}
	
	
	// ***** NestableAnnotation impl *****
	
	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
	@Override
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}
	
	
	// ********** static methods **********

	private static IndexedAnnotationAdapter buildXmlPathAnnotationAdapter(
			AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}
	
	private static IndexedDeclarationAnnotationAdapter buildXmlPathDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
				new CombinationIndexedDeclarationAnnotationAdapter(
					DECLARATION_ANNOTATION_ADAPTER,
					CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
					index,
					ELJaxb.XML_PATH);
		return idaa;
	}
}
