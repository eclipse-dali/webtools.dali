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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;

public class EclipseLinkOrmTypeConverter extends EclipseLinkOrmConverter<XmlTypeConverter> 
	implements TypeConverter
{	
	private String dataType;
	
	private String objectType;
	
	
	public EclipseLinkOrmTypeConverter(XmlContextNode parent, XmlTypeConverter xmlResource) {
		super(parent, xmlResource);
	}
		
	public String getType() {
		return EclipseLinkConverter.TYPE_CONVERTER;
	}
		
	// **************** data type **********************************************
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		getXmlResource().setDataType(newDataType);
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
		getXmlResource().setObjectType(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	protected void setObjectType_(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(XmlTypeConverter xmlResource) {
		super.initialize(xmlResource);
		this.dataType = getResourceDataType();
		this.objectType = getResourceObjectType();
	}
	
	@Override
	public void update() {
		super.update();
		setDataType_(getResourceDataType());
		setObjectType_(getResourceObjectType());
	}
	
	protected String getResourceDataType() {
		return this.resourceConverter.getDataType();
	}
	
	protected String getResourceObjectType() {
		return this.resourceConverter.getObjectType();
	}
}