/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.AbstractPersistenceUnitMetadata;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;

public class GenericPersistenceUnitMetadata2_0 extends AbstractPersistenceUnitMetadata
{

	public GenericPersistenceUnitMetadata2_0(EntityMappings parent, XmlEntityMappings resource) {
		super(parent, resource);
	}

	public XmlPersistenceUnitMetadata createResourcePersistenceUnitMetadata() {
		return Orm2_0Factory.eINSTANCE.createXmlPersistenceUnitMetadata();
	}
}