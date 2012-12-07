/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jpt.common.ui.internal.util.PaneVisibilityEnabler;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.node.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public final class PaneVisibilityEnablerTest {
	private Composite parent;

	@Before
	public void setUp() {
		this.parent = new Composite(SWTUtil.getShell(), SWT.NONE);
		this.parent.setLayout(new GridLayout());
	}

	@After
	public void tearDown() {
		if (this.parent != null) {
			this.parent.dispose();
		}
	}

	@Test
	public void testSwitchState() {

		SimplePropertyValueModel<Boolean> booleanHolder = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);

		DialogPane<Node> pane = new DialogPane<Node>(
			new SimplePropertyValueModel<Node>(),
			this.parent,
			JFaceResources.getResources()
		) {
			@Override
			protected void initializeLayout(Composite container) {
				// NOP
			}
		};

		new PaneVisibilityEnabler(booleanHolder, pane);

		assertTrue(
			"The pane should be visible",
			pane.getControl().isVisible()
		);

		// Change state (null)
		booleanHolder.setValue(null);

		assertFalse(
			"The pane should not be visible",
			pane.getControl().isVisible()
		);

		// Change state (true)
		booleanHolder.setValue(Boolean.TRUE);

		assertTrue(
			"The pane should be visible",
			pane.getControl().isVisible()
		);

		// Change state (false)
		booleanHolder.setValue(Boolean.FALSE);

		assertFalse(
			"The pane should not be visible",
			pane.getControl().isVisible()
		);

		// Dispose
		booleanHolder.setValue(Boolean.TRUE);
	}
}
