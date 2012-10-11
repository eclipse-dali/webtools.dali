/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.CharacterTools;

public class CharacterToolsTests
	extends TestCase
{
	public void testEqualsIgnoreCase() {
		assertTrue(CharacterTools.equalsIgnoreCase('a', 'a'));
		assertTrue(CharacterTools.equalsIgnoreCase('a', 'A'));
		assertTrue(CharacterTools.equalsIgnoreCase('A', 'a'));
		assertTrue(CharacterTools.equalsIgnoreCase('A', 'A'));

		assertFalse(CharacterTools.equalsIgnoreCase('a', 'b'));
		assertFalse(CharacterTools.equalsIgnoreCase('A', 'b'));
	}
}
