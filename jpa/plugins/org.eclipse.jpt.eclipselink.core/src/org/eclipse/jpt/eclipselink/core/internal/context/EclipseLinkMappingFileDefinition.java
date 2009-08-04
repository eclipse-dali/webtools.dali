/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.platform.AbstractMappingFileDefinition;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddableProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddedIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddedMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEntityProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkMappedSuperclassProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkNullAttributeMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransientMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVariableOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVersionMappingProvider;

public class EclipseLinkMappingFileDefinition
	extends AbstractMappingFileDefinition
{
	// singleton
	private static final MappingFileDefinition INSTANCE = 
			new EclipseLinkMappingFileDefinition();

	/**
	 * Return the singleton.
	 */
	public static MappingFileDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkMappingFileDefinition() {
		super();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}

	public MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildEclipseLinkMappingFile(parent, resource);
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
