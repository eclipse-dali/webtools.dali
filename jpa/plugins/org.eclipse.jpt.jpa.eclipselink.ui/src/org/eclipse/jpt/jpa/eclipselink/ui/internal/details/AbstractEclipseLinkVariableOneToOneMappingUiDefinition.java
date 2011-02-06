/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractEclipseLinkVariableOneToOneMappingUiDefinition<M, T extends EclipseLinkVariableOneToOneMapping>
	extends AbstractMappingUiDefinition<M, T>
{	
	protected AbstractEclipseLinkVariableOneToOneMappingUiDefinition() {
		super();
	}
	
	
	public Image getImage() {
		return JptJpaUiPlugin.getImage(JptUiIcons.JPA_CONTENT);
	}
	
	public String getLabel() {
		return EclipseLinkUiDetailsMessages.EclipseLinkVariableOneToOneMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return EclipseLinkUiDetailsMessages.EclipseLinkVariableOneToOneMappingUiProvider_linkLabel;
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
}
