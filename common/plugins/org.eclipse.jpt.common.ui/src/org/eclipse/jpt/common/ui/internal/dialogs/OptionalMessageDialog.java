/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;


/**
 * A <code>OptionalMessageDialog</code> is a dialog that has a check box allowing the
 * option to no longer show the dialog in the future. 
 */
public abstract class OptionalMessageDialog extends MessageDialog {

	private static final String CHECKBOX_TEXT = JptCommonUiMessages.OPTIONAL_MESSAGE_DIALOG__DO_NOT_SHOW_WARNING;

	// Dialog store id constants
	private static final String STORE_ID = "OptionalMessageDialog.hide."; //$NON-NLS-1$

	public static final int NOT_SHOWN = IDialogConstants.CLIENT_ID + 1;

	/* CU private */ final String id;

	private final String checkBoxText;
	
	/**
	 * Creates a dialog with the given parent.
	 * 
	 * @param parentShell
	 *            object that returns the current parent shell 
	 */
	public OptionalMessageDialog(String id, Shell parentShell, String dialogTitle, String dialogMessage, int dialogType, String[] buttonLabels, int defaultButtonIndex) {
		this(id, parentShell, dialogTitle, dialogMessage, dialogType, buttonLabels, defaultButtonIndex, CHECKBOX_TEXT);
	}
	
	/**
	 * Creates a dialog with the given parent and a checkbox with the labeled with the text in <code>checkBoxText</code>
	 * 
	 * @param parentShell
	 *            object that returns the current parent shell 
	 */	
	public OptionalMessageDialog(String id, Shell parentShell, String dialogTitle, String dialogMessage, int dialogType, String[] buttonLabels, int defaultButtonIndex, String checkBoxText) {
		super(parentShell, dialogTitle, null, dialogMessage, dialogType, buttonLabels, defaultButtonIndex);
		this.id = id;
		this.checkBoxText = checkBoxText;
	}
	
    @Override
    protected Control createCustomArea(Composite parent) {
    	final Button checkbox = new Button(parent, SWT.CHECK);
    	checkbox.setText(this.checkBoxText);
    	checkbox.setSelection(false);
    	checkbox.setLayoutData(new GridData(GridData.FILL_BOTH));
    	checkbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDialogEnabled(OptionalMessageDialog.this.id, !((Button)e.widget).getSelection());
			}
    	});
    	return checkbox;
    }

	/**
	 * Returns this dialog settings
	 *
	 * @return the settings to be used
	 */
	private static IDialogSettings getDialogSettings() {
		IDialogSettings settings= getJavaUIPlugin().getDialogSettings();
		settings= settings.getSection(STORE_ID);
		if (settings == null)
			settings= getJavaUIPlugin().getDialogSettings().addNewSection(STORE_ID);
		return settings;
	}

	/**
	 * Answers whether the optional dialog is enabled and should be shown.
	 */
	public static boolean isDialogEnabled(String key) {
		IDialogSettings settings= getDialogSettings();
		return !settings.getBoolean(key);
	}

	/**
	 * Sets whether the optional dialog is enabled and should be shown.
	 */
	public static void setDialogEnabled(String key, boolean isEnabled) {
		IDialogSettings settings= getDialogSettings();
		settings.put(key, !isEnabled);
	}

	/**
	 * Clears all remembered information about hidden dialogs
	 */
	public static void clearAllRememberedStates() {
		IDialogSettings settings= getJavaUIPlugin().getDialogSettings();
		settings.addNewSection(STORE_ID);
	}

	@SuppressWarnings("restriction")
	private static AbstractUIPlugin getJavaUIPlugin() {
		return org.eclipse.jdt.internal.ui.JavaPlugin.getDefault();
	}
}
