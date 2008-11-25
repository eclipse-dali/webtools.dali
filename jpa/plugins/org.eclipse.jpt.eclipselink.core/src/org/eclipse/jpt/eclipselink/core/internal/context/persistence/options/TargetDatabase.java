/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.options;

/**
 *  TargetDatabase
 */
public enum TargetDatabase {
			attunity,
			auto,
			cloudscape,
			database,
			db2,
			db2mainframe,
			dbase,
			derby,
			hsql,
			informix,
			javadb,
			mysql,
			oracle,
			oracle11,
			oracle10,
			oracle9,
			oracle8,
			pointbase,
			postgresql,
			sqlanywhere,
			sqlserver,
			sybase,
			timesten;

	// EclipseLink value string
	static final String ATTUNITY = "Attunity";
    static final String AUTO = "Auto";
    static final String CLOUDSCAPE = "Cloudscape";
    static final String DATABASE = "Database";
    static final String DB2 = "DB2";
    static final String DB2MAINFRAME = "DB2Mainframe";
    static final String DBASE = "DBase";
    static final String DERBY = "Derby";
    static final String HSQL = "HSQL";
    static final String INFORMIX = "Informix";
    static final String JAVADB = "JavaDB";
    static final String MYSQL = "MySQL";
    static final String ORACLE = "Oracle";
    static final String ORACLE11 = "Oracle11";
    static final String ORACLE10 = "Oracle10g";
    static final String ORACLE9 = "Oracle9i";
    static final String ORACLE8 = "Oracle8i";
    static final String POINTBASE = "PointBase";
    static final String POSTGRESQL = "PostgreSQL";
    static final String SQLANYWHERE = "SQLAnywhere";
    static final String SQLSERVER = "SQLServer";
    static final String SYBASE = "Sybase";
    static final String TIMESTEN = "TimesTen";

	/**
	 * Return the TargetDatabase value corresponding to the given literal.
	 */
	public static TargetDatabase getTargetDatabaseFor(String literal) {
		
		for( TargetDatabase targetDatabase : TargetDatabase.values()) {
			if(targetDatabase.toString().equals(literal)) {
				return targetDatabase;
			}
		}
		return null;
	}
}
