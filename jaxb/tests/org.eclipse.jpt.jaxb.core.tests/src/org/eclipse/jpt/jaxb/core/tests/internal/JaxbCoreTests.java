/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbCoreContextModelTests;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.TestJaxbProject;
import org.eclipse.jpt.jaxb.core.tests.internal.resource.JaxbCoreResourceModelTests;

public class JaxbCoreTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreTests.class.getPackage().getName());
		suite.addTestSuite(SchemaLibraryTests.class);
		suite.addTest(JaxbCoreResourceModelTests.suite());
		suite.addTest(JaxbCoreContextModelTests.suite());
		return suite;
	}
	
	public static boolean requiredJarsExists() {
		return jaxbJarPropertyExists() && jaxbJarFileExists();
	}
	
	public static boolean jaxbJarPropertyExists() {
		return getSystemProperty(TestJaxbProject.JAXB_JAR_NAME_SYSTEM_PROPERTY) != null;
	}
	
	public static boolean jaxbJarFileExists() {
		return (new File(getSystemProperty(TestJaxbProject.JAXB_JAR_NAME_SYSTEM_PROPERTY))).exists();
	}
	
	public static String buildMissingJarErrorMessage() {
		if( ! jaxbJarPropertyExists()) {
			return errorMissingProperty(TestJaxbProject.JAXB_JAR_NAME_SYSTEM_PROPERTY);
		}
		return errorJarFileDoesNotExist(getSystemProperty(TestJaxbProject.JAXB_JAR_NAME_SYSTEM_PROPERTY));
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
