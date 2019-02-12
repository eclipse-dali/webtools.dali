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
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.ObjectTypeConverter</code>
 */
public final class EclipseLinkSourceObjectTypeConverterAnnotation
	extends EclipseLinkSourceBaseTypeConverterAnnotation
	implements ObjectTypeConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLink.OBJECT_TYPE_CONVERTERS);

	private final DeclarationAnnotationElementAdapter<String> defaultObjectValueDeclarationAdapter;
	private final AnnotationElementAdapter<String> defaultObjectValueAdapter;
	private String defaultObjectValue;
	private TextRange defaultObjectValueTextRange;

	private final ConversionValuesAnnotationContainer conversionValuesContainer = new ConversionValuesAnnotationContainer();


	public static EclipseLinkSourceObjectTypeConverterAnnotation buildSourceObjectTypeConverterAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildObjectTypeConverterDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildObjectTypeConverterAnnotationAdapter(element, idaa);
		return new EclipseLinkSourceObjectTypeConverterAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}

	private EclipseLinkSourceObjectTypeConverterAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.defaultObjectValueDeclarationAdapter = this.buildDefaultObjectValueAdapter();
		this.defaultObjectValueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, this.defaultObjectValueDeclarationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.defaultObjectValue = this.buildDefaultObjectValue(astAnnotation);
		this.defaultObjectValueTextRange = this.buildDefaultObjectValueTextRange(astAnnotation);
		this.conversionValuesContainer.initializeFromContainerAnnotation(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncDefaultObjectValue(this.buildDefaultObjectValue(astAnnotation));
		this.defaultObjectValueTextRange = this.buildDefaultObjectValueTextRange(astAnnotation);
		this.conversionValuesContainer.synchronize(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.defaultObjectValue == null) &&
				this.conversionValuesContainer.isEmpty();
	}


	// ********** SourceNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.OBJECT_TYPE_CONVERTER__NAME;
	}


	// ********** SourceBaseTypeConverterAnnotation implementation **********

	@Override
	String getDataTypeElementName() {
		return EclipseLink.OBJECT_TYPE_CONVERTER__DATA_TYPE;
	}

	@Override
	String getObjectTypeElementName() {
		return EclipseLink.OBJECT_TYPE_CONVERTER__OBJECT_TYPE;
	}


	// ********** ObjectTypeConverterAnnotation implementation **********

	// ***** default object value
	public String getDefaultObjectValue() {
		return this.defaultObjectValue;
	}

	public void setDefaultObjectValue(String defaultObjectValue) {
		if (ObjectTools.notEquals(this.defaultObjectValue, defaultObjectValue)) {
			this.defaultObjectValue = defaultObjectValue;
			this.defaultObjectValueAdapter.setValue(defaultObjectValue);
		}
	}

	private void syncDefaultObjectValue(String astDefaultObjectValue) {
		String old = this.defaultObjectValue;
		this.defaultObjectValue = astDefaultObjectValue;
		this.firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, old, astDefaultObjectValue);
	}

	private String buildDefaultObjectValue(Annotation astAnnotation) {
		return this.defaultObjectValueAdapter.getValue(astAnnotation);
	}

	public TextRange getDefaultObjectValueTextRange() {
		return this.defaultObjectValueTextRange;
	}

	private TextRange buildDefaultObjectValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.defaultObjectValueDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildDefaultObjectValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, EclipseLink.OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE, StringExpressionConverter.instance());
	}

	// ***** conversion values

	public ListIterable<ConversionValueAnnotation> getConversionValues() {
		return this.conversionValuesContainer.getNestedAnnotations();
	}

	public int getConversionValuesSize() {
		return this.conversionValuesContainer.getNestedAnnotationsSize();
	}

	public ConversionValueAnnotation conversionValueAt(int index) {
		return this.conversionValuesContainer.getNestedAnnotation(index);
	}

	public ConversionValueAnnotation addConversionValue(int index) {
		return this.conversionValuesContainer.addNestedAnnotation(index);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		this.conversionValuesContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeConversionValue(int index) {
		this.conversionValuesContainer.removeNestedAnnotation(index);
	}

	protected ConversionValueAnnotation buildConversionValue(int index) {
		return EclipseLinkSourceConversionValueAnnotation.buildNestedSourceConversionValueAnnotation(
			this, this.annotatedElement, this.buildConversionValueIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildConversionValueIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, EclipseLink.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES, index, EclipseLink.CONVERSION_VALUE);
	}



	// ********** static methods **********

	private static IndexedAnnotationAdapter buildObjectTypeConverterAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildObjectTypeConverterDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}

	// ********** hint container **********
	/**
	 * adapt the AnnotationContainer interface to the xml schema's xmlns
	 */
	class ConversionValuesAnnotationContainer 
		extends AnnotationContainer<ConversionValueAnnotation>
	{
		@Override
		protected String getNestedAnnotationsListName() {
			return CONVERSION_VALUES_LIST;
		}
		@Override
		protected String getElementName() {
			return EclipseLink.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES;
		}
		@Override
		protected String getNestedAnnotationName() {
			return ConversionValueAnnotation.ANNOTATION_NAME;
		}
		@Override
		protected ConversionValueAnnotation buildNestedAnnotation(int index) {
			return EclipseLinkSourceObjectTypeConverterAnnotation.this.buildConversionValue(index);
		}
	}
}
