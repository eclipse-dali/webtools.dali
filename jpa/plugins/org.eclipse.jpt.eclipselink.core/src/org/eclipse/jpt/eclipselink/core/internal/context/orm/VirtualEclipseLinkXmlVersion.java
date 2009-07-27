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
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlVersion;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlVersion extends XmlVersion
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaVersionMapping javaAttributeMapping;

	protected final VirtualXmlVersion virtualXmlVersion;
	
	public VirtualEclipseLinkXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaVersionMapping;
		this.virtualXmlVersion = new VirtualXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlVersion.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlVersion.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlVersion.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlVersion.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlVersion.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlVersion.setColumn(value);
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlVersion.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlVersion.getEnumerated();
	}
	
	@Override
	public void setEnumerated(EnumType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public boolean isLob() {
		return this.virtualXmlVersion.isLob();
	}
	
	@Override
	public void setLob(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}	
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return this.virtualXmlVersion.getEnumeratedTextRange();
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlVersion.getLobTextRange();
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlVersion.getTemporalTextRange();
	}

	@Override
	public Boolean getMutable() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		return Boolean.valueOf(((EclipseLinkVersionMapping) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	@Override
	public void setMutable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getConvert() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
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
	public TextRange getMutableTextRange() {
		return null;
	}
	
	@Override
	public TextRange getConvertTextRange() {
		return null;
	}

	@Override
	public XmlConverter getConverter() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.CUSTOM_CONVERTER) {
				return new VirtualEclipseLinkXmlConverter(this.ormTypeMapping, (EclipseLinkCustomConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
				return new VirtualEclipseLinkXmlObjectTypeConverter(this.ormTypeMapping, (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.STRUCT_CONVERTER) {
				return new VirtualEclipseLinkXmlStructConverter(this.ormTypeMapping, (EclipseLinkStructConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
		if (this.javaAttributeMapping.getConverter().getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.TYPE_CONVERTER) {
				return new VirtualEclipseLinkXmlTypeConverter(this.ormTypeMapping, (EclipseLinkTypeConverter) ((EclipseLinkConvert) this.javaAttributeMapping.getConverter()).getConverter());
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
