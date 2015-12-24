/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TypeConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.TypeConverter</code>
 */
public class EclipseLinkJavaTypeConverter
	extends EclipseLinkJavaConverter<TypeConverterAnnotation>
	implements EclipseLinkTypeConverter
{
	private String dataType;
	private String fullyQualifiedDataType;

	private String objectType;
	private String fullyQualifiedObjectType;


	public EclipseLinkJavaTypeConverter(EclipseLinkJavaConverterContainer parent, TypeConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation);
		this.dataType = this.converterAnnotation.getDataType();
		this.objectType = this.converterAnnotation.getObjectType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setDataType_(this.converterAnnotation.getDataType());
		this.setObjectType_(this.converterAnnotation.getObjectType());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setFullyQualifiedDataType(this.converterAnnotation.getFullyQualifiedDataType());
		this.setFullyQualifiedObjectType(this.converterAnnotation.getFullyQualifiedObjectType());
	}


	// ********** data type **********

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.converterAnnotation.setDataType(dataType);
		this.setDataType_(dataType);
	}

	protected void setDataType_(String dataType) {
		String old = this.dataType;
		this.dataType = dataType;
		this.firePropertyChanged(DATA_TYPE_PROPERTY, old, dataType);
	}


	// ********** fully qualified data type **********

	public String getFullyQualifiedDataType() {
		return this.fullyQualifiedDataType;
	}

	protected void setFullyQualifiedDataType(String dataType) {
		String old = this.fullyQualifiedDataType;
		this.fullyQualifiedDataType = dataType;
		this.firePropertyChanged(FULLY_QUALIFIED_DATA_TYPE_PROPERTY, old, dataType);
	}


	// ********** object type **********

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.converterAnnotation.setObjectType(objectType);
		this.setObjectType_(objectType);
	}

	protected void setObjectType_(String objectType) {
		String old = this.objectType;
		this.objectType = objectType;
		this.firePropertyChanged(OBJECT_TYPE_PROPERTY, old, objectType);
	}


	// ********** fully qualified object type **********

	public String getFullyQualifiedObjectType() {
		return this.fullyQualifiedObjectType;
	}

	protected void setFullyQualifiedObjectType(String objectType) {
		String old = this.fullyQualifiedObjectType;
		this.fullyQualifiedObjectType = objectType;
		this.firePropertyChanged(FULLY_QUALIFIED_OBJECT_TYPE_PROPERTY, old, objectType);
	}


	// ********** misc **********

	public Class<EclipseLinkTypeConverter> getConverterType() {
		return EclipseLinkTypeConverter.class;
	}


	// ********** validation *********

	@Override
	protected boolean isEquivalentTo_(EclipseLinkConverter other) {
		return super.isEquivalentTo_(other) &&
				this.isEquivalentTo_((EclipseLinkTypeConverter) other);
	}

	protected boolean isEquivalentTo_(EclipseLinkTypeConverter other) {
		return ObjectTools.equals(this.fullyQualifiedDataType, other.getFullyQualifiedDataType()) &&
				ObjectTools.equals(this.fullyQualifiedObjectType, other.getFullyQualifiedObjectType());
	}


	// ********** metadata conversion **********

	@Override
	public void convertTo(EclipseLinkOrmConverterContainer ormConverterContainer) {
		ormConverterContainer.addTypeConverter(this.getName()).convertFrom(this);
	}
	
	@Override
	public void delete() {
		this.parent.removeTypeConverter(this);
	}
}
