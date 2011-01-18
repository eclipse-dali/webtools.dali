/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.orm.SpecifiedOrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkPersistentAttributeValidator;

/**
 * EclipseLink
 * <code>orm.xml</code> persistent attribute
 */
public class OrmEclipseLinkPersistentAttribute
	extends SpecifiedOrmPersistentAttribute
{
	public OrmEclipseLinkPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		super(parent, xmlMapping);
	}
	

	// ********** access **********

	/**
	 * EclipseLink 1.0 does not support a specified access for attributes.
	 */
	@Override
	public AccessType getSpecifiedAccess() {
		return null;
	}

	public void setSpecifiedAccess(AccessType access) {
		throw new UnsupportedOperationException("A specified access is not supported in EclipseLink 1.0: " + this); //$NON-NLS-1$
	}

	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new EclipseLinkPersistentAttributeValidator(this, getJavaPersistentAttribute(), buildTextRangeResolver());
	}
}
