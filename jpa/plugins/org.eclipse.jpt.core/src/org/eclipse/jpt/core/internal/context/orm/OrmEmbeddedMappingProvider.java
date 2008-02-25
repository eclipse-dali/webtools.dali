/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.platform.JpaFactory;

public class OrmEmbeddedMappingProvider implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmEmbeddedMappingProvider INSTANCE = new OrmEmbeddedMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private OrmEmbeddedMappingProvider() {
		super();
	}

	public GenericOrmEmbeddedMapping buildAttributeMapping(JpaFactory factory, OrmPersistentAttribute parent) {
		return new GenericOrmEmbeddedMapping(parent);
	}

	public String key() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
}
