/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;

public class OrmNullAttributeMappingProvider implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmNullAttributeMappingProvider INSTANCE = new OrmNullAttributeMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmNullAttributeMappingProvider() {
		super();
	}

	public String getKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}

	public OrmAttributeMapping buildMapping(OrmPersistentAttribute parent, JpaFactory factory) {
		return factory.buildOrmNullAttributeMapping(parent);
	}
	
	public XmlNullAttributeMapping buildVirtualResourceMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping, JpaFactory factory) {
		return factory.buildVirtualXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
}
