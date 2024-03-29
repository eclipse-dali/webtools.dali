/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.JaxbProjectTestHarness;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbCoreContextModelTests;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.ELJaxbCoreResourceModelTests;

@SuppressWarnings("nls")
public class ELJaxbCoreTests {
	
	public static Test suite() {
		// ***** keep test.xml in sync with this list *****
		// test.xml is split up because this test suite was taking too
		// long and triggering a time-out during the WTP build
		TestSuite suite = new TestSuite(ELJaxbCoreTests.class.getPackage().getName());
		suite.addTest(ELJaxbCoreResourceModelTests.suite());
		suite.addTest(ELJaxbCoreContextModelTests.suite());
		suite.addTest(ELJaxbCoreMiscTests.suite());
		return suite;
	}
	
	public static boolean requiredJarsExists() {
		return jaxbJarPropertyExists() 
			&& jaxbJarFileExists()
			&& eclipselinkJarPropertyExists() 
			&& eclipselinkJarFileExists();
	}
	
	public static boolean jaxbJarPropertyExists() {
		return getSystemProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY) != null;
	}
	
	public static boolean jaxbJarFileExists() {
		return (new File(getSystemProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY))).exists();
	}
	
	public static boolean eclipselinkJarPropertyExists() {
		return getSystemProperty(JaxbProjectTestHarness.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY) != null;
	}
	
	public static boolean eclipselinkJarFileExists() {
		return (new File(getSystemProperty(JaxbProjectTestHarness.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY))).exists();
	}
	
	public static String buildMissingJarErrorMessage() {
		if( ! jaxbJarPropertyExists()) {
			return errorMissingProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY);
		}
		else if( ! jaxbJarFileExists()) {
			return errorJarFileDoesNotExist(getSystemProperty(JaxbProjectTestHarness.JAXB_JAR_NAME_SYSTEM_PROPERTY));
		}
		else if( ! eclipselinkJarPropertyExists()) {
			return errorMissingProperty(JaxbProjectTestHarness.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
		}
		return errorJarFileDoesNotExist(getSystemProperty(JaxbProjectTestHarness.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY));
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
	
	
	private ELJaxbCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
