/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
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
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.graphics.Image;


public abstract class AbstractBasicMappingUiProvider<T extends BasicMapping>
	implements AttributeMappingUiProvider<T>
{
	protected AbstractBasicMappingUiProvider() {}
	
	
	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getMappingKey());
	}
	
	public String getLabel() {
		return JptUiMappingsMessages.BasicMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return JptUiMappingsMessages.BasicMappingUiProvider_linkLabel;
	}
	
	public String getMappingKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
}
