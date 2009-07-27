/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public abstract class EclipseLinkTransformationMappingUiProvider<T extends EclipseLinkTransformationMapping>
	implements AttributeMappingUiProvider<T>
{
	
	protected EclipseLinkTransformationMappingUiProvider() {
		super();
	}
	
	public Image getImage() {
		return JptUiPlugin.getImage(JptUiIcons.JPA_CONTENT);
	}
	
	public String getLabel() {
		return EclipseLinkUiMappingsMessages.EclipseLinkTransformationMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return EclipseLinkUiMappingsMessages.EclipseLinkTransformationMappingUiProvider_linkLabel;
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
}
