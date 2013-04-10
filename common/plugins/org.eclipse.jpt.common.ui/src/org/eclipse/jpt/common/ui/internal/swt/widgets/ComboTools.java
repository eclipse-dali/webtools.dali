/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.widgets;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;

/**
 * A suite of utility methods related to the user interface.
 *
 * @version 2.0
 * @since 1.0
 */
public final class ComboTools {

	/**
	 * Tweak the specified combo-box to remove the default value when the
	 * widget receives the focus and to show the default when the widget loses
	 * the focus. The default value must be the first value in the combo-box's
	 * selection list.
	 */
	public static void handleDefaultValue(Combo combo) {
		ComboListener listener = new ComboListener();
		combo.addFocusListener(listener);
		combo.addModifyListener(listener);
	}

	/**
	 * This handler is responsible for removing the default value when the combo
	 * has the focus or when the selected item is the default value and to select
	 * it when the combo loses the focus.
	 */
	/* CU private */ static class ComboListener
		implements FocusListener, ModifyListener
	{
		public void focusGained(FocusEvent e) {
			Combo combo = (Combo) e.widget;
			if (combo.getSelectionIndex() == 0) {	
				// cannot modify the combo while it is notifying its listeners
				DisplayTools.asyncExec(new SelectText(combo));
			}
		}

		public void focusLost(FocusEvent e) {
			// NOP
		}

		private class SelectText
			implements Runnable
		{
			private final Combo combo;

			SelectText(Combo combo) {
				super();
				this.combo = combo;
			}

			public void run() {
				if (this.combo.isDisposed()) {
					return;
				}
				String text = this.combo.getText();
				this.combo.setSelection(new Point(0, text.length()));
			}
		}

		public void modifyText(ModifyEvent e) {
			Combo combo = (Combo) e.widget;
			if (combo.isFocusControl() && (combo.getSelectionIndex() == 0)) {
				// cannot modify the combo while it is notifying its listeners
				DisplayTools.asyncExec(new ModifyText(combo));
			}
		}

		private class ModifyText
			implements Runnable
		{
			private final Combo combo;

			ModifyText(Combo combo) {
				super();
				this.combo = combo;
			}

			public void run() {
				if (this.combo.isDisposed()) {
					return;
				}
				String text = this.combo.getText();
				if (text.length() == 0) {
					text = this.combo.getItem(0);
					this.combo.setText(text);
				}
				this.combo.setSelection(new Point(0, text.length()));
			}
		}
	}
}
