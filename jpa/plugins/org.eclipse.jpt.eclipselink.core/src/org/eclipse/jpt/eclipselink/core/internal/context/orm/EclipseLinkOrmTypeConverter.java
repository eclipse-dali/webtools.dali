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

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;

public class EclipseLinkOrmTypeConverter extends AbstractXmlContextNode implements TypeConverter, EclipseLinkOrmConverter
{	
	private XmlConvertibleMapping resourceMapping;
	
	private String name;
	
	private String dataType;
	
	private String objectType;

	public EclipseLinkOrmTypeConverter(XmlContextNode parent, XmlConvertibleMapping resourceMapping) {
		super(parent);
		this.initialize(resourceMapping);
	}

	public String getType() {
		return EclipseLinkConverter.TYPE_CONVERTER;
	}
		
	public void addToResourceModel() {
		this.resourceMapping.setTypeConverter(EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverterImpl());
	}
	
	public void removeFromResourceModel() {
		this.resourceMapping.setTypeConverter(null);
	}

	protected XmlTypeConverter getResourceConverter() {
		return this.resourceMapping.getTypeConverter();
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

	protected void initialize(XmlConvertibleMapping resourceMapping) {
		this.resourceMapping = resourceMapping;
		this.name = this.name();
		this.dataType = this.dataType();
		this.objectType = this.objectType();
	}
	
	public void update() {
		this.setName_(this.name());
		this.setDataType_(this.dataType());
		this.setObjectType_(this.objectType());
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

	public TextRange getValidationTextRange() {
		return getResourceConverter().getValidationTextRange();
	}
}
