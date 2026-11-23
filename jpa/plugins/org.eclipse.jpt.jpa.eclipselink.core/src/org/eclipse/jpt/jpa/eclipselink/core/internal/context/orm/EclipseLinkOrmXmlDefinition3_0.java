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
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v3_0.EclipseLink3_0;

/**
 * EclipseLink 3.0 <code>eclipselink-orm.xml</code> definition.
 * <p>
 * The EclipseLink mapping file content is that of 2.5 less the deprecated
 * <code>&lt;cache shared&gt;</code> attribute, so the mapping and managed type
 * definitions are shared; only the resource type version differs.
 */
public class EclipseLinkOrmXmlDefinition3_0
	extends EclipseLinkAbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXmlDefinition3_0();

	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXmlDefinition3_0() {
		super();
	}


	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink3_0.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextModelFactory buildContextModelFactory() {
		return new EclipseLinkOrmXmlContextModelFactory2_5();
	}


	// ********* Managed Types *********

	@Override
	protected void addManagedTypeDefinitionsTo(ArrayList<OrmManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, MANAGED_TYPE_DEFINITIONS_3_0);
	}

	protected static final OrmManagedTypeDefinition[] MANAGED_TYPE_DEFINITIONS_3_0 = new OrmManagedTypeDefinition[] {
		OrmPersistentTypeDefinition.instance(),
		OrmConverterTypeDefinition2_1.instance(),
	};


	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkOrmXmlDefinition2_3.ECLIPSELINK_2_3_ATTRIBUTE_MAPPING_DEFINITIONS);
	}
}
