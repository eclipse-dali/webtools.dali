/*******************************************************************************
 *  Copyright (c) 2008, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.graphics.Image;

public class JpaMappingImageHelper
{

	public static Image imageForTypeMapping(String mappingKey) {
		return JptUiPlugin.getImage(iconKeyForTypeMapping(mappingKey));
	}

	public static String iconKeyForTypeMapping(String mappingKey) {
		if (Tools.valuesAreEqual(mappingKey, MappingKeys.NULL_TYPE_MAPPING_KEY)) {
			return JptUiIcons.NULL_TYPE_MAPPING;
		}
		if (Tools.valuesAreEqual(mappingKey, MappingKeys.ENTITY_TYPE_MAPPING_KEY)) {
			return JptUiIcons.ENTITY;
		}
		if (Tools.valuesAreEqual(mappingKey, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY)) {
			return JptUiIcons.EMBEDDABLE;
		}
		if (Tools.valuesAreEqual(mappingKey, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY)) {
			return JptUiIcons.MAPPED_SUPERCLASS;
		}
		return null;
	}

	public static Image imageForAttributeMapping(String mappingKey) {
		return JptUiPlugin.getImage(iconKeyForAttributeMapping(mappingKey));
	}

	public static String iconKeyForAttributeMapping(String mappingKey) {
		if (MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY == mappingKey) {
			return JptUiIcons.NULL_ATTRIBUTE_MAPPING;
		}
		else if (MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.BASIC;
		}
		else if (MappingKeys.ID_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.ID;
		}
		else if (MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.VERSION;
		}
		else if (MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.EMBEDDED_ID;
		}
		else if (MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.EMBEDDED;
		}
		else if (MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.ONE_TO_ONE;
		}
		else if (MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.ONE_TO_MANY;
		}
		else if (MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.MANY_TO_ONE;
		}
		else if (MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.MANY_TO_MANY;
		}
		else if (MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.ELEMENT_COLLECTION;
		}
		else if (MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptUiIcons.TRANSIENT;
		}
		//return the JPA_CONTENT icon instead of null, might as well have an icon if one is not defined
		return JptUiIcons.JPA_CONTENT;
	}
}
