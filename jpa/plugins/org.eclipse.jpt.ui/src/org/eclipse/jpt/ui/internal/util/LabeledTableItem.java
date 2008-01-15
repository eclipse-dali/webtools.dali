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
	 * The label to be updated with a different icon and text.
	 */
	private TableItem tableItem;

	/**
	 * Creates a new <code>LabeledTableItem</code>.
	 *
	 * @param tableItem The <code>TableItem</code> that will have its text and
	 * icon updated when required
	 */
	public LabeledTableItem(TableItem tableItem) {
		super();
		Assert.isNotNull(tableItem, "The TableItem cannot be null");
		this.tableItem = tableItem;
	}

	/*
	 * (non-Javadoc)
	 */
	public void setIcon(Image image) {
		this.updateTableItem(this.tableItem.getText(), image);
	}

	/*
	 * (non-Javadoc)
	 */
	public void setText(String text) {
		this.updateTableItem(text, this.tableItem.getImage());
	}

	private void updateTableItem(String text, Image image) {

		Table table = this.tableItem.getParent();
		table.getParent().setRedraw(false);

		int index = table.indexOf(this.tableItem);
		table.remove(index);

		this.tableItem = new TableItem(table, SWT.CHECK, index);
		this.tableItem.setText(text);
		this.tableItem.setImage(image);

		table.getParent().pack(true);
		table.getParent().setRedraw(true);
	}
}
