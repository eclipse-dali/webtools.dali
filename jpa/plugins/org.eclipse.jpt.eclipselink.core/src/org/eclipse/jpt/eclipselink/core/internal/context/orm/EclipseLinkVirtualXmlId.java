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
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlId;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.CustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlId extends XmlId
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaIdMapping javaAttributeMapping;

	protected final VirtualXmlId virtualXmlId;
		
	public EclipseLinkVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaIdMapping;
		this.virtualXmlId = new VirtualXmlId(ormTypeMapping, javaIdMapping);
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlId.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlId.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlId.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlId.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlId.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlId.setColumn(value);
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlId.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		this.virtualXmlId.setTemporal(newTemporal);
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlId.getTemporalTextRange();
	}

	@Override
	public XmlGeneratedValue getGeneratedValue() {
		return this.virtualXmlId.getGeneratedValue();
	}
	
	@Override
	public void setGeneratedValue(XmlGeneratedValue value) {
		this.virtualXmlId.setGeneratedValue(value);
	}

	@Override
	public XmlSequenceGenerator getSequenceGenerator() {
		return this.virtualXmlId.getSequenceGenerator();
	}

	@Override
	public void setSequenceGenerator(XmlSequenceGenerator value) {
		this.virtualXmlId.setSequenceGenerator(value);
	}

	@Override
	public XmlTableGenerator getTableGenerator() {
		return this.virtualXmlId.getTableGenerator();
	}

	@Override
	public void setTableGenerator(XmlTableGenerator value) {
		this.virtualXmlId.setTableGenerator(value);
	}
	
	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlId.getEnumerated();
	}
	
	@Override
	public void setEnumerated(EnumType value) {
		this.virtualXmlId.setEnumerated(value);
	}
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return this.virtualXmlId.getEnumeratedTextRange();
	}
	
	@Override
	public boolean isLob() {
		return this.virtualXmlId.isLob();
	}
	
	@Override
	public void setLob(boolean value) {
		this.virtualXmlId.setLob(value);
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlId.getLobTextRange();
	}
	
	
	@Override
	public Boolean getMutable() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		return Boolean.valueOf(((EclipseLinkIdMapping) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	@Override
	public void setMutable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getConvert() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			return ((Convert) this.javaAttributeMapping.getConverter()).getConverterName();
		}
		return null;
	}
	
	@Override
	public void setConvert(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public XmlConverter getConverter() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((Convert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.CUSTOM_CONVERTER) {
				return new EclipseLinkVirtualXmlConverter(this.ormTypeMapping, (CustomConverter) ((Convert) this.javaAttributeMapping.getConverter()).getConverter());
			}
		}
		return null;
	}

	@Override
	public void setConverter(XmlConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public XmlObjectTypeConverter getObjectTypeConverter() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((Convert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
				return new EclipseLinkVirtualXmlObjectTypeConverter(this.ormTypeMapping, (ObjectTypeConverter) ((Convert) this.javaAttributeMapping.getConverter()).getConverter());
			}
		}
		return null;
	}

	@Override
	public void setObjectTypeConverter(XmlObjectTypeConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public XmlStructConverter getStructConverter() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((Convert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.STRUCT_CONVERTER) {
				return new EclipseLinkVirtualXmlStructConverter(this.ormTypeMapping, (StructConverter) ((Convert) this.javaAttributeMapping.getConverter()).getConverter());
			}
		}
		return null;
	}

	@Override
	public void setStructConverter(XmlStructConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public XmlTypeConverter getTypeConverter() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((Convert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.TYPE_CONVERTER) {
				return new EclipseLinkVirtualXmlTypeConverter(this.ormTypeMapping, (TypeConverter) ((Convert) this.javaAttributeMapping.getConverter()).getConverter());
			}
		}
		return null;
	}

	@Override
	public void setTypeConverter(XmlTypeConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return null;
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$		
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		// TODO get from java annotations
		return null;
	}
	
	@Override
	public TextRange getMutableTextRange() {
		return null;
	}

	@Override
	public TextRange getConvertTextRange() {
		return null;
	}

}
