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

import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlId;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingImpl;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlId extends VirtualXmlId implements XmlId
{
		
	public VirtualEclipseLinkXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		super(ormTypeMapping, javaIdMapping);
	}

	public Boolean getMutable() {
		return Boolean.valueOf(((EclipseLinkJavaIdMappingImpl) this.javaAttributeMapping).getMutable().isMutable());
	}
	
	public void setMutable(@SuppressWarnings("unused") Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public TextRange getMutableTextRange() {
		return null;
	}
}
