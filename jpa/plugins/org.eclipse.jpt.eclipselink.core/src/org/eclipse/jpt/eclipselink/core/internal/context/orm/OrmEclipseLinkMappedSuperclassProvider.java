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
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass;

/**
 * EclipseLink ORM Mapped Superclass provider
 * Allow for EclipseLink extensions to Mapped Superclass
 */
public class OrmEclipseLinkMappedSuperclassProvider
	implements OrmTypeMappingProvider
{
	// singleton
	private static final OrmTypeMappingProvider INSTANCE = new OrmEclipseLinkMappedSuperclassProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkMappedSuperclassProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}
	
	public XmlTypeMapping buildResourceMapping() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlMappedSuperclass();
	}

	public OrmTypeMapping buildMapping(OrmPersistentType parent, XmlTypeMapping resourceMapping, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildOrmEclipseLinkMappedSuperclass(parent, (XmlMappedSuperclass) resourceMapping);
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

}
