/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This <code>TriStateCheckBox</code> can display one of three states:
 * unchecked, checked, or partially checked. It can be modified via a mouse
 * selection, via a keyboard selection, or programmatically. The selection state is
 * represented by a <code>Boolean</code> value where a <code>null</code>
 * value means partially checked.
 * <p>
 * The order of state changes is: unchecked -> partially checked -> checked.
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class TriStateCheckBox
{
	/**
	 * A check box button.
	 */
	private final Button button;

	/**
	 * The current selection state.
	 */
	private TriState state;

	/**
	 * Creates a new <code>TriStateCheckBox</code> with no text.
	 *
	 * @param parent The parent composite
	 * @param widgetFactory The factory used to create the check box
	 */
	public TriStateCheckBox(Composite parent, WidgetFactory widgetFactory) {
		this(parent, null, widgetFactory);
	}

	/**
	 * Creates a new <code>TriStateCheckBox</code>.
	 *
	 * @param parent The parent composite
	 * @param text The check box's text
	 * @param widgetFactory The factory used to create the check box
	 */
	public TriStateCheckBox(Composite parent,
	                        String text,
	                        WidgetFactory widgetFactory) {
		super();
		this.state = TriState.UNCHECKED;
		this.button = widgetFactory.createCheckBox(parent, text);
		this.button.addSelectionListener(this.buildSelectionListener());
	}

	/**
	 * Convenience method: Adds a dispose listener to the check box.
	 * The source of any events sent to the listener will be the check box widget.
	 */
	public void addDisposeListener(DisposeListener disposeListener) {
		this.button.addDisposeListener(disposeListener);
	}

	/**
	 * Convenience method: Adds a selection listener to the check box.
	 * The source of any events sent to the listener will be the check box widget.
	 */
	public void addSelectionListener(SelectionListener selectionListener) {
		this.button.addSelectionListener(selectionListener);
	}

	private SelectionListener buildSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TriStateCheckBox.this.checkBoxClicked();
			}
		};
	}

	/**
	 * The check box was clicked, change the tri-state to the next value and
	 * update the check box's state.
	 */
	void checkBoxClicked() {
		this.state = this.nextState();
		this.updateCheckBox();
	}

	/**
	 * Disposes the check box widget.
	 */
	public void dispose() {
		this.button.dispose();
	}

	/**
	 * Returns the <code>Button</code> used to show a tri-state check box.
	 *
	 * @return The <code>Button</code> used to show a tri-state check box
	 */
	public Button getCheckBox() {
		return this.button;
	}

	/**
	 * Returns the check box's image.
	 *
	 * @return The check box's image.
	 */
	public Image getImage() {
		return this.button.getImage();
	}

	/**
	 * Returns the check box's selection state.
	 *
	 * @return Either <code>true</code> or <code>false</code> for checked or
	 * unchecked; or <code>null</code> for partially selected
	 */
	public Boolean getSelection() {
		return (this.state == TriState.PARTIALLY_CHECKED) ? null : Boolean.valueOf(this.state == TriState.CHECKED);
	}

	/**
	 * Returns the check box's text.
	 *
	 * @return The text of the check box
	 */
	public String getText() {
		return this.button.getText();
	}

	/**
	 * Returns whether the check box is disposed.
	 *
	 * @return <code>true</code> if the check box is disposed; <code>false</code>
	 * otherwise
	 */
	public boolean isDisposed() {
		return this.button.isDisposed();
	}

	/**
	 * Returns whether the check box is enabled.
	 *
	 * @return <code>true</code> if the check box is enabled; <code>false</code>
	 * otherwise
	 */
	public boolean isEnabled() {
		return this.button.isEnabled();
	}

	/**
	 * Returns the next state:
	 *     UNCHECKED -> PARTIALLY_CHECKED
	 *     PARTIALLY_CHECKED -> CHECKED
	 *     CHECKED -> UNCHECKED
	 */
	private TriState nextState() {
		switch (this.state) {
			case UNCHECKED:
				return TriState.PARTIALLY_CHECKED;
			case PARTIALLY_CHECKED:
				return TriState.CHECKED;
			case CHECKED:
				return TriState.UNCHECKED;
			default:
				throw new IllegalStateException("unknown state: " + this.state);
		}
	}

	/**
	 * Convenience method: Removes a dispose listener from the check box.
	 */
	public void removeDisposeListener(DisposeListener disposeListener) {
		this.button.removeDisposeListener(disposeListener);
	}

	/**
	 * Convenience method: Removes a selection listener from the check box.
	 */
	public void removeSelectionListener(SelectionListener selectionListener) {
		this.button.removeSelectionListener(selectionListener);
	}

	/**
	 * Changes the check box's enablement state.
	 *
	 * @param enabled <code>true</code> to enable the check box or <code>false</code>
	 * to disable it
	 */
	public void setEnabled(boolean enabled) {
		this.button.setEnabled(enabled);
	}

	/**
	 * Sets the check box's image.
	 *
	 * @param image The new image of the check box
	 */
	public void setImage(Image image) {
		this.button.setImage(image);
	}

	/**
	 * Changes the check box's selection state.
	 *
	 * @param selection Either <code>true</code> or <code>false</code> for
	 * checked and unchecked; or <code>null</code> for partially selected
	 */
	public void setSelection(Boolean selection) {
		TriState old = this.state;
		this.state = this.stateForBoolean(selection);
		if (old != this.state) {
			this.updateCheckBox();
		}
	}

	/**
	 * Sets the check box's text.
	 *
	 * @param text The new text of the check box
	 */
	public void setText(String text) {
		this.button.setText(text);
	}

	/**
	 * Returns the tri-state corresponding to the boolean.
	 *
	 * @param selection The boolean to be converted to a tri-state
	 */
	private TriState stateForBoolean(Boolean selection) {
		return (selection == null) ? TriState.PARTIALLY_CHECKED :
			selection.booleanValue() ? TriState.CHECKED : TriState.UNCHECKED;
	}

	/**
	 * Updates the selection state of the of the check box based on the tri-state
	 * value.
	 */
	void updateCheckBox() {
		switch (this.state) {
			case UNCHECKED:
				this.button.setSelection(false);
				this.button.setGrayed(false);
				break;
			case PARTIALLY_CHECKED:
				this.button.setSelection(true);
				this.button.setGrayed(true);
				break;
			case CHECKED:
				this.button.setSelection(true);
				this.button.setGrayed(false);
				break;
			default:
				throw new IllegalStateException("unknown state: " + this.state);
		}
	}

	/**
	 * An enum containing the possible selections.
	 */
	enum TriState {
		CHECKED,
		PARTIALLY_CHECKED,
		UNCHECKED
	}

}
