/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public final class ControlVisibilityEnablerTest {
	private Composite parent;

	@Before
	public void setUp() {
		parent = new Composite(DisplayTools.getShell(), SWT.NONE);
		parent.setLayout(new GridLayout());
	}

	@After
	public void tearDown() {
		if (parent != null) {
			parent.dispose();
		}
	}

	@Test
	public void testSwitchState() {

		SimplePropertyValueModel<Boolean> booleanHolder =
			new SimplePropertyValueModel<Boolean>(true);

		Combo combo = new Combo(parent, SWT.BORDER);

		SWTBindingTools.bindVisibleState(booleanHolder, combo);

		assertTrue(
			"The Combo should be visible",
			combo.isVisible()
		);

		// Change state (null)
		booleanHolder.setValue(null);

		assertFalse(
			"The Combo should not be visible",
			combo.isVisible()
		);

		// Change state (true)
		booleanHolder.setValue(true);

		assertTrue(
			"The Combo should be visible",
			combo.isVisible()
		);

		// Change state (false)
		booleanHolder.setValue(false);

		assertFalse(
			"The Combo should not be visible",
			combo.isVisible()
		);

		// Dispose
		combo.dispose();
		booleanHolder.setValue(true);
	}
}
