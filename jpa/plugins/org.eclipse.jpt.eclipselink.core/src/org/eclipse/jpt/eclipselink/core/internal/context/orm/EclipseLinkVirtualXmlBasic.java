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

import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlBasic;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.Converter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlBasic extends VirtualXmlBasic implements XmlBasic
{
		
	public EclipseLinkVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		super(ormTypeMapping, javaBasicMapping);
	}

	public Boolean getMutable() {
		if (isOrmMetadataComplete()) {
			return Boolean.valueOf(((EclipseLinkBasicMapping) this.javaAttributeMapping).getMutable().isDefaultMutable());
		}
		return Boolean.valueOf(((EclipseLinkBasicMapping) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	public void setMutable(@SuppressWarnings("unused") Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getConvert() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			return ((Convert) this.javaAttributeMapping.getConverter()).getConverterName();
		}
		return null;
	}
	
	public void setConvert(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public TextRange getMutableTextRange() {
		return null;
	}
	
	public TextRange getConvertTextRange() {
		return null;
	}

	public XmlConverter getConverter() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			EclipseLinkConverter converter = ((Convert) this.javaAttributeMapping.getConverter()).getConverter();
			if (converter != null && converter.getType() == EclipseLinkConverter.CONVERTER) {
				return new EclipseLinkVirtualXmlConverter(this.ormTypeMapping, (Converter) ((Convert) this.javaAttributeMapping.getConverter()).getConverter());
			}
		}
		return null;
	}

	public void setConverter(@SuppressWarnings("unused") XmlConverter value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public XmlObjectTypeConverter getObjectTypeConverter() {
		if (isOrmMetadataComplete()) {
			return null;
		}
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
		if (isOrmMetadataComplete()) {
			return null;
		}
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
		if (isOrmMetadataComplete()) {
			return null;
		}
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
}
