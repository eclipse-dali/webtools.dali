/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;

public class JavaEclipseLinkTypeConverter extends JavaEclipseLinkConverter
	implements EclipseLinkTypeConverter
{	
	private String dataType;
	private String fullyQualifiedDataType;
		public static final String FULLY_QUALIFIED_DATA_TYPE_PROPERTY = "fullyQualifiedDataType"; //$NON-NLS-1$
	
	private String objectType;
	private String fullyQualifiedObjectType;
		public static final String FULLY_QUALIFIED_OBJECT_TYPE_PROPERTY = "fullyQualifiedObjectType"; //$NON-NLS-1$
	
	
	public JavaEclipseLinkTypeConverter(JavaJpaContextNode parent) {
		super(parent);
	}
	
	public String getType() {
		return EclipseLinkConverter.TYPE_CONVERTER;
	}
	
	@Override
	public String getAnnotationName() {
		return EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected EclipseLinkTypeConverterAnnotation getAnnotation() {
		return (EclipseLinkTypeConverterAnnotation) super.getAnnotation();
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

	public String getFullyQualifiedDataType() {
		return this.fullyQualifiedDataType;
	}

	protected void setFullyQualifiedDataType(String dataType) {
		String old = this.fullyQualifiedDataType;
		this.fullyQualifiedDataType = dataType;
		this.firePropertyChanged(FULLY_QUALIFIED_DATA_TYPE_PROPERTY, old, dataType);
	}

	protected String buildFullyQualifiedDataType(EclipseLinkTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ?
				null :
					resourceConverter.getFullyQualifiedDataType();
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

	public String getFullyQualifiedObjectType() {
		return this.fullyQualifiedObjectType;
	}

	protected void setFullyQualifiedObjectType(String objectType) {
		String old = this.fullyQualifiedObjectType;
		this.fullyQualifiedObjectType = objectType;
		this.firePropertyChanged(FULLY_QUALIFIED_OBJECT_TYPE_PROPERTY, old, objectType);
	}

	protected String buildFullyQualifiedObjectType(EclipseLinkTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ?
				null :
					resourceConverter.getFullyQualifiedObjectType();
	}
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(JavaResourcePersistentMember jrpm) {
		super.initialize(jrpm);
		EclipseLinkTypeConverterAnnotation resourceConverter = getAnnotation();
		this.dataType = this.dataType(resourceConverter);
		this.fullyQualifiedDataType = this.buildFullyQualifiedDataType(resourceConverter);
		this.objectType = this.objectType(resourceConverter);
		this.fullyQualifiedObjectType = this.buildFullyQualifiedObjectType(resourceConverter);
	}
	
	@Override
	public void update(JavaResourcePersistentMember jrpm) {
		super.update(jrpm);
		EclipseLinkTypeConverterAnnotation resourceConverter = getAnnotation();
		this.setDataType_(this.dataType(resourceConverter));
		this.setFullyQualifiedDataType(this.buildFullyQualifiedDataType(resourceConverter));
		this.setObjectType_(this.objectType(resourceConverter));
		this.setFullyQualifiedObjectType(this.buildFullyQualifiedObjectType(resourceConverter));
	}
	
	protected String dataType(EclipseLinkTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getDataType();
	}
	
	protected String objectType(EclipseLinkTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getObjectType();
	}
}
