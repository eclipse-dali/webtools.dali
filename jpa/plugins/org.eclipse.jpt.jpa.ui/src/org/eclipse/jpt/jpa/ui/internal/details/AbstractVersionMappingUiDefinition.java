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
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;

public abstract class AbstractVersionMappingUiDefinition<M, T extends VersionMapping>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractVersionMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getLabel() {
		return JptUiDetailsMessages.VersionMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return JptUiDetailsMessages.VersionMappingUiProvider_linkLabel;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.VERSION;
	}
}
