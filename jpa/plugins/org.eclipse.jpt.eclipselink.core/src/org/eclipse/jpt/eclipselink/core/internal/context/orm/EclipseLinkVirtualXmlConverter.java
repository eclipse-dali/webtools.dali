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
import org.eclipse.jpt.eclipselink.core.context.CustomConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlConverter extends AbstractJpaEObject implements XmlConverter
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected CustomConverter javaConverter;
	
	public EclipseLinkVirtualXmlConverter(OrmTypeMapping ormTypeMapping, CustomConverter javaConverter) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaConverter = javaConverter;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}

	public String getClassName() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getConverterClass();
	}
	
	public void setClassName(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public String getName() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.javaConverter.getName();
	}
	
	public void setName(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
