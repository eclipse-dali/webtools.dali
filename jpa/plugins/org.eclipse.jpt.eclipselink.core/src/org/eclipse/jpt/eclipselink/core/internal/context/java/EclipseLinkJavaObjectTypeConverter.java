/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaObjectTypeConverter extends EclipseLinkJavaConverter
	implements EclipseLinkObjectTypeConverter
{	
	private String dataType;
	
	private String objectType;
	
	private String defaultObjectValue;
	
	private final List<EclipseLinkJavaConversionValue> conversionValues;
	
	
	public EclipseLinkJavaObjectTypeConverter(JavaJpaContextNode parent) {
		super(parent);
		this.conversionValues = new ArrayList<EclipseLinkJavaConversionValue>();
	}
	
	
	public String getType() {
		return EclipseLinkConverter.OBJECT_TYPE_CONVERTER;
	}

	@Override
	public String getAnnotationName() {
		return EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected EclipseLinkObjectTypeConverterAnnotation getAnnotation() {
		return (EclipseLinkObjectTypeConverterAnnotation) super.getAnnotation();
	}
	
	
	// **************** data type **********************************************
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		getAnnotation().setDataType(newDataType);
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	protected void setDataType_(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	
	// **************** object type ********************************************
	
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		getAnnotation().setObjectType(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	protected void setObjectType_(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	
	// **************** conversion values **************************************
	
	public ListIterator<EclipseLinkJavaConversionValue> conversionValues() {
		return new CloneListIterator<EclipseLinkJavaConversionValue>(this.conversionValues);
	}
	
	public int conversionValuesSize() {
		return this.conversionValues.size();
	}
	
	public EclipseLinkJavaConversionValue addConversionValue(int index) {
		EclipseLinkJavaConversionValue conversionValue = new EclipseLinkJavaConversionValue(this);
		this.conversionValues.add(index, conversionValue);
		EclipseLinkConversionValueAnnotation resourceConversionValue = getAnnotation().addConversionValue(index);
		conversionValue.initialize(resourceConversionValue);
		fireItemAdded(CONVERSION_VALUES_LIST, index, conversionValue);
		return conversionValue;
	}
	
	public EclipseLinkJavaConversionValue addConversionValue() {
		return this.addConversionValue(this.conversionValues.size());
	}
	
	protected void addConversionValue(int index, EclipseLinkJavaConversionValue conversionValue) {
		addItemToList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	protected void addConversionValue(EclipseLinkJavaConversionValue conversionValue) {
		this.addConversionValue(this.conversionValues.size(), conversionValue);
	}
	
	public void removeConversionValue(EclipseLinkConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValues.indexOf(conversionValue));
	}
	
	public void removeConversionValue(int index) {
		EclipseLinkJavaConversionValue removedConversionValue = this.conversionValues.remove(index);
		getAnnotation().removeConversionValue(index);
		fireItemRemoved(CONVERSION_VALUES_LIST, index, removedConversionValue);
	}
	
	protected void removeConversionValue_(EclipseLinkJavaConversionValue conversionValue) {
		removeItemFromList(conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	public void moveConversionValue(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.conversionValues, targetIndex, sourceIndex);
		getAnnotation().moveConversionValue(targetIndex, sourceIndex);
		fireItemMoved(CONVERSION_VALUES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<String> dataValues() {
		return new TransformationListIterator<EclipseLinkJavaConversionValue, String>(conversionValues()) {
			@Override
			protected String transform(EclipseLinkJavaConversionValue next) {
				return next.getDataValue();
			}
		};
	}
	
	
	// **************** default object value ***********************************
	
	public String getDefaultObjectValue() {
		return this.defaultObjectValue;
	}
	
	public void setDefaultObjectValue(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		getAnnotation().setDefaultObjectValue(newDefaultObjectValue);
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	protected void setDefaultObjectValue_(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(JavaResourcePersistentMember jrpm) {
		super.initialize(jrpm);
		EclipseLinkObjectTypeConverterAnnotation resourceConverter = getAnnotation();
		this.dataType = this.dataType(resourceConverter);
		this.objectType = this.objectType(resourceConverter);
		this.defaultObjectValue = this.defaultObjectValue(resourceConverter);
		this.initializeConversionValues(resourceConverter);
	}
	
	protected void initializeConversionValues(EclipseLinkObjectTypeConverterAnnotation resourceConverter) {
		if (resourceConverter == null) {
			return;
		}
		ListIterator<EclipseLinkConversionValueAnnotation> resourceConversionValues = resourceConverter.conversionValues();
		
		while(resourceConversionValues.hasNext()) {
			this.conversionValues.add(buildConversionValue(resourceConversionValues.next()));
		}
	}

	protected EclipseLinkJavaConversionValue buildConversionValue(EclipseLinkConversionValueAnnotation resourceConversionValue) {
		EclipseLinkJavaConversionValue conversionValue = new EclipseLinkJavaConversionValue(this);
		conversionValue.initialize(resourceConversionValue);
		return conversionValue;
	}
	
	@Override
	public void update(JavaResourcePersistentMember jrpm) {
		super.update(jrpm);
		EclipseLinkObjectTypeConverterAnnotation resourceConverter = getAnnotation();
		this.setDataType_(this.dataType(resourceConverter));
		this.setObjectType_(this.objectType(resourceConverter));
		this.setDefaultObjectValue_(this.defaultObjectValue(resourceConverter));
		this.updateConversionValues(resourceConverter);
	}
	
	protected void updateConversionValues(EclipseLinkObjectTypeConverterAnnotation resourceConverter) {
		ListIterator<EclipseLinkJavaConversionValue> contextConversionValues = conversionValues();
		ListIterator<EclipseLinkConversionValueAnnotation> resourceConversionValues = resourceConverter.conversionValues();
		while (contextConversionValues.hasNext()) {
			EclipseLinkJavaConversionValue conversionValues = contextConversionValues.next();
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
	
	protected String dataType(EclipseLinkObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getDataType();
	}
	
	protected String objectType(EclipseLinkObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getObjectType();
	}
	
	protected String defaultObjectValue(EclipseLinkObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getDefaultObjectValue();
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		for (Iterator<EclipseLinkJavaConversionValue> stream = conversionValues(); stream.hasNext();) {
			stream.next().validate(messages, reporter, astRoot);
		}
	}
}
