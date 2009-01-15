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

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.ExtendedOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmXmlResource;

public class EclipseLinkOrmBasicMappingProvider
	implements ExtendedOrmAttributeMappingProvider
{
	// singleton
	private static final ExtendedOrmAttributeMappingProvider INSTANCE = new EclipseLinkOrmBasicMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static ExtendedOrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmBasicMappingProvider() {
		super();
	}

	public String getOrmType() {
		return EclipseLinkOrmXmlResource.TYPE;
	}

	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public OrmAttributeMapping buildMapping(OrmPersistentAttribute parent, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildEclipseLinkOrmBasicMapping(parent);
	}

	public XmlAttributeMapping buildVirtualResourceMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildEclipseLinkVirtualXmlBasic(ormTypeMapping, (JavaBasicMapping) javaAttributeMapping);
	}

}
