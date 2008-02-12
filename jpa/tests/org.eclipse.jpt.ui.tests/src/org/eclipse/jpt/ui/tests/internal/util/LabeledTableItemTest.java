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

import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.jpt.ui.internal.util.LabeledTableItem;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("nls")
public final class LabeledTableItemTest {

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
			parent = null;
		}
	}

	@Test
	public void testLabeledButton1() {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent);
		new LabeledTableItem(checkBox.getCheckBox());
	}

	@Test(expected=AssertionFailedException.class)
	public void testLabeledButton2() {
		new LabeledTableItem(null);
	}

	@Test
	public void testSetImage() {

		Image expected = new Image(parent.getDisplay(), 16, 16);

		try {
			TriStateCheckBox checkBox = new TriStateCheckBox(parent);
			LabeledTableItem labeledTableItem = new LabeledTableItem(checkBox.getCheckBox());

			labeledTableItem.setImage(expected);

			assertEquals(
				"The TriStateCheckBox didn't receive the Image",
				expected,
				checkBox.getImage()
			);
		}
		finally {
			expected.dispose();
		}
	}

	@Test
	public void testSetImageDispose() {

		Image expected = new Image(parent.getDisplay(), 16, 16);

		try {
			TriStateCheckBox checkBox = new TriStateCheckBox(parent);
			LabeledTableItem labeledTableItem = new LabeledTableItem(checkBox.getCheckBox());

			checkBox.dispose();

			// This should not fail but simply do nothing
			labeledTableItem.setImage(expected);
		}
		finally {
			expected.dispose();
		}
	}

	@Test
	public void testSetText() {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent);
		LabeledTableItem labeledTableItem = new LabeledTableItem(checkBox.getCheckBox());

		String expected = "This is a test";
		labeledTableItem.setText(expected);

		assertEquals(
			"The TriStateCheckBox didn't receive the text",
			expected,
			checkBox.getText()
		);
	}

	@Test
	public void testSetTextDispose() {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent);
		LabeledTableItem labeledTableItem = new LabeledTableItem(checkBox.getCheckBox());

		checkBox.dispose();

		// This should not fail but simply do nothing
		String expected = "This is a test";
		labeledTableItem.setText(expected);
	}
}
