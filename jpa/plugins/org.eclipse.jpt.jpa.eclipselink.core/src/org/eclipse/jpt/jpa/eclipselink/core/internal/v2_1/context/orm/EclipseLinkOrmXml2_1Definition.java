/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_1.context.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.orm.EclipseLinkOrmXml2_0Definition;

public class EclipseLinkOrmXml2_1Definition
	extends EclipseLinkOrmXml2_0Definition
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
	protected EclipseLinkOrmXml2_1Definition() {
		super();
	}

	@Override
	public JptResourceType getResourceType() {
		return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_1_RESOURCE_TYPE;
	}

	@Override
	protected OrmXmlContextNodeFactory buildContextNodeFactory() {
		return new EclipseLinkOrmXml2_1ContextNodeFactory();
	}
}
