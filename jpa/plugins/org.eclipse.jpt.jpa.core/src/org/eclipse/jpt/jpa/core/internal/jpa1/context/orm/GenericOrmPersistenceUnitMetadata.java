/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.internal.resource.xml.EFactoryTools;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-metadata</code> element
 */
public class GenericOrmPersistenceUnitMetadata
	extends AbstractOrmPersistenceUnitMetadata
{

	public GenericOrmPersistenceUnitMetadata(EntityMappings parent) {
		super(parent);
	}
	
	@Override
	protected XmlPersistenceUnitMetadata buildXmlPersistenceUnitMetadata_() {
		return EFactoryTools.create(
				this.getResourceModelFactory(),
				OrmPackage.eINSTANCE.getXmlPersistenceUnitMetadata(),
				XmlPersistenceUnitMetadata.class
			);
	}
}
