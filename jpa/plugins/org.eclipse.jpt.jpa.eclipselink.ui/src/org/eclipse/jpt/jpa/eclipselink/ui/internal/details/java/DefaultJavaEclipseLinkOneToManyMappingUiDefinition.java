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
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToManyMappingUiDefinition;

public class DefaultJavaEclipseLinkOneToManyMappingUiDefinition
	extends AbstractOneToManyMappingUiDefinition
	implements DefaultMappingUiDefinition
{
	// singleton
	private static final DefaultJavaEclipseLinkOneToManyMappingUiDefinition INSTANCE = new DefaultJavaEclipseLinkOneToManyMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultMappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private DefaultJavaEclipseLinkOneToManyMappingUiDefinition() {
		super();
	}


	public String getKey() {
		return null;
	}

	public String getDefaultKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkOneToManyMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkOneToManyMappingUiProvider_linkLabel;
	}
}
