/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.Synchronizer;

public class SynchronizerTests extends TestCase {

	public SynchronizerTests(String name) {
		super(name);
	}

	public void testNullSynchronizerStart() {
		Synchronizer synchronizer = Synchronizer.Null.instance();
		synchronizer.start();  // just make sure it doesn't blow up?
	}

	public void testNullSynchronizerSynchronize() {
		Synchronizer synchronizer = Synchronizer.Null.instance();
		synchronizer.synchronize();  // just make sure it doesn't blow up?
	}

	public void testNullSynchronizerStop() {
		Synchronizer synchronizer = Synchronizer.Null.instance();
		synchronizer.stop();  // just make sure it doesn't blow up?
	}

	public void testNullSynchronizerToString() {
		Synchronizer synchronizer = Synchronizer.Null.instance();
		assertNotNull(synchronizer.toString());
	}

}
