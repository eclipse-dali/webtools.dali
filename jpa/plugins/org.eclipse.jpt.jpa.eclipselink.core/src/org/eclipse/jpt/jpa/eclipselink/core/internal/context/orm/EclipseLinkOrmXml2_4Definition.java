/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;


public class EclipseLinkOrmXml2_4Definition
		extends AbstractEclipseLinkOrmXmlDefinition {

	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXml2_4Definition();


	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_4Definition() {
		super();
	}


	public JptResourceType getResourceType() {
		return this.getResourceType(XmlEntityMappings.CONTENT_TYPE, EclipseLink2_4.SCHEMA_VERSION);
	}

	@Override
	protected OrmXmlContextNodeFactory buildContextNodeFactory() {
		return new EclipseLinkOrmXml2_4ContextNodeFactory();
	}

	@Override
	protected void addAttributeMappingDefinitionsTo(ArrayList<OrmAttributeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, EclipseLinkOrmXml2_3Definition.ECLIPSELINK_2_3_ATTRIBUTE_MAPPING_DEFINITIONS);
	}

}