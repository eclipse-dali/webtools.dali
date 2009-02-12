/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;

public class GenericEntityMappings
	extends AbstractEntityMappings
{

	public GenericEntityMappings(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
	}
	
	@Override
	protected PersistenceUnitMetadata buildPersistenceUnitMetadata() {
		return 	getJpaFactory().buildPersistenceUnitMetadata(this, this.xmlEntityMappings);
	}

	@Override
	protected OrmPersistentType buildPersistentType(XmlTypeMapping resourceMapping) {
		return getJpaFactory().buildOrmPersistentType(this, resourceMapping);
	}
}
