/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

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
		return addText(container, this.textHolder);
	}

	private PostExecution<ElementTreeSelectionDialog> buildSelectionDialogPostExecution() {
		return new PostExecution<ElementTreeSelectionDialog>() {
			public void execute(ElementTreeSelectionDialog dialog) {
				if (dialog.getReturnCode() == IDialogConstants.OK_ID) {
					FileChooserPane.this.textHolder.setValue(dialog.getResult()[0].toString());
				}
			}
		};
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
					return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, "");
				}

				return new Status(IStatus.OK, JptUiPlugin.PLUGIN_ID, "");
			}
		};
	}

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
	 * Retrieves the root input that will be used by the selection dialog.
	 *
	 * @return The input used to display its content in a selection dialog
	 */
	protected abstract IResource getDialogInput();

	@Override
	protected void initialize() {
		super.initialize();
		this.textHolder = buildTextHolder();
	}

	/**
	 * The browse button was clicked, its action invokes this action which should
	 * prompt the user to select a file and set it.
	 */
	protected void promptFile() {

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
			getShell(),
			new WorkbenchLabelProvider(),
			new WorkbenchContentProvider()
		);

		dialog.setHelpAvailable(false);
		dialog.setValidator(buildValidator());
		dialog.setTitle(getDialogTitle());
		dialog.setMessage(getDialogMessage());
		dialog.addFilter(buildFilter());
		dialog.setInput(getDialogInput());
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));

		SWTUtil.show(dialog, buildSelectionDialogPostExecution());
	}
}
