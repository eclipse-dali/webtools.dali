/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.platform;

import junit.framework.TestCase;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.platform.JpaPlatformRegistry;
import org.eclipse.jpt.core.tests.extension.resource.ExtensionTestPlugin;
import org.eclipse.jpt.core.tests.extension.resource.TestJpaPlatform;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class JpaPlatformExtensionTests extends TestCase
{
	public static final String TEST_PLATFORM_ID = TestJpaPlatform.ID;
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
	
	public JpaPlatformExtensionTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		verifyExtensionTestProjectExists();
	}

	public static void verifyExtensionTestProjectExists() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = 
			registry.getExtensionPoint(JptCorePlugin.PLUGIN_ID, "jpaPlatform");
		IExtension[] extensions = extensionPoint.getExtensions();
		boolean extensionFound = false;
		for (IExtension extension : extensions) {
			if (extension.getContributor().getName().equals(ExtensionTestPlugin.PLUGIN_ID)) {
				extensionFound = true;
			}
		}
		if (!extensionFound) {
			throw new RuntimeException("Missing Extension " + TEST_PLATFORM_ID + ". The ExtensionTestProject plugin must be in your testing workspace.");
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAllJpaPlatformIds() {
		assertTrue(CollectionTools.size(JpaPlatformRegistry.instance().jpaPlatformIds()) >= 2);
	}
	
	public void testJpaPlatformLabel() {
		assertEquals(TEST_PLATFORM_LABEL, JpaPlatformRegistry.instance().getJpaPlatformLabel(TEST_PLATFORM_ID));	
	}
	
	public void testJpaPlatform() {
		assertNotNull(JpaPlatformRegistry.instance().getJpaPlatform(TEST_PLATFORM_ID));		
	}

}
