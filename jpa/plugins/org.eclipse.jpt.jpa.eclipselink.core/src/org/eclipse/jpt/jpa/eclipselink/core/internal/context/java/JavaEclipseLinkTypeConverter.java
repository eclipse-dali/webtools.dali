/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.TypeConverter</code>
 */
public class JavaEclipseLinkTypeConverter
	extends JavaEclipseLinkConverter<EclipseLinkTypeConverterAnnotation>
	implements EclipseLinkTypeConverter
{
	private String dataType;
	private String fullyQualifiedDataType;

	private String objectType;
	private String fullyQualifiedObjectType;


	public JavaEclipseLinkTypeConverter(JavaEclipseLinkConverterContainer parent, EclipseLinkTypeConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation);
		this.dataType = this.converterAnnotation.getDataType();
		this.objectType = this.converterAnnotation.getObjectType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setDataType_(this.converterAnnotation.getDataType());
		this.setObjectType_(this.converterAnnotation.getObjectType());
	}

	@Override
	public void update() {
		super.update();
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

	public Class<EclipseLinkTypeConverter> getType() {
		return EclipseLinkTypeConverter.class;
	}


	// ********** validation *********

	@Override
	public boolean isEquivalentTo(JpaNamedContextNode node) {
		return super.isEquivalentTo(node)
				&& this.isEquivalentTo((EclipseLinkTypeConverter) node);
	}

	protected boolean isEquivalentTo(EclipseLinkTypeConverter converter) {
		return ObjectTools.equals(this.fullyQualifiedDataType, converter.getFullyQualifiedDataType()) &&
				ObjectTools.equals(this.fullyQualifiedObjectType, converter.getFullyQualifiedObjectType());
	}


	// ********** metadata conversion **********

	@Override
	public void convertTo(OrmEclipseLinkConverterContainer ormConverterContainer) {
		ormConverterContainer.addTypeConverter().convertFrom(this);
	}
	
	@Override
	public void delete() {
		this.getParent().removeTypeConverter(this);
	}
}
