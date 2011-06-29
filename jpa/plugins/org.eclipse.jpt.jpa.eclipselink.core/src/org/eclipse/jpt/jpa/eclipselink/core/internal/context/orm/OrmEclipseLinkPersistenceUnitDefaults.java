/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults;

/**
 * EclipseLink <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-defaults</code> element
 */
public class OrmEclipseLinkPersistenceUnitDefaults
	extends AbstractOrmPersistenceUnitDefaults
{


	// ********** constructor/initialization **********

	public OrmEclipseLinkPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent) {
		super(parent);
	}

	@Override
	protected XmlPersistenceUnitDefaults buildXmlPersistenceUnitDefaults() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
	}

}
