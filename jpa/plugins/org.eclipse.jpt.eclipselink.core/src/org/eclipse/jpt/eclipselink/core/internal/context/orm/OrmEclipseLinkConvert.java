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

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkConvert extends AbstractOrmXmlContextNode implements EclipseLinkConvert, OrmConverter
{
	protected String specifiedConverterName;
	
	protected XmlConvertibleMapping resourceMapping;
	
	protected OrmEclipseLinkConverter<?> converter;
	
	public OrmEclipseLinkConvert(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping) {
		super(parent);
		this.initialize(resourceMapping);
	}

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	public String getType() {
		return EclipseLinkConvert.ECLIPSE_LINK_CONVERTER;
	}
		
	public void addToResourceModel() {
		this.resourceMapping.setConvert(""); //$NON-NLS-1$
	}
	
	public void removeFromResourceModel() {
		this.resourceMapping.setConvert(null);
	}

	public TextRange getValidationTextRange() {
		return this.resourceMapping.getConvertTextRange();
	}
	
	public String getConverterName() {
		return getSpecifiedConverterName() == null ? getDefaultConverterName() : getSpecifiedConverterName();
	}

	public String getDefaultConverterName() {
		return DEFAULT_CONVERTER_NAME;
	}

	public String getSpecifiedConverterName() {
		return this.specifiedConverterName;
	}

	public void setSpecifiedConverterName(String newSpecifiedConverterName) {
		String oldSpecifiedConverterName = this.specifiedConverterName;
		this.specifiedConverterName = newSpecifiedConverterName;
		this.resourceMapping.setConvert(newSpecifiedConverterName);
		firePropertyChanged(SPECIFIED_CONVERTER_NAME_PROPERTY, oldSpecifiedConverterName, newSpecifiedConverterName);
	}
	
	protected void setSpecifiedConverterName_(String newSpecifiedConverterName) {
		String oldSpecifiedConverterName = this.specifiedConverterName;
		this.specifiedConverterName = newSpecifiedConverterName;
		firePropertyChanged(SPECIFIED_CONVERTER_NAME_PROPERTY, oldSpecifiedConverterName, newSpecifiedConverterName);
	}

	public OrmEclipseLinkConverter<?> getConverter() {
		return this.converter;
	}
	
	protected String getConverterType() {
		if (this.converter == null) {
			return EclipseLinkConverter.NO_CONVERTER;
		}
		return this.converter.getType();
	}

	public void setConverter(String converterType) {
		if (getConverterType() == converterType) {
			return;
		}
		OrmEclipseLinkConverter<?> oldConverter = this.converter;
		if (oldConverter != null) {
			this.converter = null; //set to null now to avoid update triggering events
			removeConverter(oldConverter.getType());
		}
		JpaEObject resourceConverter = buildResourceConverter(converterType);
		OrmEclipseLinkConverter<?> newConverter = buildConverter(converterType, resourceConverter);
		this.converter = newConverter;
		if (newConverter != null) {
			addConverter(converterType, resourceConverter);
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}

	//TODO yes, i know, many if/else type checks in the methods below.
	//will look at factoring this out when I have time after M3!  Also EclipseLinkJavaConvert
	protected void removeConverter(String converterType) {
		if (converterType == EclipseLinkConverter.CUSTOM_CONVERTER) {
			this.resourceMapping.setConverter(null);
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			this.resourceMapping.setTypeConverter(null);
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			this.resourceMapping.setObjectTypeConverter(null);
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			this.resourceMapping.setStructConverter(null);
		}
	}
	
	protected JpaEObject buildResourceConverter(String converterType) {
		if (converterType == EclipseLinkConverter.CUSTOM_CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		}
		return null;
	}

	protected void addConverter(String converterType, JpaEObject resourceConverter) {
		if (converterType == EclipseLinkConverter.CUSTOM_CONVERTER) {
			this.resourceMapping.setConverter((XmlConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			this.resourceMapping.setTypeConverter((XmlTypeConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			this.resourceMapping.setObjectTypeConverter((XmlObjectTypeConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			this.resourceMapping.setStructConverter((XmlStructConverter) resourceConverter);
		}
	}
	
	protected OrmEclipseLinkConverter<?> buildConverter(String converterType, JpaEObject resourceConverter) {
		if (converterType == EclipseLinkConverter.NO_CONVERTER) {
			return null;
		}
		if (converterType == EclipseLinkConverter.CUSTOM_CONVERTER) {
			return buildCustomConverter((XmlConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			return buildTypeConverter((XmlTypeConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			return buildObjectTypeConverter((XmlObjectTypeConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			return buildStructConverter((XmlStructConverter) resourceConverter);
		}
		return null;
	}

	protected void setConverter(OrmEclipseLinkConverter<?> newConverter) {
		OrmEclipseLinkConverter<?> oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void initialize(XmlConvertibleMapping resourceMapping) {
		this.resourceMapping = resourceMapping;
		this.specifiedConverterName = this.getResourceConvert();
		this.converter = this.buildConverter();
	}
	
	public void update() {
		this.setSpecifiedConverterName_(this.getResourceConvert());
		if (getResourceConverterType() == getConverterType()) {
			this.converter.update();
		}
		else {
			setConverter(buildConverter());
		}
	}
	
	protected String getResourceConvert() {
		return this.resourceMapping == null ? null : this.resourceMapping.getConvert();
	}

	protected String getResourceConverterType() {
		if (this.resourceMapping.getConverter() != null) {
			return EclipseLinkConverter.CUSTOM_CONVERTER;
		}
		else if (this.resourceMapping.getTypeConverter() != null) {
			return EclipseLinkConverter.TYPE_CONVERTER;
		}
		else if (this.resourceMapping.getObjectTypeConverter() != null) {
			return EclipseLinkConverter.OBJECT_TYPE_CONVERTER;
		}
		else if (this.resourceMapping.getStructConverter() != null) {
			return EclipseLinkConverter.STRUCT_CONVERTER;
		}
		
		return null;
	}
	protected OrmEclipseLinkConverter<?> buildConverter() {
		if (this.resourceMapping.getConverter() != null) {
			return buildCustomConverter(this.resourceMapping.getConverter());
		}
		else if (this.resourceMapping.getTypeConverter() != null) {
			return buildTypeConverter(this.resourceMapping.getTypeConverter());
		}
		else if (this.resourceMapping.getObjectTypeConverter() != null) {
			return buildObjectTypeConverter(this.resourceMapping.getObjectTypeConverter());
		}
		else if (this.resourceMapping.getStructConverter() != null) {
			return buildStructConverter(this.resourceMapping.getStructConverter());
		}
		
		return null;
	}

	protected OrmEclipseLinkCustomConverter buildCustomConverter(XmlConverter resourceConverter) {
		OrmEclipseLinkCustomConverter contextConverter = new OrmEclipseLinkCustomConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected OrmEclipseLinkTypeConverter buildTypeConverter(XmlTypeConverter resourceConverter) {
		OrmEclipseLinkTypeConverter contextConverter = new OrmEclipseLinkTypeConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected OrmEclipseLinkObjectTypeConverter buildObjectTypeConverter(XmlObjectTypeConverter resourceConverter) {
		OrmEclipseLinkObjectTypeConverter contextConverter = new OrmEclipseLinkObjectTypeConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected OrmEclipseLinkStructConverter buildStructConverter(XmlStructConverter resourceConverter) {
		OrmEclipseLinkStructConverter contextConverter = new OrmEclipseLinkStructConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (getConverter() != null) {
			getConverter().validate(messages, reporter);
		}
	}
}
