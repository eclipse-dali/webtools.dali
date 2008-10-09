/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConversionValue;
import org.eclipse.jpt.eclipselink.core.context.java.JavaObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaObjectTypeConverter extends AbstractJavaJpaContextNode implements JavaObjectTypeConverter
{	
	private JavaResourcePersistentMember resourcePersistentMember;
	
	private String name;
	
	private String dataType;
	
	private String objectType;

	private String defaultObjectValue;

	private final List<JavaConversionValue> conversionValues;
	
	public EclipseLinkJavaObjectTypeConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		super(parent);
		this.conversionValues = new ArrayList<JavaConversionValue>();
		this.initialize(jrpm);
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	public String getType() {
		return EclipseLinkConverter.OBJECT_TYPE_CONVERTER;
	}

	protected String getAnnotationName() {
		return ObjectTypeConverterAnnotation.ANNOTATION_NAME;
	}
		
	public void addToResourceModel() {
		this.resourcePersistentMember.addAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistentMember.removeAnnotation(getAnnotationName());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceConverter().getTextRange(astRoot);
	}

	protected ObjectTypeConverterAnnotation getResourceConverter() {
		return (ObjectTypeConverterAnnotation) this.resourcePersistentMember.getAnnotation(getAnnotationName());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		getResourceConverter().setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		getResourceConverter().setDataType(newDataType);
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	protected void setDataType_(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		getResourceConverter().setObjectType(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	protected void setObjectType_(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}

	public ListIterator<JavaConversionValue> conversionValues() {
		return new CloneListIterator<JavaConversionValue>(this.conversionValues);
	}
	
	public int conversionValuesSize() {
		return this.conversionValues.size();
	}
	
	public JavaConversionValue addConversionValue(int index) {
		JavaConversionValue conversionValue = getJpaFactory().buildJavaConversionValue(this);
		this.conversionValues.add(index, conversionValue);
		ConversionValueAnnotation resourceConversionValue = getResourceConverter().addConversionValue(index);
		conversionValue.initialize(resourceConversionValue);
		fireItemAdded(CONVERSION_VALUES_LIST, index, conversionValue);
		return conversionValue;
	}

	public JavaConversionValue addConversionValue() {
		return this.addConversionValue(this.conversionValues.size());
	}
	
	protected void addConversionValue(int index, JavaConversionValue conversionValue) {
		addItemToList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	protected void addConversionValue(JavaConversionValue conversionValue) {
		this.addConversionValue(this.conversionValues.size(), conversionValue);
	}
	
	public void removeConversionValue(ConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValues.indexOf(conversionValue));
	}
	
	public void removeConversionValue(int index) {
		JavaConversionValue removedConversionValue = this.conversionValues.remove(index);
		getResourceConverter().removeConversionValue(index);
		fireItemRemoved(CONVERSION_VALUES_LIST, index, removedConversionValue);
	}
	
	protected void removeConversionValue_(JavaConversionValue conversionValue) {
		removeItemFromList(conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	public void moveConversionValue(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.conversionValues, targetIndex, sourceIndex);
		getResourceConverter().moveConversionValue(targetIndex, sourceIndex);
		fireItemMoved(CONVERSION_VALUES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<String> dataValues() {
		return new TransformationListIterator<JavaConversionValue, String>(conversionValues()) {
			@Override
			protected String transform(JavaConversionValue next) {
				return next.getDataValue();
			}
		};
	}
		
	public String getDefaultObjectValue() {
		return this.defaultObjectValue;
	}
	
	public void setDefaultObjectValue(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		getResourceConverter().setDefaultObjectValue(newDefaultObjectValue);
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	protected void setDefaultObjectValue_(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	protected void initialize(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		ObjectTypeConverterAnnotation resourceConverter = getResourceConverter();
		this.name = this.name(resourceConverter);
		this.dataType = this.dataType(resourceConverter);
		this.objectType = this.objectType(resourceConverter);
		this.defaultObjectValue = this.defaultObjectValue(resourceConverter);
		this.initializeConversionValues(resourceConverter);
	}
	
	protected void initializeConversionValues(ObjectTypeConverterAnnotation resourceConverter) {
		if (resourceConverter == null) {
			return;
		}
		ListIterator<ConversionValueAnnotation> resourceConversionValues = resourceConverter.conversionValues();
		
		while(resourceConversionValues.hasNext()) {
			this.conversionValues.add(buildConversionValue(resourceConversionValues.next()));
		}
	}

	protected JavaConversionValue buildConversionValue(ConversionValueAnnotation resourceConversionValue) {
		JavaConversionValue conversionValue = getJpaFactory().buildJavaConversionValue(this);
		conversionValue.initialize(resourceConversionValue);
		return conversionValue;
	}

	public void update(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		ObjectTypeConverterAnnotation resourceConverter = getResourceConverter();
		this.setName_(this.name(resourceConverter));
		this.setDataType_(this.dataType(resourceConverter));
		this.setObjectType_(this.objectType(resourceConverter));
		this.setDefaultObjectValue_(this.defaultObjectValue(resourceConverter));
		this.updateConversionValues(resourceConverter);
	}

	
	protected void updateConversionValues(ObjectTypeConverterAnnotation resourceConverter) {
		ListIterator<JavaConversionValue> contextConversionValues = conversionValues();
		ListIterator<ConversionValueAnnotation> resourceConversionValues = resourceConverter.conversionValues();
		while (contextConversionValues.hasNext()) {
			JavaConversionValue conversionValues = contextConversionValues.next();
			if (resourceConversionValues.hasNext()) {
				conversionValues.update(resourceConversionValues.next());
			}
			else {
				removeConversionValue_(conversionValues);
			}
		}
		
		while (resourceConversionValues.hasNext()) {
			addConversionValue(buildConversionValue(resourceConversionValues.next()));
		}
	}

	protected String name(ObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getName();
	}

	protected String dataType(ObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getDataType();
	}

	protected String objectType(ObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getObjectType();
	}

	protected String defaultObjectValue(ObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getDefaultObjectValue();
	}

	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		for (Iterator<JavaConversionValue> stream = conversionValues(); stream.hasNext();) {
			stream.next().validate(messages, astRoot);
		}
	}
}
