/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;

/**
 * <code>org.eclipse.persistence.annotations.Converter</code>
 */
public final class EclipseLinkSourceConverterAnnotation
	extends EclipseLinkSourceNamedConverterAnnotation
	implements ConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLink.CONVERTERS);

	private final DeclarationAnnotationElementAdapter<String> converterClassDeclarationAdapter;
	private final AnnotationElementAdapter<String> converterClassAdapter;
	private String converterClass;
	private TextRange converterClassTextRange;

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	private String fullyQualifiedConverterClassName;
	// we need a flag since the f-q name can be null
	private boolean fqConverterClassNameStale = true;


	public static EclipseLinkSourceConverterAnnotation buildSourceConverterAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildConverterDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildConverterAnnotationAdapter(element, idaa);
		return new EclipseLinkSourceConverterAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}

	public EclipseLinkSourceConverterAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.converterClassDeclarationAdapter = this.buildConverterDeclarationClassAdapter();
		this.converterClassAdapter = this.buildConverterClassAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.converterClass = this.buildConverterClass(astAnnotation);
		this.converterClassTextRange = this.buildConverterClassTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncConverterClass(this.buildConverterClass(astAnnotation));
		this.converterClassTextRange = this.buildConverterClassTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.converterClass == null);
	}


	// ********** SourceNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.CONVERTER__NAME;
	}


	// ********** ConverterAnnotation implementation **********

	// ***** converter class
	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		if (ObjectTools.notEquals(this.converterClass, converterClass)) {
			this.converterClass = converterClass;
			this.fqConverterClassNameStale = true;
			this.converterClassAdapter.setValue(converterClass);
		}
	}

	private void syncConverterClass(String astConverterClass) {
		if (ObjectTools.notEquals(this.converterClass, astConverterClass)) {
			this.syncConverterClass_(astConverterClass);
		}
	}

	private void syncConverterClass_(String astConverterClass) {
		String old = this.converterClass;
		this.converterClass = astConverterClass;
		this.fqConverterClassNameStale = true;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, astConverterClass);
	}

	private String buildConverterClass(Annotation astAnnotation) {
		return this.converterClassAdapter.getValue(astAnnotation);
	}

	public TextRange getConverterClassTextRange() {
		return this.converterClassTextRange;
	}

	private TextRange buildConverterClassTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.converterClassDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildConverterDeclarationClassAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, EclipseLink.CONVERTER__CONVERTER_CLASS, SimpleTypeStringExpressionConverter.instance());
	}

	private AnnotationElementAdapter<String> buildConverterClassAdapter() {
		return this.buildStringElementAdapter(this.converterClassDeclarationAdapter);
	}

	// ***** fully-qualified converter class name
	public String getFullyQualifiedConverterClassName() {
		if (this.fqConverterClassNameStale) {
			this.fullyQualifiedConverterClassName = this.buildFullyQualifiedConverterClassName();
			this.fqConverterClassNameStale = false;
		}
		return this.fullyQualifiedConverterClassName;
	}

	private String buildFullyQualifiedConverterClassName() {
		return (this.converterClass == null) ? null : this.buildFullyQualifiedConverterClassName_();
	}

	private String buildFullyQualifiedConverterClassName_() {
		return ASTTools.resolveFullyQualifiedName(this.converterClassAdapter.getExpression(this.buildASTRoot()));
	}


	// ********** static methods **********

	private static IndexedAnnotationAdapter buildConverterAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildConverterDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
