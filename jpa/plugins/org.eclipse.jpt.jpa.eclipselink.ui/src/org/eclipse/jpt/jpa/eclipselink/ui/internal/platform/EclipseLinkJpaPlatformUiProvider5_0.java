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
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.EclipseLinkJavaResourceUiDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition1_1;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition1_2;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition2_1;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition3_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition3_1;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition5_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition2_5;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition2_7;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition3_1;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition5_0;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmXmlUiDefinition2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.orm.OrmXmlUiDefinition2_1;
import org.eclipse.jpt.jpa.ui.internal.jpa2_2.details.orm.OrmXmlUiDefinition2_2;
import org.eclipse.jpt.jpa.ui.internal.jpa3_0.details.orm.OrmXmlUiDefinition3_0;
import org.eclipse.jpt.jpa.ui.internal.jpa3_1.details.orm.OrmXmlUiDefinition3_1;
import org.eclipse.jpt.jpa.ui.internal.jpa3_2.details.orm.OrmXmlUiDefinition3_2;

/**
 * EclipseLink 5.0 platform UI.
 * <p>
 * Adds the Jakarta Persistence 3.2 and EclipseLink 5.0 mapping file UI.
 */
public class EclipseLinkJpaPlatformUiProvider5_0
	extends AbstractJpaPlatformUiProvider
{
	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLinkJpaPlatformUiProvider5_0();


	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJpaPlatformUiProvider5_0() {
		super();
	}


	// ********** resource ui definitions **********

	@Override
	protected void addResourceUiDefinitionsTo(List<ResourceUiDefinition> definitions) {
		definitions.add(EclipseLinkJavaResourceUiDefinition2_5.instance());
		definitions.add(OrmXmlUiDefinition.instance());
		definitions.add(OrmXmlUiDefinition2_0.instance());
		definitions.add(OrmXmlUiDefinition2_1.instance());
		definitions.add(OrmXmlUiDefinition2_2.instance());
		definitions.add(OrmXmlUiDefinition3_0.instance());
		definitions.add(OrmXmlUiDefinition3_1.instance());
		definitions.add(OrmXmlUiDefinition3_2.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition1_1.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition1_2.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition2_0.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition2_1.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition2_2.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition2_3.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition2_4.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition2_5.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition3_0.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition3_1.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition5_0.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition2_4.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition2_5.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition2_7.instance());
		// No 3_0 entry: a JPA 3.1 persistence.xml declares version="3.0", so the 3_1 definition already handles it (both would clash)
		definitions.add(EclipseLinkPersistenceXmlUiDefinition3_1.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition5_0.instance());
	}
}
