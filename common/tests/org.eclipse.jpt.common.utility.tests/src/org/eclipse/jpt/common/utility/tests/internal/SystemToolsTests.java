/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.SystemTools;

@SuppressWarnings("nls")
public class SystemToolsTests
	extends TestCase
{
	public SystemToolsTests(String name) {
		super(name);
	}

	public void testJavaSpecificationVersionIsGreaterThan() {
		String version = SystemTools.javaSpecificationVersion();
		if (version.equals("1.4")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThan("1.3"));
		} else if (version.equals("1.5")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThan("1.4"));
		} else if (version.equals("1.6")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThan("1.5"));
		} else if (version.equals("1.7")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThan("1.6"));
		} else if (version.equals("1.8")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThan("1.7"));
		} else {
			fail("untested Java specification version: " + version);
		}
	}

	public void testJavaSpecificationVersionIsLessThan() {
		String version = SystemTools.javaSpecificationVersion();
		if (version.equals("1.4")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThan("1.5"));
		} else if (version.equals("1.5")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThan("1.6"));
		} else if (version.equals("1.6")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThan("1.7"));
		} else if (version.equals("1.7")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThan("2.0"));
		} else if (version.equals("1.8")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThan("2.0"));
		} else {
			fail("untested Java specification version: " + version);
		}
	}

	public void testJavaSpecificationVersionIsGreaterThanOrEqualTo() {
		String version = SystemTools.javaSpecificationVersion();
		if (version.equals("1.4")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.3"));
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.4"));
		} else if (version.equals("1.5")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.4"));
		} else if (version.equals("1.6")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.5"));
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.6"));
		} else if (version.equals("1.7")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.6"));
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.7"));
		} else if (version.equals("1.8")) {
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.7"));
			assertTrue(SystemTools.javaSpecificationVersionIsGreaterThanOrEqualTo("1.8"));
		} else {
			fail("untested Java specification version: " + version);
		}
	}

	public void testJavaSpecificationVersionIsLessThanOrEqualTo() {
		String version = SystemTools.javaSpecificationVersion();
		if (version.equals("1.4")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.5"));
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.4"));
		} else if (version.equals("1.5")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.5"));
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.6"));
		} else if (version.equals("1.6")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.6"));
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.7"));
		} else if (version.equals("1.7")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.7"));
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("2.0"));
		} else if (version.equals("1.8")) {
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("1.8"));
			assertTrue(SystemTools.javaSpecificationVersionIsLessThanOrEqualTo("2.0"));
		} else {
			fail("untested Java specification version: " + version);
		}
	}
}
