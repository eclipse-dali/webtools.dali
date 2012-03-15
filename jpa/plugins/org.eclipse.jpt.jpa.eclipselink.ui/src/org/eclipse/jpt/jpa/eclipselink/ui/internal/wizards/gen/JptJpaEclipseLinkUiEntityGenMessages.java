/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.osgi.util.NLS;

public class JptJpaEclipseLinkUiEntityGenMessages {
	private static final String BUNDLE_NAME = "eclipselink_ui_dynamic_entity_gen"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaEclipseLinkUiEntityGenMessages.class;

	public static String GenerateDynamicEntitiesWizard_generateEntities;
	
	//Default table gen properties
	public static String GenerateDynamicEntitiesWizard_defaultTablePage_domainJavaClass;
	public static String GenerateDynamicEntitiesWizard_defaultTablePage_xmlMappingFile;

	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptJpaEclipseLinkUiEntityGenMessages() {
		throw new UnsupportedOperationException();
	}

}
