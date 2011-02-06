/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.EclipseLinkJavaResourceUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkEntityMappingsDetailsProvider;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkOrmXmlUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.details.java.JavaPersistentAttributeDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.java.JavaPersistentTypeDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.EntityMappingsDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmPersistentAttributeDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmPersistentTypeDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmXmlUiDefinition;

public class EclipseLinkJpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLinkJpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		providers.add(JavaPersistentTypeDetailsProvider.instance());
		providers.add(JavaPersistentAttributeDetailsProvider.instance());
		providers.add(EntityMappingsDetailsProvider.instance());
		providers.add(OrmPersistentTypeDetailsProvider.instance());
		providers.add(OrmPersistentAttributeDetailsProvider.instance());
		providers.add(EclipseLinkEntityMappingsDetailsProvider.instance());
	}
	
	
	// ********** resource ui definitions **********
	
	@Override
	protected void addResourceUiDefinitionsTo(List<ResourceUiDefinition> definitions) {
		definitions.add(EclipseLinkJavaResourceUiDefinition.instance());
		definitions.add(OrmXmlUiDefinition.instance());
		definitions.add(EclipseLinkOrmXmlUiDefinition.instance());
		definitions.add(EclipseLinkPersistenceXmlUiDefinition.instance());
	}
}
