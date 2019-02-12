/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;


public class SourceXmlJoinNodeAnnotation
		extends SourceAnnotation
		implements XmlJoinNodeAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = 
			new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_JOIN_NODE);
	public static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = 
			new SimpleDeclarationAnnotationAdapter(ELJaxb.XML_JOIN_NODES);
	
	private final DeclarationAnnotationElementAdapter<String> xmlPathDeclarationAdapter;
	private final AnnotationElementAdapter<String> xmlPathAdapter;
	private String xmlPath;
	private TextRange xmlPathTextRange;
	private TextRange xmlPathValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> referencedXmlPathDeclarationAdapter;
	private final AnnotationElementAdapter<String> referencedXmlPathAdapter;
	private String referencedXmlPath;
	private TextRange referencedXmlPathTextRange;
	private TextRange referencedXmlPathValidationTextRange;
	
	
	public static SourceXmlJoinNodeAnnotation buildSourceXmlJoinNodeAnnotation(
			JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		
		IndexedDeclarationAnnotationAdapter idaa = buildXmlJoinNodeDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildXmlJoinNodeAnnotationAdapter(annotatedElement, idaa);
		return new SourceXmlJoinNodeAnnotation(parent, annotatedElement, idaa, iaa);
	}
	
	
	private SourceXmlJoinNodeAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		
		super(parent, annotatedElement, daa, annotationAdapter);
		this.xmlPathDeclarationAdapter = buildXmlPathAdapter(daa);
		this.xmlPathAdapter = buildAnnotationElementAdapter(this.xmlPathDeclarationAdapter);
		this.referencedXmlPathDeclarationAdapter = buildReferencedXmlPathAdapter(daa);
		this.referencedXmlPathAdapter = buildAnnotationElementAdapter(this.referencedXmlPathDeclarationAdapter);
	}
	
	
	private DeclarationAnnotationElementAdapter<String> buildXmlPathAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, ELJaxb.XML_JOIN_NODE__XML_PATH, StringExpressionConverter.instance());
	}
	
	private DeclarationAnnotationElementAdapter<String> buildReferencedXmlPathAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, ELJaxb.XML_JOIN_NODE__REFERENCED_XML_PATH, StringExpressionConverter.instance());
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
		return ELJaxb.XML_JOIN_NODE;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.xmlPath = buildXmlPath(astAnnotation);
		this.xmlPathTextRange = buildXmlPathTextRange(astAnnotation);
		this.xmlPathValidationTextRange = buildXmlPathValidationTextRange(astAnnotation);
		this.referencedXmlPath = buildReferencedXmlPath(astAnnotation);
		this.referencedXmlPathTextRange = buildReferencedXmlPathTextRange(astAnnotation);
		this.referencedXmlPathValidationTextRange = buildReferencedXmlPathValidationTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncXmlPath(buildXmlPath(astAnnotation));
		this.xmlPathTextRange = buildXmlPathTextRange(astAnnotation);
		this.xmlPathValidationTextRange = buildXmlPathValidationTextRange(astAnnotation);
		syncReferencedXmlPath(buildReferencedXmlPath(astAnnotation));
		this.referencedXmlPathTextRange = buildReferencedXmlPathTextRange(astAnnotation);
		this.referencedXmlPathValidationTextRange = buildReferencedXmlPathValidationTextRange(astAnnotation);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.xmlPath + " -> " + this.referencedXmlPath); //$NON-NLS-1$
	}
	
	
	// ***** xmlPath *****
	
	public String getXmlPath() {
		return this.xmlPath;
	}
	
	public void setXmlPath(String xmlPath) {
		if (ObjectTools.notEquals(this.xmlPath, xmlPath)) {
			this.xmlPath = xmlPath;
			this.xmlPathAdapter.setValue(xmlPath);
		}
	}
	
	private void syncXmlPath(String astXmlPath) {
		String old = this.xmlPath;
		this.xmlPath = astXmlPath;
		this.firePropertyChanged(XML_PATH_PROPERTY, old, astXmlPath);
	}
	
	private String buildXmlPath(Annotation astAnnotation) {
		return this.xmlPathAdapter.getValue(astAnnotation);
	}
	
	private TextRange buildXmlPathTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(this.xmlPathDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildXmlPathValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.xmlPathDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getXmlPathTextRange() {
		return this.xmlPathTextRange;
	}
	
	public TextRange getXmlPathValidationTextRange() {
		return this.xmlPathValidationTextRange;
	}
	
	public boolean xmlPathTouches(int pos) {
		return textRangeTouches(this.xmlPathTextRange, pos);
	}
	
	
	// ***** referencedXmlPath *****
	
	public String getReferencedXmlPath() {
		return this.referencedXmlPath;
	}
	
	public void setReferencedXmlPath(String referencedXmlPath) {
		if (ObjectTools.notEquals(this.referencedXmlPath, referencedXmlPath)) {
			this.referencedXmlPath = referencedXmlPath;
			this.referencedXmlPathAdapter.setValue(referencedXmlPath);
		}
	}
	
	private void syncReferencedXmlPath(String astReferencedXmlPath) {
		String old = this.referencedXmlPath;
		this.referencedXmlPath = astReferencedXmlPath;
		this.firePropertyChanged(REFERENCED_XML_PATH_PROPERTY, old, astReferencedXmlPath);
	}
	
	private String buildReferencedXmlPath(Annotation astAnnotation) {
		return this.referencedXmlPathAdapter.getValue(astAnnotation);
	}
	
	private TextRange buildReferencedXmlPathTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(this.referencedXmlPathDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildReferencedXmlPathValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.referencedXmlPathDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getReferencedXmlPathTextRange() {
		return this.referencedXmlPathTextRange;
	}
	
	public TextRange getReferencedXmlPathValidationTextRange() {
		return this.referencedXmlPathValidationTextRange;
	}
	
	public boolean referencedXmlPathTouches(int pos) {
		return textRangeTouches(this.referencedXmlPathTextRange, pos);
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

	private static IndexedAnnotationAdapter buildXmlJoinNodeAnnotationAdapter(
			AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}
	
	private static IndexedDeclarationAnnotationAdapter buildXmlJoinNodeDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
				new CombinationIndexedDeclarationAnnotationAdapter(
					DECLARATION_ANNOTATION_ADAPTER,
					CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
					index,
					ELJaxb.XML_JOIN_NODE);
		return idaa;
	}
}
