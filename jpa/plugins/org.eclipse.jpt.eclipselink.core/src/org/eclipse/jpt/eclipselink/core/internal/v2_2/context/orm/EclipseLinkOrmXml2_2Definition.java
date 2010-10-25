/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_2.context.orm;

import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.v2_1.context.orm.EclipseLinkOrmXml2_1ContextNodeFactory;
import org.eclipse.jpt.eclipselink.core.internal.v2_1.context.orm.EclipseLinkOrmXml2_1Definition;

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
	private EclipseLinkOrmXml2_2Definition() {
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
	public JpaResourceType getResourceType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_2_RESOURCE_TYPE;
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
