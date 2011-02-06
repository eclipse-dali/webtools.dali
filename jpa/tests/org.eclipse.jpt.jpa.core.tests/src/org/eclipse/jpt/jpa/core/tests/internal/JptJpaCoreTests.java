/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.core.tests.internal.context.JptJpaCoreContextModelTests;
import org.eclipse.jpt.jpa.core.tests.internal.model.JptJpaCoreModelTests;
import org.eclipse.jpt.jpa.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.jpa.core.tests.internal.resource.JptJpaCoreResourceModelTests;

/**
 * decentralize test creation code
 * 
 * Required Java system property:
 *    -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 */
@SuppressWarnings("nls")
public class JptJpaCoreTests {
	private static final String JPA_JAR_PROPERTY = TestJpaProject.JPA_JAR_NAME_SYSTEM_PROPERTY;

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaCoreTests.class.getPackage().getName());
		suite.addTest(JptJpaCoreModelTests.suite());
		suite.addTest(JptJpaCoreResourceModelTests.suite());
		suite.addTest(JptJpaCoreContextModelTests.suite());
		return suite;
	}
	
	public static boolean requiredJarsExists() {
		return jpaJarPropertyExists() && jpaJarFileExists();
	}

	public static boolean jpaJarPropertyExists() {
		return getSystemProperty(JPA_JAR_PROPERTY) != null;
	}
	
	public static boolean jpaJarFileExists() {
		return (new File(getSystemProperty(JPA_JAR_PROPERTY))).exists();
	}

	public static String buildMissingJarErrorMessage() {
		if( ! jpaJarPropertyExists()) {
			return errorMissingProperty(JPA_JAR_PROPERTY);
		}
		return errorJarFileDoesNotExist(getSystemProperty(JPA_JAR_PROPERTY));
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
	
	private JptJpaCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
