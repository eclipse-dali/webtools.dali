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
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
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
		parent.setLayout(new GridLayout(1, false));
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
		updateGridData(label);

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
		updateGridData(button);
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
		updateGridData(label);

		Button button = new Button(parent, SWT.NULL);
		button.setText("Short text");
		updateGridData(button);

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
	public void testAddControlAligner1() throws Exception {

		Label label1 = new Label(parent, SWT.NULL);
		Label label2 = new Label(parent, SWT.NULL);

		updateGridData(label1);
		updateGridData(label2);

		ControlAligner controlAligner1 = new ControlAligner();
		controlAligner1.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner1.add(controlAligner2);
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");
		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner1.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);
	}

	@Test
	public void testAddControlAligner2() throws Exception {

		Label label1 = new Label(parent, SWT.NULL);
		Label label2 = new Label(parent, SWT.NULL);

		updateGridData(label1);
		updateGridData(label2);

		ControlAligner controlAligner1 = new ControlAligner();
		controlAligner1.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");

		controlAligner1.add(controlAligner2);
		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner1.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddControlAlignerToItself() throws Exception {
		ControlAligner aligner = new ControlAligner();
		aligner.add(aligner);
		fail("A ControlAligner can't be added to itself");
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
				updateGridData(label);

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
				updateGridData(label);

				controlAligner.add(label);

				button = new Button(parent, SWT.NULL);
				button.setText("Short text");
				updateGridData(button);

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
	public void testHierarchyOfControlAligners() throws Exception {

		// Aligner1
		//  ^
		//  |-Aligner2
		//     ^
		//     |-Aligner3
		ControlAligner aligner1 = new ControlAligner();

		ControlAligner aligner2 = new ControlAligner();
		aligner1.add(aligner2);

		ControlAligner aligner3 = new ControlAligner();
		aligner2.add(aligner3);

		// Test 1
		Label label1 = new Label(parent, SWT.NULL);
		label1.setText("This is a label widget");
		parent.layout(true);

		int labelWidth1 = label1.getSize().x;
		aligner3.add(label1);

		assertEquals(aligner3.getMaximumWidth(), labelWidth1);
		assertEquals(aligner2.getMaximumWidth(), labelWidth1);
		assertEquals(aligner1.getMaximumWidth(), labelWidth1);

		// Test 2
		Label label2 = new Label(parent, SWT.NULL);
		label2.setText("ShortLabel");
		parent.layout(true);
		aligner2.add(label2);

		int newLabelWidth1 = label1.getSize().x;
		int newLabelWidth2 = label2.getSize().x;

		assertEquals(aligner3.getMaximumWidth(), aligner2.getMaximumWidth());
		assertEquals(aligner2.getMaximumWidth(), aligner1.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth1, aligner1.getMaximumWidth());

		// Test 3
		Label label3 = new Label(parent, SWT.NULL);
		label3.setText("A very long label that takes a lot of horizontal space");
		parent.layout(true);
		aligner1.add(label3);

		newLabelWidth1 = label1.getSize().x;
		newLabelWidth2 = label2.getSize().x;
		int newLabelWidth3 = label3.getSize().x;

		assertEquals(aligner3.getMaximumWidth(), aligner2.getMaximumWidth());
		assertEquals(aligner2.getMaximumWidth(), aligner1.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(newLabelWidth1, aligner1.getMaximumWidth());

		// Make sure all the locked are removed
		assertEquals(ClassTools.attemptToGetFieldValue(aligner1, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner2, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner3, "locked"), Boolean.FALSE);

		// Change the text of label2
		label2.setText("mm");
		parent.layout(true);

		newLabelWidth1 = label1.getSize().x;
		newLabelWidth2 = label2.getSize().x;
		newLabelWidth3 = label3.getSize().x;

		assertEquals(aligner3.getMaximumWidth(), aligner2.getMaximumWidth());
		assertEquals(aligner2.getMaximumWidth(), aligner1.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(newLabelWidth1, aligner1.getMaximumWidth());

		assertEquals(ClassTools.attemptToGetFieldValue(aligner1, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner2, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner3, "locked"), Boolean.FALSE);

		// Change the text of label1
		label1.setText("a");
		parent.layout(true);

		Composite parent1 = new Composite(SWTUtil.getShell(), SWT.NULL);
		parent1.setLayout(new GridLayout());

		Label tempLabel = new Label(parent1, SWT.NULL);
		tempLabel.setText("a");
		parent1.layout(true);

		int realWidth = tempLabel.getSize().x;

		newLabelWidth1 = label1.getSize().x;
		newLabelWidth2 = label2.getSize().x;
		newLabelWidth3 = label3.getSize().x;

		assertEquals(aligner3.getMaximumWidth(), aligner2.getMaximumWidth());
		assertEquals(aligner2.getMaximumWidth(), aligner1.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(newLabelWidth1, aligner1.getMaximumWidth());
		assertFalse(newLabelWidth1 == realWidth);

		assertEquals(ClassTools.attemptToGetFieldValue(aligner1, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner2, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner3, "locked"), Boolean.FALSE);

		// Change the text of label1
		label1.setText("Yes another big long long text so that all the labels will have to take the size of this label to make sure ControlAligner works correctly");
		parent.layout(true);

		// Weird: It seems no notification is sent, fire one manually
		Event event  = new Event();
		event.widget = label1;
		event.type   = SWT.Resize;
		label1.notifyListeners(SWT.Resize, event);

		Composite parent2 = new Composite(SWTUtil.getShell(), SWT.NULL);
		parent2.setLayout(new GridLayout());

		tempLabel = new Label(parent2, SWT.NULL);
		tempLabel.setText(label1.getText());
		parent2.layout(true);

		realWidth = tempLabel.getSize().x;

		newLabelWidth1 = label1.getSize().x;
		newLabelWidth2 = label2.getSize().x;
		newLabelWidth3 = label3.getSize().x;

		assertEquals(aligner3.getMaximumWidth(), aligner2.getMaximumWidth());
		assertEquals(aligner2.getMaximumWidth(), aligner1.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(aligner1.getMaximumWidth(), newLabelWidth1);
		assertEquals(realWidth, newLabelWidth1);

		assertEquals(ClassTools.attemptToGetFieldValue(aligner1, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner2, "locked"), Boolean.FALSE);
		assertEquals(ClassTools.attemptToGetFieldValue(aligner3, "locked"), Boolean.FALSE);
	}

	@Test
	public void testRemoveControl1() throws Exception {

		Button button = new Button(parent, SWT.NULL);
		button.setText("This is a ControlAligner");
		updateGridData(button);
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
		updateGridData(label);

		Button button = new Button(parent, SWT.NULL);
		button.setText("Short text");
		updateGridData(button);
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
		updateGridData(label);

		Button button = new Button(parent, SWT.NULL);
		button.setText("Short text");
		updateGridData(button);
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

	@Test
	public void testRemoveControlAligner1() throws Exception {

		Label label1 = new Label(parent, SWT.NULL);
		Label label2 = new Label(parent, SWT.NULL);

		updateGridData(label1);
		updateGridData(label2);

		ControlAligner controlAligner1 = new ControlAligner();
		controlAligner1.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner1.add(controlAligner2);
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");
		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		// Test 1
		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner1.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);

		// Test 2
		controlAligner1.remove(label1);

		width = label2.getSize().x;

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner1.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);
	}

	@Test
	public void testRemoveControlAligner2() throws Exception {

		Label label1 = new Label(parent, SWT.NULL);
		Label label2 = new Label(parent, SWT.NULL);

		updateGridData(label1);
		updateGridData(label2);

		ControlAligner controlAligner1 = new ControlAligner();
		controlAligner1.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner1.add(controlAligner2);
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");
		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		// Test 1
		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner1.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);

		// Test 2
		controlAligner2.remove(label2);

		width = label1.getSize().x;

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner1.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);
	}

	private void updateGridData(Control control) {
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = false;
		control.setLayoutData(data);
	}
}