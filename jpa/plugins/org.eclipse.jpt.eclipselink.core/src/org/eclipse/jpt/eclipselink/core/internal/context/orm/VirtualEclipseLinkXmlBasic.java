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
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingImpl;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlBasic extends VirtualXmlBasic implements XmlBasic
{
		
	public VirtualEclipseLinkXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		super(ormTypeMapping, javaBasicMapping);
	}

	public Boolean getMutable() {
		return Boolean.valueOf(((EclipseLinkJavaBasicMappingImpl) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	public void setMutable(@SuppressWarnings("unused") Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getConvert() {
		if (this.javaAttributeMapping.getConverter().getType() == Convert.ECLIPSE_LINK_CONVERTER) {
			return ((Convert) this.javaAttributeMapping.getConverter()).getConverterName();
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
