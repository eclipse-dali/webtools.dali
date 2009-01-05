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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlId;
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
public class EclipseLinkVirtualXmlId extends VirtualXmlId implements XmlId
{
		
	public EclipseLinkVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		super(ormTypeMapping, javaIdMapping);
	}

	public Boolean getMutable() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		return Boolean.valueOf(((EclipseLinkIdMapping) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	public void setMutable(@SuppressWarnings("unused") Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getConvert() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			return ((Convert) this.javaAttributeMapping.getConverter()).getConverterName();
		}
		return null;
	}
	
	public void setConvert(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
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

	public void setConverter(@SuppressWarnings("unused") XmlConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

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

	public void setObjectTypeConverter(@SuppressWarnings("unused") XmlObjectTypeConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

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

	public void setStructConverter(@SuppressWarnings("unused") XmlStructConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

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

	public void setTypeConverter(@SuppressWarnings("unused") XmlTypeConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public XmlAccessMethods getAccessMethods() {
		return null;
	}
	
	public void setAccessMethods(XmlAccessMethods value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$		
	}
	
	public EList<XmlProperty> getProperties() {
		// TODO get from java annotations
		return null;
	}
	
	public TextRange getMutableTextRange() {
		return null;
	}

	public TextRange getConvertTextRange() {
		return null;
	}

}
