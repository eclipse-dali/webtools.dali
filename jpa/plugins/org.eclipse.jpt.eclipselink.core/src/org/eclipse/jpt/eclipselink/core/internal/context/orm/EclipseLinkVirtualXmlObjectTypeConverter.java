/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlObjectTypeConverter extends AbstractJpaEObject implements XmlObjectTypeConverter
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected ObjectTypeConverter javaConverter;
	
	public EclipseLinkVirtualXmlObjectTypeConverter(OrmTypeMapping ormTypeMapping, ObjectTypeConverter javaConverter) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaConverter = javaConverter;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	public String getName() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getName();
	}
	
	public void setName(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getDataType() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getDataType();
	}
	
	public void setDataType(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getObjectType() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getObjectType();
	}
	
	public void setObjectType(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getDefaultObjectValue() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getDefaultObjectValue();
	}

	public void setDefaultObjectValue(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public EList<XmlConversionValue> getConversionValues() {
		EList<XmlConversionValue> conversionValues = new EObjectContainmentEList<XmlConversionValue>(XmlConversionValue.class, this, EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER__CONVERSION_VALUES);
		if (isOrmMetadataComplete()) {
			return conversionValues;
		}
		for (ConversionValue javaConversionValue : CollectionTools.iterable(this.javaConverter.conversionValues())) {
			XmlConversionValue xmlConversionValue = new EclipseLinkVirtualXmlConversionValue(this.ormTypeMapping, javaConversionValue);
			conversionValues.add(xmlConversionValue);
		}
		return conversionValues;
	}

}
