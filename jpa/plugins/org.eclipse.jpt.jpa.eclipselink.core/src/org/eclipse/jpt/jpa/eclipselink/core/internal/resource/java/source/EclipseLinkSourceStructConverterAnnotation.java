/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.StructConverter</code>
 */
public final class EclipseLinkSourceStructConverterAnnotation
	extends SourceEclipseLinkNamedConverterAnnotation
	implements StructConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLink.STRUCT_CONVERTERS);

	private final DeclarationAnnotationElementAdapter<String> converterDeclarationAdapter;
	private final AnnotationElementAdapter<String> converterAdapter;
	private String converter;
	private TextRange converterTextRange;


	public static EclipseLinkSourceStructConverterAnnotation buildSourceStructConverterAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildStructConverterDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildStructConverterAnnotationAdapter(element, idaa);
		return new EclipseLinkSourceStructConverterAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}

	private EclipseLinkSourceStructConverterAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.converterDeclarationAdapter = this.buildConverterDeclarationAdapter();
		this.converterAdapter = this.buildConverterAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.converter = this.buildConverter(astAnnotation);
		this.converterTextRange = this.buildConverterTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncConverter(this.buildConverter(astAnnotation));
		this.converterTextRange = this.buildConverterTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.converter == null);
	}


	// ********** SourceNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.STRUCT_CONVERTER__NAME;
	}


	// ********** StructConverterAnnotation implementation **********

	// ***** converter
	public String getConverter() {
		return this.converter;
	}

	public void setConverter(String converter) {
		if (this.attributeValueHasChanged(this.converter, converter)) {
			this.converter = converter;
			this.converterAdapter.setValue(converter);
		}
	}

	private void syncConverter(String astConverter) {
		String old = this.converter;
		this.converter = astConverter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, astConverter);
	}

	private String buildConverter(Annotation astAnnotation) {
		return this.converterAdapter.getValue(astAnnotation);
	}

	public TextRange getConverterTextRange() {
		return this.converterTextRange;
	}

	private TextRange buildConverterTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.converterDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildConverterDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, EclipseLink.STRUCT_CONVERTER__CONVERTER);
	}

	private AnnotationElementAdapter<String> buildConverterAdapter() {
		return this.buildStringElementAdapter(this.converterDeclarationAdapter);
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildStructConverterAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildStructConverterDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
