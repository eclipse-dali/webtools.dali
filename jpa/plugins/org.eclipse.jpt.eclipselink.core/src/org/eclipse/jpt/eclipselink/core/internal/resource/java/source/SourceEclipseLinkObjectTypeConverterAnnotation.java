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

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.AnnotationContainerTools;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.NestableEclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * org.eclipse.persistence.annotations.ObjectTypeConverter
 */
public final class SourceEclipseLinkObjectTypeConverterAnnotation
	extends SourceBaseEclipseLinkTypeConverterAnnotation
	implements EclipseLinkObjectTypeConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> DEFAULT_OBJECT_VALUE_ADAPTER = buildDefaultObjectValueAdapter();
	private final AnnotationElementAdapter<String> defaultObjectValueAdapter;
	private String defaultObjectValue;

	private final Vector<NestableEclipseLinkConversionValueAnnotation> conversionValues = new Vector<NestableEclipseLinkConversionValueAnnotation>();
	private final ConversionValuesAnnotationContainer conversionValuesContainer = new ConversionValuesAnnotationContainer();


	public SourceEclipseLinkObjectTypeConverterAnnotation(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.defaultObjectValueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(member, DEFAULT_OBJECT_VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.defaultObjectValue = this.buildDefaultObjectValue(astRoot);
		AnnotationContainerTools.initialize(this.conversionValuesContainer, astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncDefaultObjectValue(this.buildDefaultObjectValue(astRoot));
		AnnotationContainerTools.synchronize(this.conversionValuesContainer, astRoot);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.defaultObjectValue == null) &&
				this.conversionValues.isEmpty();
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
		return this.getElementTextRange(DEFAULT_OBJECT_VALUE_ADAPTER, astRoot);
	}

	// ***** conversion values
	public ListIterator<EclipseLinkConversionValueAnnotation> conversionValues() {
		return new CloneListIterator<EclipseLinkConversionValueAnnotation>(this.conversionValues);
	}

	Iterable<NestableEclipseLinkConversionValueAnnotation> getNestableConversionValues() {
		return new LiveCloneIterable<NestableEclipseLinkConversionValueAnnotation>(this.conversionValues);
	}

	public int conversionValuesSize() {
		return this.conversionValues.size();
	}

	public NestableEclipseLinkConversionValueAnnotation conversionValueAt(int index) {
		return this.conversionValues.get(index);
	}

	public int indexOfConversionValue(EclipseLinkConversionValueAnnotation conversionValue) {
		return this.conversionValues.indexOf(conversionValue);
	}

	public NestableEclipseLinkConversionValueAnnotation addConversionValue(int index) {
		return (NestableEclipseLinkConversionValueAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.conversionValuesContainer);
	}

	NestableEclipseLinkConversionValueAnnotation addConversionValue_() {
		return this.addConversionValue_(this.conversionValues.size());
	}

	private NestableEclipseLinkConversionValueAnnotation addConversionValue_(int index) {
		NestableEclipseLinkConversionValueAnnotation conversionValue = this.buildConversionValue(index);
		this.conversionValues.add(index, conversionValue);
		return conversionValue;
	}

	void syncAddConversionValue(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		int index = this.conversionValues.size();
		NestableEclipseLinkConversionValueAnnotation conversionValue = this.addConversionValue_(index);
		conversionValue.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(CONVERSION_VALUES_LIST, index, conversionValue);
	}

	private NestableEclipseLinkConversionValueAnnotation buildConversionValue(int index) {
		return SourceEclipseLinkConversionValueAnnotation.createConversionValue(this, this.annotatedElement, this.daa, index);
	}

	void conversionValueAdded(int index, NestableEclipseLinkConversionValueAnnotation conversionValue) {
		this.fireItemAdded(CONVERSION_VALUES_LIST, index, conversionValue);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.conversionValuesContainer);
	}

	NestableEclipseLinkConversionValueAnnotation moveConversionValue_(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.conversionValues, targetIndex, sourceIndex).get(targetIndex);
	}

	public void removeConversionValue(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.conversionValuesContainer);
	}

	NestableEclipseLinkConversionValueAnnotation removeConversionValue_(int index) {
		return this.conversionValues.remove(index);
	}

	void syncRemoveConversionValues(int index) {
		this.removeItemsFromList(index, this.conversionValues, CONVERSION_VALUES_LIST);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildDefaultObjectValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE, StringExpressionConverter.instance());
	}


	// ********** conversion value container **********

	/**
	 * adapt the AnnotationContainer interface to the object type converter's
	 * conversion values
	 */
	class ConversionValuesAnnotationContainer
		implements AnnotationContainer<NestableEclipseLinkConversionValueAnnotation> 
	{
		public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.getAstAnnotation(astRoot);
		}

		public String getElementName() {
			return EclipseLink.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES;
		}

		public String getNestedAnnotationName() {
			return EclipseLinkConversionValueAnnotation.ANNOTATION_NAME;
		}

		public Iterable<NestableEclipseLinkConversionValueAnnotation> getNestedAnnotations() {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.getNestableConversionValues();
		}

		public int getNestedAnnotationsSize() {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.conversionValuesSize();
		}

		public NestableEclipseLinkConversionValueAnnotation addNestedAnnotation() {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.addConversionValue_();
		}

		public void syncAddNestedAnnotation(Annotation astAnnotation) {
			SourceEclipseLinkObjectTypeConverterAnnotation.this.syncAddConversionValue(astAnnotation);
		}

		public NestableEclipseLinkConversionValueAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.moveConversionValue_(targetIndex, sourceIndex);
		}

		public NestableEclipseLinkConversionValueAnnotation removeNestedAnnotation(int index) {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.removeConversionValue_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceEclipseLinkObjectTypeConverterAnnotation.this.syncRemoveConversionValues(index);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}

}
