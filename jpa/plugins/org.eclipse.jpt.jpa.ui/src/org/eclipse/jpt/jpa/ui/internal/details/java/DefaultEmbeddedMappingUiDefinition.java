/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingUiDefinition;

public class DefaultEmbeddedMappingUiDefinition
	extends AbstractEmbeddedMappingUiDefinition
	implements DefaultMappingUiDefinition
{
	// singleton
	private static final DefaultEmbeddedMappingUiDefinition INSTANCE = new DefaultEmbeddedMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultMappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private DefaultEmbeddedMappingUiDefinition() {
		super();
	}


	public String getKey() {
		return null;
	}

	public String getDefaultKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaUiDetailsMessages.DEFAULT_EMBEDDED_MAPPING_UI_PROVIDER_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaUiDetailsMessages.DEFAULT_EMBEDDED_MAPPING_UI_PROVIDER_LINK_LABEL;
	}
}
