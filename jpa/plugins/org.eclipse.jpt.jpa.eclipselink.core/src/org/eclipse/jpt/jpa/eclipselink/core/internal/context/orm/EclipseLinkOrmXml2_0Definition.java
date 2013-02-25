/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.OrmElementCollectionMapping2_0Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_0JpaPlatformProvider;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;

public class EclipseLinkOrmXml2_0Definition
	extends AbstractEclipseLinkOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXml2_0Definition();

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

	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink2_0.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextModelFactory buildContextNodeFactory() {
		return new EclipseLinkOrmXmlContextModelFactory2_0();
	}

	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, ECLIPSELINK_2_0_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	/**
	 * Order should not matter here; but we'll use the same order as for Java.
	 * @see EclipseLink2_0JpaPlatformProvider
	 */
	protected static final OrmAttributeMappingDefinition[] ECLIPSELINK_2_0_ATTRIBUTE_MAPPING_DEFINITIONS = new OrmAttributeMappingDefinition[] {
		OrmTransientMappingDefinition.instance(),
		OrmEclipseLinkBasicCollectionMappingDefinition.instance(),
		OrmEclipseLinkBasicMapMappingDefinition.instance(),
		OrmElementCollectionMapping2_0Definition.instance(),
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
		NullOrmAttributeMappingDefinition.instance()
	};
}
