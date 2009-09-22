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

import org.eclipse.jpt.core.jpa2.resource.persistence.XmlPersistenceUnitValidationModeType;

/**
 *  ValidationMode
 */
public enum ValidationMode {
	auto,
	callback,
	none; 

	// JPA value string
	public static final String AUTO = "AUTO";
	public static final String CALLBACK = "CALLBACK";
	public static final String NONE = "NONE";

	public static ValidationMode fromXmlResourceModel(XmlPersistenceUnitValidationModeType validationMode) {
		if (validationMode == null) {
			return null;
		}
		switch (validationMode) {
			case AUTO:
				return auto;
			case CALLBACK:
				return callback;
			case NONE:
				return none;
			default:
				throw new IllegalArgumentException("unknown validation mode: " + validationMode); //$NON-NLS-1$
		}
	}
	
	public static XmlPersistenceUnitValidationModeType toXmlResourceModel(ValidationMode validationMode) {
		if (validationMode == null) {
			return null;
		}
		switch (validationMode) {
			case auto:
				return XmlPersistenceUnitValidationModeType.AUTO;
			case callback:
				return XmlPersistenceUnitValidationModeType.CALLBACK;
			case none:
				return XmlPersistenceUnitValidationModeType.NONE;
			default:
				throw new IllegalArgumentException("unknown validation mode: " + validationMode); //$NON-NLS-1$
		}
	}
}

