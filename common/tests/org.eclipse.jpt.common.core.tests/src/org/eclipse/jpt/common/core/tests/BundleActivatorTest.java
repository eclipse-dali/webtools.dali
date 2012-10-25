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

import java.util.Dictionary;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;

/**
 * This test case will verify that a class's "bundle activator" class
 * (i.e. its "plug-in" class - e.g. <code>JptCommonCorePlugin</code>)
 * has not been (accidentally) exported.
 * <p>
 * Construct the test case with any class exported by the plug-in to be verified.
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
		// get the list of exported packages...
		Dictionary<String, String> headers = this.bundle.getHeaders();
		String exportPackageHeader = headers.get("Export-Package");
		String[] exportPackageEntries = exportPackageHeader.split(",");
		String[] exportPackages = ArrayTools.transform(exportPackageEntries, PACKAGE_NAME_EXTRACTOR, String.class);

		// ...get the activator class's package...
		BundleContext bundleContext = this.bundle.getBundleContext();
		// a little hackery:
		BundleActivator activator = (BundleActivator) ObjectTools.get(bundleContext, "activator");
		String activatorClassName = activator.getClass().getName();
		Class<?> activatorClass = activator.getClass().getClassLoader().loadClass(activatorClassName);
		String activatorPackage = activatorClass.getPackage().getName();

		// ...and make sure it is not exported
		if (ArrayTools.contains(exportPackages, activatorPackage)) {
			fail("Bundle activator class is exported: " + activatorClassName);
		}
	}

	private static final TransformerAdapter<String, String> PACKAGE_NAME_EXTRACTOR = new PackageNameExtractor();
	/* CU private */ static class PackageNameExtractor
		extends TransformerAdapter<String, String>
	{
		@Override
		public String transform(String exportPackageEntry) {
			return exportPackageEntry.split(";")[0];
		}
	}
}
