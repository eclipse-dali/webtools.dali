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

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.context.orm.NullOrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingDefinition;
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
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;

public abstract class AbstractEclipseLinkOrmXmlDefinition
	extends AbstractOrmXmlDefinition
{

	/**
	 * zero-argument constructor
	 */
	protected AbstractEclipseLinkOrmXmlDefinition() {
		super();
	}
	
	
	public EFactory getResourceNodeFactory() {
		return EclipseLinkOrmFactory.eINSTANCE;
	}
	
	
	// ********* ORM type mappings *********
	
	@Override
	protected OrmTypeMappingDefinition[] buildOrmTypeMappingDefinitions() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLinkJpaPlatformProvider}
		return new OrmTypeMappingDefinition[] {
			OrmEntityDefinition.instance(),
			OrmEmbeddableDefinition.instance(),
			OrmMappedSuperclassDefinition.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingDefinition[] buildOrmAttributeMappingDefinitions() {
		// order should not matter here, but we'll use the same order as for java
		// @see {@link EclipseLinkJpaPlatformProvider}
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
