/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.persistence.options;

import org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0;

/**
 *  ValidationMode
 */
public enum ValidationMode 
{
	AUTO(XmlPersistenceUnitValidationModeType_2_0.AUTO),
	CALLBACK(XmlPersistenceUnitValidationModeType_2_0.CALLBACK),
	NONE(XmlPersistenceUnitValidationModeType_2_0.NONE); 

	private XmlPersistenceUnitValidationModeType_2_0 xmlEnumValue;

	ValidationMode(XmlPersistenceUnitValidationModeType_2_0 xmlEnumValue) {
		if (xmlEnumValue == null) {
			throw new NullPointerException();
		}
		this.xmlEnumValue = xmlEnumValue;
	}

	public XmlPersistenceUnitValidationModeType_2_0 getXmlEnumValue() {
		return this.xmlEnumValue;
	}
	
	
	// ********** static methods **********

	public static ValidationMode fromXmlResourceModel(XmlPersistenceUnitValidationModeType_2_0 xmlValidationMode) {
		return (xmlValidationMode == null) ? null : fromXmlResourceModel_(xmlValidationMode);
	}

	private static ValidationMode fromXmlResourceModel_(XmlPersistenceUnitValidationModeType_2_0 xmlValidationMode) {
		for (ValidationMode validationMode : ValidationMode.values()) {
			if (validationMode.getXmlEnumValue().equals(xmlValidationMode)) {
				return validationMode;
			}
		}
		return null;
	}

	public static XmlPersistenceUnitValidationModeType_2_0 toXmlResourceModel(ValidationMode validationMode) {
		return (validationMode == null) ? null : validationMode.getXmlEnumValue();
	}

}
