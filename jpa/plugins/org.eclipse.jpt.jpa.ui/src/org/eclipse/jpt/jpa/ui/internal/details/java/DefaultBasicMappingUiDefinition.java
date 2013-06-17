/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractBasicMappingUiDefinition;

public class DefaultBasicMappingUiDefinition
	extends AbstractBasicMappingUiDefinition
	implements DefaultMappingUiDefinition
{
	// singleton
	private static final DefaultBasicMappingUiDefinition INSTANCE = new DefaultBasicMappingUiDefinition();


	/**
	 * Return the singleton.
	 */
	public static DefaultMappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private DefaultBasicMappingUiDefinition() {
		super();
	}


	public String getKey() {
		return null;
	}

	public String getDefaultKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaUiDetailsMessages.DEFAULT_BASIC_MAPPING_UI_PROVIDER_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaUiDetailsMessages.DEFAULT_BASIC_MAPPING_UI_PROVIDER_LINK_LABEL;
	}
}
