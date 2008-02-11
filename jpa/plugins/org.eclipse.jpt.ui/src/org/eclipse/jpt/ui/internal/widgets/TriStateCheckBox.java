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

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

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
@SuppressWarnings("nls")
public final class TriStateCheckBox {

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
	 * A tri-state check box can only be used within a tree or table, we used
	 * here the table widget.
	 */
	private Table table;

	/**
	 * Creates a new <code>TriStateCheckBox</code>.
	 *
	 * @param parent The parent composite
	 */
	public TriStateCheckBox(Composite parent) {
		super();

		this.state = TriState.UNCHECKED;
		this.buildWidgets(parent);
	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#addDisposeListener(DisposeListener)
	 */
	public void addDisposeListener(DisposeListener disposeListener) {
		this.table.addDisposeListener(disposeListener);
	}

	/**
	 * @see Table#addSelectionListener(SelectionListener)
	 */
	public void addSelectionListener(SelectionListener selectionListener) {
		this.table.addSelectionListener(selectionListener);
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

	private SelectionAdapter buildSelectionListener() {

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

	private Layout buildTableLayout() {

		return new Layout() {

			@Override
			protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
				Rectangle bounds = TriStateCheckBox.this.getCheckBox().getBounds();
				return new Point(bounds.x + bounds.width, bounds.y + bounds.height);
			}

			private int indentation() {
				if (Platform.OS_WIN32.equals(Platform.getOS())) {
					try {
						String version = System.getProperty("os.version");

						// Under Vista, the check box has to be indented by -6
						if (Double.parseDouble(version) >= 6) {
							return 6;
						}
						// Under XP, the check box has to be indented by -5
						else if (Double.parseDouble(version) == 5) {
							return 5;
						}
					}
					catch (Exception e) {
						// Ignore and return 0
					}
				}

				return 0;
			}

			@Override
			protected void layout(Composite composite, boolean flushCache) {

				Rectangle bounds = TriStateCheckBox.this.getCheckBox().getBounds();
				int indentation = indentation();

				TriStateCheckBox.this.table.setBounds(
					-indentation,
					0,
					bounds.x + bounds.width + indentation,
					bounds.y + bounds.height
				);
			}
		};
	}

	private void buildWidgets(Composite parent) {

		parent = new Composite(parent, SWT.NULL);
		parent.setLayout(this.buildTableLayout());

		this.table = new Table(parent, SWT.CHECK | SWT.SINGLE | SWT.TRANSPARENT);
		this.table.addMouseListener(buildMouseListener());
		this.table.addSelectionListener(buildSelectionListener());
		this.table.getHorizontalBar().setVisible(false);
		this.table.getVerticalBar().setVisible(false);

		new TableItem(this.table, SWT.CHECK | SWT.TRANSPARENT);
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
	public TableItem getCheckBox() {
		return this.table.getItem(0);
	}

	/**
	 * Returns the main widget of the tri-state check box.
	 *
	 * @return The main composite used to display the tri-state check box
	 */
	public Control getControl() {
		return this.table.getParent();
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
		return this.table.isEnabled();
	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#removeDisposeListener(DisposeListener)
	 */
	public void removeDisposeListener(DisposeListener disposeListener) {
		this.table.removeDisposeListener(disposeListener);
	}

	/**
	 * @see Table#removeSelectionListener(SelectionListener)
	 */
	public void removeSelectionListener(SelectionListener selectionListener) {
		this.table.removeSelectionListener(selectionListener);
	}

	/**
	 * Changes the enablement state of the widgets of this pane.
	 *
	 * @param enabled <code>true</code> to enable the widgets or <code>false</code>
	 * to disable them
	 */
	public void setEnabled(boolean enabled) {
		this.table.setEnabled(enabled);
	}

	/**
	 * Sets the check box's image.
	 *
	 * @param image The new image of the check box
	 */
	public void setImage(Image image) {
		// TODO: Not sure this will update the layout, if that is the case,
		// then copy the code from LabeledTableItem.updateTableItem()
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
		TableItem checkBox = this.getCheckBox();

		if (this.state == TriState.PARTIALLY_CHECKED) {
			checkBox.setChecked(true);
			checkBox.setGrayed(true);
		}
		else {
			checkBox.setGrayed(false);
			checkBox.setChecked(this.state == TriState.CHECKED);
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