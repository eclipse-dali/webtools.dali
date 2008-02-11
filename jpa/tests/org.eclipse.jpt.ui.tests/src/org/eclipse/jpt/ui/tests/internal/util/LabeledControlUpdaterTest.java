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

import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledLabel;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("nls")
public final class LabeledControlUpdaterTest {

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
	public void testSetImage() {

		Image expected = new Image(parent.getDisplay(), 16, 16);

		try {
			Label label = new Label(parent, SWT.NULL);
			LabeledLabel labeledLabel = new LabeledLabel(label);

			WritablePropertyValueModel<Image> imageHolder = new SimplePropertyValueModel<Image>();
			new LabeledControlUpdater(labeledLabel, null, imageHolder);

			labeledLabel.setImage(expected);

			assertEquals(
				"The Label didn't receive the Image",
				expected,
				label.getImage()
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
			Label label = new Label(parent, SWT.NULL);
			LabeledLabel labeledLabel = new LabeledLabel(label);

			WritablePropertyValueModel<Image> imageHolder = new SimplePropertyValueModel<Image>();
			new LabeledControlUpdater(labeledLabel, null, imageHolder);

			label.dispose();
			labeledLabel.setImage(expected);
		}
		finally {
			expected.dispose();
		}
	}

	@Test
	public void testSetText() {

		Label label = new Label(parent, SWT.NULL);
		LabeledLabel labeledLabel = new LabeledLabel(label);

		WritablePropertyValueModel<String> textHolder = new SimplePropertyValueModel<String>();
		new LabeledControlUpdater(labeledLabel, textHolder);

		String expected = "This is a test";
		textHolder.setValue(expected);

		assertEquals(
			"The Label didn't receive the text",
			expected,
			label.getText()
		);
	}

	@Test
	public void testSetTextDispose() {

		Label label = new Label(parent, SWT.NULL);
		LabeledLabel labeledLabel = new LabeledLabel(label);

		WritablePropertyValueModel<String> textHolder = new SimplePropertyValueModel<String>();
		new LabeledControlUpdater(labeledLabel, textHolder);

		label.dispose();

		String expected = "This is a test";
		textHolder.setValue(expected);
	}
}
