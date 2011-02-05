/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.PageBook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public final class ControlSwitcherTest {

	private PageBook pageBook;
	private Composite pane1;
	private Composite pane2;
	private Composite parent;

	private Composite buildPane1() {

		if (pane1 == null) {

			pane1 = new Composite(pageBook, SWT.NULL);
			pane1.setLayout(new GridLayout(2, false));

			Label label = new Label(pane1, SWT.NULL);
			label.setText("&Test2:");

			Text text = new Text(pane1, SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			Combo combo = new Combo(pane1, SWT.BORDER);

			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			combo.setLayoutData(data);
		}

		return pane1;
	}

	private Composite buildPane2() {

		if (pane2 == null) {

			pane2 = new Composite(pageBook, SWT.NULL);
			pane2.setLayout(new GridLayout(2, false));

			Label label = new Label(pane2, SWT.NULL);
			label.setText("&Test1:");

			Text text = new Text(pane2, SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		return pane2;
	}

	private Transformer<Boolean, Control> buildTransformer() {
		return new Transformer<Boolean, Control>() {
			public Control transform(Boolean value) {
				return (value == null) ? null : (value ? pane1 : pane2);
			}
		};
	}

	@Before
	public void setUp() {
		parent = new Composite(SWTUtil.getShell(), SWT.NONE);
		parent.setLayout(new GridLayout());

		pageBook = new PageBook(parent, SWT.NULL);
		pageBook.setLayoutData(new GridData());
	}

	@After
	public void tearDown() {

		if (parent != null) {

			parent.dispose();

			parent   = null;
			pageBook = null;
		}
	}

	@Test
	public void testSwitch() {

		SimplePropertyValueModel<Boolean> switchHolder = new SimplePropertyValueModel<Boolean>();
		Transformer<Boolean, Control> transformer = buildTransformer();

		pane1 = buildPane1();
		pane1.setVisible(false);

		pane2 = buildPane2();
		pane2.setVisible(false);

		new ControlSwitcher(
			switchHolder,
			transformer,
			pageBook
		);

		// Test 1
		switchHolder.setValue(true);
		Control control = (Control) ReflectionTools.getFieldValue(pageBook, "currentPage");

		assertNotNull(
			"The page book's page shouldn't be null",
			control
		);

		assertSame(
			"The current pane should be pane1",
			pane1,
			control
		);

		Point pane1Size = pane1.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		Point pageBookSize = pageBook.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		assertEquals(
			"The width of the PageBook should be the same as the width of pane1",
			pane1Size.x,
			pageBookSize.x
		);

		assertEquals(
			"The height of the PageBook should be the same as the height of pane1",
			pane1Size.y,
			pageBookSize.y
		);

		// Test 2
		switchHolder.setValue(false);
		control = (Control) ReflectionTools.getFieldValue(pageBook, "currentPage");

		assertNotNull(
			"The page book's page shouldn't be null",
			control
		);

		assertSame(
			"The current pane should be pane2",
			pane2,
			control
		);

		Point pane2Size = pane2.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		pageBookSize = pageBook.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		assertEquals(
			"The width of the PageBook should be the same as the width of pane2",
			pane2Size.x,
			pageBookSize.x
		);

		assertEquals(
			"The height of the PageBook should be the same as the height of pane2",
			pane2Size.y,
			pageBookSize.y
		);
	}
}
