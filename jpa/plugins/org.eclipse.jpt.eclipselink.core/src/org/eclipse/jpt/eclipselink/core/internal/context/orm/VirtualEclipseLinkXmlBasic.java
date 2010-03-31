/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlBasic;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlBasic extends XmlBasic
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaBasicMapping javaAttributeMapping;
	
	protected final VirtualXmlBasic virtualXmlBasic;
	
	public VirtualEclipseLinkXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaBasicMapping;
		this.virtualXmlBasic = new VirtualXmlBasic(ormTypeMapping, javaBasicMapping);
	}
	
	public boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlBasic.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlBasic.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlBasic.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlBasic.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlBasic.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlBasic.setColumn(value);
	}
	
	@Override
	public FetchType getFetch() {
		return this.virtualXmlBasic.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlBasic.setFetch(newFetch);
	}

	@Override
	public Boolean getOptional() {
		return this.virtualXmlBasic.getOptional();
	}

	@Override
	public void setOptional(Boolean newOptional) {
		this.virtualXmlBasic.setOptional(newOptional);
	}

	@Override
	public boolean isLob() {
		return this.virtualXmlBasic.isLob();
	}

	@Override
	public void setLob(boolean newLob) {
		this.virtualXmlBasic.setLob(newLob);
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlBasic.getLobTextRange();
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlBasic.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType setTemporal){
		this.virtualXmlBasic.setTemporal(setTemporal);
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlBasic.getTemporalTextRange();
	}

	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlBasic.getEnumerated();
	}

	@Override
	public void setEnumerated(EnumType setEnumerated) {
		this.virtualXmlBasic.setEnumerated(setEnumerated);
	}
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return null;
	}

	@Override
	public Boolean getMutable() {
		if (isOrmMetadataComplete()) {
			return Boolean.valueOf(((EclipseLinkBasicMapping) this.javaAttributeMapping).getMutable().isDefaultMutable());
		}
		return Boolean.valueOf(((EclipseLinkBasicMapping) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	@Override
	public void setMutable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getMutableTextRange() {
		return null;
	}

	@Override
	public String getConvert() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			return ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverterName();
		}
		return null;
	}
	
	@Override
	public void setConvert(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getConvertTextRange() {
		return null;
	}

	@Override
	public XmlConverter getConverter() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.CUSTOM_CONVERTER) {
				return new VirtualEclipseLinkXmlConverter(this.ormTypeMapping, (JavaEclipseLinkCustomConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
		if (isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
				return new VirtualEclipseLinkXmlObjectTypeConverter(this.ormTypeMapping, (JavaEclipseLinkObjectTypeConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
		if (isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.STRUCT_CONVERTER) {
				return new VirtualEclipseLinkXmlStructConverter(this.ormTypeMapping, (JavaEclipseLinkStructConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
		if (isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.TYPE_CONVERTER) {
				return new VirtualEclipseLinkXmlTypeConverter(this.ormTypeMapping, (JavaEclipseLinkTypeConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
}
