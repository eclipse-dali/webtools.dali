/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This <code>TriStateCheckBox</code> is responsible to handle three states:
 * unchecked, checked and partially selected. Either from a mouse selection,
 * keyboard selection or programmatically, the selection state is using a
 * <code>Boolean</code> value where a <code>null</code> value means partially
 * selected.
 * <p>
 * The order of state state is: unchecked -> partially selected -> checked.
 *
 * @version 2.0
 * @since 2.0
 */
public final class TriStateCheckBox
{
	/**
	 * A tri-state check box can only be used within a tree or table, we used
	 * here the table widget.
	 */
	private Button button;

	/**
	 * Flag used to prevent the selection listener from changing the selection
	 * state when the mouse is changing it since the selection state is called
	 * after the mouse down event and before the mouse up event.
	 */
	private boolean handledByMouseEvent;

	/**
	 * The current selection state.
	 */
	private TriState state;

	/**
	 * Creates a new <code>TriStateCheckBox</code>.
	 *
	 * @param parent The parent composite
	 * @param widgetFactory The factory used to create the check box
	 */
	public TriStateCheckBox(Composite parent, IWidgetFactory widgetFactory) {
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
	                        IWidgetFactory widgetFactory) {
		super();
		this.buildWidgets(parent, text, widgetFactory);
	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#addDisposeListener(DisposeListener)
	 */
	public void addDisposeListener(DisposeListener disposeListener) {
		this.button.addDisposeListener(disposeListener);
	}

	/**
	 * @see Table#addSelectionListener(SelectionListener)
	 */
	public void addSelectionListener(SelectionListener selectionListener) {
		this.button.addSelectionListener(selectionListener);
	}

	private MouseAdapter buildMouseListener() {

		return new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {

				handledByMouseEvent = true;

				TriStateCheckBox.this.changeTriState();
				TriStateCheckBox.this.updateCheckBox();
			}

			@Override
			public void mouseUp(MouseEvent e) {

				TriStateCheckBox.this.updateCheckBox();
			}
		};
	}

	private SelectionListener buildSelectionListener() {

		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (handledByMouseEvent) {
					handledByMouseEvent = false;
				}
				else {
					TriStateCheckBox.this.changeTriState();
					TriStateCheckBox.this.updateCheckBox();
				}
			}
		};
	}

	private TriState buildState(Boolean selection) {

		if (selection == null) {
			return TriState.PARTIALLY_CHECKED;
		}

		return selection.booleanValue() ? TriState.CHECKED : TriState.UNCHECKED;
	}

	private void buildWidgets(Composite parent,
	                          String text,
	                          IWidgetFactory widgetFactory) {

		this.state = TriState.UNCHECKED;

		this.button = widgetFactory.createCheckBox(parent, text);
		this.button.addMouseListener(buildMouseListener());
		this.button.addSelectionListener(buildSelectionListener());
	}

	private void changeTriState() {

		if (this.state == TriState.UNCHECKED) {
			this.state = TriState.PARTIALLY_CHECKED;
		}
		else if (this.state == TriState.CHECKED) {
			this.state = TriState.UNCHECKED;
		}
		else {
			this.state = TriState.CHECKED;
		}
	}

	/**
	 * Disposes the actual widget.
	 */
	public void dispose() {
		getCheckBox().dispose();
	}

	/**
	 * Returns the actual <code>TableItem</code> used to show a tri-state check
	 * box.
	 *
	 * @return The unique item of the table that handles tri-state selection
	 */
	public Button getCheckBox() {
		return this.button;
	}

	/**
	 * Returns the main widget of the tri-state check box.
	 *
	 * @return The main composite used to display the tri-state check box
	 */
	public Control getControl() {
		return this.button;
	}

	/**
	 * Returns the check box's image.
	 *
	 * @return The image of the check box
	 */
	public Image getImage() {
		return this.getCheckBox().getImage();
	}

	/**
	 * Returns the selection state of the check box.
	 *
	 * @return Either <code>true</code> or <code>false</code> for checked and
	 * unchecked; or <code>null</code> for partially selected
	 */
	public Boolean getSelection() {
		return (this.state == TriState.PARTIALLY_CHECKED) ? null : (this.state == TriState.CHECKED);
	}

	/**
	 * Returns the check box's text.
	 *
	 * @return The text of the check box
	 */
	public String getText() {
		return this.getCheckBox().getText();
	}

	/**
	 * Determines whether the check box is enabled or not.
	 *
	 * @return <code>true</code> if the check box is enabled; <code>false</code>
	 * otherwise
	 */
	public boolean isEnabled() {
		return this.button.isEnabled();
	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#removeDisposeListener(DisposeListener)
	 */
	public void removeDisposeListener(DisposeListener disposeListener) {
		this.button.removeDisposeListener(disposeListener);
	}

	/**
	 * @see Table#removeSelectionListener(SelectionListener)
	 */
	public void removeSelectionListener(SelectionListener selectionListener) {
		this.button.removeSelectionListener(selectionListener);
	}

	/**
	 * Changes the enablement state of the widgets of this pane.
	 *
	 * @param enabled <code>true</code> to enable the widgets or <code>false</code>
	 * to disable them
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
		this.getCheckBox().setImage(image);
	}

	/**
	 * Changes the selection state of the check box.
	 *
	 * @param selection Either <code>true</code> or <code>false</code> for
	 * checked and unchecked; or <code>null</code> for partially selected
	 */
	public void setSelection(Boolean selection) {
		TriState oldState = this.state;
		this.state = this.buildState(selection);

		if (oldState != this.state) {
			this.updateCheckBox();
		}
	}

	/**
	 * Sets the check box's text.
	 *
	 * @param text The new text of the check box
	 */
	public void setText(String text) {
		// TODO: Not sure this will update the layout, if that is the case,
		// then copy the code from LabeledTableItem.updateTableItem()
		this.getCheckBox().setText(text);
	}

	/**
	 * Updates the selection state of the of the check box based on the tri-state
	 * value.
	 */
	private void updateCheckBox() {
		Button checkBox = this.getCheckBox();

		if (this.state == TriState.PARTIALLY_CHECKED) {
			checkBox.setSelection(true);
			checkBox.setGrayed(true);
		}
		else {
			checkBox.setGrayed(false);
			checkBox.setSelection(this.state == TriState.CHECKED);
		}
	}

	/**
	 * An enum containing the possible selection.
	 */
	public enum TriState {
		CHECKED,
		PARTIALLY_CHECKED,
		UNCHECKED
	}
}