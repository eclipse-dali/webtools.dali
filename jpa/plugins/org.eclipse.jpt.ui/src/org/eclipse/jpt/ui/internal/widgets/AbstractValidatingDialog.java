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

import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.jpt.utility.internal.node.Problem;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog is similar to it superclass, <code>AbstractDialog</code>, with
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
public abstract class AbstractValidatingDialog<T extends Node> extends AbstractDialog<T> {

	public AbstractValidatingDialog(Shell parent) {
		super(parent);
	}

	public AbstractValidatingDialog(Shell parent, String title) {
		super(parent, title);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	Node.Validator buildValidator() {
		return new Node.Validator() {
			public void pause() {
			}

			public void resume() {
			}

			public void validate() {
				AbstractValidatingDialog.this.validate();
			}
		};
	}

	protected void clearErrorMessage() {
		setErrorMessage(null);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	boolean hasTitleArea() {
		return true;
	}

	/**
	 * convenience method for simple error message
	 */
	protected void setErrorMessageKey(String key, Object... argument) {
		setErrorMessage(NLS.bind(key, argument));
	}

	/**
	 * Updates the error message, either shows the first error problem or hides
	 * the error pane. If the progress bar is shown, then the error message will
	 * not be shown.
	 */
	private void updateErrorMessage() {
		if (subject().hasBranchProblems()) {
			Problem problem = subject().branchProblems().next();
			setErrorMessageKey(problem.messageKey(), problem.messageArguments());
		}
		// TODO: It would be nice to add warnings to the model
//		else if (this.subject().hasBranchWarnings()) {
//			Problem problem = this.subject().branchWarnings().next();
//			this.setWarningMessageKey(problem.getMessageKey(), problem.getMessageArguments());
//		}
		else {
			clearErrorMessage();
		}
	}

	private void validate() {
		subject().validateBranch();
		updateErrorMessage();
		getButton(OK).setEnabled(!subject().hasBranchProblems());
	}
}
