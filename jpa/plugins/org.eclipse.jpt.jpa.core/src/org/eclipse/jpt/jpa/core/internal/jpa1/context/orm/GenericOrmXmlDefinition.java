/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.context.orm.NullOrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformProvider;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.GenericOrmXmlContextModelFactory;
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
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;

public class GenericOrmXmlDefinition
	extends AbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new GenericOrmXmlDefinition();

	/**
	 * Return the singleton
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private GenericOrmXmlDefinition() {
		super();
	}

	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, JPA.SCHEMA_VERSION);
	}

	public EFactory getResourceModelFactory() {
		return OrmFactory.eINSTANCE;
	}

	@Override
	protected OrmXmlContextModelFactory buildContextModelFactory() {
		return new GenericOrmXmlContextModelFactory();
	}

	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, ATTRIBUTE_MAPPING_DEFINITIONS);
	}

	/**
	 * Order should not matter here; but we'll use the same order as for Java.
	 * @see GenericJpaPlatformProvider
	 */
	protected static final OrmAttributeMappingDefinition[] ATTRIBUTE_MAPPING_DEFINITIONS = new OrmAttributeMappingDefinition[] {
		OrmTransientMappingDefinition.instance(),
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
