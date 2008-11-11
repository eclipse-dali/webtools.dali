/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.graphics.Image;

public class JpaMappingImageHelper
{
	public static Image imageForTypeMapping(String mappingKey) {
		if (MappingKeys.NULL_TYPE_MAPPING_KEY == mappingKey) {
			return JptUiPlugin.getImage(JptUiIcons.NULL_TYPE_MAPPING);
		}
		else if (MappingKeys.ENTITY_TYPE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ENTITY);
		}
		else if (MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.EMBEDDABLE);
		}
		else if (MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.MAPPED_SUPERCLASS);
		}
		return null;
	}
	
	public static Image imageForAttributeMapping(String mappingKey) {
		if (MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY == mappingKey) {
			return JptUiPlugin.getImage(JptUiIcons.NULL_ATTRIBUTE_MAPPING);
		}
		else if (MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.BASIC);
		}
		else if (MappingKeys.ID_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ID);
		}
		else if (MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.VERSION);
		}
		else if (MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.EMBEDDED_ID);
		}
		else if (MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.EMBEDDED);
		}
		else if (MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ONE_TO_ONE);
		}
		else if (MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ONE_TO_MANY);
		}
		else if (MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.MANY_TO_ONE);
		}
		else if (MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.MANY_TO_MANY);
		}
		else if (MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.TRANSIENT);
		}
		//return the JPA_CONTENT icon instead of null, might as well have an icon if one is not defined
		return JptUiPlugin.getImage(JptUiIcons.JPA_CONTENT);
	}
}
