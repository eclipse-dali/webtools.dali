/*******************************************************************************
 * Copyright (c) 1998, 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License
 * v1.0, both of which accompany this distribution.
 * The Eclipse Public License is available at
 * http://www.eclipse.org/org/documents/epl-2.0/.
 * The Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.eol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;

public class CheckWinEOLTests
	extends TestCase
{
	public CheckWinEOLTests(String name) {
		super(name);
	}

	public void testStreamHasInvalidWinEOL() throws IOException {
		byte CR = CheckWinEOL.CR;
		byte LF = CheckWinEOL.LF;
	
		// invalid EOLs
		this.verifyInvalidWinEOL(new byte[] {CR});
		this.verifyInvalidWinEOL(new byte[] {LF});
		this.verifyInvalidWinEOL(new byte[] {LF, CR});
		this.verifyInvalidWinEOL(new byte[] {CR, CR, LF});
		this.verifyInvalidWinEOL(new byte[] {CR, LF, LF});
		this.verifyInvalidWinEOL(new byte[] {CR, LF, CR});
	
		// valid EOLs
		this.verifyValidWinEOL(new byte[] {});
		this.verifyValidWinEOL(new byte[] {CR, LF});
		this.verifyValidWinEOL(new byte[] {CR, LF, CR, LF});
		this.verifyValidWinEOL(new byte[] {CR, LF, 'a', CR, LF});
	}

	private void verifyInvalidWinEOL(byte[] bytes) throws IOException {
		assertTrue(CheckWinEOL.streamHasInvalidWinEOL(this.buildInputStream(bytes)));
	}

	private void verifyValidWinEOL(byte[] bytes) throws IOException {
		assertFalse(CheckWinEOL.streamHasInvalidWinEOL(this.buildInputStream(bytes)));
	}

	private InputStream buildInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}
}
