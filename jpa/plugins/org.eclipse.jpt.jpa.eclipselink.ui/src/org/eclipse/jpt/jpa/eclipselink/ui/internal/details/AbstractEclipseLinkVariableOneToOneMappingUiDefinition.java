/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;

public abstract class AbstractEclipseLinkVariableOneToOneMappingUiDefinition<M, T extends EclipseLinkVariableOneToOneMapping>
	extends AbstractMappingUiDefinition<M, T>
{	
	protected AbstractEclipseLinkVariableOneToOneMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return EclipseLinkUiDetailsMessages.EclipseLinkVariableOneToOneMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return EclipseLinkUiDetailsMessages.EclipseLinkVariableOneToOneMappingUiProvider_linkLabel;
	}
}
