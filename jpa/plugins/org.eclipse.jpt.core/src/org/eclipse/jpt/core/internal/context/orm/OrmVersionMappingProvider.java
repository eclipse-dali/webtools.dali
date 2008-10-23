/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;

public class OrmVersionMappingProvider implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmVersionMappingProvider INSTANCE = new OrmVersionMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmVersionMappingProvider() {
		super();
	}
	
	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	public OrmVersionMapping buildAttributeMapping(JpaFactory factory, OrmPersistentAttribute parent) {
		return factory.buildOrmVersionMapping(parent);
	}
}
