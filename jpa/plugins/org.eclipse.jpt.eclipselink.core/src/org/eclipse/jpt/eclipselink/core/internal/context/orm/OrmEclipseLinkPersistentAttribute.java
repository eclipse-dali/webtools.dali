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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkPersistentAttributeValidator;


public class OrmEclipseLinkPersistentAttribute extends AbstractOrmPersistentAttribute
{
	
	public OrmEclipseLinkPersistentAttribute(OrmPersistentType parent, Owner owner, XmlAttributeMapping resourceMapping) {
		super(parent, owner, resourceMapping);
	}
	
	//****************** AccessHolder implementation *******************
	
	/**
	 * EclipseLinkOrmPersistentAttribute does not support specified access (no access element in 1.0), so we return null
	 */
	public AccessType getSpecifiedAccess() {
		return null;
	}
	
	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		throw new UnsupportedOperationException("specifiedAccess is not supported for OrmEclipseLinkPersistentAttribute"); //$NON-NLS-1$
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new EclipseLinkPersistentAttributeValidator(this, getJavaPersistentAttribute(), buildTextRangeResolver());
	}
}
