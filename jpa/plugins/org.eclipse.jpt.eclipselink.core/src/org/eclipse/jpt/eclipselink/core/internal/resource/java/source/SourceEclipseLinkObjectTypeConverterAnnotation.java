/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.AnnotationContainerTools;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.NestableEclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
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
		this.defaultObjectValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, DEFAULT_OBJECT_VALUE_ADAPTER);
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
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setDefaultObjectValue(this.buildDefaultObjectValue(astRoot));
		AnnotationContainerTools.update(this.conversionValuesContainer, astRoot);
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
		if (this.attributeValueHasNotChanged(this.defaultObjectValue, defaultObjectValue)) {
			return;
		}
		String old = this.defaultObjectValue;
		this.defaultObjectValue = defaultObjectValue;
		this.defaultObjectValueAdapter.setValue(defaultObjectValue);
		this.firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, old, defaultObjectValue);
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

	ListIterator<NestableEclipseLinkConversionValueAnnotation> nestableConversionValues() {
		return new CloneListIterator<NestableEclipseLinkConversionValueAnnotation>(this.conversionValues);
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

	NestableEclipseLinkConversionValueAnnotation addConversionValueInternal() {
		NestableEclipseLinkConversionValueAnnotation conversionValue = this.buildConversionValue(this.conversionValues.size());
		this.conversionValues.add(conversionValue);
		return conversionValue;
	}

	private NestableEclipseLinkConversionValueAnnotation buildConversionValue(int index) {
		return SourceEclipseLinkConversionValueAnnotation.createConversionValue(this, this.member, this.daa, index);
	}

	void conversionValueAdded(int index, NestableEclipseLinkConversionValueAnnotation conversionValue) {
		this.fireItemAdded(CONVERSION_VALUES_LIST, index, conversionValue);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.conversionValuesContainer);
	}

	NestableEclipseLinkConversionValueAnnotation moveConversionValueInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.conversionValues, targetIndex, sourceIndex).get(targetIndex);
	}

	void conversionValueMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(CONVERSION_VALUES_LIST, targetIndex, sourceIndex);
	}

	public void removeConversionValue(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.conversionValuesContainer);
	}

	NestableEclipseLinkConversionValueAnnotation removeConversionValueInternal(int index) {
		return this.conversionValues.remove(index);
	}

	void conversionValueRemoved(int index, NestableEclipseLinkConversionValueAnnotation conversionValue) {
		this.fireItemRemoved(CONVERSION_VALUES_LIST, index, conversionValue);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildDefaultObjectValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE, false, StringExpressionConverter.instance());
	}


	// ********** conversion value container **********

	/**
	 * adapt the AnnotationContainer interface to the object type converter's
	 * conversion values
	 */
	class ConversionValuesAnnotationContainer
		implements AnnotationContainer<NestableEclipseLinkConversionValueAnnotation> 
	{
		public String getContainerAnnotationName() {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.getAnnotationName();
		}

		public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.getJdtAnnotation(astRoot);
		}

		public String getElementName() {
			return EclipseLink.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES;
		}

		public String getNestableAnnotationName() {
			return EclipseLinkConversionValueAnnotation.ANNOTATION_NAME;
		}

		public ListIterator<NestableEclipseLinkConversionValueAnnotation> nestedAnnotations() {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.nestableConversionValues();
		}

		public int nestedAnnotationsSize() {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.conversionValuesSize();
		}

		public NestableEclipseLinkConversionValueAnnotation addNestedAnnotationInternal() {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.addConversionValueInternal();
		}

		public void nestedAnnotationAdded(int index, NestableEclipseLinkConversionValueAnnotation nestedAnnotation) {
			SourceEclipseLinkObjectTypeConverterAnnotation.this.conversionValueAdded(index, nestedAnnotation);
		}

		public NestableEclipseLinkConversionValueAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.moveConversionValueInternal(targetIndex, sourceIndex);
		}

		public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
			SourceEclipseLinkObjectTypeConverterAnnotation.this.conversionValueMoved(targetIndex, sourceIndex);
		}

		public NestableEclipseLinkConversionValueAnnotation removeNestedAnnotationInternal(int index) {
			return SourceEclipseLinkObjectTypeConverterAnnotation.this.removeConversionValueInternal(index);
		}

		public void nestedAnnotationRemoved(int index, NestableEclipseLinkConversionValueAnnotation nestedAnnotation) {
			SourceEclipseLinkObjectTypeConverterAnnotation.this.conversionValueRemoved(index, nestedAnnotation);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}

}
