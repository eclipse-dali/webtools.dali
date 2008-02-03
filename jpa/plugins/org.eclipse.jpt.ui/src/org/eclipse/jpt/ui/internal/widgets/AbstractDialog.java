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

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

/**
 * The abstract implementation of a dialog using a "state object" (model object)
 * for behavior.
 * <p>
 * The main pane of this dialog should be extending <code>AbstractDialogPane</code>
 * for creating the right type of widgets and it has the "state object" (subject)
 * behavior built-in.
 *
 * @see Node
 * @see AbstractDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class AbstractDialog<T extends Node> extends TitleAreaDialog
{
	/**
	 * The holder of the "state object" used by this dialog.
	 */
	private WritablePropertyValueModel<T> subjectHolder;

	/**
	 * Caches the title text until the dialog is created and the dialog's shell
	 * needs to be configured.
	 */
	private String title;

	/**
	 * Creates a new <code>AbstractDialog</code>.
	 *
	 * @param parent The parent shell
	 */
	protected AbstractDialog(Shell parent) {
		this(parent, "");
	}

	/**
	 * Creates a new <code>AbstractDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param title The dialog's title
	 */
	protected AbstractDialog(Shell parent, String title) {
		super(parent);
		this.title = title;
		initialize();
	}

	protected T buildStateObject() {
		return null;
	}

	Node.Validator buildValidator() {
		return Node.NULL_VALIDATOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create() {
		super.create();
		installSubject();
		getButton(OK).setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Control createContents(Composite parent) {
		if (hasTitleArea()) {
			return super.createContents(parent);
		}

		return createDefaultContent(parent);
	}

	private Control createDefaultContent(Composite parent) {

		Composite composite = new Composite(parent, 0);

		GridLayout layout      = new GridLayout();
		layout.marginHeight    = 0;
		layout.marginWidth     = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		applyDialogFont(composite);
		initializeDialogUnits(composite);
		this.dialogArea = createDialogArea(composite);
		this.buttonBar  = createButtonBar(composite);

		return composite;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Composite createDialogArea(Composite parent) {

		parent = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;

		Composite dialogPane = new Composite(parent, SWT.NULL);
		dialogPane.setLayout(layout);
		initializeMainPane(dialogPane);

		return parent;
	}

	protected final WritablePropertyValueModel<T> getSubjectHolder() {
		return this.subjectHolder;
	}

	boolean hasTitleArea() {
		return false;
	}

	/**
	 * Returns the helps system.
	 *
	 * @return The platform's help system
	 *
	 * @category Helper
	 */
	protected final IWorkbenchHelpSystem helpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}

	/**
	 * Initializes this dialog.
	 */
	protected void initialize() {
		this.subjectHolder = new SimplePropertyValueModel<T>();
	}

	/**
	 * The dialog is built when the show() method is called not
	 * when the Dialog is constructed
	 */
	protected abstract void initializeMainPane(Composite container);

	private void installSubject() {

		T subject = this.buildStateObject();

		if (subject != null) {
			subject.setValidator(buildValidator());
		}

		this.subjectHolder.setValue(subject);
	}

	/**
	 * Asynchronously launches this dialog in the UI thread.
	 */
	public final void openDialog() {
		SWTUtil.setUserInterfaceActive(false);
		SWTUtil.show(this);
	}

	/**
	 * Asynchronously launches this dialog in the UI thread and invoke the given
	 * <code>PostExecution</code> to perform any post-task.
	 *
	 * @param postExecution This interface let the caller to invoke a piece of
	 * code once the dialog is disposed
	 */
	public final void openDialog(PostExecution<? extends AbstractDialog<T>> execution) {
		SWTUtil.setUserInterfaceActive(false);
		SWTUtil.show(this, execution);
	}

	/**
	 * Returns the subject of this dialog.
	 *
	 * @return The subject of this dialog or <code>null</code> if no subject was
	 * used
	 */
	public T subject() {
		return this.subjectHolder.value();
	}

	protected String title() {
		return this.title;
	}

	public boolean wasCancelled() {
		return getReturnCode() == CANCEL;
	}

	public boolean wasConfirmed() {
		return getReturnCode() == OK;
	}
}
