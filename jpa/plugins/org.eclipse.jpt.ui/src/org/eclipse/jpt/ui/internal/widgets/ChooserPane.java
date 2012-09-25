/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A chooser is simply a pane with three widgets, the label on the left, a main
 * widget, usually a text field, and a right widget which is usually a browse
 * button.
 *
 * @see ClassChooserPane
 * @see PackageChooserPane
 *
 * @version 3.0
 * @since 2.0
 */
public abstract class ChooserPane<T extends Model> extends Pane<T>
{
	/**
	 * The control shown after the label (left control).
	 */
	private Control mainControl;

	/**
	 * The control shown after the main control.
	 */
	private Control rightControl;

	/**
	 * Creates a new <code>ChooserPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public ChooserPane(Pane<? extends T> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>ChooserPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public ChooserPane(Pane<?> parentPane,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.mainControl  = addMainControl(container);
		this.rightControl = addRightControl(container);

		addLabeledComposite(
			container,
			addLeftControl(container),
			this.mainControl,
			this.rightControl,
			getHelpId()
		);
	}

	/**
	 * Creates the left control. By default a label is created and its text is
	 * retrieved by {@link #getLabelText()}.
	 *
	 * @param container The parent container
	 * @return The newly created left control
	 */
	protected Control addLeftControl(Composite container) {
		return addLabel(container, getLabelText());
	}

	/**
	 * The text of the label. This method is called by
	 * {@link #buildLeftControl(Composite)}.
	 *
	 * @return The localized text of the left control (which is a label by
	 * default)
	 */
	protected abstract String getLabelText();

	/**
	 * Creates the main control of this pane.
	 *
	 * @param container The parent container
	 * @return The newly created main control
	 */
	protected abstract Control addMainControl(Composite container);

	/**
	 * Creates the right control. By default a browse button is created and its
	 * action is performed by {@link #buildBrowseAction()} and its text is
	 * retrieved by {@link #getBrowseButtonText()}.
	 *
	 * @param container The parent container
	 * @return The newly created right control
	 */
	protected Control addRightControl(Composite container) {
		return addButton(
			container,
			getBrowseButtonText(),
			buildBrowseAction()
		);
	}

	/**
	 * Returns the text of the browse button. This method is called by
	 * {@link #buildRightControl(Composite)}.
	 *
	 * @return "Browse..."
	 */
	protected String getBrowseButtonText() {
		return JptUiMessages.ChooserPane_browseButton;
	}

	/**
	 * Creates the action responsible to perform the action when the Browse is
	 * clicked.
	 *
	 * @return A new <code>Runnable</code> performing the actual action of the
	 * button
	 */
	protected abstract Runnable buildBrowseAction();

	/**
	 * Returns the help topic ID for the controls of this pane.
	 *
	 * @return <code>null</code> is returned otherwise the subclass can return an ID
	 */
	protected String getHelpId() {
		return null;
	}

	@Override
	public void enableWidgets(boolean enabled) {

		super.enableWidgets(enabled);

		if (!this.mainControl.isDisposed()) {
			this.mainControl.setEnabled(enabled);
		}

		if (!this.rightControl.isDisposed()) {
			this.rightControl.setEnabled(enabled);
		}
	}
}
