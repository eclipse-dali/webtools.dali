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

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * The main pane of this dialog should be extending <code>DialogPane</code>
 * for creating the right type of widgets and it has the "state object" (subject)
 * behavior built-in.
 *
 * @see Node
 * @see DialogPane
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class Dialog<T extends Node> extends TitleAreaDialog
{
	/**
	 * The main content pane of this dialog.
	 */
	private DialogPane<?> pane;

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
	 * Creates a new <code>Dialog</code>.
	 *
	 * @param parent The parent shell
	 */
	protected Dialog(Shell parent) {
		this(parent, "");
	}

	/**
	 * Creates a new <code>Dialog</code>.
	 *
	 * @param parent The parent shell
	 * @param title The dialog's title
	 */
	protected Dialog(Shell parent, String title) {
		super(parent);
		this.title = title;
		initialize();
	}

	/**
	 * Initializes the main pane of this dialog. This method is invoked only
	 * when the dialog is requested to show on screen and not during
	 * initialization.
	 *
	 * @param container The container to which the widgets should be added to,
	 * the layout is already set
	 */
	protected abstract DialogPane<?> buildLayout(Composite container);

	/**
	 * Creates the state object (model object) that will be used to keep track
	 * of the information entered in this dialog. The state object will be stored
	 * in the subject holder and can be retrieved using {@link #subject()}.
	 *
	 * @return A new state object
	 */
	protected T buildStateObject() {
		return null;
	}

	/**
	 * Creates the <code>Validator</code> that will be notified when changes are
	 * made to the state object.
	 *
	 * @return The validator that will be set on the state object
	 */
	Node.Validator buildValidator() {
		return Node.NULL_VALIDATOR;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public boolean close() {

		// Dispose the pane in order to remove any listeners that could
		// have been installed outside the scrope of the state object
		if (pane != null) {
			pane.dispose();
			pane = null;
		}

		return super.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(getTitle());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create() {
		super.create();
		installSubject();
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

	/**
	 * Creates the default main container of this dialog when the title area is
	 * not required. The top part is the dialog area populated by the subclass
	 * and the lower part is the button pane having the OK and Cancel buttons.
	 *
	 * @param parent The parent container
	 * @return The
	 */
	private Composite createDefaultContent(Composite parent) {

		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout layout      = new GridLayout(1, false);
		layout.marginHeight    = 0;
		layout.marginWidth     = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		applyDialogFont(composite);
		initializeDialogUnits(composite);
		dialogArea = createDialogArea(composite);
		buttonBar  = createButtonBar(composite);

		return composite;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Composite createDialogArea(Composite parent) {

		// If the title area needs to be shown, then leave the superclass to
		// create the necessary widgets
		if (hasTitleArea()) {
			parent = (Composite) super.createDialogArea(parent);
		}

		// Create the main area's container
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.verticalAlignment         = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		container.setLayoutData(gridData);

		// Initialize the content pane
		pane = buildLayout(container);

		// Initialize the UI part, which requires the widgets being created
		initializeUI();

		return parent;
	}

	/**
	 * Determines whether the description area (where a title, description and
	 * image) should be visible or hidden. <code>ValidatingDialog</code>
	 * automatically show the description area in order to show problems.
	 *
	 * @return <code>false</code> by default, which means the methods used to
	 * update the title, description and image shouldn't be called; <code>true</code>
	 * to make the description pane visible
	 */
	protected boolean hasTitleArea() {
		return false;
	}

	/**
	 * Returns the helps system.
	 *
	 * @return The platform's help system
	 *
	 * @category Helper
	 */
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}

	/**
	 * Initializes this dialog.
	 */
	protected void initialize() {
		this.subjectHolder = new SimplePropertyValueModel<T>();
	}

	/**
	 * Initializes the UI part of this dialog, this is called after the widgets
	 * have been created.
	 */
	protected void initializeUI() {
	}

	/**
	 * Creates the state object, if one is needed and install a <code>Validator</code>
	 * in order to receive notification of changes done to that state object. The
	 * subject can be retrieved from the subject holder.
	 */
	private void installSubject() {

		T subject = buildStateObject();

		if (subject != null) {
			subject.setValidator(buildValidator());
		}

		subjectHolder.setValue(subject);
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
	public final void openDialog(PostExecution<? extends Dialog<T>> execution) {
		SWTUtil.setUserInterfaceActive(false);
		SWTUtil.show(this, execution);
	}

	/**
	 * Gives access to the dialog's main pane.
	 *
	 * @return The pane showing the custom widgets
	 */
	protected DialogPane<?> getPane() {
		return pane;
	}

	/**
	 * Returns the subject of this dialog.
	 *
	 * @return The subject of this dialog or <code>null</code> if no subject was
	 * used
	 */
	public T getSubject() {
		return subjectHolder.getValue();
	}

	/**
	 * Returns the holder of the subject.
	 *
	 * @return The subject holder used to be passed to the dialog pane, which is
	 * an instance of <code>DialogPane</code>
	 */
	protected final PropertyValueModel<T> getSubjectHolder() {
		return subjectHolder;
	}

	/**
	 * Retrieves the dialog's title. The title passed to the constructor will be
	 * returned by default but if it wasn't specified, this method can be used
	 * to return it.
	 *
	 * @return Either the title passed to the constructor or a different title
	 */
	protected String getTitle() {
		return title;
	}

	/**
	 * Determines whether the dialog was cancelled or not.
	 *
	 * @return <code>true</code> if the dialog was cancelled; <code>false</code>
	 * if it was confirmed
	 */
	public final boolean wasCancelled() {
		return getReturnCode() == CANCEL;
	}

	/**
	 * Determines whether the dialog was confirmed or not.
	 *
	 * @return <code>true</code> if the dialog was confirmed; <code>false</code>
	 * if it was cancelled
	 */
	public final boolean wasConfirmed() {
		return getReturnCode() == OK;
	}
}