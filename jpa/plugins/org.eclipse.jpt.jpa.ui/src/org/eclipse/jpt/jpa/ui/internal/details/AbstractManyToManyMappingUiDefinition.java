/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;

public abstract class AbstractManyToManyMappingUiDefinition<M, T extends ManyToManyMapping>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractManyToManyMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getLabel() {
		return JptUiDetailsMessages.ManyToManyMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel;
	}
	@Override
	protected String getImageKey() {
		return JptUiIcons.MANY_TO_MANY;
	}
}
