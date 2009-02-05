/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.ExtendedOrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;

/**
 * EclipseLink ORM Mapped Superclass provider
 * Allow for EclipseLink extensions to Mapped Superclass
 */
public class EclipseLinkOrmMappedSuperclassProvider
	implements ExtendedOrmTypeMappingProvider
{
	// singleton
	private static final ExtendedOrmTypeMappingProvider INSTANCE = new EclipseLinkOrmMappedSuperclassProvider();

	/**
	 * Return the singleton.
	 */
	public static ExtendedOrmTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmMappedSuperclassProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}

	public OrmTypeMapping buildMapping(OrmPersistentType parent, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildEclipseLinkOrmMappedSuperclass(parent);
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

}
