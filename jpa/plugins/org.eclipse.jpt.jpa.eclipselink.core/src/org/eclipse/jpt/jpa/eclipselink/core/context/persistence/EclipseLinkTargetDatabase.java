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
 *  TargetDatabase
 */
public enum EclipseLinkTargetDatabase implements PersistenceXmlEnumValue {
	attunity("Attunity"), //$NON-NLS-1$
	auto("Auto"), //$NON-NLS-1$
	cloudscape("Cloudscape"), //$NON-NLS-1$
	database("Database"), //$NON-NLS-1$
	db2("DB2"), //$NON-NLS-1$
	db2mainframe("DB2Mainframe"), //$NON-NLS-1$
	dbase("DBase"), //$NON-NLS-1$
	derby("Derby"), //$NON-NLS-1$
	hsql("HSQL"), //$NON-NLS-1$
	informix("Informix"), //$NON-NLS-1$
	informix11("Informix11"), //$NON-NLS-1$
	javadb("JavaDB"), //$NON-NLS-1$
	maxdb("MaxDB"), //$NON-NLS-1$
	mysql("MySQL"), //$NON-NLS-1$
	oracle("Oracle"), //$NON-NLS-1$
	oracle11("Oracle11"), //$NON-NLS-1$
	oracle10("Oracle10g"), //$NON-NLS-1$
	oracle9("Oracle9i"), //$NON-NLS-1$
	oracle8("Oracle8i"), //$NON-NLS-1$
	pointbase("PointBase"), //$NON-NLS-1$
	postgresql("PostgreSQL"), //$NON-NLS-1$
	sqlanywhere("SQLAnywhere"), //$NON-NLS-1$
	sqlserver("SQLServer"), //$NON-NLS-1$
	sybase("Sybase"), //$NON-NLS-1$
	symfoware("Symfoware"), //$NON-NLS-1$
	timesten("TimesTen"); //$NON-NLS-1$

	private final String propertyValue;

	EclipseLinkTargetDatabase(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}

	/**
	 * Return the TargetDatabase value corresponding to the given literal.
	 */
	public static EclipseLinkTargetDatabase getTargetDatabaseFor(String literal) {
		for (EclipseLinkTargetDatabase targetDatabase : EclipseLinkTargetDatabase.values()) {
			if (targetDatabase.toString().equals(literal)) {
				return targetDatabase;
			}
		}
		return null;
	}

	public static boolean isOracleDatabase(String literal) {
		return literal.contains("Oracle"); //$NON-NLS-1$
	}
}
