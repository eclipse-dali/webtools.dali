/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;

public class OrmPersistentTypeDefinition 
	implements OrmManagedTypeDefinition
{
	// singleton
	private static final OrmManagedTypeDefinition INSTANCE = new OrmPersistentTypeDefinition();

	/**
	 * Return the singleton.
	 */
	public static OrmManagedTypeDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private OrmPersistentTypeDefinition() {
		super();
	}

	public Class<PersistentType> getContextManagedTypeType() {
		return PersistentType.class;
	}

	public Class<XmlTypeMapping> getResourceManagedTypeType() {
		return XmlTypeMapping.class;
	}

	public OrmManagedType buildContextManagedType(JpaContextModel parent, XmlManagedType resourceManagedType, OrmXmlContextModelFactory factory) {
		return factory.buildOrmPersistentType((EntityMappings) parent, (XmlTypeMapping) resourceManagedType);
	}
}
