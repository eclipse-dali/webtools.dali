/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.EclipseLinkJavaResourceUiDefinition1_2;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition1_1;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition1_2;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmXmlUiDefinition;

public class EclipseLinkJpaPlatformUiProvider1_2
	extends AbstractJpaPlatformUiProvider
{
	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLinkJpaPlatformUiProvider1_2();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJpaPlatformUiProvider1_2() {
		super();
	}
	

	// ********** resource ui definitions **********
	
	@Override
	protected void addResourceUiDefinitionsTo(List<ResourceUiDefinition> definitions) {
		definitions.add(EclipseLinkJavaResourceUiDefinition1_2.instance());
		definitions.add(OrmXmlUiDefinition.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition1_1.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition1_2.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition.instance());
	}
}
