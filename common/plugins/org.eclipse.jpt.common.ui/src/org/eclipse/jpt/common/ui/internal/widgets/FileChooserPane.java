/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.common.ui.JptCommonUiPlugin;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * This chooser allows the user to choose a file when browsing.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ---------------------------------------------------- ------------- |
 * | Label: | I                                                | | Browse... | |
 * |        ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class FileChooserPane<T extends Model> extends ChooserPane<T>
{
	private WritablePropertyValueModel<String> textHolder;

	/**
	 * Creates a new <code>FileChooserPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public FileChooserPane(Pane<? extends T> parentPane,
	                       Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>FileChooserPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public FileChooserPane(Pane<?> parentPane,
	                       PropertyValueModel<? extends T> subjectHolder,
	                       Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected final Runnable buildBrowseAction() {
		return new Runnable() {
			public void run() {
				promptFile();
			}
		};
	}

	/**
	 * Creates the <code>ViewerFilter</code> that will filter the content of the
	 * dialog and only displays what is valid.
	 *
	 * @return A new <code>ViewerFilter</code>
	 */
	protected ViewerFilter buildFilter() {
		return new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer,
			                      Object parentElement,
			                      Object element) {

				return true;
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
	 * Creates the validator that will show a status message based on what is
	 * selected in the selection dialog.
	 *
	 * @return A new <code>ISelectionStatusValidator</code>
	 */
	protected ISelectionStatusValidator buildValidator() {
		return new ISelectionStatusValidator() {
			public IStatus validate(Object[] selection) {

				if (selection.length != 1) {
					return new Status(IStatus.ERROR, JptCommonUiPlugin.PLUGIN_ID, "");
				}

				return new Status(IStatus.OK, JptCommonUiPlugin.PLUGIN_ID, "");
			}
		};
	}

	/**
	 * Returns the selection dialog's title.
	 *
	 * @return A non-<code>null</code> string
	 */
	protected abstract String getDialogTitle();

	/**
	 * Retrieves the project path that will be used by the selection dialog.
	 *
	 * @return The project path used to display its content in a selection dialog
	 */
	protected abstract String getProjectPath();

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
	 * prompt the user to select a file and set it.
	 */
	protected void promptFile() {
		String projectPath= this.getProjectPath();

		FileDialog dialog = new FileDialog(getShell());
		dialog.setText(this.getDialogTitle());
		dialog.setFilterPath(projectPath);
		String filePath = dialog.open();
		if (filePath != null) {
			FileChooserPane.this.textHolder.setValue(filePath);
		}
	}
}
