/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;

public class EclipseLinkOrmXml2_2Definition
	extends EclipseLinkOrmXml2_1Definition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = 
			new EclipseLinkOrmXml2_2Definition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected EclipseLinkOrmXml2_2Definition() {
		super();
	}
	
	
//	@Override
//	public EFactory getResourceNodeFactory() {
//		return EclipseLinkOrmFactory.eINSTANCE;
//	}
	
	@Override
	protected OrmXmlContextNodeFactory buildContextNodeFactory() {
		return new EclipseLinkOrmXml2_1ContextNodeFactory();
	}
	
	@Override
	public JptResourceType getResourceType() {
		return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_2_RESOURCE_TYPE;
	}
	
	
	// ********* ORM type mappings *********
	
//	@Override
//	protected OrmTypeMappingDefinition[] buildOrmTypeMappingDefinitions() {
//		// order should not matter here, but we'll use the same order as for java
//		// @see {@link EclipseLink1_1JpaPlatformProvider}
//		// NOTE: no new type mapping providers from eclipselink 1.0 to 1.1
//		return new OrmTypeMappingDefinition[] {
//			OrmEntityDefinition.instance(),
//			OrmEmbeddableDefinition.instance(),
//			OrmMappedSuperclassDefinition.instance()};
//	}
	
	
	// ********** ORM attribute mappings **********
	
//	@Override
//	protected OrmAttributeMappingDefinition[] buildOrmAttributeMappingDefinitions() {
//		// order should not matter here, but we'll use the same order as for java
//		// @see {@link EclipseLink1_1JpaPlatformProvider}
//		return new OrmAttributeMappingDefinition[] {
//			OrmTransientMappingDefinition.instance(),
//			OrmEclipseLinkBasicCollectionMappingDefinition.instance(),
//			OrmEclipseLinkBasicMapMappingDefinition.instance(),
//			OrmElementCollectionMapping2_0Definition.instance(),
//			OrmIdMappingDefinition.instance(),
//			OrmVersionMappingDefinition.instance(),
//			OrmBasicMappingDefinition.instance(),
//			OrmEmbeddedMappingDefinition.instance(),
//			OrmEmbeddedIdMappingDefinition.instance(),
//			OrmEclipseLinkTransformationMappingDefinition.instance(),
//			OrmManyToManyMappingDefinition.instance(),
//			OrmManyToOneMappingDefinition.instance(),
//			OrmOneToManyMappingDefinition.instance(),
//			OrmOneToOneMappingDefinition.instance(),
//			OrmEclipseLinkVariableOneToOneMappingDefinition.instance(),
//			NullOrmAttributeMappingDefinition.instance()};
//	}
}
