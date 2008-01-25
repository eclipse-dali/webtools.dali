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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.swt.graphics.Image;

public class JpaMappingImageHelper
{
	public static Image imageForTypeMapping(String mappingKey) {
		if (IMappingKeys.NULL_TYPE_MAPPING_KEY == mappingKey) {
			return JptUiPlugin.getImage(JptUiIcons.NULL_TYPE_MAPPING);
		}
		else if (IMappingKeys.ENTITY_TYPE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ENTITY);
		}
		else if (IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.EMBEDDABLE);
		}
		else if (IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.MAPPED_SUPERCLASS);
		}
		return null;
	}
	
	public static Image imageForAttributeMapping(String mappingKey) {
		if (IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY == mappingKey) {
			return JptUiPlugin.getImage(JptUiIcons.NULL_ATTRIBUTE_MAPPING);
		}
		else if (IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.BASIC);
		}
		else if (IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ID);
		}
		else if (IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.VERSION);
		}
		else if (IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.EMBEDDED_ID);
		}
		else if (IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.EMBEDDED);
		}
		else if (IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ONE_TO_ONE);
		}
		else if (IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.ONE_TO_MANY);
		}
		else if (IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.MANY_TO_ONE);
		}
		else if (IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.MANY_TO_MANY);
		}
		else if (IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiPlugin.getImage(JptUiIcons.TRANSIENT);
		}
		return null;
	}
}
