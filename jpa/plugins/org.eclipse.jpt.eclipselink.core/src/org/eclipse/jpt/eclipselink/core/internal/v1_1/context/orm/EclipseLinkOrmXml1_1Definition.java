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

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.context.orm.NullOrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmBasicMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddableDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedIdMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmEntityDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmIdMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToManyMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToOneMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmMappedSuperclassDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToManyMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToOneMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmTransientMappingDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmVersionMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicCollectionMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransformationMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVariableOneToOneMappingDefinition;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.EclipseLink1_1OrmFactory;

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
	
	
	public EFactory getResourceNodeFactory() {
		return EclipseLink1_1OrmFactory.eINSTANCE;
	}
	
	@Override
	protected OrmXmlContextNodeFactory buildContextNodeFactory() {
		return new EclipseLinkOrmXml1_1ContextNodeFactory();
	}
	
	public JpaResourceType getResourceType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE;
	}
	
	
	// ********* ORM type mappings *********
	
	@Override
	protected OrmTypeMappingDefinition[] buildOrmTypeMappingDefinitions() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLink1_1JpaPlatformProvider}
		// NOTE: no new type mapping providers from eclipselink 1.0 to 1.1
		return new OrmTypeMappingDefinition[] {
			OrmEntityDefinition.instance(),
			OrmEmbeddableDefinition.instance(),
			OrmMappedSuperclassDefinition.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingDefinition[] buildOrmAttributeMappingDefinitions() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLink1_1JpaPlatformProvider}
		return new OrmAttributeMappingDefinition[] {
			OrmTransientMappingDefinition.instance(),
			OrmEclipseLinkBasicCollectionMappingDefinition.instance(),
			OrmEclipseLinkBasicMapMappingDefinition.instance(),
			OrmIdMappingDefinition.instance(),
			OrmVersionMappingDefinition.instance(),
			OrmBasicMappingDefinition.instance(),
			OrmEmbeddedMappingDefinition.instance(),
			OrmEmbeddedIdMappingDefinition.instance(),
			OrmEclipseLinkTransformationMappingDefinition.instance(),
			OrmManyToManyMappingDefinition.instance(),
			OrmManyToOneMappingDefinition.instance(),
			OrmOneToManyMappingDefinition.instance(),
			OrmOneToOneMappingDefinition.instance(),
			OrmEclipseLinkVariableOneToOneMappingDefinition.instance(),
			NullOrmAttributeMappingDefinition.instance()};
	}
}
