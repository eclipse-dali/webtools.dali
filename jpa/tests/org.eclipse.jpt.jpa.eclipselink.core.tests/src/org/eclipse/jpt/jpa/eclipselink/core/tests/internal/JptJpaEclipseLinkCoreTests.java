/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.JptJpaEclipseLinkCoreContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.JptJpaEclipselinkCoreResourceModelTests;

/**
 * decentralize test creation code
 * 
 * Required Java system property:
 *    -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 *    -Dorg.eclipse.jpt.eclipselink.jar=<eclipselink.jar path>
 */
@SuppressWarnings("nls")
public class JptJpaEclipseLinkCoreTests
{
	private static final String JPA_JAR_PROPERTY = TestJpaProject.JPA_JAR_NAME_SYSTEM_PROPERTY;
	private static final String ECLIPSELINK_JAR_PROPERTY = TestJpaProject.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY;

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaEclipseLinkCoreTests.class.getPackage().getName());
		suite.addTest(JptJpaEclipselinkCoreResourceModelTests.suite());
		suite.addTest(JptJpaEclipseLinkCoreContextModelTests.suite());
		return suite;
	}

	public static boolean requiredJarsExists() {
		return jpaJarPropertyExists()
			&& jpaJarFileExists()
			&& eclipselinkJarPropertyExists()
			&& eclipselinkJarFileExists();
	}
	
	public static boolean jpaJarPropertyExists() {
		return getSystemProperty(JPA_JAR_PROPERTY) != null;
	}
	
	public static boolean jpaJarFileExists() {
		return (new File(getSystemProperty(JPA_JAR_PROPERTY))).exists();
	}
	
	public static boolean eclipselinkJarPropertyExists() {
		return getSystemProperty(ECLIPSELINK_JAR_PROPERTY) != null;
	}
	
	public static boolean eclipselinkJarFileExists() {
		return (new File(getSystemProperty(ECLIPSELINK_JAR_PROPERTY))).exists();
	}

	public static String buildMissingJarErrorMessage() {
		if( ! jpaJarPropertyExists()) {
			return errorMissingProperty(JPA_JAR_PROPERTY);
		}
		else if( ! jpaJarFileExists()) {
			return errorJarFileDoesNotExist(getSystemProperty(JPA_JAR_PROPERTY));
		}
		else if( ! eclipselinkJarPropertyExists()) {
			return errorMissingProperty(ECLIPSELINK_JAR_PROPERTY);
		}
		return errorJarFileDoesNotExist(getSystemProperty(ECLIPSELINK_JAR_PROPERTY));
	}

	/*********** private **********/

	private static String errorMissingProperty(String propertyName) {
		return "missing Java system property: \"" + propertyName + "\"";
	}

	private static String errorJarFileDoesNotExist(String propertyValue) {
		return "JAR file doesn't exist: \"" + propertyValue + "\"";
	}

	private static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}
	
	private JptJpaEclipseLinkCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
