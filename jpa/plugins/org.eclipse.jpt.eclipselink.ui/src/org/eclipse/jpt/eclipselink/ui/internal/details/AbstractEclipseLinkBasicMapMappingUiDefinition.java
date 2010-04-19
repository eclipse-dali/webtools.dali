/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapMapping;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractEclipseLinkBasicMapMappingUiDefinition<M, T extends EclipseLinkBasicMapMapping>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractEclipseLinkBasicMapMappingUiDefinition() {
		super();
	}
	
	
	public Image getImage() {
		return JptUiPlugin.getImage(JptUiIcons.JPA_CONTENT);
	}
	
	public String getLabel() {
		return EclipseLinkUiDetailsMessages.EclipseLinkBasicMapMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return EclipseLinkUiDetailsMessages.EclipseLinkBasicMapMappingUiProvider_linkLabel;
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY;
	}
}
