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

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlConversionValue extends AbstractJpaEObject implements XmlConversionValue
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
	
	public String getDataValue() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConversionValue.getDataValue();
	}
	
	public void setDataValue(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public String getObjectValue() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConversionValue.getObjectValue();
	}
	
	public void setObjectValue(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public TextRange getDataValueTextRange() {
		return null;
	}
	
	public TextRange getObjectValueTextRange() {
		return null;
	}
}
