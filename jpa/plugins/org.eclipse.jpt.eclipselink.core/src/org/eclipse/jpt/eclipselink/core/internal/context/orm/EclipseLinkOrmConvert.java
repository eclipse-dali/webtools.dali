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

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.common.JpaEObject;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmConvert extends AbstractXmlContextNode implements Convert, OrmConverter
{
	protected String specifiedConverterName;
	
	protected XmlConvertibleMapping resourceMapping;
	
	protected EclipseLinkOrmConverter converter;
	
	public EclipseLinkOrmConvert(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping) {
		super(parent);
		this.initialize(resourceMapping);
	}

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	public String getType() {
		return Convert.ECLIPSE_LINK_CONVERTER;
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

	public EclipseLinkOrmConverter getConverter() {
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
		EclipseLinkOrmConverter oldConverter = this.converter;
		if (oldConverter != null) {
			this.converter = null; //set to null now to avoid update triggering events
			removeConverter(oldConverter.getType());
		}
		JpaEObject resourceConverter = buildResourceConverter(converterType);
		EclipseLinkOrmConverter newConverter = buildConverter(converterType, resourceConverter);
		this.converter = newConverter;
		if (newConverter != null) {
			addConverter(converterType, resourceConverter);
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}

	//TODO yes, i know, many if/else type checks in the methods below.
	//will look at factoring this out when I have time after M3!  Also EclipseLinkJavaConvert
	protected void removeConverter(String converterType) {
		if (converterType == EclipseLinkConverter.CONVERTER) {
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
		if (converterType == EclipseLinkConverter.CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlConverterImpl();
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverterImpl();
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverterImpl();
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverterImpl();
		}
		return null;
	}

	protected void addConverter(String converterType, JpaEObject resourceConverter) {
		if (converterType == EclipseLinkConverter.CONVERTER) {
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
	
	protected EclipseLinkOrmConverter buildConverter(String converterType, JpaEObject resourceConverter) {
		if (converterType == EclipseLinkConverter.NO_CONVERTER) {
			return null;
		}
		if (converterType == EclipseLinkConverter.CONVERTER) {
			return new EclipseLinkOrmConverterImpl(this, (XmlConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			return new EclipseLinkOrmTypeConverter(this, (XmlTypeConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			return new EclipseLinkOrmObjectTypeConverter(this, (XmlObjectTypeConverter) resourceConverter);
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			return new EclipseLinkOrmStructConverter(this, (XmlStructConverter) resourceConverter);
		}
		return null;
	}

	protected void setConverter(EclipseLinkOrmConverter newConverter) {
		EclipseLinkOrmConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void initialize(XmlConvertibleMapping resourceMapping) {
		this.resourceMapping = resourceMapping;
		this.specifiedConverterName = this.specifiedConverterName();
		this.converter = this.buildConverter();
	}
	
	public void update() {
		this.setSpecifiedConverterName_(this.specifiedConverterName());
		if (converterType() == getConverterType()) {
			this.converter.update();
		}
		else {
			setConverter(buildConverter());
		}
	}
	
	protected String specifiedConverterName() {
		return this.resourceMapping == null ? null : this.resourceMapping.getConvert();
	}

	protected String converterType() {
		if (this.resourceMapping.getConverter() != null) {
			return EclipseLinkConverter.CONVERTER;
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
	protected EclipseLinkOrmConverter buildConverter() {
		if (this.resourceMapping.getConverter() != null) {
			return new EclipseLinkOrmConverterImpl(this, this.resourceMapping.getConverter());
		}
		else if (this.resourceMapping.getTypeConverter() != null) {
			return new EclipseLinkOrmTypeConverter(this, this.resourceMapping.getTypeConverter());
		}
		else if (this.resourceMapping.getObjectTypeConverter() != null) {
			return new EclipseLinkOrmObjectTypeConverter(this, this.resourceMapping.getObjectTypeConverter());
		}
		else if (this.resourceMapping.getStructConverter() != null) {
			return new EclipseLinkOrmStructConverter(this, this.resourceMapping.getStructConverter());
		}
		
		return null;
	}


	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (getConverter() != null) {
			getConverter().validate(messages);
		}
	}
}
