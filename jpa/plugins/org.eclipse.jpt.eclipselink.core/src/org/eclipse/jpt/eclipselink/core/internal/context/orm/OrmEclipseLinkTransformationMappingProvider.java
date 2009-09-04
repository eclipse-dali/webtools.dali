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

import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkOrmXmlContextNodeFactory;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;

public class OrmEclipseLinkTransformationMappingProvider
	implements OrmAttributeMappingProvider
{

	// singleton
	private static final OrmEclipseLinkTransformationMappingProvider INSTANCE = new OrmEclipseLinkTransformationMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkTransformationMappingProvider() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTransformation();
	}

	public OrmAttributeMapping buildMapping(OrmPersistentAttribute parent, XmlAttributeMapping resourceMapping, OrmXmlContextNodeFactory factory) {
		return ((EclipseLinkOrmXmlContextNodeFactory) factory).buildOrmEclipseLinkTransformationMapping(parent, (XmlTransformation) resourceMapping);
	}
	
	public XmlAttributeMapping buildVirtualResourceMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping, OrmXmlContextNodeFactory factory) {
		return ((EclipseLinkOrmXmlContextNodeFactory) factory).buildVirtualEclipseLinkXmlTransformation(ormTypeMapping, (JavaEclipseLinkTransformationMapping) javaAttributeMapping);
	}
}
