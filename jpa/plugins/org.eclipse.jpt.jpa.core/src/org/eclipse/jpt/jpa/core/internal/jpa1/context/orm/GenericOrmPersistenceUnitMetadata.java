/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.resource.xml.EFactoryTools;
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
