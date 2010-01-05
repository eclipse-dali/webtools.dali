/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.util;

import static org.junit.Assert.*;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jpt.ui.internal.util.ControlAligner;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public final class ControlAlignerTest {

	private ControlAligner controlAligner;
	private Composite parent;
	private Shell shell;

	private Layout buildSpacerLayout() {
		return new Layout() {
			@Override
			protected Point computeSize(Composite composite,
			                            int widthHint,
			                            int heightHint,
			                            boolean flushCache) {

				return new Point(widthHint, heightHint);
			}

			@Override
			protected void layout(Composite composite, boolean flushCache) {
				GridData data = (GridData) composite.getLayoutData();
				composite.setBounds(0, 0, data.widthHint, data.heightHint);
			}
		};
	}

	@Before
	public void setUp() {

		controlAligner = new ControlAligner();

		shell  = new Shell(Display.getCurrent());
		shell.setLayout(new GridLayout(1, false));

		parent = new Composite(shell, SWT.NONE);
		parent.setLayout(new GridLayout(1, false));
		parent.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
	}

	@After
	public void tearDown() {

		if (controlAligner != null) {
			controlAligner.dispose();
			controlAligner = null;
		}

		if (shell != null) {
			shell.dispose();
			shell = null;
		}
	}

	@Test
	public void testAddControl1() throws Exception {

		Composite pane = new Composite(parent, SWT.NULL);
		pane.setLayout(new GridLayout(3, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(pane, SWT.NULL);
		updateGridData(label);

		controlAligner.add(label);

		assertEquals(
			"The maximum width should be 0,",
			0,
			controlAligner.getMaximumWidth()
		);

		label.setText("This is a ControlAligner");
//		parent.layout(true, true);

		Point size = label.getSize();

		assertEquals(
			"The width should be " + size.x + ",",
			size.x,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testAddControl2() throws Exception {

		Composite pane = new Composite(parent, SWT.NULL);
		pane.setLayout(new GridLayout(3, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button button = new Button(pane, SWT.NULL);
		button.setText("This is a ControlAligner");
		updateGridData(button);

		controlAligner.add(button);
		parent.layout(true, true);

		Point size = button.getSize();

		assertEquals(
			"The width should be " + size.x + ",",
			size.x,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testAddControl3() throws Exception {

		Composite pane = new Composite(parent, SWT.NULL);
		pane.setLayout(new GridLayout(3, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(pane, SWT.NULL);
		label.setText("This is very long text");
		updateGridData(label);

		Button button = new Button(pane, SWT.NULL);
		button.setText("Short text");
		updateGridData(button);

//		parent.layout(true, true);

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

		controlAligner.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner.add(controlAligner2);
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");
//		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner.getMaximumWidth()
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

		controlAligner.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");

		controlAligner.add(controlAligner2);
//		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddControlAlignerToItself() throws Exception {
		controlAligner.add(controlAligner);
		fail("A ControlAligner can't be added to itself");
	}

	@Test
	public void testDialog_AddControl1() throws Exception {

		final int[] maximumWidth = new int[1];
		final int[] size = new int[1];

		TitleAreaDialog dialog = new TitleAreaDialog(SWTUtil.getShell()) {

			private Label label;

			@Override
			protected Control createDialogArea(Composite parent) {

				Composite pane = new Composite(parent, SWT.NULL);
				pane.setLayout(new GridLayout(3, false));
				pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				label = new Label(pane, SWT.LEFT);
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

		final int[] maximumWidth = new int[1];
		final int[] sizes = new int[2];

		TitleAreaDialog dialog = new TitleAreaDialog(SWTUtil.getShell()) {

			private Button button;
			private Label label;

			@Override
			protected Control createDialogArea(Composite parent) {

				Composite pane = new Composite(parent, SWT.NULL);
				pane.setLayout(new GridLayout(3, false));
				pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				label = new Label(pane, SWT.NULL);
				label.setText("This is a ControlAligner");
				updateGridData(label);

				controlAligner.add(label);

				button = new Button(pane, SWT.NULL);
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
	public void testDispose() throws Exception {

		Composite pane = new Composite(parent, SWT.NULL);
		pane.setLayout(new GridLayout(3, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(pane, SWT.NULL);
		label.setText("This is very long text");
		updateGridData(label);

		Button button = new Button(pane, SWT.NULL);
		button.setText("Short text");
		updateGridData(button);

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
	public void testHierarchyOfControlAligners() throws Exception {

		// Aligner1
		//  ^
		//  |-Aligner2
		//     ^
		//     |-Aligner3
		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner.add(controlAligner2);

		ControlAligner controlAligner3 = new ControlAligner();
		controlAligner2.add(controlAligner3);

		// Test 1
		Label label1 = new Label(parent, SWT.NULL);
		label1.setText("This is a label widget");
		parent.layout(true, true);

		int labelWidth1 = label1.getSize().x;
		controlAligner3.add(label1);

		assertEquals(controlAligner3.getMaximumWidth(), labelWidth1);
		assertEquals(controlAligner2.getMaximumWidth(), labelWidth1);
		assertEquals(controlAligner.getMaximumWidth(), labelWidth1);

		// Test 2
		Label label2 = new Label(parent, SWT.NULL);
		label2.setText("ShortLabel");
		controlAligner2.add(label2);
		parent.layout(true);

		int newLabelWidth1 = label1.getSize().x;
		int newLabelWidth2 = label2.getSize().x;

		assertEquals(controlAligner3.getMaximumWidth(), controlAligner2.getMaximumWidth());
		assertEquals(controlAligner2.getMaximumWidth(), controlAligner.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth1, controlAligner.getMaximumWidth());

		// Test 3
		Label label3 = new Label(parent, SWT.NULL);
		label3.setText("A very long label that takes a lot of horizontal space");
//		parent.layout(true);
		controlAligner.add(label3);

		newLabelWidth1 = label1.getSize().x;
		newLabelWidth2 = label2.getSize().x;
		int newLabelWidth3 = label3.getSize().x;

		assertEquals(controlAligner3.getMaximumWidth(), controlAligner2.getMaximumWidth());
		assertEquals(controlAligner2.getMaximumWidth(), controlAligner.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(newLabelWidth1, controlAligner.getMaximumWidth());

		// Make sure all the locked are removed
		assertEquals(ReflectionTools.getFieldValue_(controlAligner, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner2, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner3, "locked"), Boolean.FALSE);

		// Change the text of label2
		label2.setText("mm");
//		parent.layout(true);

		newLabelWidth1 = label1.getSize().x;
		newLabelWidth2 = label2.getSize().x;
		newLabelWidth3 = label3.getSize().x;

		assertEquals(controlAligner3.getMaximumWidth(), controlAligner2.getMaximumWidth());
		assertEquals(controlAligner2.getMaximumWidth(), controlAligner.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(newLabelWidth1, controlAligner.getMaximumWidth());

		assertEquals(ReflectionTools.getFieldValue_(controlAligner, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner2, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner3, "locked"), Boolean.FALSE);

		// Change the text of label1
		label1.setText("a");
//		parent.layout(true);

		Composite parent1 = new Composite(SWTUtil.getShell(), SWT.NULL);
		parent1.setLayout(new GridLayout());

		Label tempLabel = new Label(parent1, SWT.NULL);
		tempLabel.setText("a");
//		parent1.layout(true);

		int realWidth = tempLabel.getSize().x;

		newLabelWidth1 = label1.getSize().x;
		newLabelWidth2 = label2.getSize().x;
		newLabelWidth3 = label3.getSize().x;

		assertEquals(controlAligner3.getMaximumWidth(), controlAligner2.getMaximumWidth());
		assertEquals(controlAligner2.getMaximumWidth(), controlAligner.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(newLabelWidth1, controlAligner.getMaximumWidth());
		assertFalse(newLabelWidth1 == realWidth);

		assertEquals(ReflectionTools.getFieldValue_(controlAligner, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner2, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner3, "locked"), Boolean.FALSE);

		// Change the text of label1
		label1.setText("Yes another big long long text so that all the labels will have to take the size of this label to make sure ControlAligner works correctly");
//		parent.layout(true);

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

		assertEquals(controlAligner3.getMaximumWidth(), controlAligner2.getMaximumWidth());
		assertEquals(controlAligner2.getMaximumWidth(), controlAligner.getMaximumWidth());
		assertEquals(newLabelWidth1, newLabelWidth2);
		assertEquals(newLabelWidth2, newLabelWidth3);
		assertEquals(controlAligner.getMaximumWidth(), newLabelWidth1);
		assertEquals(realWidth, newLabelWidth1);

		assertEquals(ReflectionTools.getFieldValue_(controlAligner, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner2, "locked"), Boolean.FALSE);
		assertEquals(ReflectionTools.getFieldValue_(controlAligner3, "locked"), Boolean.FALSE);
	}

	@Test
	public void testRemoveControl1() throws Exception {

		Composite pane = new Composite(parent, SWT.NULL);
		pane.setLayout(new GridLayout(3, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button button = new Button(pane, SWT.NULL);
		button.setText("This is a ControlAligner");
		updateGridData(button);

		controlAligner.add(button);
		parent.layout(true, true);

		Point size = button.getSize();

		assertEquals(
			"The width should be " + size.x + ",",
			size.x,
			controlAligner.getMaximumWidth()
		);

		controlAligner.remove(button);

		assertEquals(
			"The width should be 0, ",
			0,
			controlAligner.getMaximumWidth()
		);
	}

	@Test
	public void testRemoveControl2() throws Exception {

		Composite pane = new Composite(parent, SWT.NULL);
		pane.setLayout(new GridLayout(3, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(pane, SWT.NULL);
		label.setText("This is very long text");
		updateGridData(label);

		Button button = new Button(pane, SWT.NULL);
		button.setText("Short text");
		updateGridData(button);
//		parent.layout(true, true);

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
	public void testRemoveControl4() throws Exception {

		Composite pane = new Composite(parent, SWT.NULL);
		pane.setLayout(new GridLayout(3, false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Widget 1
		Label label = new Label(pane, SWT.NULL);
		label.setText("This is very long text");
		updateGridData(label);
		controlAligner.add(label);

		// Widget 2
		Composite spacer = new Composite(pane, SWT.NULL);
		spacer.setLayout(buildSpacerLayout());
		updateGridData(spacer);
		controlAligner.add(spacer);

		// Widget 3
		Button button = new Button(pane, SWT.NULL);
		button.setText("Short text");
		updateGridData(button);
		controlAligner.add(button);

//		parent.layout(true, true);

		// Make sure the 3 widgets have the same width
		Point labelSize  = label.getSize();
		Point spacerSize = spacer.getSize();
		Point buttonSize = button.getSize();
		int max = Math.max(labelSize.x, buttonSize.x);
		max = Math.max(max, spacerSize.x);

		assertEquals(
			"The width should be " + max + ",",
			max,
			controlAligner.getMaximumWidth()
		);

		assertEquals(
			"The spacer's width should be " + max + ",",
			max,
			spacerSize.x
		);

		// Remove the label (the widest widget) and make sure the width was
		// correctly calculated
		controlAligner.remove(label);

		spacerSize = spacer.getSize();
		buttonSize = button.getSize();
		int max2 = Math.max(spacerSize.x, buttonSize.x);

		assertNotSame(
			"The old max and new max should not be the same",
			max,
			max2
		);

		assertEquals(
			"The ControlAligner doesn't have the right maximum width",
			max2,
			controlAligner.getMaximumWidth()
		);

		assertEquals(
			"The spacer's width should have been adjusted",
			max2,
			spacerSize.x
		);
	}

	@Test
	public void testRemoveControlAligner1() throws Exception {

		Label label1 = new Label(parent, SWT.NULL);
		Label label2 = new Label(parent, SWT.NULL);

		updateGridData(label1);
		updateGridData(label2);

		controlAligner.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner.add(controlAligner2);
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");
//		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		// Test 1
		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);

		// Test 2
		controlAligner.remove(label1);

		width = label2.getSize().x;

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner.getMaximumWidth()
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

		controlAligner.add(label1);

		ControlAligner controlAligner2 = new ControlAligner();
		controlAligner.add(controlAligner2);
		controlAligner2.add(label2);

		label1.setText("This is a ControlAligner");
		label2.setText("This is a very long ControlAligner");
//		parent.layout(true, true);

		Point size1 = label1.getSize();
		Point size2 = label2.getSize();
		int width = Math.max(size1.x, size2.x);

		// Test 1
		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner.getMaximumWidth()
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
			controlAligner.getMaximumWidth()
		);

		assertEquals(
			"The width should be " + width + ",",
			width,
			controlAligner2.getMaximumWidth()
		);
	}

	private void updateGridData(Control control) {
		GridData data = new GridData();
		data.horizontalAlignment       = GridData.FILL;
		data.grabExcessHorizontalSpace = false;
		control.setLayoutData(data);
	}
}