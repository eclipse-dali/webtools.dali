/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.context.orm.NullOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEntityProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmVersionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVariableOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmFactory;

public class EclipseLinkOrmXml2_0Definition
	extends AbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = 
			new EclipseLinkOrmXml2_0Definition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_0Definition() {
		super();
	}
	
	
	public EFactory getResourceNodeFactory() {
		return EclipseLink2_0OrmFactory.eINSTANCE;
	}
	
	@Override
	protected OrmXmlContextNodeFactory buildContextNodeFactory() {
		return new EclipseLinkOrmXml2_0ContextNodeFactory();
	}
	
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK2_0_ORM_XML_CONTENT_TYPE;
	}
	
	
	// ********* ORM type mappings *********
	
	@Override
	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLink1_1JpaPlatformProvider}
		// NOTE: no new type mapping providers from eclipselink 1.0 to 1.1
		return new OrmTypeMappingProvider[] {
			OrmEntityProvider.instance(),
			OrmEmbeddableProvider.instance(),
			OrmMappedSuperclassProvider.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLink1_1JpaPlatformProvider}
		return new OrmAttributeMappingProvider[] {
			OrmTransientMappingProvider.instance(),
			OrmEclipseLinkBasicCollectionMappingProvider.instance(),
			OrmEclipseLinkBasicMapMappingProvider.instance(),
			OrmIdMappingProvider.instance(),
			OrmVersionMappingProvider.instance(),
			OrmBasicMappingProvider.instance(),
			OrmEmbeddedMappingProvider.instance(),
			OrmEmbeddedIdMappingProvider.instance(),
			OrmEclipseLinkTransformationMappingProvider.instance(),
			OrmManyToManyMappingProvider.instance(),
			OrmManyToOneMappingProvider.instance(),
			OrmOneToManyMappingProvider.instance(),
			OrmOneToOneMappingProvider.instance(),
			OrmEclipseLinkVariableOneToOneMappingProvider.instance(),
			NullOrmAttributeMappingProvider.instance()};
	}
}
