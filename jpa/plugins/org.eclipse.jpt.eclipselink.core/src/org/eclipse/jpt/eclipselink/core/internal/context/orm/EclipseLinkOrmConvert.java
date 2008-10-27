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
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping;
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
		EclipseLinkOrmConverter newConverter = buildConverter(converterType);
		this.converter = null;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.converter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(EclipseLinkOrmConverter newConverter) {
		EclipseLinkOrmConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void initialize(XmlConvertibleMapping resourceMapping) {
		this.resourceMapping = resourceMapping;
		this.specifiedConverterName = this.specifiedConverterName();
		this.converter = this.buildConverter(this.converterType());
	}
	
	public void update() {
		this.setSpecifiedConverterName_(this.specifiedConverterName());
		if (converterType() == getConverterType()) {
			this.converter.update();
		}
		else {
			EclipseLinkOrmConverter javaConverter = buildConverter(converterType());
			setConverter(javaConverter);
		}
	}
	
	protected String specifiedConverterName() {
		return this.resourceMapping == null ? null : this.resourceMapping.getConvert();
	}

	
	protected EclipseLinkOrmConverter buildConverter(String converterType) {
		if (converterType == EclipseLinkConverter.NO_CONVERTER) {
			return null;
		}
		if (converterType == EclipseLinkConverter.CONVERTER) {
			return new EclipseLinkOrmConverterImpl(this, this.resourceMapping);
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			return new EclipseLinkOrmTypeConverter(this, this.resourceMapping);
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			return new EclipseLinkOrmObjectTypeConverter(this, this.resourceMapping);
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			return new EclipseLinkOrmStructConverter(this, this.resourceMapping);
		}
		return null;
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

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (getConverter() != null) {
			getConverter().validate(messages);
		}
	}
}
