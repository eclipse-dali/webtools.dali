/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.resource.xml.EmfTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitMetadata;

/**
 * <code>eclipselink orm.xml</code> file
 * <br>
 * <code>persistence-unit-metadata</code> element
 */
public class OrmEclipseLinkPersistenceUnitMetadata
	extends AbstractOrmPersistenceUnitMetadata
{

	public OrmEclipseLinkPersistenceUnitMetadata(EntityMappings parent) {
		super(parent);
	}
	
	@Override
	protected XmlPersistenceUnitMetadata buildXmlPersistenceUnitMetadata_() {
		return EmfTools.create(
				this.getResourceNodeFactory(),
				EclipseLinkOrmPackage.eINSTANCE.getXmlPersistenceUnitMetadata(),
				XmlPersistenceUnitMetadata.class
			);
	}
}
