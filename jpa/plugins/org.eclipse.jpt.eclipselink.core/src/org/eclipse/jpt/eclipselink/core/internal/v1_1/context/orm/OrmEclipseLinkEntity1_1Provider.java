/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.EclipseLink1_1OrmFactory;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlEntity;

/**
 * EclipseLink ORM Entity provider
 * Allow for EclipseLink extensions to Entity
 */
public class OrmEclipseLinkEntity1_1Provider
	implements OrmTypeMappingProvider
{
	// singleton
	private static final OrmTypeMappingProvider INSTANCE = new OrmEclipseLinkEntity1_1Provider();

	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkEntity1_1Provider() {
		super();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE;
	}
	
	public XmlTypeMapping buildResourceMapping() {
		return EclipseLink1_1OrmFactory.eINSTANCE.createXmlEntity();
	}

	public OrmTypeMapping buildMapping(OrmPersistentType parent, XmlTypeMapping resourceMapping, OrmXmlContextNodeFactory factory) {
		return factory.buildOrmEntity(parent, (XmlEntity) resourceMapping);
	}

	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

}
