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

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jpt.ui.internal.util.ControlAligner;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("nls")
public final class ControlAlignerTest {

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
	public void testAddControl1() throws Exception {

		Label label = new Label(parent, SWT.NULL);

		ControlAligner controlAligner = new ControlAligner();
		controlAligner.add(label);

		assertEquals(
			"The maximum width should be 0,",
			0,
			controlAligner.getMaximumWidth()
		);

		label.setText("This is a ControlAligner");
		parent.layout(true, true);

		Point size = label.getSize();

		assertEquals(
			"The width should be " + size.x + ",",
			size.x,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testAddControl2() throws Exception {

		Button button = new Button(parent, SWT.NULL);
		button.setText("This is a ControlAligner");
		parent.layout(true, true);

		ControlAligner controlAligner = new ControlAligner();
		controlAligner.add(button);

		Point size = button.getSize();

		assertEquals(
			"The width should be " + size.x + ",",
			size.x,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testAddControl3() throws Exception {

		Label label = new Label(parent, SWT.NULL);
		label.setText("This is very long text");

		Button button = new Button(parent, SWT.NULL);
		button.setText("Short text");
		parent.layout(true, true);

		ControlAligner controlAligner = new ControlAligner();
		controlAligner.add(label);
		controlAligner.add(button);

		Point labelSize  = label.getSize();
		Point buttonSize = button.getSize();
		int max = Math.max(labelSize.x, buttonSize.x);

		assertEquals(
			"The width should be " + max + ",",
			max,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testDialog_AddControl1() throws Exception {

		final ControlAligner controlAligner = new ControlAligner();
		final int[] maximumWidth = new int[1];
		final int[] size = new int[1];

		TitleAreaDialog dialog = new TitleAreaDialog(SWTUtil.getShell()) {

			private Label label;

			@Override
			protected Control createDialogArea(Composite parent) {

				label = new Label(parent, SWT.LEFT);
				label.setText("This is a ControlAligner");

				GridData data = new GridData();
				data.grabExcessHorizontalSpace = false;
				data.horizontalAlignment       = SWT.LEFT;
				label.setLayoutData(data);

				controlAligner.add(label);

				return parent;
			}

			@Override
			protected void initializeBounds() {
				super.initializeBounds();
				size[0] = label.getSize().x;
				maximumWidth[0] = controlAligner.getMaximumWidth();
			}
		};

		dialog.create();
		dialog.close();

		assertEquals(
			"The width should be " + size[0] + ",",
			size[0],
			maximumWidth[0]
		);
	}

	@Test
	public void testDialog_AddControl2() throws Exception {

		final ControlAligner controlAligner = new ControlAligner();
		final int[] maximumWidth = new int[1];
		final int[] sizes = new int[2];

		TitleAreaDialog dialog = new TitleAreaDialog(SWTUtil.getShell()) {

			private Button button;
			private Label label;

			@Override
			protected Control createDialogArea(Composite parent) {

				label = new Label(parent, SWT.NULL);
				label.setText("This is a ControlAligner");

				GridData data = new GridData();
				data.grabExcessHorizontalSpace = false;
				data.horizontalAlignment       = SWT.LEFT;
				label.setLayoutData(data);

				controlAligner.add(label);

				button = new Button(parent, SWT.NULL);
				button.setText("Short text");

				data = new GridData();
				data.grabExcessHorizontalSpace = false;
				data.horizontalAlignment       = SWT.LEFT;
				button.setLayoutData(data);

				controlAligner.add(button);

				return parent;
			}

			@Override
			protected void initializeBounds() {
				super.initializeBounds();
				sizes[0] = label.getSize().x;
				sizes[1] = button.getSize().x;
				maximumWidth[0] = controlAligner.getMaximumWidth();
			}
		};

		dialog.create();
		dialog.close();

		int labelSize  = sizes[0];
		int buttonSize = sizes[1];
		int max = Math.max(labelSize, buttonSize);

		assertEquals(
			"The width should be " + max + ",",
			max,
			maximumWidth[0]
		);
	}

	@Test
	public void testRemoveControl1() throws Exception {

		Button button = new Button(parent, SWT.NULL);
		button.setText("This is a ControlAligner");
		parent.layout(true, true);

		ControlAligner controlAligner = new ControlAligner();
		controlAligner.add(button);

		Point size = button.getSize();

		assertEquals(
			"The width should be " + size.x + ",",
			size.x,
			controlAligner.getMaximumWidth()
		);

		controlAligner.remove(button);

		assertEquals(
			"The width should be -1, ",
			-1,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testRemoveControl2() throws Exception {

		Label label = new Label(parent, SWT.NULL);
		label.setText("This is very long text");

		Button button = new Button(parent, SWT.NULL);
		button.setText("Short text");
		parent.layout(true, true);

		ControlAligner controlAligner = new ControlAligner();
		controlAligner.add(label);
		controlAligner.add(button);

		Point labelSize  = label.getSize();
		Point buttonSize = button.getSize();
		int max = Math.max(labelSize.x, buttonSize.x);

		assertEquals(
			"The width should be " + max + ",",
			max,
			controlAligner.getMaximumWidth()
		);

		controlAligner.remove(label);

		Point newButtonSize = button.getSize();

		assertNotSame(
			"The old max and new max should not be the same",
			max,
			newButtonSize.x
		);

		assertEquals(
			"The ControlAligner doesn't have the right maximum width",
			newButtonSize.x,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testRemoveControl3() throws Exception {

		Label label = new Label(parent, SWT.NULL);
		label.setText("This is very long text");

		Button button = new Button(parent, SWT.NULL);
		button.setText("Short text");
		parent.layout(true, true);

		ControlAligner controlAligner = new ControlAligner();
		controlAligner.add(label);
		controlAligner.add(button);

		Point labelSize  = label.getSize();
		Point buttonSize = button.getSize();
		int max = Math.max(labelSize.x, buttonSize.x);

		assertEquals(
			"The width should be " + max + ",",
			max,
			controlAligner.getMaximumWidth()
		);

		label.dispose();

		Point newButtonSize = button.getSize();

		assertNotSame(
			"The old max and new max should not be the same",
			max,
			newButtonSize.x
		);

		assertEquals(
			"The ControlAligner doesn't have the right maximum width",
			newButtonSize.x,
			controlAligner.getMaximumWidth()
		);
	}
}
