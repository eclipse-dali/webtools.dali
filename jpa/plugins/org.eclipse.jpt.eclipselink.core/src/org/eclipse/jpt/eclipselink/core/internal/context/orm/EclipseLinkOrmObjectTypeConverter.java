/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmObjectTypeConverter extends AbstractXmlContextNode implements ObjectTypeConverter, EclipseLinkOrmConverter
{	
	private XmlObjectTypeConverter resourceConverter;
	
	private String name;
	
	private String dataType;
	
	private String objectType;
	
	private String defaultObjectValue;

	private final List<EclipseLinkOrmConversionValue> conversionValues;

	public EclipseLinkOrmObjectTypeConverter(XmlContextNode parent, XmlObjectTypeConverter resourceConverter) {
		super(parent);
		this.conversionValues = new ArrayList<EclipseLinkOrmConversionValue>();
		this.initialize(resourceConverter);
	}

	public String getType() {
		return EclipseLinkConverter.OBJECT_TYPE_CONVERTER;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceConverter.setName(newName);
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
		this.resourceConverter.setDataType(newDataType);
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
		this.resourceConverter.setObjectType(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	protected void setObjectType_(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}

	public ListIterator<EclipseLinkOrmConversionValue> conversionValues() {
		return new CloneListIterator<EclipseLinkOrmConversionValue>(this.conversionValues);
	}

	public int conversionValuesSize() {
		return this.conversionValues.size();
	}
	
	public EclipseLinkOrmConversionValue addConversionValue(int index) {
		XmlConversionValue resourceConversionValue = EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValueImpl();
		EclipseLinkOrmConversionValue contextConversionValue = buildConversionValue(resourceConversionValue);
		this.conversionValues.add(index, contextConversionValue);
		this.resourceConverter.getConversionValues().add(index, resourceConversionValue);
		this.fireItemAdded(CONVERSION_VALUES_LIST, index, contextConversionValue);
		return contextConversionValue;
	}

	public EclipseLinkOrmConversionValue addConversionValue() {
		return this.addConversionValue(this.conversionValues.size());
	}
	
	protected void addConversionValue(int index, EclipseLinkOrmConversionValue conversionValue) {
		addItemToList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	protected void addConversionValue(EclipseLinkOrmConversionValue conversionValue) {
		this.addConversionValue(this.conversionValues.size(), conversionValue);
	}
	
	public void removeConversionValue(int index) {
		EclipseLinkOrmConversionValue removedJoinColumn = this.conversionValues.remove(index);
		this.resourceConverter.getConversionValues().remove(index);
		fireItemRemoved(CONVERSION_VALUES_LIST, index, removedJoinColumn);
	}

	public void removeConversionValue(ConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValues.indexOf(conversionValue));
	}
	
	protected void removeConversionValue_(ConversionValue conversionValue) {
		removeItemFromList(conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	public void moveConversionValue(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.conversionValues, targetIndex, sourceIndex);
		this.resourceConverter.getConversionValues().move(targetIndex, sourceIndex);
		fireItemMoved(CONVERSION_VALUES_LIST, targetIndex, sourceIndex);		
	}

	public ListIterator<String> dataValues() {
		return new TransformationListIterator<EclipseLinkOrmConversionValue, String>(conversionValues()) {
			@Override
			protected String transform(EclipseLinkOrmConversionValue next) {
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
		this.resourceConverter.setDefaultObjectValue(newDefaultObjectValue);
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	protected void setDefaultObjectValue_(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	
	protected void initialize(XmlObjectTypeConverter resourceConverter) {
		this.resourceConverter = resourceConverter;
		this.name = this.name();
		this.dataType = this.dataType();
		this.objectType = this.objectType();
		this.defaultObjectValue = this.defaultObjectValue();
		this.initializeConversionValues();
	}
	
	protected void initializeConversionValues() {	
		for (XmlConversionValue resourceConversionValue : this.resourceConverter.getConversionValues()) {
			this.conversionValues.add(buildConversionValue(resourceConversionValue));
		}
	}
	
	public void update() {
		this.setName_(this.name());
		this.setDataType_(this.dataType());
		this.setObjectType_(this.objectType());
		this.setDefaultObjectValue_(this.defaultObjectValue());
		this.updateConversionValues();
	}
	
	protected void updateConversionValues( ) {
		ListIterator<EclipseLinkOrmConversionValue> contextConversionValues = conversionValues();
		ListIterator<XmlConversionValue> resourceConversionValues = new CloneListIterator<XmlConversionValue>(this.resourceConverter.getConversionValues());//prevent ConcurrentModificiationException
		while (contextConversionValues.hasNext()) {
			EclipseLinkOrmConversionValue conversionValues = contextConversionValues.next();
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

	protected EclipseLinkOrmConversionValue buildConversionValue(XmlConversionValue resourceConversionValue) {
		EclipseLinkOrmConversionValue conversionValue = new EclipseLinkOrmConversionValue(this);
		conversionValue.initialize(resourceConversionValue);
		return conversionValue;
	}

	protected String name() {
		return this.resourceConverter.getName();
	}

	protected String dataType() {
		return this.resourceConverter.getDataType();
	}

	protected String objectType() {
		return this.resourceConverter.getObjectType();
	}

	protected String defaultObjectValue() {
		return this.resourceConverter.getDefaultObjectValue();
	}

	public TextRange getValidationTextRange() {
		return this.resourceConverter.getValidationTextRange();
	}
	

	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		for (Iterator<EclipseLinkOrmConversionValue> stream = conversionValues(); stream.hasNext();) {
			stream.next().validate(messages);
		}
	}
}
