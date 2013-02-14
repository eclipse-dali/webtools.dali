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

import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
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

	public Class<? extends OrmPersistentType> getContextType() {
		return OrmPersistentType.class;
	}

	public Class<? extends XmlManagedType> getResourceType() {
		return XmlTypeMapping.class;
	}

	public OrmManagedType buildContextManagedType(JpaContextNode parent, XmlManagedType resourceManagedType, OrmXmlContextNodeFactory factory) {
		return factory.buildOrmPersistentType((EntityMappings) parent, (XmlTypeMapping) resourceManagedType);
	}
}
