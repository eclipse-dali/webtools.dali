/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.reference.ReferenceTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ReferenceToolsTests
	extends TestCase
{
	public ReferenceToolsTests(String name) {
		super(name);
	}

	public void testBooleanReference() {
		assertTrue(ReferenceTools.booleanReference(true).getValue());
		assertFalse(ReferenceTools.booleanReference(false).getValue());
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(ReferenceTools.class);
			fail("bogus: " + at);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}
}
