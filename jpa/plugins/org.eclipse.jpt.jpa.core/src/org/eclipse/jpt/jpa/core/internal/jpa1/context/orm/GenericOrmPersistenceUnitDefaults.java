/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitDefaults;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-defaults</code> element
 */
public class GenericOrmPersistenceUnitDefaults
	extends AbstractOrmPersistenceUnitDefaults
{


	// ********** constructor/initialization **********

	public GenericOrmPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent) {
		super(parent);
	}

	@Override
	protected XmlPersistenceUnitDefaults buildXmlPersistenceUnitDefaults() {
		return OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
	}

}
