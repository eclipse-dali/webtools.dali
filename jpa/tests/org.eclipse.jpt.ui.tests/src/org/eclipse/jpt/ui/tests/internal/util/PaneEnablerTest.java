/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.util;

import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("nls")
public final class PaneEnablerTest {
	private Composite parent;

	@Before
	public void setUp() {
		parent = new Composite(SWTUtil.getShell(), SWT.NONE);
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

		DialogPane<Node> pane = new DialogPane<Node>(
			new SimplePropertyValueModel<Node>(),
			parent)
		{
			@Override
			protected void initializeLayout(Composite container) {
			}
		};

		pane.populate();
		new PaneEnabler(booleanHolder, pane);

		assertTrue(
			"The pane should be enabled",
			pane.getControl().isEnabled()
		);

		// Change state (null)
		booleanHolder.setValue(null);

		assertFalse(
			"The pane should not be enabled",
			pane.getControl().isEnabled()
		);

		// Change state (true)
		booleanHolder.setValue(true);

		assertTrue(
			"The pane should be enabled",
			pane.getControl().isEnabled()
		);

		// Change state (false)
		booleanHolder.setValue(false);

		assertFalse(
			"The pane should not be enabled",
			pane.getControl().isEnabled()
		);

		// Dispose
		pane.dispose();
		booleanHolder.setValue(true);
	}
}
