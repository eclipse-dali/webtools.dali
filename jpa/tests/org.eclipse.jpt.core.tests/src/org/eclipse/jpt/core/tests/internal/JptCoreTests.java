/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.core.tests.internal.context.JptCoreContextModelTests;
import org.eclipse.jpt.core.tests.internal.model.JptCoreModelTests;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.core.tests.internal.resource.JptCoreResourceModelTests;
import org.eclipse.jpt.core.tests.internal.utility.jdt.JptCoreUtilityJdtTests;

/**
 * Runs MOST JPT Core Tests. Currently we do not have a jpa.jar checked into cvs. 
 * As a result we cannot run any tests that depend on that jar during the nightly build.
 * In our development environments we should run JptAllCoreTests (NOT this suite)
 * until jpa.jar is checked into CVS.
 */
@SuppressWarnings("nls")
public class JptCoreTests {
	private static final String JPA_JAR_PROPERTY = TestJpaProject.JPA_JAR_NAME_SYSTEM_PROPERTY;

	public static Test suite() {
		return suite(false);
	}
	
	// TODO delete parameter all
	public static Test suite(boolean all) {
		String quantity = all ? "All" : "Most";
		TestSuite suite = new TestSuite(quantity + " JPT Core Tests");

		if(requiredJarsExists()) {
			suite.addTest(JptCoreUtilityJdtTests.suite(all));
			suite.addTest(JptCoreModelTests.suite(all));
			suite.addTest(JptCoreResourceModelTests.suite(all));
			suite.addTest(JptCoreContextModelTests.suite(all));
		}
		else {
			suite.addTest(TestSuite.warning(buildMissingJarErrorMessage()));
		}
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

	/*********** private **********/
	private static String buildMissingJarErrorMessage() {

		if( ! jpaJarPropertyExists()) {
			return errorMissingProperty(JPA_JAR_PROPERTY);
		}
		return errorJarFileDoesNotExist(getSystemProperty(JPA_JAR_PROPERTY));
	}

	private static String errorMissingProperty(String propertyName) {
		return "missing Java system property: \"" + propertyName + "\"";
	}

	private static String errorJarFileDoesNotExist(String propertyValue) {
		return "JAR file doesn't exist: \"" + propertyValue + "\"";
	}

	private static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}
	
	private JptCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
