/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.prefs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.prefs.JpaEntityGenPreferencesManager;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This is the root of the JPA preferences hierarchy in the IDE
 * preferences dialog.
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
	private Text defaultPackageText;

	// ********** constructors **********

	public JpaPreferencesPage() {
		super();
	}

	// ********** overrides **********

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		this.initializeDialogUnits(parent);

		parent = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = this.convertVerticalDLUsToPixels(10);
		layout.horizontalSpacing = this.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		parent.setLayout(layout);

		Label description = new Label(parent, SWT.NONE);
		description.setText(JptUiMessages.JpaPreferencesPage_description);

		this.addEntityGenGroup(parent);
		this.addJpqlEditorGroup(parent);

		Dialog.applyDialogFont(parent);
		return parent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performDefaults() {

		this.lowercase = this.isDefaultJpqlIdentifierLowercase();
		this.matchFirstCharacterCase = this.shouldDefaultMatchFirstCharacterCase();

		this.lowerCaseRadioButton.setSelection(this.lowercase);
		this.upperCaseRadioButton.setSelection( ! this.lowercase);
		this.matchFirstCharacterCaseCheckBox.setSelection(this.matchFirstCharacterCase);

		super.performDefaults();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performOk() {

		JpaEntityGenPreferencesManager.setDefaultPackageWorkspacePreference(this.getDefaultPackage());
		
		//TODO use JpaPreferencesManager
		this.getPreferenceStore().setValue(JptJpaUiPlugin.JPQL_IDENTIFIER_CASE_PREF_KEY, jpqlIdentifierPreferenceValue());
		this.getPreferenceStore().setValue(JptJpaUiPlugin.JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY, matchFirstCharacterCase);

		return super.performOk();
	}


	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(JptJpaUiPlugin.instance().getPreferenceStore());
		this.lowercase = this.shouldUseLowercaseIdentifiers();
		this.matchFirstCharacterCase = this.shouldMatchFirstCharacterCase();
	}

	// ********** internal methods **********
	
	private void addEntityGenGroup(Composite parent) {

		// Entity Gen group box
		Group group = new Group(parent, SWT.NONE);
		group.setText(JptUiMessages.JpaPreferencesPage_entityGen);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// default package
		this.buildLabel(group, 1, JptUiMessages.JpaPreferencesPage_entityGen_defaultPackageLabel);
		this.defaultPackageText = this.buildText(group, 1);
		this.defaultPackageText.setText(
			JpaEntityGenPreferencesManager.getDefaultPackageWorkspacePreference());
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

	private boolean isDefaultJpqlIdentifierLowercase() {
		String value = getPreferenceStore().getDefaultString(JptJpaUiPlugin.JPQL_IDENTIFIER_CASE_PREF_KEY);
		return JptJpaUiPlugin.JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE.equals(value);
	}

	private String jpqlIdentifierPreferenceValue() {
		return lowercase ? JptJpaUiPlugin.JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE : JptJpaUiPlugin.JPQL_IDENTIFIER_UPPERCASE_PREF_VALUE;
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

	private String getDefaultPackage() {
		if(this.defaultPackageText == null) {
			return null;
		}
		return (StringTools.stringIsEmpty(this.defaultPackageText.getText())) ? 
						null : 
						this.defaultPackageText.getText();
	}

	// ********** UI controls **********

	private Text buildText(Composite parent, int horizontalSpan) {
		Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = horizontalSpan;
		text.setLayoutData(gridData);
		return text;
	}
	
	private Label buildLabel(Composite parent, int span, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		GridData gridData = new GridData();
		gridData.horizontalSpan = span;
		label.setLayoutData(gridData);
		return label;
	}
	
}