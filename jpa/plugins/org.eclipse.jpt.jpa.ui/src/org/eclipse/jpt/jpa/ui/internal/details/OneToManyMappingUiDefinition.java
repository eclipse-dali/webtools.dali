/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;

public class OneToManyMappingUiDefinition
	extends AbstractOneToManyMappingUiDefinition
{
	// singleton
	private static final OneToManyMappingUiDefinition INSTANCE = 
			new OneToManyMappingUiDefinition();


	/**
	 * Return the singleton.
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private OneToManyMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiDetailsMessages.OneToManyMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel;
	}
}
