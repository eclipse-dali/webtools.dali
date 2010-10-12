/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.StructConverter
 */
public final class SourceEclipseLinkStructConverterAnnotation
	extends SourceEclipseLinkNamedConverterAnnotation
	implements EclipseLinkStructConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> CONVERTER_ADAPTER = buildConverterAdapter();
	private final AnnotationElementAdapter<String> converterAdapter;
	private String converter;


	public SourceEclipseLinkStructConverterAnnotation(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.converterAdapter = new AnnotatedElementAnnotationElementAdapter<String>(member, CONVERTER_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.converter = this.buildConverter(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncConverter(this.buildConverter(astRoot));
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
		return this.getElementTextRange(CONVERTER_ADAPTER, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildConverterAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.STRUCT_CONVERTER__CONVERTER, false, StringExpressionConverter.instance());
	}

}
