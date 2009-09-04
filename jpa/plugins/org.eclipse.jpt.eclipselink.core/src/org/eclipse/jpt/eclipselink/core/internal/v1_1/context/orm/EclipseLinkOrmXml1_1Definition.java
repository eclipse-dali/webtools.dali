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
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;

public class EclipseLinkOrmXml1_1Definition
	extends AbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = 
			new EclipseLinkOrmXml1_1Definition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml1_1Definition() {
		super();
	}
	
	@Override
	protected OrmXmlContextNodeFactory buildFactory() {
		return new EclipseLinkOrmXml1_1ContextNodeFactory();
	}
	
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE;
	}
	
	
	// ********* ORM type mappings *********
	
	@Override
	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLink1_1JpaPlatformProvider}
		// NOTE: no new type mapping providers from eclipselink 1.0 to 1.1
		return new OrmTypeMappingProvider[] {
			OrmEclipseLinkEntity1_1Provider.instance(),
			OrmEclipseLinkEmbeddable1_1Provider.instance(),
			OrmEclipseLinkMappedSuperclass1_1Provider.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLink1_1JpaPlatformProvider}
		return new OrmAttributeMappingProvider[] {
			OrmEclipseLinkTransientMapping1_1Provider.instance(),
			OrmEclipseLinkBasicCollectionMapping1_1Provider.instance(),
			OrmEclipseLinkBasicMapMapping1_1Provider.instance(),
			OrmEclipseLinkIdMapping1_1Provider.instance(),
			OrmEclipseLinkVersionMapping1_1Provider.instance(),
			OrmEclipseLinkBasicMapping1_1Provider.instance(),
			OrmEclipseLinkEmbeddedMapping1_1Provider.instance(),
			OrmEclipseLinkEmbeddedIdMapping1_1Provider.instance(),
			OrmEclipseLinkTransformationMapping1_1Provider.instance(),
			OrmEclipseLinkManyToManyMapping1_1Provider.instance(),
			OrmEclipseLinkManyToOneMapping1_1Provider.instance(),
			OrmEclipseLinkOneToManyMapping1_1Provider.instance(),
			OrmEclipseLinkOneToOneMapping1_1Provider.instance(),
			OrmEclipseLinkVariableOneToOneMapping1_1Provider.instance(),
			OrmEclipseLinkNullAttributeMapping1_1Provider.instance()};
	}
}
