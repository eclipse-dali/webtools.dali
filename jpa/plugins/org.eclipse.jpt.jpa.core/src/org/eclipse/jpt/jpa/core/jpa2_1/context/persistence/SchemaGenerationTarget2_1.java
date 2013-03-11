/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;

/**
 *  SchemaGenerationSource
 */
public enum SchemaGenerationTarget2_1 implements PersistenceXmlEnumValue {
	metadata("metadata"),  //$NON-NLS-1$
	script("script"),  //$NON-NLS-1$
	metadata_then_script("metadata-then-script"),  //$NON-NLS-1$
	script_then_metadata("script-then-metadata"); //$NON-NLS-1$

	private final String propertyValue;
	
	SchemaGenerationTarget2_1(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}
}