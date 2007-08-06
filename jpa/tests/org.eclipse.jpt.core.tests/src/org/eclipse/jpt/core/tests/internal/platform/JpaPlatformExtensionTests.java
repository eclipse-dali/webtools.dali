/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.platform;

import junit.framework.TestCase;
import org.eclipse.jpt.core.internal.JpaPlatformRegistry;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class JpaPlatformExtensionTests extends TestCase
{
	public static final String TEST_PLATFORM_ID = "core.testJpaPlatform";
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
	
	public JpaPlatformExtensionTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAllJpaPlatformIds() {
		assertEquals(2, CollectionTools.size(JpaPlatformRegistry.instance().allJpaPlatformIds()));
	}
	
	public void testJpaPlatformLabel() {
		assertEquals(TEST_PLATFORM_LABEL, JpaPlatformRegistry.instance().jpaPlatformLabel(TEST_PLATFORM_ID));	
	}
	
	public void testJpaPlatform() {
		assertNotNull(JpaPlatformRegistry.instance().jpaPlatform(TEST_PLATFORM_ID));		
	}

}
