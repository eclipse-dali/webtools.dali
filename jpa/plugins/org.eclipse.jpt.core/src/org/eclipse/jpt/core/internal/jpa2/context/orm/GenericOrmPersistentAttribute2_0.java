/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa1.context.GenericPersistentAttributeValidator;
import org.eclipse.jpt.core.resource.orm.XmlAccessHolder;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;


public class GenericOrmPersistentAttribute2_0 
	extends AbstractOrmPersistentAttribute 
{
	
	protected AccessType specifiedAccess;
	
	public GenericOrmPersistentAttribute2_0(OrmPersistentType parent, Owner owner, XmlAttributeMapping resourceMapping) {
		super(parent, owner, resourceMapping);
		this.specifiedAccess = getResourceAccess();
	}
	
	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}
	
	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		((XmlAccessHolder) this.getResourceAttributeMapping()).setAccess(AccessType.toOrmResourceModel(newSpecifiedAccess));
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldAccess, newSpecifiedAccess);
	}
	
	protected void setSpecifiedAccess_(AccessType newSpecifiedAccess) {
		AccessType oldAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldAccess, newSpecifiedAccess);
	}
	
	@Override
	public void update() {
		super.update();
		setSpecifiedAccess_(getResourceAccess());
	}
	
	protected AccessType getResourceAccess() {
		return AccessType.fromOrmResourceModel(((XmlAccessHolder) getResourceAttributeMapping()).getAccess());
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new GenericPersistentAttributeValidator(this, getJavaPersistentAttribute(), buildTextRangeResolver());
	}
}
