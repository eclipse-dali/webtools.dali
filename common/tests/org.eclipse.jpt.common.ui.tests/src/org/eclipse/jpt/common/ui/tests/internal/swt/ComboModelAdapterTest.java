/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.swt;

import org.eclipse.jpt.common.ui.internal.swt.AbstractComboModelAdapter;
import org.eclipse.jpt.common.ui.internal.swt.ComboModelAdapter;
import org.eclipse.jpt.common.utility.internal.swing.SimpleDisplayable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.junit.After;

public class ComboModelAdapterTest extends AbstractComboModelAdapterTest {

	private Combo combo;
	private boolean editable;

	@Override
	protected AbstractComboModelAdapter<SimpleDisplayable> buildEditableComboModelAdapter() {

		combo = new Combo(shell(), SWT.NULL);
		editable = true;

		return ComboModelAdapter.adapt(
			buildListHolder(),
			selectedItemHolder(),
			combo,
			buildStringConverter()
		);
	}

	@Override
	protected AbstractComboModelAdapter<SimpleDisplayable> buildReadOnlyComboModelAdapter() {

		combo = new Combo(shell(), SWT.READ_ONLY);
		editable = false;

		return ComboModelAdapter.adapt(
			buildListHolder(),
			selectedItemHolder(),
			combo,
			buildStringConverter()
		);
	}

	@Override
	public String comboSelectedItem() {
		return combo.getText();
	}

	@Override
	protected boolean emptyComboCanHaveSelectedValue() {
		return editable;
	}

	@Override
	protected String itemAt(int index) {
		return this.combo.getItem(index);
	}

	@Override
	protected int itemCounts() {
		return combo.getItemCount();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		combo = null;
	}
}
