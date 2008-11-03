/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.JptEclipseLinkCoreContextModelTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.resource.JptEclipselinkCoreResourceModelTests;

/**
 * decentralize test creation code
 */
@SuppressWarnings("nls")
public class JptEclipseLinkCoreTests
{
	private static final String JPA_JAR_PROPERTY = TestJpaProject.JPA_JAR_NAME_SYSTEM_PROPERTY;

	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreTests.class.getPackage().getName());
		
		if(jpaJarPropertyExists() && jpaJarFileExists()) {
			suite.addTest(JptEclipselinkCoreResourceModelTests.suite());
			suite.addTest(JptEclipseLinkCoreContextModelTests.suite());
		}
		else {
			String message = ( ! jpaJarPropertyExists()) ?
				"missing Java system property: \"" + JPA_JAR_PROPERTY + "\"" :
				"missing JPA jar file: \"" + getJpaJarProperty() + "\"";
			suite.addTest(TestSuite.warning(message));
		}
		return suite;
	}
	
	public static boolean jpaJarPropertyExists() {
		String jpaJarName = getJpaJarProperty();
		return jpaJarName != null;
	}
	
	public static boolean jpaJarFileExists() {
		File file = new File(getJpaJarProperty());
		return file.exists();
	}
	
	public static String getJpaJarProperty() {
		return System.getProperty(JPA_JAR_PROPERTY);
	}

	private JptEclipseLinkCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
