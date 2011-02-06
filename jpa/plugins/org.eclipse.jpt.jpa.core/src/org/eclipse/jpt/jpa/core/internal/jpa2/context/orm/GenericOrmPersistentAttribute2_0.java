/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.SpecifiedOrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericPersistentAttributeValidator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAccessHolder;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;

/**
 * JPA 2.0
 * <code>orm.xml</code> persistent attribute
 */
public class GenericOrmPersistentAttribute2_0
	extends SpecifiedOrmPersistentAttribute
{
	protected AccessType specifiedAccess;


	public GenericOrmPersistentAttribute2_0(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
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
		this.getXmlAccessHolder().setAccess(AccessType.toOrmResourceModel(access));
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromOrmResourceModel(this.getXmlAccessHolder().getAccess());
	}

	protected XmlAccessHolder getXmlAccessHolder() {
		return this.getXmlAttributeMapping();
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new GenericPersistentAttributeValidator(this, this.getJavaPersistentAttribute(), this.buildTextRangeResolver());
	}
}
