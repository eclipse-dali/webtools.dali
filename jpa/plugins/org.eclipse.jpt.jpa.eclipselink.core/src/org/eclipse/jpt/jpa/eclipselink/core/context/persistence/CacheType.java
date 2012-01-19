/*******************************************************************************
* Copyright (c) 2008, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;

/**
 *  CacheType
 */
public enum CacheType implements PersistenceXmlEnumValue {
	soft_weak("SoftWeak"),  //$NON-NLS-1$
	hard_weak("HardWeak"),  //$NON-NLS-1$
	weak("Weak"), //$NON-NLS-1$
	soft("Soft"), //$NON-NLS-1$
	full("Full"), //$NON-NLS-1$
	none("NONE");  //$NON-NLS-1$

	private final String propertyValue;

	CacheType(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}
}
