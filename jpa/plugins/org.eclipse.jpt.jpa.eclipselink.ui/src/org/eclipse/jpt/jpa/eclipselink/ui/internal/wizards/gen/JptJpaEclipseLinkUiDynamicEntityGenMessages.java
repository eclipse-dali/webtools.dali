/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA ElipseLink UI dynamic entity generation.
 */
public class JptJpaEclipseLinkUiDynamicEntityGenMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_eclipselink_ui_dynamic_entity_gen"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaEclipseLinkUiDynamicEntityGenMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String GENERATE_DYNAMIC_ENTITIES_WIZARD__GENERATE_ENTITIES;
	
	//Default table gen properties
	public static String GENERATE_DYNAMIC_ENTITIES_WIZARD__DEFAULT_TABLE_PAGE__DOMAIN_JAVA_CLASS;
	public static String GENERATE_DYNAMIC_ENTITIES_WIZARD__DEFAULT_TABLE_PAGE__XML_MAPPING_FILE;

	private JptJpaEclipseLinkUiDynamicEntityGenMessages() {
		throw new UnsupportedOperationException();
	}

}
