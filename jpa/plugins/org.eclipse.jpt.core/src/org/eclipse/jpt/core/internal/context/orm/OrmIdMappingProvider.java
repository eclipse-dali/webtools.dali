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
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;

public class OrmIdMappingProvider implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmIdMappingProvider INSTANCE = new OrmIdMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmIdMappingProvider() {
		super();
	}
	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	public OrmIdMapping buildAttributeMapping(JpaFactory factory, OrmPersistentAttribute parent) {
		return factory.buildOrmIdMapping(parent);
	}
}
