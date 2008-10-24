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
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingImpl;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;

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

	public TextRange getMutableTextRange() {
		return null;
	}
}
