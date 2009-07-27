/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public abstract class EclipseLinkVariableOneToOneMappingUiProvider<T extends EclipseLinkVariableOneToOneMapping>
	implements AttributeMappingUiProvider<T>
{	
	protected EclipseLinkVariableOneToOneMappingUiProvider() {
		super();
	}
		
	public Image getImage() {
		return JptUiPlugin.getImage(JptUiIcons.JPA_CONTENT);
	}
	
	public String getLabel() {
		return EclipseLinkUiMappingsMessages.EclipseLinkVariableOneToOneMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return EclipseLinkUiMappingsMessages.EclipseLinkVariableOneToOneMappingUiProvider_linkLabel;
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
}
