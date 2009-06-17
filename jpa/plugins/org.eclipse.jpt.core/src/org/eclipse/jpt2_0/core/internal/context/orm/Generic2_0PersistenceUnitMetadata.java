/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.AbstractPersistenceUnitMetadata;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt2_0.core.resource.orm.Orm2_0Factory;
import org.eclipse.jpt2_0.core.resource.orm.XmlPersistenceUnitMetadata;

public class Generic2_0PersistenceUnitMetadata extends AbstractPersistenceUnitMetadata
{

	public Generic2_0PersistenceUnitMetadata(EntityMappings parent, XmlEntityMappings resource) {
		super(parent, resource);
	}

	public XmlPersistenceUnitMetadata createResourcePersistenceUnitMetadata() {
		return Orm2_0Factory.eINSTANCE.createXmlPersistenceUnitMetadata();
	}
}