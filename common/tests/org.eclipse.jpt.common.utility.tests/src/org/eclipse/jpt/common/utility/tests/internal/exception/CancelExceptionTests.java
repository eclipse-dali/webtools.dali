/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.exception;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.exception.CancelException;

public class CancelExceptionTests
	extends TestCase
{
	public CancelExceptionTests(String name) {
		super(name);
	}

	public void testConstruction() {
		CancelException ex = new CancelException();
		assertNotNull(ex);
	}

	public void testToString() {
		CancelException ex = new CancelException();
		assertNotNull(ex.toString());
	}
}
