/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.EclipseLinkJavaResourceUiDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXml1_1UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXml1_2UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXml2_0UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXml2_1UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXml2_2UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXml2_3UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition2_4;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.EclipseLinkPersistenceXml2_4UiDefinition;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmXmlUiDefinition2_0;


public class EclipseLink2_4JpaPlatformUiProvider
		extends AbstractJpaPlatformUiProvider {

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLink2_4JpaPlatformUiProvider();


	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLink2_4JpaPlatformUiProvider() {
		super();
	}


	// ********** resource ui definitions **********

	@Override
	protected void addResourceUiDefinitionsTo(List<ResourceUiDefinition> definitions) {
		definitions.add(EclipseLinkJavaResourceUiDefinition2_3.instance());
		definitions.add(OrmXmlUiDefinition.instance());
		definitions.add(OrmXmlUiDefinition2_0.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition.instance());
		definitions.add(EclipseLinkOrmXml1_1UiDefinition.instance());
		definitions.add(EclipseLinkOrmXml1_2UiDefinition.instance());
		definitions.add(EclipseLinkOrmXml2_0UiDefinition.instance());
		definitions.add(EclipseLinkOrmXml2_1UiDefinition.instance());
		definitions.add(EclipseLinkOrmXml2_2UiDefinition.instance());
		definitions.add(EclipseLinkOrmXml2_3UiDefinition.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition2_4.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition.instance());
		definitions.add(EclipseLinkPersistenceXml2_4UiDefinition.instance());
	}
}