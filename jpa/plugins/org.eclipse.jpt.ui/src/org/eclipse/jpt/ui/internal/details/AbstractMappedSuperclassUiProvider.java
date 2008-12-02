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
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractMappedSuperclassUiProvider<T extends MappedSuperclass>
	implements TypeMappingUiProvider<T>
{
	protected AbstractMappedSuperclassUiProvider() {}
	
	
	public String getMappingKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}
	
	public String getLabel() {
		return JptUiMappingsMessages.MappedSuperclassUiProvider_label;
	}
	
	public String getLinkLabel() {
		return JptUiMappingsMessages.MappedSuperclassUiProvider_linkLabel;
	}
	
	public Image getImage() {
		return JpaMappingImageHelper.imageForTypeMapping(getMappingKey());
	}
}
