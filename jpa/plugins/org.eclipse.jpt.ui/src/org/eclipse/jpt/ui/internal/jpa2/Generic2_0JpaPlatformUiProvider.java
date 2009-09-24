/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2;

import java.util.List;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.FileUiDefinition;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaPersistentAttributeDetailsProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaPersistentTypeDetailsProvider;
import org.eclipse.jpt.ui.internal.details.orm.EntityMappingsDetailsProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmXmlUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmPersistentAttributeDetailsProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmPersistentTypeDetailsProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.java.Generic2_0JavaFileUiDefinition;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.EntityMappings2_0DetailsProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmXml2_0UiDefinition;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class Generic2_0JpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new Generic2_0JpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Generic2_0JpaPlatformUiProvider() {
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
		providers.add(EntityMappings2_0DetailsProvider.instance());
	}
	
	
	// ********** file ui definitions **********
	
	@Override
	protected void addFileUiDefinitionsTo(List<FileUiDefinition> providers) {
		providers.add(Generic2_0JavaFileUiDefinition.instance());
		providers.add(OrmXmlUiDefinition.instance());
		providers.add(OrmXml2_0UiDefinition.instance());
	}
}