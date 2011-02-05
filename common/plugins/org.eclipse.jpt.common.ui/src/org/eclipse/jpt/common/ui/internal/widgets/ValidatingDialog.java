/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import java.util.ListIterator;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jpt.common.utility.internal.node.Node;
import org.eclipse.jpt.common.utility.internal.node.Problem;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog is similar to it superclass, <code>Dialog</code>, with
 * the added value of an error message label below the main panel. A subclass
 * can set this error message as needed so that it can inform the user something
 * incorrect has been entered.
 * <p>
 * If there is an error message, it will be shown. If there is a warning
 * message, it will only be shown if there is no error message. Warning messages
 * have a different icon than error messages.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class ValidatingDialog<T extends Node> extends Dialog<T> {

	/**
	 * Creates a new <code>ValidatingDialog</code>.
	 *
	 * @param parent The parent shell
	 */
	public ValidatingDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Creates a new <code>ValidatingDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param title The dialog's title
	 */
	public ValidatingDialog(Shell parent, String title) {
		super(parent, title);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	final Node.Validator buildValidator() {
		return new Node.Validator() {
			public void pause() {
			}

			public void resume() {
			}

			public void validate() {
				ValidatingDialog.this.validate();
			}
		};
	}

	/**
	 * Clears the messages from the description pane
	 */
	protected final void clearMessage(){
		setMessage(getDescription());
	}

	/**
	 * Clears the error message from the description pane.
	 */
	protected final void clearErrorMessage() {
		setErrorMessage(null);
	}

	/**
	 * Returns the description shown in the description pane.
	 *
	 * @return The description under the description's title
	 */
	protected String getDescription() {
		return null;
	}

	/**
	 * Returns the image shown in the description pane.
	 *
	 * @return The image of the description pane or <code>null</code> if none is
	 * required
	 */
	protected Image getDescriptionImage() {
		return null;
	}

	/**
	 * Returns the title of the description pane.
	 *
	 * @return The title shown in the description pane
	 */
	protected String getDescriptionTitle() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Point getInitialSize() {
		Point result = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		Point paneSize = getPane().getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int width = convertHorizontalDLUsToPixels(400);
		result.x = Math.max(width, paneSize.x);
		return result;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected final boolean hasTitleArea() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeUI() {

		super.initializeUI();

		// Update the description title
		String descriptionTitle = getDescriptionTitle();

		if (descriptionTitle != null) {
			setTitle(descriptionTitle);
		}

		// Update the description title
		String description = getDescription();

		if (description != null) {
			setMessage(description);
		}

		// Update the description image
		Image image = getDescriptionImage();

		if (image != null) {
			setTitleImage(image);
		}
	}

	/**
	 * Updates the description pane by showing the given error message and format
	 * the text with the given list of arguments if any exists.
	 *
	 * @param errorMessage The error message to show in the description pane
	 * @param arguments The list of arguments used to format the error message
	 */
	protected final void setErrorMessage(String errorMessage, Object... arguments) {
		setErrorMessage(NLS.bind(errorMessage, arguments));
	}

	/**
	 * Updates the description pane by showing the given warning message and format
	 * the text with the given list of arguments if any exists.
	 *
	 * @param warningMessage The warning message to show in the description pane
	 * @param arguments The list of arguments used to format the error message
	 */
	
	protected final void setWarningMessage(String warningMessage, Object... arguments) {
		setMessage(NLS.bind(warningMessage, arguments), IMessageProvider.WARNING);
	}

	/**
	 * Updates the error message, either shows the first error problem or hides
	 * the error pane. If the progress bar is shown, then the error message will
	 * not be shown.
	 */
	private void updateMessage() {
		if (getSubject().branchProblemsSize() == 0) {
			clearMessage();
		} else {
				for (ListIterator<Problem> problems = getSubject().branchProblems(); problems.hasNext();){
					Problem problem = problems.next();
					if (problem.messageType() == IMessageProvider.ERROR){
						this.setErrorMessage(problem.messageKey(), problem.messageArguments());
					} 
					else if (problem.messageType() == IMessageProvider.WARNING){
						this.setWarningMessage(problem.messageKey(), problem.messageArguments());
					}
				}
		}
		if (!this.containsErrorMessage()){
			clearErrorMessage();
		}
	}
	
	public final boolean containsErrorMessage(){
		boolean error = false;
		for (ListIterator<Problem> problems = getSubject().branchProblems(); problems.hasNext();){
			Problem problem = problems.next();
			if (problem.messageType() ==IMessageProvider.ERROR){
				error = true;
			} 
		}
		return error;
	}
	/**
	 * Validates the state object and based on its status, update the description
	 * pane to show the first error if any exists and update the enablement of
	 * the OK button.
	 */
	private void validate() {
		getSubject().validateBranch();
		updateMessage();
		getButton(OK).setEnabled(!containsErrorMessage());
	}
}