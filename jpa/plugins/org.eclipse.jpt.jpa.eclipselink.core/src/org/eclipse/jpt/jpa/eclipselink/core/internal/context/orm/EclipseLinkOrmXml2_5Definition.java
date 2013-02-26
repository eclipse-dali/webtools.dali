/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmPersistentTypeDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmConverterTypeDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLink2_5;


public class EclipseLinkOrmXml2_5Definition
		extends AbstractEclipseLinkOrmXmlDefinition {

	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXml2_5Definition();


	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_5Definition() {
		super();
	}


	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink2_5.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextModelFactory buildContextModelFactory() {
		return new EclipseLinkOrmXmlContextModelFactory2_5();
	}


	// ********* Managed Types *********

	@Override
	protected void addManagedTypeDefinitionsTo(ArrayList<OrmManagedTypeDefinition> definitions) {
		CollectionTools.addAll(definitions, MANAGED_TYPE_DEFINITIONS_2_5);
	}

	protected static final OrmManagedTypeDefinition[] MANAGED_TYPE_DEFINITIONS_2_5 = new OrmManagedTypeDefinition[] {
		OrmPersistentTypeDefinition.instance(),
		OrmConverterTypeDefinition.instance(),
	};


	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkOrmXml2_3Definition.ECLIPSELINK_2_3_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

}