/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.ExtendedOrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;

/**
 * EclipseLink ORM Embeddable provider
 * Allow for EclipseLink extensions to Embeddable
 */
public class EclipseLinkOrmEmbeddableProvider
	implements ExtendedOrmTypeMappingProvider
{

	// singleton
	private static final ExtendedOrmTypeMappingProvider INSTANCE = new EclipseLinkOrmEmbeddableProvider();

	/**
	 * Return the singleton.
	 */
	public static ExtendedOrmTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmEmbeddableProvider() {
		super();
	}

	public String getOrmType() {
		return EclipseLinkOrmResource.TYPE;
	}

	public OrmTypeMapping buildMapping(OrmPersistentType parent, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildEclipseLinkOrmEmbeddable(parent);
	}

	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

}
