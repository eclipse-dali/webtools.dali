/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.swt;

import org.eclipse.jpt.ui.internal.swt.AbstractComboModelAdapter;
import org.eclipse.jpt.ui.internal.swt.CComboModelAdapter;
import org.eclipse.jpt.utility.internal.swing.SimpleDisplayable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.junit.After;

public class CComboModelAdapterTest extends AbstractComboModelAdapterTest {

	private CCombo combo;

	@Override
	protected AbstractComboModelAdapter<SimpleDisplayable> buildEditableComboModelAdapter() {

		combo = new CCombo(shell(), SWT.NULL);

		return CComboModelAdapter.adapt(
			buildListHolder(),
			selectedItemHolder(),
			combo,
			buildStringConverter()
		);
	}

	@Override
	protected AbstractComboModelAdapter<SimpleDisplayable> buildReadOnlyComboModelAdapter() {

		combo = new CCombo(shell(), SWT.READ_ONLY);

		return CComboModelAdapter.adapt(
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
		return true;
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
