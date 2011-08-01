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
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.ObjectTypeConverter</code>
 */
public final class SourceEclipseLinkObjectTypeConverterAnnotation
	extends SourceBaseEclipseLinkTypeConverterAnnotation
	implements EclipseLinkObjectTypeConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> DEFAULT_OBJECT_VALUE_ADAPTER = buildDefaultObjectValueAdapter();
	private final AnnotationElementAdapter<String> defaultObjectValueAdapter;
	private String defaultObjectValue;
	private TextRange defaultObjectValueTextRange;

	private final ConversionValuesAnnotationContainer conversionValuesContainer = new ConversionValuesAnnotationContainer();


	public SourceEclipseLinkObjectTypeConverterAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.defaultObjectValueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, DEFAULT_OBJECT_VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.defaultObjectValue = this.buildDefaultObjectValue(astRoot);
		this.defaultObjectValueTextRange = this.buildDefaultObjectValueTextRange(astRoot);
		this.conversionValuesContainer.initialize(this.getAstAnnotation(astRoot));
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncDefaultObjectValue(this.buildDefaultObjectValue(astRoot));
		this.defaultObjectValueTextRange = this.buildDefaultObjectValueTextRange(astRoot);
		this.conversionValuesContainer.synchronize(this.getAstAnnotation(astRoot));
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
		if (this.attributeValueHasChanged(this.defaultObjectValue, defaultObjectValue)) {
			this.defaultObjectValue = defaultObjectValue;
			this.defaultObjectValueAdapter.setValue(defaultObjectValue);
		}
	}

	private void syncDefaultObjectValue(String astDefaultObjectValue) {
		String old = this.defaultObjectValue;
		this.defaultObjectValue = astDefaultObjectValue;
		this.firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, old, astDefaultObjectValue);
	}

	private String buildDefaultObjectValue(CompilationUnit astRoot) {
		return this.defaultObjectValueAdapter.getValue(astRoot);
	}

	public TextRange getDefaultObjectValueTextRange(CompilationUnit astRoot) {
		return this.defaultObjectValueTextRange;
	}

	private TextRange buildDefaultObjectValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(DEFAULT_OBJECT_VALUE_ADAPTER, astRoot);
	}

	// ***** conversion values

	public ListIterable<EclipseLinkConversionValueAnnotation> getConversionValues() {
		return this.conversionValuesContainer.getNestedAnnotations();
	}

	public int getConversionValuesSize() {
		return this.conversionValuesContainer.getNestedAnnotationsSize();
	}

	public EclipseLinkConversionValueAnnotation conversionValueAt(int index) {
		return this.conversionValuesContainer.nestedAnnotationAt(index);
	}

	public EclipseLinkConversionValueAnnotation addConversionValue(int index) {
		return this.conversionValuesContainer.addNestedAnnotation(index);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		this.conversionValuesContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeConversionValue(int index) {
		this.conversionValuesContainer.removeNestedAnnotation(index);
	}

	protected EclipseLinkConversionValueAnnotation buildConversionValue(int index) {
		return SourceEclipseLinkConversionValueAnnotation.buildNestedSourceConversionValueAnnotation(
			this, this.annotatedElement, this.buildConversionValueIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildConversionValueIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, EclipseLink.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES, index, EclipseLink.CONVERSION_VALUE);
	}



	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildDefaultObjectValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE, StringExpressionConverter.instance());
	}


	// ********** hint container **********
	/**
	 * adapt the AnnotationContainer interface to the xml schema's xmlns
	 */
	class ConversionValuesAnnotationContainer 
		extends AnnotationContainer<EclipseLinkConversionValueAnnotation>
	{
		@Override
		protected String getAnnotationsPropertyName() {
			return CONVERSION_VALUES_LIST;
		}
		@Override
		protected String getElementName() {
			return EclipseLink.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES;
		}
		@Override
		protected String getNestedAnnotationName() {
			return EclipseLinkConversionValueAnnotation.ANNOTATION_NAME;
		}
		@Override
		protected EclipseLinkConversionValueAnnotation buildNestedAnnotation(int index) {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.buildConversionValue(index);
		}
	}
}
