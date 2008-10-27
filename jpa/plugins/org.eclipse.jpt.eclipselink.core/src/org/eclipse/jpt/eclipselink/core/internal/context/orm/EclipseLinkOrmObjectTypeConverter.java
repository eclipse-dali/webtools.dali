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
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;

public class EclipseLinkOrmObjectTypeConverter extends AbstractXmlContextNode implements ObjectTypeConverter, EclipseLinkOrmConverter
{	
	private XmlConvertibleMapping resourceMapping;
	
	private String name;
	
	private String dataType;
	
	private String objectType;
	
	private String defaultObjectValue;

	private final List<EclipseLinkOrmConversionValue> conversionValues;

	public EclipseLinkOrmObjectTypeConverter(XmlContextNode parent, XmlConvertibleMapping resourceMapping) {
		super(parent);
		this.conversionValues = new ArrayList<EclipseLinkOrmConversionValue>();
		this.initialize(resourceMapping);
	}

	public String getType() {
		return EclipseLinkConverter.OBJECT_TYPE_CONVERTER;
	}
		
	public void addToResourceModel() {
		this.resourceMapping.setObjectTypeConverter(EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverterImpl());
	}
	
	public void removeFromResourceModel() {
		this.resourceMapping.setObjectTypeConverter(null);
	}

	protected XmlObjectTypeConverter getResourceConverter() {
		return this.resourceMapping.getObjectTypeConverter();
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

	public ListIterator<EclipseLinkOrmConversionValue> conversionValues() {
		return new CloneListIterator<EclipseLinkOrmConversionValue>(this.conversionValues);
	}

	public int conversionValuesSize() {
		return this.conversionValues.size();
	}

	public ConversionValue addConversionValue(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public ConversionValue addConversionValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void moveConversionValue(int targetIndex, int sourceIndex) {
		// TODO Auto-generated method stub
		
	}

	public void removeConversionValue(int index) {
		// TODO Auto-generated method stub
		
	}

	public void removeConversionValue(ConversionValue conversionValue) {
		// TODO Auto-generated method stub
		
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
		getResourceConverter().setDefaultObjectValue(newDefaultObjectValue);
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	protected void setDefaultObjectValue_(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	
	protected void initialize(XmlConvertibleMapping resourceMapping) {
		this.resourceMapping = resourceMapping;
		this.name = this.name();
		this.dataType = this.dataType();
		this.objectType = this.objectType();
		this.defaultObjectValue = this.defaultObjectValue();
	}
	
	public void update() {
		this.setName_(this.name());
		this.setDataType_(this.dataType());
		this.setObjectType_(this.objectType());
		this.setDefaultObjectValue_(this.defaultObjectValue());
	}

	protected String name() {
		return getResourceConverter() == null ? null : getResourceConverter().getName();
	}

	protected String dataType() {
		return getResourceConverter() == null ? null : getResourceConverter().getDataType();
	}

	protected String objectType() {
		return getResourceConverter() == null ? null : getResourceConverter().getObjectType();
	}

	protected String defaultObjectValue() {
		return getResourceConverter() == null ? null : getResourceConverter().getDefaultObjectValue();
	}

	public TextRange getValidationTextRange() {
		return getResourceConverter().getValidationTextRange();
	}
}
