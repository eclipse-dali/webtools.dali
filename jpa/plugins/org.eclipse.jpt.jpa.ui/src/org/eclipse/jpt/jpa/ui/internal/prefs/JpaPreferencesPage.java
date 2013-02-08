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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
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
import org.eclipse.swt.widgets.Spinner;
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
 * @version 3.3
 * @since 3.0
 */
@SuppressWarnings("nls")
public class JpaPreferencesPage extends PreferencePage
                                implements IWorkbenchPreferencePage {

	private boolean lowercase;
	private Button lowerCaseRadioButton;
	private boolean matchFirstCharacterCase;
	private Spinner numberOfLinesInJpqlQuerySpinner;
	private int numberOfLinesInJpqlQueryTextArea;
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
		description.setText(JptJpaUiMessages.JpaPreferencesPage_description);

		this.addEntityGenGroup(parent);
		this.addJpqlEditorGroup(parent);

		Dialog.applyDialogFont(parent);
		return parent;
	}

	@Override
	protected void performDefaults() {
		// entitygen prefs
		this.setDefaultPackage(JpaPreferences.getEntityGenDefaultPackageName());

		// jpql query text area prefs
		this.numberOfLinesInJpqlQueryTextArea = getJpqlQueryTextAreaNumberOfLinesDefault();
		this.numberOfLinesInJpqlQuerySpinner.setSelection(this.numberOfLinesInJpqlQueryTextArea);

		// jpql identifier prefs
		this.lowercase = this.isDefaultJpqlIdentifierLowercase();
		this.matchFirstCharacterCase = this.shouldMatchFirstCharacterCaseDefault();

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
		// entitygen prefs
		JpaPreferences.setEntityGenDefaultPackageName(this.getDefaultPackage());

		// jpql query text area prefs
		JpaPreferences.setJpqlQueryTextAreaNumberOfLines(this.numberOfLinesInJpqlQueryTextArea);

		// jpql identifier prefs
		JpaPreferences.setJpqlIdentifierLowercase(this.lowercase);
		JpaPreferences.setJpqlIdentifierMatchFirstCharacterCase(this.matchFirstCharacterCase);

		return super.performOk();
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		this.migrateLegacyJpqlWorkspacePreferences();
		this.numberOfLinesInJpqlQueryTextArea = this.getJpqlQueryTextAreaNumberOfLines();
		this.lowercase = this.shouldUseLowercaseIdentifiers();
		this.matchFirstCharacterCase = this.shouldMatchFirstCharacterCase();
	}

	// ********** internal methods **********

	private void addEntityGenGroup(Composite parent) {

		// Entity Gen group box
		Group group = new Group(parent, SWT.NONE);
		group.setText(JptJpaUiMessages.JpaPreferencesPage_entityGen);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// default package
		this.buildLabel(group, 1, JptJpaUiMessages.JpaPreferencesPage_entityGen_defaultPackageLabel);
		this.defaultPackageText = this.buildText(group, 1);
		this.defaultPackageText.setText(JpaPreferences.getEntityGenDefaultPackageName());
	}

	private void addJpqlEditorGroup(Composite parent) {

		// JPQL Editing group box
		Group group = new Group(parent, SWT.NONE);
		group.setText(JptJpaUiMessages.JpaPreferencesPage_jpqlEditor);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// JPQL Query Text Area Number of Lines widgets
		Composite jpqlQueryTextAreaComposite = new Composite(group, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginBottom = 10;
		jpqlQueryTextAreaComposite.setLayout(layout);

		this.buildLabel(jpqlQueryTextAreaComposite, 1, JptJpaUiMessages.JpaPreferencesPage_jpqlEditor_textAreaNumberOfLines);
		this.numberOfLinesInJpqlQuerySpinner = new Spinner(jpqlQueryTextAreaComposite, SWT.BORDER);
		this.numberOfLinesInJpqlQuerySpinner.setValues(this.numberOfLinesInJpqlQueryTextArea, 0, 100, 0, 1, 10);
		this.numberOfLinesInJpqlQuerySpinner.addSelectionListener(buildNumberOfLinesInJpqlQuerySelectionListener());

		// Top description
		Label description = new Label(group, SWT.NONE);
		description.setText(JptJpaUiMessages.JpaPreferencesPage_jpqlEditor_description);

		// Uppercase radio button
		this.lowerCaseRadioButton = new Button(group, SWT.RADIO);
		this.lowerCaseRadioButton.setText(JptJpaUiMessages.JpaPreferencesPage_jpqlEditor_lowerCaseRadioButton);
		this.lowerCaseRadioButton.addSelectionListener(this.buildLowercaseSelectionListener());
		this.lowerCaseRadioButton.setSelection(this.lowercase);

		// Uppercase radio button
		this.upperCaseRadioButton = new Button(group, SWT.RADIO);
		this.upperCaseRadioButton.setText(JptJpaUiMessages.JpaPreferencesPage_jpqlEditor_upperCaseRadioButton);
		this.upperCaseRadioButton.addSelectionListener(this.buildUppercaseSelectionListener());
		this.upperCaseRadioButton.setSelection( ! this.lowercase);

		new Label(group, SWT.NONE);

		// Match Case of First Letter check box
		this.matchFirstCharacterCaseCheckBox = new Button(group, SWT.CHECK);
		this.matchFirstCharacterCaseCheckBox.setText(JptJpaUiMessages.JpaPreferencesPage_jpqlEditor_matchFirstCharacterCaseRadioButton);
		this.matchFirstCharacterCaseCheckBox.addSelectionListener(buildMatchFirstCharacterCaseSelectionListener());
		this.matchFirstCharacterCaseCheckBox.setSelection(this.matchFirstCharacterCase);
	}

	private SelectionListener buildNumberOfLinesInJpqlQuerySelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Spinner spinner = (Spinner) e.widget;
				numberOfLinesInJpqlQueryTextArea = spinner.getSelection();
			}
		};
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
		return JpaPreferences.getJpqlIdentifierLowercaseDefault();
	}

	private boolean shouldMatchFirstCharacterCase() {
		return JpaPreferences.getJpqlIdentifierMatchFirstCharacterCase();
	}

	private boolean shouldMatchFirstCharacterCaseDefault() {
		return JpaPreferences.getJpqlIdentifierMatchFirstCharacterCaseDefault();
	}

	private boolean shouldUseLowercaseIdentifiers() {
		return JpaPreferences.getJpqlIdentifierLowercase();
	}

	private int getJpqlQueryTextAreaNumberOfLinesDefault() {
		return JpaPreferences.getJpqlQueryTextAreaNumberOfLinesDefault();
	}

	private int getJpqlQueryTextAreaNumberOfLines() {
		return JpaPreferences.getJpqlQueryTextAreaNumberOfLines();
	}

	private String getDefaultPackage() {
		if(this.defaultPackageText == null) {
			return null;
		}
		return (StringTools.isBlank(this.defaultPackageText.getText())) ?
						null :
						this.defaultPackageText.getText();
	}

	private void setDefaultPackage(String defaultPackage) {
		if(this.defaultPackageText == null) {
			return;
		}
		this.defaultPackageText.setText(defaultPackage);
	}

	private void migrateLegacyJpqlWorkspacePreferences() {

		IPreferenceStore preferences = JptJpaUiPlugin.instance().getPreferenceStore();

		String legacyCase = preferences.getString(JPQL_IDENTIFIER_CASE_PREF_KEY);
		if(StringTools.isNotBlank(legacyCase)) { // value is not empty when legacy preference exist
			JpaPreferences.setJpqlIdentifierLowercase(legacyCase.equals("lowercase"));
		}

		boolean legacyMatchFirstCharacterCase = preferences.getBoolean(JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY);
		if(legacyMatchFirstCharacterCase) { // value is true when legacy preference exist
			JpaPreferences.setJpqlIdentifierMatchFirstCharacterCase(legacyMatchFirstCharacterCase);
		}

		this.removeLegacyJpqlPreferences();
	}

	private void removeLegacyJpqlPreferences() {
		IPreferenceStore preferences = JptJpaUiPlugin.instance().getPreferenceStore();

		preferences.setToDefault(JPQL_IDENTIFIER_CASE_PREF_KEY);
		preferences.setToDefault(JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY);
	}

	private static final String JPQL_IDENTIFIER_CASE_PREF_KEY = JptJpaUiPlugin.instance().getPluginID() + ".jpqlIdentifier.case";
	private static final String JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY = JptJpaUiPlugin.instance().getPluginID() + ".jpqlIdentifier.matchFirstCharacterCase";

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