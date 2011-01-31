/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.ArrayList;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
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
import org.eclipse.jpt.core.internal.jpa2.Generic2_0JpaPlatformProvider;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class GenericOrmXml2_0Definition
	extends AbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new GenericOrmXml2_0Definition();

	/**
	 * Return the singleton
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private GenericOrmXml2_0Definition() {
		super();
	}

	public JptResourceType getResourceType() {
		return JptCorePlugin.ORM_XML_2_0_RESOURCE_TYPE;
	}

	public EFactory getResourceNodeFactory() {
		return OrmFactory.eINSTANCE;
	}

	@Override
	protected OrmXmlContextNodeFactory buildContextNodeFactory() {
		return new GenericOrmXml2_0ContextNodeFactory();
	}

	@Override
	protected void addTypeMappingDefinitionsTo(ArrayList<OrmTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, TYPE_MAPPING_DEFINITIONS);
	}

	/**
	 * Order should not matter here; but we'll use the same order as for Java.
	 * @see Generic2_0JpaPlatformProvider
	 */
	protected static final OrmTypeMappingDefinition[] TYPE_MAPPING_DEFINITIONS = new OrmTypeMappingDefinition[] {
		OrmEntityDefinition.instance(),
		OrmEmbeddableDefinition.instance(),
		OrmMappedSuperclassDefinition.instance()
	};

	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	/**
	 * Order should not matter here; but we'll use the same order as for Java.
	 * @see Generic2_0JpaPlatformProvider
	 */
	protected static final OrmAttributeMappingDefinition[] ATTRIBUTE_MAPPING_DEFINITIONS = new OrmAttributeMappingDefinition[] {
		OrmTransientMappingDefinition.instance(),
		OrmElementCollectionMapping2_0Definition.instance(),
		OrmIdMappingDefinition.instance(),
		OrmVersionMappingDefinition.instance(),
		OrmBasicMappingDefinition.instance(),
		OrmEmbeddedMappingDefinition.instance(),
		OrmEmbeddedIdMappingDefinition.instance(),
		OrmManyToManyMappingDefinition.instance(),
		OrmManyToOneMappingDefinition.instance(),
		OrmOneToManyMappingDefinition.instance(),
		OrmOneToOneMappingDefinition.instance(),
		NullOrmAttributeMappingDefinition.instance()
	};
}
