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
 *  BatchWriting
 */
public enum EclipseLinkBatchWriting implements PersistenceXmlEnumValue {
	none("None"),  //$NON-NLS-1$
	jdbc("JDBC"),  //$NON-NLS-1$
	buffered("Buffered"),  //$NON-NLS-1$
	oracle_jdbc("Oracle-JDBC"); //$NON-NLS-1$

	private final String propertyValue;

	EclipseLinkBatchWriting(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}
}
