/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;

public abstract class EclipseLinkAbstractBasicCollectionMappingUiDefinition
	extends AbstractMappingUiDefinition
{
	protected EclipseLinkAbstractBasicCollectionMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_BASIC_COLLECTION_MAPPING_UI_PROVIDER_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_BASIC_COLLECTION_MAPPING_UI_PROVIDER_LINK_LABEL;
	}
}
