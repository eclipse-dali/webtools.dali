/*******************************************************************************
 *  Copyright (c) 2008, 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractEmbeddedMappingUiDefinition<M, T extends EmbeddedMapping>
	extends AbstractMappingUiDefinition<M, T>
{
	protected AbstractEmbeddedMappingUiDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiDetailsMessages.EmbeddedMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel;
	}

	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getKey());
	}
}
