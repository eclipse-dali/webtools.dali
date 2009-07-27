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
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable;

/**
 * EclipseLink ORM Embeddable provider
 * Allow for EclipseLink extensions to Embeddable
 */
public class OrmEclipseLinkEmbeddableProvider
	implements OrmTypeMappingProvider
{

	// singleton
	private static final OrmTypeMappingProvider INSTANCE = new OrmEclipseLinkEmbeddableProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkEmbeddableProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}
	
	public XmlTypeMapping buildResourceMapping() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlEmbeddable();
	}

	public OrmTypeMapping buildMapping(OrmPersistentType parent, XmlTypeMapping resourceMapping, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildOrmEclipseLinkEmbeddable(parent, (XmlEmbeddable) resourceMapping);
	}

	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

}
