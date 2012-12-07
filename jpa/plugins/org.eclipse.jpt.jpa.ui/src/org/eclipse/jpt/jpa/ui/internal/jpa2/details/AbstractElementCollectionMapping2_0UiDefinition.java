/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;

public abstract class AbstractElementCollectionMapping2_0UiDefinition<M, T extends ElementCollectionMapping2_0>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractElementCollectionMapping2_0UiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiDetailsMessages2_0.ElementCollectionMapping2_0_label;
	}

	public String getLinkLabel() {
		return JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.ELEMENT_COLLECTION;
	}
}
