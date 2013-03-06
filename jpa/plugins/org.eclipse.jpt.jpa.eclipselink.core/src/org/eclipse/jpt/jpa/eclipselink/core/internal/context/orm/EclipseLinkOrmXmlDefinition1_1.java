/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;

public class EclipseLinkOrmXmlDefinition1_1
	extends EclipseLinkAbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXmlDefinition1_1();

	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXmlDefinition1_1() {
		super();
	}
	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink1_1.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextModelFactory buildContextModelFactory() {
		return EclipseLinkOrmXmlDefinition.instance().getContextModelFactory();
	}

	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkOrmXmlDefinition.ECLIPSELINK_ATTRIBUTE_MAPPING_DEFINITIONS);
	}
}
