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

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.TypeConverterAnnotation;

public class EclipseLinkJavaTypeConverter extends EclipseLinkJavaConverter
	implements TypeConverter
{	
	private String dataType;
	
	private String objectType;
	
	
	public EclipseLinkJavaTypeConverter(JavaJpaContextNode parent) {
		super(parent);
	}
	
	public String getType() {
		return EclipseLinkConverter.TYPE_CONVERTER;
	}
	
	@Override
	public String getAnnotationName() {
		return TypeConverterAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected TypeConverterAnnotation getAnnotation() {
		return (TypeConverterAnnotation) super.getAnnotation();
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
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(JavaResourcePersistentMember jrpm) {
		super.initialize(jrpm);
		TypeConverterAnnotation resourceConverter = getAnnotation();
		this.dataType = this.dataType(resourceConverter);
		this.objectType = this.objectType(resourceConverter);
	}
	
	@Override
	public void update(JavaResourcePersistentMember jrpm) {
		super.update(jrpm);
		TypeConverterAnnotation resourceConverter = getAnnotation();
		this.setDataType_(this.dataType(resourceConverter));
		this.setObjectType_(this.objectType(resourceConverter));
	}
	
	protected String dataType(TypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getDataType();
	}
	
	protected String objectType(TypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getObjectType();
	}
}
