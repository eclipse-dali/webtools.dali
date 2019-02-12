/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbCoreContextModelTests;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.JaxbProjectTestHarness;
import org.eclipse.jpt.jaxb.core.tests.internal.resource.JaxbCoreResourceModelTests;
import org.eclipse.jpt.jaxb.core.tests.internal.utility.JaxbCoreUtilityTests;

@SuppressWarnings("nls")
public class JaxbCoreTests {

	public static Test suite() {
		// ***** keep test.xml in sync with this list *****
		// test.xml is split up because this test suite was taking too
		// long and triggering a time-out during the WTP build
		TestSuite suite = new TestSuite(JaxbCoreTests.class.getPackage().getName());
		suite.addTest(JaxbCoreResourceModelTests.suite());
		suite.addTest(JaxbCoreContextModelTests.suite());
		suite.addTest(JaxbCoreUtilityTests.suite());
		suite.addTest(JaxbCoreMiscTests.suite());
		return suite;
	}
	
	public static boolean requiredJarsExists() {
		return jaxbJarPropertyExists() && jaxbJarFileExists();
	}
	
	public static boolean jaxbJarPropertyExists() {
		return getSystemProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY) != null;
	}
	
	public static boolean jaxbJarFileExists() {
		return (new File(getSystemProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY))).exists();
	}
	
	public static String buildMissingJarErrorMessage() {
		if( ! jaxbJarPropertyExists()) {
			return errorMissingProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY);
		}
		return errorJarFileDoesNotExist(getSystemProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY));
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
	
	
	private JaxbCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
