/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.platform.AbstractMappingFileDefinition;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddableProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEntityProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkMappedSuperclassProvider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.EclipseLink1_1JpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkBasicCollectionMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkBasicMapMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkBasicMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkEmbeddedIdMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkEmbeddedMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkIdMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkManyToManyMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkManyToOneMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkNullAttributeMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkOneToManyMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkOneToOneMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkTransformationMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkTransientMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkVariableOneToOneMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkVersionMapping1_1Provider;

public class EclipseLink1_1MappingFileDefinition
	extends AbstractMappingFileDefinition
{
	// singleton
	private static final MappingFileDefinition INSTANCE = 
			new EclipseLink1_1MappingFileDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static MappingFileDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLink1_1MappingFileDefinition() {
		super();
	}
	
	
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE;
	}
	
	public MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource, JpaFactory factory) {
		return ((EclipseLink1_1JpaFactory) factory).buildEclipseLink1_1MappingFile(parent, resource);
	}
	
	
	// ********* ORM type mappings *********
	
	@Override
	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLink1_1JpaPlatformProvider}
		// NOTE: no new type mapping providers from eclipselink 1.0 to 1.1
		return new OrmTypeMappingProvider[] {
			OrmEclipseLinkEntityProvider.instance(),
			OrmEclipseLinkEmbeddableProvider.instance(),
			OrmEclipseLinkMappedSuperclassProvider.instance()};
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
