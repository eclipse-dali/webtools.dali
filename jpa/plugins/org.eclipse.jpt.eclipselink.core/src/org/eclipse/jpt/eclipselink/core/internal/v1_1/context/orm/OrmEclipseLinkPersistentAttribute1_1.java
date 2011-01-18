/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.orm.SpecifiedOrmPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkPersistentAttributeValidator;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAttributeMapping;

/**
 * EclipseLink 1.1
 * <code>orm.xml</code> persistent attribute
 */
public class OrmEclipseLinkPersistentAttribute1_1
	extends SpecifiedOrmPersistentAttribute
{
	protected AccessType specifiedAccess;


	public OrmEclipseLinkPersistentAttribute1_1(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		super(parent, xmlMapping);
		this.specifiedAccess = this.buildSpecifiedAccess();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
	}


	// ********** access **********

	@Override
	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		this.setSpecifiedAccess_(access);
		this.getXmlAttributeMapping().setAccess(AccessType.toOrmResourceModel(access));
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromOrmResourceModel(this.getXmlAttributeMapping().getAccess());
	}

	@Override
	protected XmlAttributeMapping getXmlAttributeMapping() {
		return (XmlAttributeMapping) super.getXmlAttributeMapping();
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new EclipseLinkPersistentAttributeValidator(this, this.getJavaPersistentAttribute(), this.buildTextRangeResolver());
	}
}
