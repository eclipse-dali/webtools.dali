/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;

public class EclipseLinkOrmXml2_1Definition
	extends AbstractEclipseLinkOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXml2_1Definition();

	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_1Definition() {
		super();
	}

	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink2_1.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextModelFactory buildContextModelFactory() {
		return new EclipseLinkOrmXmlContextModelFactory2_1();
	}

	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkOrmXml2_0Definition.ECLIPSELINK_2_0_ATTRIBUTE_MAPPING_DEFINITIONS);
	}
}
