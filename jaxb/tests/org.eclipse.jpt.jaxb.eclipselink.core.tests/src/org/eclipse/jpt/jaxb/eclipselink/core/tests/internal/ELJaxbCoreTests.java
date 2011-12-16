/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.TestJaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbCoreContextModelTests;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.ELJaxbCoreResourceModelTests;


public class ELJaxbCoreTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(ELJaxbCoreTests.class.getPackage().getName());
		suite.addTest(ELJaxbCoreResourceModelTests.suite());
		suite.addTest(ELJaxbCoreContextModelTests.suite());
		return suite;
	}
	
	public static boolean requiredJarsExists() {
		return elJaxbJarPropertyExists() && elJaxbJarFileExists();
	}
	
	public static boolean elJaxbJarPropertyExists() {
		return getSystemProperty(TestJaxbProject.EL_JAXB_JAR_NAME_SYSTEM_PROPERTY) != null;
	}
	
	public static boolean elJaxbJarFileExists() {
		return (new File(getSystemProperty(TestJaxbProject.EL_JAXB_JAR_NAME_SYSTEM_PROPERTY))).exists();
	}
	
	public static String buildMissingJarErrorMessage() {
		if( ! elJaxbJarPropertyExists()) {
			return errorMissingProperty(TestJaxbProject.EL_JAXB_JAR_NAME_SYSTEM_PROPERTY);
		}
		return errorJarFileDoesNotExist(getSystemProperty(TestJaxbProject.EL_JAXB_JAR_NAME_SYSTEM_PROPERTY));
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
