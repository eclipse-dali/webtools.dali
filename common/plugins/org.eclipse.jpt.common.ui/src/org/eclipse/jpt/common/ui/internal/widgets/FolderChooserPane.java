/*******************************************************************************
 * Copyright (c) 2008, 20010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;

/**
 * This chooser allows the user to choose a folder when browsing.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ---------------------------------------------------- ------------- |
 * | Label: | I                                                | | Browse... | |
 * |        ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 3.0
 * @since 2.0
 */
public abstract class FolderChooserPane<T extends Model> extends ChooserPane<T>
{
	private WritablePropertyValueModel<String> textHolder;

	/**
	 * Creates a new <code>FolderChooserPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public FolderChooserPane(Pane<? extends T> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>FolderChooserPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public FolderChooserPane(Pane<?> parentPane,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected final Runnable buildBrowseAction() {
		return new Runnable() {
			public void run() {
				promptFolder();
			}
		};
	}

	@Override
	protected Control addMainControl(Composite container) {
		return this.addText(container, this.textHolder);
	}

	/**
	 * Creates the value holder of the subject's property.
	 *
	 * @return The holder of the class name
	 */
	protected abstract WritablePropertyValueModel<String> buildTextHolder();

	/**
	 * Returns the message to be shown in the selection dialog.
	 *
	 * @return A non-<code>null</code> string shown above the text field of the
	 * selection dialog
	 */
	protected abstract String getDialogMessage();

	/**
	 * Returns the selection dialog's title.
	 *
	 * @return A non-<code>null</code> string
	 */
	protected abstract String getDialogTitle();

	/**
	 * Returns the path that the dialog will use to filter the directories it
	 * shows to the argument, which may be null. If the string is null, then the
	 * operating system's default filter path will be used.
	 * <p>
	 * Note that the path string is platform dependent. For convenience, either
	 * '/' or '\' can be used as a path separator.
	 * </p>
	 *
	 * @return The filter path
	 */
	protected String filterPath() {
		return null;
	}

	protected  WritablePropertyValueModel<String> getTextHolder() {
		return this.textHolder;
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.textHolder = this.buildTextHolder();
	}

	/**
	 * The browse button was clicked, its action invokes this action which should
	 * prompt the user to select a folder and set it.
	 */
	protected void promptFolder() {

		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setMessage(this.getDialogMessage());
		dialog.setText(this.getDialogTitle());
		dialog.setFilterPath(this.filterPath());
		String directory = dialog.open();

		if (directory != null) {
			this.textHolder.setValue(directory);
		}
	}
}
