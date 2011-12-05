/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.StructConverter</code>
 */
public final class SourceEclipseLinkStructConverterAnnotation
	extends SourceEclipseLinkNamedConverterAnnotation
	implements EclipseLinkStructConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLink.STRUCT_CONVERTERS);

	private final DeclarationAnnotationElementAdapter<String> converterDeclarationAdapter;
	private final AnnotationElementAdapter<String> converterAdapter;
	private String converter;
	private TextRange converterTextRange;


	public static SourceEclipseLinkStructConverterAnnotation buildSourceStructConverterAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildStructConverterDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildStructConverterAnnotationAdapter(element, idaa);
		return new SourceEclipseLinkStructConverterAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}

	private SourceEclipseLinkStructConverterAnnotation(
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
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.converter = this.buildConverter(astRoot);
		this.converterTextRange = this.buildConverterTextRange(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncConverter(this.buildConverter(astRoot));
		this.converterTextRange = this.buildConverterTextRange(astRoot);
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

	private String buildConverter(CompilationUnit astRoot) {
		return this.converterAdapter.getValue(astRoot);
	}

	public TextRange getConverterTextRange(CompilationUnit astRoot) {
		return this.converterTextRange;
	}

	private TextRange buildConverterTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.converterDeclarationAdapter, astRoot);
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
