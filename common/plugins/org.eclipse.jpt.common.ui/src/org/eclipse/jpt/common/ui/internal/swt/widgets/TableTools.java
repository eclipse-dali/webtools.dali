/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.widgets;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Table;

/**
 * {@link Table} utility methods.
 */
public final class TableTools {

	public static int calculateHeightHint(Table table, int rowCount) {
		if (table.getFont().equals(JFaceResources.getDefaultFont())) {
			table.setFont(JFaceResources.getDialogFont());
		}
		int hint = table.getHeaderHeight() + (table.getItemHeight() * rowCount);
		if (table.getLinesVisible()) {
			hint += table.getGridLineWidth() * (rowCount - 1);
		}
		return hint;
	}


	// ********** disabled constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private TableTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
