/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.core.internal.resource.java.AbstractResourceAnnotation;
import org.eclipse.jpt.core.internal.resource.java.ContainerAnnotationTools;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.NestableConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public class ObjectTypeConverterImpl extends AbstractResourceAnnotation<Attribute> implements ObjectTypeConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> nameAdapter;
	private final AnnotationElementAdapter<String> dataTypeAdapter;
	private final AnnotationElementAdapter<String> objectTypeAdapter;
	private final AnnotationElementAdapter<String> defaultObjectValueAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private static final DeclarationAnnotationElementAdapter<String> DATA_TYPE_ADAPTER = buildDataTypeAdapter();
	private static final DeclarationAnnotationElementAdapter<String> OBJECT_TYPE_ADAPTER = buildObjectTypeAdapter();
	private static final DeclarationAnnotationElementAdapter<String> DEFAULT_OBJECT_VALUE_ADAPTER = buildDefaultObjectValueAdapter();

	
	private String name;
	private String dataType;
	private String objectType;
	private String defaultObjectValue;
	
	private final List<NestableConversionValue> conversionValues;
	private final ConversionValuesContainerAnnotation conversionValuesContainerAnnotation;
	
	protected ObjectTypeConverterImpl(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, NAME_ADAPTER);
		this.dataTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, DATA_TYPE_ADAPTER);
		this.objectTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, OBJECT_TYPE_ADAPTER);
		this.defaultObjectValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, DEFAULT_OBJECT_VALUE_ADAPTER);
		this.conversionValues = new ArrayList<NestableConversionValue>();
		this.conversionValuesContainerAnnotation = new ConversionValuesContainerAnnotation();
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.dataType = this.dataType(astRoot);
		this.objectType = this.objectType(astRoot);
		this.defaultObjectValue = this.defaultObjectValue(astRoot);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.conversionValuesContainerAnnotation);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** ObjectTypeConverter implementation ****************
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		if (attributeValueHasNotChanged(this.name, newName)) {
			return;
		}
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String newDataType) {
		if (attributeValueHasNotChanged(this.dataType, newDataType)) {
			return;
		}
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		this.dataTypeAdapter.setValue(newDataType);
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String newObjectType) {
		if (attributeValueHasNotChanged(this.objectType, newObjectType)) {
			return;
		}
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		this.objectTypeAdapter.setValue(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	public String getDefaultObjectValue() {
		return this.defaultObjectValue;
	}
	
	public void setDefaultObjectValue(String newDefaultObjectValue) {
		if (attributeValueHasNotChanged(this.defaultObjectValue, newDefaultObjectValue)) {
			return;
		}
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		this.defaultObjectValueAdapter.setValue(newDefaultObjectValue);
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	public ListIterator<ConversionValueAnnotation> conversionValues() {
		return new CloneListIterator<ConversionValueAnnotation>(this.conversionValues);
	}
	
	public int conversionValuesSize() {
		return this.conversionValues.size();
	}
	
	public NestableConversionValue conversionValueAt(int index) {
		return this.conversionValues.get(index);
	}
	
	public int indexOfConversionValue(ConversionValueAnnotation conversionValue) {
		return this.conversionValues.indexOf(conversionValue);
	}
	
	public NestableConversionValue addConversionValue(int index) {
		NestableConversionValue conversionValue = (NestableConversionValue) ContainerAnnotationTools.addNestedAnnotation(index, this.conversionValuesContainerAnnotation);
		fireItemAdded(AssociationOverrideAnnotation.JOIN_COLUMNS_LIST, index, conversionValue);
		return conversionValue;
	}
	
	private void addConversionValue(int index, NestableConversionValue conversionValue) {
		addItemToList(index, conversionValue, this.conversionValues, AssociationOverrideAnnotation.JOIN_COLUMNS_LIST);
	}
	
	public void removeConversionValue(int index) {
		NestableConversionValue conversionValue = this.conversionValues.get(index);
		removeConversionValue(conversionValue);
		conversionValue.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.conversionValuesContainerAnnotation);
	}

	private void removeConversionValue(NestableConversionValue conversionValue) {
		removeItemFromList(conversionValue, this.conversionValues, AssociationOverrideAnnotation.JOIN_COLUMNS_LIST);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		moveConversionValueInternal(targetIndex, sourceIndex);
		ContainerAnnotationTools.synchAnnotationsAfterMove(targetIndex, sourceIndex, this.conversionValuesContainerAnnotation);
		fireItemMoved(AssociationOverrideAnnotation.JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}
	
	protected void moveConversionValueInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.conversionValues, targetIndex, sourceIndex);
	}

	protected NestableConversionValue createConversionValue(int index) {
		return ConversionValueImpl.createConversionValue(this, getMember(), getDeclarationAnnotationAdapter(), index);
	}

	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
	}

	public TextRange getDataTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(DATA_TYPE_ADAPTER, astRoot);
	}
	
	public TextRange getObjectTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(OBJECT_TYPE_ADAPTER, astRoot);
	}
	
	public TextRange getDefaultObjectValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(DEFAULT_OBJECT_VALUE_ADAPTER, astRoot);		
	}
	
	public void update(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setDataType(this.dataType(astRoot));
		this.setObjectType(this.objectType(astRoot));
		this.setDefaultObjectValue(this.defaultObjectValue(astRoot));
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.conversionValuesContainerAnnotation);
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String dataType(CompilationUnit astRoot) {
		return this.dataTypeAdapter.getValue(astRoot);
	}
	
	protected String objectType(CompilationUnit astRoot) {
		return this.objectTypeAdapter.getValue(astRoot);
	}
	
	protected String defaultObjectValue(CompilationUnit astRoot) {
		return this.defaultObjectValueAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.OBJECT_TYPE_CONVERTER__NAME, false, StringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildDataTypeAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.OBJECT_TYPE_CONVERTER__DATE_TYPE, false, SimpleTypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildObjectTypeAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.OBJECT_TYPE_CONVERTER__OBJECT_TYPE, false, SimpleTypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildDefaultObjectValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE, false, StringExpressionConverter.instance());
	}
	
	public static class ObjectTypeConverterAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final ObjectTypeConverterAnnotationDefinition INSTANCE = new ObjectTypeConverterAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static ObjectTypeConverterAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private ObjectTypeConverterAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new ObjectTypeConverterImpl((JavaResourcePersistentAttribute) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
	private class ConversionValuesContainerAnnotation extends AbstractJavaResourceNode 
		implements ContainerAnnotation<NestableConversionValue> 
	{
		public ConversionValuesContainerAnnotation() {
			super(ObjectTypeConverterImpl.this);
		}
	
		public void initialize(CompilationUnit astRoot) {
			//nothing to initialize
		}
		
		public NestableConversionValue addInternal(int index) {
			NestableConversionValue conversionValue = ObjectTypeConverterImpl.this.createConversionValue(index);
			ObjectTypeConverterImpl.this.conversionValues.add(index, conversionValue);
			return conversionValue;
		}
		
		public NestableConversionValue add(int index) {
			NestableConversionValue conversionValue = ObjectTypeConverterImpl.this.createConversionValue(index);
			ObjectTypeConverterImpl.this.addConversionValue(index, conversionValue);
			return conversionValue;
		}
		
		public int indexOf(NestableConversionValue conversionValue) {
			return ObjectTypeConverterImpl.this.indexOfConversionValue(conversionValue);
		}
	
		public void move(int targetIndex, int sourceIndex) {
			ObjectTypeConverterImpl.this.moveConversionValue(targetIndex, sourceIndex);
		}
	
		public void moveInternal(int targetIndex, int sourceIndex) {
			ObjectTypeConverterImpl.this.moveConversionValueInternal(targetIndex, sourceIndex);			
		}
		
		public NestableConversionValue nestedAnnotationAt(int index) {
			return ObjectTypeConverterImpl.this.conversionValueAt(index);
		}
	
		public ListIterator<NestableConversionValue> nestedAnnotations() {
			return new CloneListIterator<NestableConversionValue>(ObjectTypeConverterImpl.this.conversionValues);
		}
	
		public int nestedAnnotationsSize() {
			return conversionValuesSize();
		}
	
		public void remove(int index) {
			this.remove(nestedAnnotationAt(index));
		}		
	
		public void remove(NestableConversionValue conversionValue) {
			ObjectTypeConverterImpl.this.removeConversionValue(conversionValue);
		}
	
		public String getAnnotationName() {
			return ObjectTypeConverterImpl.this.getAnnotationName();
		}
	
		public String getNestableAnnotationName() {
			return EclipseLinkJPA.CONVERSION_VALUE;
		}
	
		public NestableConversionValue nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			for (NestableConversionValue conversionValue : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == conversionValue.getJdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return conversionValue;
				}
			}
			return null;
		}
	
		public org.eclipse.jdt.core.dom.Annotation getJdtAnnotation(CompilationUnit astRoot) {
			return ObjectTypeConverterImpl.this.getJdtAnnotation(astRoot);
		}
	
		public void newAnnotation() {
			ObjectTypeConverterImpl.this.newAnnotation();
		}
	
		public void removeAnnotation() {
			ObjectTypeConverterImpl.this.removeAnnotation();
		}
	
		public void update(CompilationUnit astRoot) {
			ObjectTypeConverterImpl.this.update(astRoot);
		}
		
		public TextRange getTextRange(CompilationUnit astRoot) {
			return ObjectTypeConverterImpl.this.getTextRange(astRoot);
		}
		
		public String getElementName() {
			return EclipseLinkJPA.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES;
		}
	}

}
