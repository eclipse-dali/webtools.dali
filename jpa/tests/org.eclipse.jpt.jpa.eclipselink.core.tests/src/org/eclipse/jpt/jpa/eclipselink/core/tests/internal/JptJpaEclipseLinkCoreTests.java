/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.core.tests.internal.projects.JpaProjectTestHarness;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.JptJpaEclipseLinkCoreContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.JptJpaEclipseLinkCoreResourceModelTests;

/**
 * decentralize test creation code
 * <p>
 * Required Java system properties:<code><ul>
 * <li>-Dorg.eclipse.jpt.jpa.jar=&lt;jpa.jar path&gt;
 * <li>-Dorg.eclipse.jpt.eclipselink.jar=&lt;eclipselink.jar path&gt;
 * </ul></code>
 */
@SuppressWarnings("nls")
public class JptJpaEclipseLinkCoreTests {

	private static final String JPA_JAR_PROPERTY = JpaProjectTestHarness.JPA_JAR_NAME_SYSTEM_PROPERTY;
	private static final String ECLIPSELINK_JAR_PROPERTY = JpaProjectTestHarness.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY;

	public static Test suite() {
		// ***** keep test.xml in sync with this list *****
		// test.xml is split up because this test suite was taking too
		// long and triggering a time-out during the WTP build
		TestSuite suite = new TestSuite(JptJpaEclipseLinkCoreTests.class.getPackage().getName());
		suite.addTest(JptJpaEclipseLinkCoreResourceModelTests.suite());
		suite.addTest(JptJpaEclipseLinkCoreContextModelTests.suite());
		suite.addTest(JptJpaEclipseLinkCoreMiscTests.suite());
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
