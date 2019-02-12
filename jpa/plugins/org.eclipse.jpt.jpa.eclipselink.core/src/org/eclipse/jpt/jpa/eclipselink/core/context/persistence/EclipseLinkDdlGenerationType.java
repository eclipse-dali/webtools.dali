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
 *  DdlGenerationType
 */
public enum EclipseLinkDdlGenerationType implements PersistenceXmlEnumValue{
	none("none") ,  //$NON-NLS-1$
	create_tables("create-tables"),  //$NON-NLS-1$
	drop_and_create_tables("drop-and-create-tables"); //$NON-NLS-1$

	private final String propertyValue;
	
	EclipseLinkDdlGenerationType(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}
}