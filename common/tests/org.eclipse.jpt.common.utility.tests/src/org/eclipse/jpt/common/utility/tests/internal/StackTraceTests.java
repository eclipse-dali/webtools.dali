/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.StackTrace;

public class StackTraceTests
	extends TestCase
{
	public StackTraceTests(String name) {
		super(name);
	}

	public void testToString() throws Exception {
		StackTrace st = new StackTrace();
		assertTrue(st.toString().contains(this.getClass().getName()));
	}

	public void testToString_noTestClass() throws Exception {
		StackTrace st = new StackTrace(this.getClass());
		assertFalse(st.toString().contains(this.getClass().getName()));
	}
}
