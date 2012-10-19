/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;

/**
 * This test case will verify that a class's "bundle activator" class
 * (i.e. its "plug-in" class - e.g. <code>JptCommonCorePlugin</code>)
 * has not been exported (accidentally).
 * <p>
 * Construct the test case with any class exported by the plug-in to be verified.
 * <p>
 * We use reflection(!) to fetch the bundle activator from the class's bundle
 * and then attempt to dynamically load the activator's class. This will fail
 * if the activator's class is <em>not</em> exported; which is what we want.
 */
@SuppressWarnings("nls")
public class BundleActivatorTest
	extends TestCase
{
	private final Class<?> clazz;
	private final Bundle bundle;

	public BundleActivatorTest(Class<?> clazz) {
		super(buildName(clazz));
		this.bundle = buildBundle(clazz);
		this.clazz = clazz;
	}

	private static String buildName(Class<?> clazz) {
		Bundle bundle = buildBundle(clazz);
		return BundleActivatorTest.class.getSimpleName() + ": " + bundle.getSymbolicName();
	}

	private static Bundle buildBundle(Class<?> clazz) {
		ClassLoader classLoader = clazz.getClassLoader();
		return (classLoader instanceof BundleReference) ? ((BundleReference) classLoader).getBundle() : null;
	}

	@Override
	protected void runTest() throws Throwable {
		if (this.bundle == null) {
			fail("non-bundle class: " + this.clazz);
		}
		BundleContext bundleContext = this.bundle.getBundleContext();
		// a little hackery:
		BundleActivator activator = (BundleActivator) ObjectTools.get(bundleContext, "activator");
		String activatorClassName = activator.getClass().getName();
		try {
			Class<?> activatorClass = activator.getClass().getClassLoader().loadClass(activatorClassName);
			fail("activator class is exported: " + activatorClass.getName());
		} catch (ClassNotFoundException ex) {
			// success
		}
	}
}
