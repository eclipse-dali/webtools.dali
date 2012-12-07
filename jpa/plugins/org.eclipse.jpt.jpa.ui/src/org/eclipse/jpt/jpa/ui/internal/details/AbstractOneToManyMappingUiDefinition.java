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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;

public abstract class AbstractOneToManyMappingUiDefinition<M, T extends OneToManyMapping>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractOneToManyMappingUiDefinition() {
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

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.ONE_TO_MANY;
	}
}
