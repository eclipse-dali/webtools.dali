/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.AbstractPersistenceUnitMetadata;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;

public class GenericPersistenceUnitMetadata extends AbstractPersistenceUnitMetadata
{

	public GenericPersistenceUnitMetadata(EntityMappings parent, XmlEntityMappings resource) {
		super(parent, resource);
	}

	public XmlPersistenceUnitMetadata createResourcePersistenceUnitMetadata() {
		return OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
	}
}