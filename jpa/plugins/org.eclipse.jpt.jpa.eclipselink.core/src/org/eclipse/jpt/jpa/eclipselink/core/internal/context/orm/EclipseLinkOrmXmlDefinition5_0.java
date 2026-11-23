/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmPersistentTypeDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmConverterTypeDefinition2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v5_0.EclipseLink5_0;

/**
 * EclipseLink 5.0 <code>eclipselink-orm.xml</code> definition.
 * <p>
 * <code>eclipselink_orm_5_0.xsd</code> adds the Jakarta Persistence 3.2
 * check constraint, comment, options and second-precision nodes. They are not
 * yet in the resource model - see {@link EclipseLink5_0} - so the mapping and
 * managed type definitions are still those shared from 2.5.
 */
public class EclipseLinkOrmXmlDefinition5_0
	extends EclipseLinkAbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXmlDefinition5_0();

	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXmlDefinition5_0() {
		super();
	}


	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink5_0.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextModelFactory buildContextModelFactory() {
		return new EclipseLinkOrmXmlContextModelFactory2_5();
	}


	// ********* Managed Types *********

	@Override
	protected void addManagedTypeDefinitionsTo(ArrayList<OrmManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, MANAGED_TYPE_DEFINITIONS_5_0);
	}

	protected static final OrmManagedTypeDefinition[] MANAGED_TYPE_DEFINITIONS_5_0 = new OrmManagedTypeDefinition[] {
		OrmPersistentTypeDefinition.instance(),
		OrmConverterTypeDefinition2_1.instance(),
	};


	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkOrmXmlDefinition2_3.ECLIPSELINK_2_3_ATTRIBUTE_MAPPING_DEFINITIONS);
	}
}
