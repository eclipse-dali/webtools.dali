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
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;

public class EclipseLinkOrmXmlDefinition
	extends AbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = 
			new EclipseLinkOrmXmlDefinition();

	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmXmlDefinition() {
		super();
	}
	
	@Override
	protected OrmXmlContextNodeFactory buildFactory() {
		return new EclipseLinkOrmXmlContextNodeFactory();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}
	
	
	// ********* ORM type mappings *********
	
	@Override
	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLinkJpaPlatformProvider}
		return new OrmTypeMappingProvider[] {
			OrmEclipseLinkEntityProvider.instance(),
			OrmEclipseLinkEmbeddableProvider.instance(),
			OrmEclipseLinkMappedSuperclassProvider.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLinkJpaPlatformProvider}
		return new OrmAttributeMappingProvider[] {
			OrmEclipseLinkTransientMappingProvider.instance(),
			OrmEclipseLinkBasicCollectionMappingProvider.instance(),
			OrmEclipseLinkBasicMapMappingProvider.instance(),
			OrmEclipseLinkIdMappingProvider.instance(),
			OrmEclipseLinkVersionMappingProvider.instance(),
			OrmEclipseLinkBasicMappingProvider.instance(),
			OrmEclipseLinkEmbeddedMappingProvider.instance(),
			OrmEclipseLinkEmbeddedIdMappingProvider.instance(),
			OrmEclipseLinkTransformationMappingProvider.instance(),
			OrmEclipseLinkManyToManyMappingProvider.instance(),
			OrmEclipseLinkManyToOneMappingProvider.instance(),
			OrmEclipseLinkOneToManyMappingProvider.instance(),
			OrmEclipseLinkOneToOneMappingProvider.instance(),
			OrmEclipseLinkVariableOneToOneMappingProvider.instance(),
			OrmEclipseLinkNullAttributeMappingProvider.instance()};
	}
}
