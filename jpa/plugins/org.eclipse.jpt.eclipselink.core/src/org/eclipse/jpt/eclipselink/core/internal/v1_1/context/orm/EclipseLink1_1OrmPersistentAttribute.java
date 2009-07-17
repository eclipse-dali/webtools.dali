/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlAttributeMapping;


public class EclipseLink1_1OrmPersistentAttribute 
	extends AbstractOrmPersistentAttribute 
{
	
	protected AccessType specifiedAccess;
	
	public EclipseLink1_1OrmPersistentAttribute(OrmPersistentType parent, Owner owner, XmlAttributeMapping resourceMapping) {
		super(parent, owner, resourceMapping);
		this.specifiedAccess = getResourceAccess();
	}

	@Override
	public XmlAttributeMapping getResourceAttributeMapping() {
		return (XmlAttributeMapping) super.getResourceAttributeMapping();
	}
	
	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}
	
	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		this.getResourceAttributeMapping().setAccess(AccessType.toOrmResourceModel(newSpecifiedAccess));
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
		return AccessType.fromOrmResourceModel(getResourceAttributeMapping().getAccess());
	}
	
}
