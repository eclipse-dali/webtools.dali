/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.ArrayList;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.context.orm.NullOrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmBasicMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmEmbeddedIdMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmEmbeddedMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmIdMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmManyToManyMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmManyToOneMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmOneToManyMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmOneToOneMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmTransientMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmVersionMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.OrmElementCollectionMappingDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformProvider2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;

public class EclipseLinkOrmXmlDefinition2_0
	extends EclipseLinkAbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXmlDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXmlDefinition2_0() {
		super();
	}

	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink2_0.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextModelFactory buildContextModelFactory() {
		return new EclipseLinkOrmXmlContextModelFactory2_0();
	}

	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, ECLIPSELINK_2_0_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	/**
	 * Order should not matter here; but we'll use the same order as for Java.
	 * @see EclipseLinkJpaPlatformProvider2_0
	 */
	protected static final OrmAttributeMappingDefinition[] ECLIPSELINK_2_0_ATTRIBUTE_MAPPING_DEFINITIONS = new OrmAttributeMappingDefinition[] {
		OrmTransientMappingDefinition.instance(),
		EclipseLinkOrmBasicCollectionMappingDefinition.instance(),
		EclipseLinkOrmBasicMapMappingDefinition.instance(),
		OrmElementCollectionMappingDefinition2_0.instance(),
		OrmIdMappingDefinition.instance(),
		OrmVersionMappingDefinition.instance(),
		OrmBasicMappingDefinition.instance(),
		OrmEmbeddedMappingDefinition.instance(),
		OrmEmbeddedIdMappingDefinition.instance(),
		EclipseLinkOrmTransformationMappingDefinition.instance(),
		OrmManyToManyMappingDefinition.instance(),
		OrmManyToOneMappingDefinition.instance(),
		OrmOneToManyMappingDefinition.instance(),
		OrmOneToOneMappingDefinition.instance(),
		EclipseLinkOrmVariableOneToOneMappingDefinition.instance(),
		NullOrmAttributeMappingDefinition.instance()
	};
}
