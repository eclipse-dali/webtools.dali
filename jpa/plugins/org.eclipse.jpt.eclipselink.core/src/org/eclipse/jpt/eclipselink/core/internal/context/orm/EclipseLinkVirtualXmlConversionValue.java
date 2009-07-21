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

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlConversionValue extends XmlConversionValue
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected ConversionValue javaConversionValue;
	
	public EclipseLinkVirtualXmlConversionValue(OrmTypeMapping ormTypeMapping, ConversionValue javaConversionValue) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaConversionValue = javaConversionValue;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getDataValue() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConversionValue.getDataValue();
	}
	
	@Override
	public void setDataValue(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getObjectValue() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConversionValue.getObjectValue();
	}
	
	@Override
	public void setObjectValue(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public TextRange getDataValueTextRange() {
		return null;
	}
	
	@Override
	public TextRange getObjectValueTextRange() {
		return null;
	}
}
