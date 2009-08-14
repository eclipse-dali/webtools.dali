/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.internal.jpa1.GenericJpaProject;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistenceUnit;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.connection.GenericConnection2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.options.GenericOptions2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;

/**
 *  GenericPersistenceUnit2_0
 */
public class GenericPersistenceUnit2_0 extends GenericPersistenceUnit
{
	private JpaConnection2_0 connection;
	private JpaOptions2_0 options;

	// ********** constructors **********
	public GenericPersistenceUnit2_0(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}

	@Override
	protected void initializeProperties() {
		super.initializeProperties();

		this.connection = new GenericConnection2_0(this);
		this.options = new GenericOptions2_0(this);
	}
	
	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		this.connection.propertyValueChanged(propertyName, newValue);
		this.options.propertyValueChanged(propertyName, newValue);
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		this.connection.propertyRemoved(propertyName);
		this.options.propertyRemoved(propertyName);
	}

	@Override
	public GenericJpaProject getJpaProject() {
		return (GenericJpaProject) super.getJpaProject();
	}

	// **************** properties *********************************************

	public JpaConnection2_0 getConnection() {
		return this.connection;
	}

	public JpaOptions2_0 getOptions() {
		return this.options;
	}

}
