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
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;

public class OrmEmbeddedIdMappingProvider implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmEmbeddedIdMappingProvider INSTANCE = new OrmEmbeddedIdMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmEmbeddedIdMappingProvider() {
		super();
	}

	public OrmEmbeddedIdMapping buildAttributeMapping(JpaFactory factory, OrmPersistentAttribute parent) {
		return factory.buildOrmEmbeddedIdMapping(parent);
	}

	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}
}
