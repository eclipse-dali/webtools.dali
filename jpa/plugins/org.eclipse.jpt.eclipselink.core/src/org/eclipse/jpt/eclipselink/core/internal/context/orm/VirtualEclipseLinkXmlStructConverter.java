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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlStructConverter extends XmlStructConverter
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected EclipseLinkStructConverter javaConverter;
	
	public VirtualEclipseLinkXmlStructConverter(OrmTypeMapping ormTypeMapping, EclipseLinkStructConverter javaConverter) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaConverter = javaConverter;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}

	@Override
	public String getConverter() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getConverterClass();
	}
	
	@Override
	public void setConverter(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getName() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getName();
	}
	
	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
