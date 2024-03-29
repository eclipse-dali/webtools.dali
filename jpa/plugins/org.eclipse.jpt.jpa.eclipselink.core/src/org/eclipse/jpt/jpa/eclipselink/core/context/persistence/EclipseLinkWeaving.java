/*******************************************************************************
* Copyright (c) 2008, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;

/**
 *  Weaving
 */
public enum EclipseLinkWeaving implements PersistenceXmlEnumValue {
	true_("true"), //$NON-NLS-1$
	false_("false"),  //$NON-NLS-1$
	static_("static"); //$NON-NLS-1$


	/**
	 * EclipseLink property value
	 */
	private final String propertyValue;

	EclipseLinkWeaving(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}
}
