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

import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.ConvertibleMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlVersion;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlVersion extends VirtualXmlVersion implements XmlVersion
{
		
	public EclipseLinkVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		super(ormTypeMapping, javaVersionMapping);
	}

	public Boolean getMutable() {
		//don't need isOrmMetadataComplete() check because there is no default Id mapping
		return Boolean.valueOf(((EclipseLinkVersionMapping) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	public void setMutable(@SuppressWarnings("unused") Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getConvert() {
		Converter converter = ((ConvertibleMapping) this.javaAttributeMapping).getConverter();
		if (converter.getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			return ((Convert) converter).getConverterName();
		}
		return null;
	}
	
	public void setConvert(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public TextRange getMutableTextRange() {
		return null;
	}
	
	public TextRange getConvertTextRange() {
		return null;
	}

	public XmlConverter getConverter() {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlObjectTypeConverter getObjectTypeConverter() {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlStructConverter getStructConverter() {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlTypeConverter getTypeConverter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConverter(XmlConverter value) {
		// TODO Auto-generated method stub
		
	}

	public void setObjectTypeConverter(XmlObjectTypeConverter value) {
		// TODO Auto-generated method stub
		
	}

	public void setStructConverter(XmlStructConverter value) {
		// TODO Auto-generated method stub
		
	}

	public void setTypeConverter(XmlTypeConverter value) {
		// TODO Auto-generated method stub
		
	}
}
