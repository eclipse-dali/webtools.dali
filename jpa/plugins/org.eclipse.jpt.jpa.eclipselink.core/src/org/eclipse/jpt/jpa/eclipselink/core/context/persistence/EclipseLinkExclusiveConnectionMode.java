/*******************************************************************************
* Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
 *  ExclusiveConnectionMode
 */
public enum EclipseLinkExclusiveConnectionMode implements PersistenceXmlEnumValue {
	always("Always"), //$NON-NLS-1$
	isolated("Isolated"), //$NON-NLS-1$
	transactional("Transactional"); //$NON-NLS-1$

	private final String propertyValue;

	EclipseLinkExclusiveConnectionMode(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}
}
