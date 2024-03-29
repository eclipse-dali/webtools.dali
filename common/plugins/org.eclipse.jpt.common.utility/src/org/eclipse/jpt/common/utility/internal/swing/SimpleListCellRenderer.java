/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.swing;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * This renderer should behave the same as the DefaultListCellRenderer;
 * but it slightly refactors the calculation of the icon and text of the list
 * cell so that subclasses can easily override the methods that build
 * the icon and text.
 * 
 * In most cases, you need only override:
 *     #buildIcon(Object value)
 *     #buildText(Object value)
 */
public class SimpleListCellRenderer
	extends DefaultListCellRenderer
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a simple renderer.
	 */
	public SimpleListCellRenderer() {
		super();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// substitute null for the cell value so nothing is drawn initially...
		super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);
		this.setOpaque(true);

		// ...then set the icon and text manually
		this.setIcon(this.buildIcon(list, value, index, isSelected, cellHasFocus));
		this.setText(this.buildText(list, value, index, isSelected, cellHasFocus));

		this.setToolTipText(this.buildToolTipText(list, value, index, isSelected, cellHasFocus));

		// the context will be initialized only if a reader is running
		if (this.accessibleContext != null) {
			this.accessibleContext.setAccessibleName(this.buildAccessibleName(list, value, index, isSelected, cellHasFocus));
		}

		return this;
	}

	/**
	 * Return the icon representation of the specified cell
	 * value and other settings. (Even more settings are
	 * accessible via inherited getters: hasFocus, isEnabled, etc.)
	 */
	protected Icon buildIcon(@SuppressWarnings("unused") JList list, Object value, @SuppressWarnings("unused") int index, @SuppressWarnings("unused") boolean isSelected, @SuppressWarnings("unused") boolean cellHasFocus) {
		return this.buildIcon(value);
	}

	/**
	 * Return the icon representation of the specified cell
	 * value. The default is to display no icon at all unless the
	 * value itself is an icon.
	 */
	protected Icon buildIcon(Object value) {
		// replicate the default behavior
		return (value instanceof Icon) ? (Icon) value : null;
	}

	/**
	 * Return the textual representation of the specified cell
	 * value and other settings. (Even more settings are
	 * accessible via inherited getters: hasFocus, isEnabled, etc.)
	 */
	protected String buildText(@SuppressWarnings("unused") JList list, Object value, @SuppressWarnings("unused") int index, @SuppressWarnings("unused") boolean isSelected, @SuppressWarnings("unused") boolean cellHasFocus) {
		return this.buildText(value);
	}

	/**
	 * Return the textual representation of the specified cell
	 * value. The default is to display the object's default string
	 * representation (as returned by #toString()); unless the
	 * value itself is an icon, in which case no text is displayed.
	 */
	protected String buildText(Object value) {
		return (value instanceof Icon) ? StringTools.EMPTY_STRING : ((value == null) ? StringTools.EMPTY_STRING : value.toString());
	}

	/**
	 * Return the text displayed when the cursor lingers over the specified cell.
	 * (Even more settings are accessible via inherited getters: hasFocus, isEnabled, etc.)
	 */
	protected String buildToolTipText(@SuppressWarnings("unused") JList list, Object value, @SuppressWarnings("unused") int index, @SuppressWarnings("unused") boolean isSelected, @SuppressWarnings("unused") boolean cellHasFocus) {
		return this.buildToolTipText(value);
	}

	/**
	 * Return the text displayed when the cursor lingers over the specified cell.
	 */
	protected String buildToolTipText(@SuppressWarnings("unused") Object value) {
		return null;
	}

	/**
	 * Return the accessible name to be given to the component used to render
	 * the given value and other settings. (Even more settings are accessible via
	 * inherited getters: hasFocus, isEnabled, etc.)
	 */
	protected String buildAccessibleName(@SuppressWarnings("unused") JList list, Object value, @SuppressWarnings("unused") int index, @SuppressWarnings("unused") boolean isSelected, @SuppressWarnings("unused") boolean cellHasFocus) {
		return this.buildAccessibleName(value);
	}

	/**
	 * Return the accessible name to be given to the component used to render
	 * the given value.
	 */
	protected String buildAccessibleName(@SuppressWarnings("unused") Object value) {
		return null;
	}
}
