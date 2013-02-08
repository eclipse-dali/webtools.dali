/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToOneMappingUiDefinition;

public class DefaultJavaEclipseLinkOneToOneMappingUiDefinition
	extends AbstractOneToOneMappingUiDefinition
	implements DefaultMappingUiDefinition
{
	// singleton
	private static final DefaultJavaEclipseLinkOneToOneMappingUiDefinition INSTANCE = new DefaultJavaEclipseLinkOneToOneMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultMappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private DefaultJavaEclipseLinkOneToOneMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return null;
	}

	public String getDefaultKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.DEFAULT_ECLIPSELINK_ONE_TO_ONE_MAPPING_UI_PROVIDER_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.DEFAULT_ECLIPSELINK_ONE_TO_ONE_MAPPING_UI_PROVIDER_LINK_LABEL;
	}
}
