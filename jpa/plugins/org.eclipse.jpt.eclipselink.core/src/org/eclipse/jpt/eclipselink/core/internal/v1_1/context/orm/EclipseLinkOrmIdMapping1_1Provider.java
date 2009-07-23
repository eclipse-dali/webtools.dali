/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.EclipseLink1_1JpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.EclipseLink1_1OrmFactory;

public class EclipseLinkOrmIdMapping1_1Provider
	implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmAttributeMappingProvider INSTANCE = new EclipseLinkOrmIdMapping1_1Provider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmIdMapping1_1Provider() {
		super();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE;
	}

	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping() {
		return EclipseLink1_1OrmFactory.eINSTANCE.createXmlId();
	}

	public OrmAttributeMapping buildMapping(OrmPersistentAttribute parent, XmlAttributeMapping resourceMapping, JpaFactory factory) {
		return ((EclipseLink1_1JpaFactory) factory).buildEclipseLinkOrmIdMapping1_1(parent, (XmlId) resourceMapping);
	}

	public XmlAttributeMapping buildVirtualResourceMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping, JpaFactory factory) {
		return ((EclipseLink1_1JpaFactory) factory).buildEclipseLinkVirtualXmlId1_1(ormTypeMapping, (JavaIdMapping) javaAttributeMapping);
	}

}
