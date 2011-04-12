/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.preferences;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This is the root of the Java Persistence preferences hierarchy in the IDE preferences dialog.
 * <p>
 * Structure:
 * <p>
 * JPA<br>
 *  |- Errors/Warnings
 *
 * @version 3.0
 * @since 3.0
 */
public class JpaPreferencesPage extends PreferencePage
                                implements IWorkbenchPreferencePage {

	private boolean lowercase;
	private Button lowerCaseRadioButton;
	private boolean matchFirstCharacterCase;
	private Button matchFirstCharacterCaseCheckBox;
	private Button upperCaseRadioButton;

	/**
	 * Creates a new <code>JpaPreferencesPage</code>.
	 */
	public JpaPreferencesPage() {
		super();
	}

	private void addJpqlEditorGroup(Composite parent) {

		// JPQL Editing group box
		Group group = new Group(parent, SWT.NONE);
		group.setText(JptUiMessages.JpaPreferencesPage_jpqlEditor);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Top description
		Label description = new Label(group, SWT.NONE);
		description.setText(JptUiMessages.JpaPreferencesPage_jpqlEditor_description);

		// Uppercase radio button
		lowerCaseRadioButton = new Button(group, SWT.RADIO);
		lowerCaseRadioButton.setText(JptUiMessages.JpaPreferencesPage_jpqlEditor_lowerCaseRadioButton);
		lowerCaseRadioButton.addSelectionListener(buildLowercaseSelectionListener());
		lowerCaseRadioButton.setSelection(lowercase);

		// Uppercase radio button
		upperCaseRadioButton = new Button(group, SWT.RADIO);
		upperCaseRadioButton.setText(JptUiMessages.JpaPreferencesPage_jpqlEditor_upperCaseRadioButton);
		upperCaseRadioButton.addSelectionListener(buildUppercaseSelectionListener());
		upperCaseRadioButton.setSelection(!lowercase);

		new Label(group, SWT.NONE);

		// Match Case of First Letter check box
		matchFirstCharacterCaseCheckBox = new Button(group, SWT.CHECK);
		matchFirstCharacterCaseCheckBox.setText(JptUiMessages.JpaPreferencesPage_jpqlEditor_matchFirstCharacterCaseRadioButton);
		matchFirstCharacterCaseCheckBox.addSelectionListener(buildMatchFirstCharacterCaseSelectionListener());
		matchFirstCharacterCaseCheckBox.setSelection(matchFirstCharacterCase);
	}

	private SelectionListener buildLowercaseSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				lowercase = button.getSelection();
			}
		};
	}

	private SelectionListener buildMatchFirstCharacterCaseSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				matchFirstCharacterCase = button.getSelection();
			}
		};
	}

	private SelectionListener buildUppercaseSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				lowercase = !button.getSelection();
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);

		parent = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = convertVerticalDLUsToPixels(10);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		parent.setLayout(layout);

		Label description = new Label(parent, SWT.NONE);
		description.setText(JptUiMessages.JpaPreferencesPage_description);

		addJpqlEditorGroup(parent);

		Dialog.applyDialogFont(parent);
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(JptJpaUiPlugin.instance().getPreferenceStore());
		lowercase = shouldUseLowercaseIdentifiers();
		matchFirstCharacterCase = shouldMatchFirstCharacterCase();
	}

	private boolean isDefaultJpqlIdentifierLowercase() {
		String value = getPreferenceStore().getDefaultString(JptJpaUiPlugin.JPQL_IDENTIFIER_CASE_PREF_KEY);
		return JptJpaUiPlugin.JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE.equals(value);
	}

	private String jpqlIdentifierPreferenceValue() {
		return lowercase ? JptJpaUiPlugin.JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE : JptJpaUiPlugin.JPQL_IDENTIFIER_UPPERCASE_PREF_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performDefaults() {

		lowercase = isDefaultJpqlIdentifierLowercase();
		matchFirstCharacterCase = shouldDefaultMatchFirstCharacterCase();

		lowerCaseRadioButton.setSelection(lowercase);
		upperCaseRadioButton.setSelection(!lowercase);
		matchFirstCharacterCaseCheckBox.setSelection(matchFirstCharacterCase);

		super.performDefaults();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performOk() {
		getPreferenceStore().setValue(JptJpaUiPlugin.JPQL_IDENTIFIER_CASE_PREF_KEY, jpqlIdentifierPreferenceValue());
		getPreferenceStore().setValue(JptJpaUiPlugin.JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY, matchFirstCharacterCase);
		return super.performOk();
	}

	private boolean shouldDefaultMatchFirstCharacterCase() {
		return getPreferenceStore().getDefaultBoolean(JptJpaUiPlugin.JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY);
	}

	private boolean shouldMatchFirstCharacterCase() {
		return getPreferenceStore().getBoolean(JptJpaUiPlugin.JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY);
	}

	private boolean shouldUseLowercaseIdentifiers() {
		String value = getPreferenceStore().getString(JptJpaUiPlugin.JPQL_IDENTIFIER_CASE_PREF_KEY);
		return JptJpaUiPlugin.JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE.equals(value);
	}
}