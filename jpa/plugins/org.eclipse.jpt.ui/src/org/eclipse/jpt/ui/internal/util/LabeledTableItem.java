/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * A default implementation of <code>LabeledControl</code> that updates a
 * <code>TableItem</code> when required.
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class LabeledTableItem implements LabeledControl
{
	/**
	 * The table cell to be updated with a different icon and text.
	 */
	private TableItem tableItem;

	/**
	 * Creates a new <code>LabeledTableItem</code>.
	 *
	 * @param tableItem The <code>TableItem</code> that will have its text and
	 * icon updated when required
	 * @exception AssertionFailedException If the given <code>TableItem</code> is
	 * <code>null</code>
	 */
	public LabeledTableItem(TableItem tableItem) {
		super();

		Assert.isNotNull(tableItem, "The TableItem cannot be null");
		this.tableItem = tableItem;
	}

	/*
	 * (non-Javadoc)
	 */
	public void setImage(final Image image) {

		if (!tableItem.isDisposed()) {
			updateTableItem(tableItem.getText(), image);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	public void setText(final String text) {

		if (!tableItem.isDisposed()) {
			updateTableItem(text, tableItem.getImage());
		}
	}

	private void updateTableItem(String text, Image image) {

		if (text == null) {
			text = "";
		}

		Table table = tableItem.getParent();
		table.setRedraw(false);

		boolean checked  = tableItem.getChecked();
		boolean grayed   = tableItem.getGrayed();
		boolean hasFocus = table.isFocusControl();

		int index = table.indexOf(tableItem);
		table.remove(index);

		tableItem = new TableItem(table, SWT.CHECK | SWT.TRANSPARENT, index);
		tableItem.setText(text);
		tableItem.setImage(image);
		tableItem.setChecked(checked);
		tableItem.setGrayed(grayed);
		tableItem.setBackground(table.getBackground());

		table.layout(true, true);
		table.getParent().getParent().layout(true, true);
		table.setRedraw(true);

		if (hasFocus) {
			table.forceFocus();
		}
	}
}