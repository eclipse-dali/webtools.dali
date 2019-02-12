/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.prefs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.SWT;
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
 * This is the root of the Java Persistence preferences hierarchy in the IDE
 * preferences dialog.
 * <p>
 * Structure:
 * <p>
 * Java Persistence<br>
 *
 * @version 2.2
 * @since 2.2
 */
public class JptPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	protected Control createContents(Composite parent) {
        
		Composite container= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.marginHeight= convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth= 0;
		layout.verticalSpacing= convertVerticalDLUsToPixels(10);
		layout.horizontalSpacing= convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		container.setLayout(layout);

		layout = new GridLayout();
		layout.numColumns= 2;

		Group dontAskGroup = new Group(container, SWT.NONE);
		dontAskGroup.setLayout(layout);
		dontAskGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dontAskGroup.setText(JptJpaUiMessages.JPT_PREFERENCES_PAGE_DO_NOT_SHOW_DIALOGS);

		Label label = new Label(dontAskGroup, SWT.WRAP);
		label.setText(JptJpaUiMessages.JPT_PREFERENCES_PAGE_DO_NOT_SHOW_TEXT);
		GridData data= new GridData(GridData.FILL, GridData.CENTER, true, false);
		data.widthHint= convertVerticalDLUsToPixels(50);
		label.setLayoutData(data);
		
		Button button = new Button(dontAskGroup, SWT.PUSH);
		button.setText(JptJpaUiMessages.JPT_PREFERENCES_PAGE_CLEAR_BUTTON_TEXT);
		button.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
		button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				unhideAllDialogs();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				unhideAllDialogs();
			}
		});
		
		return container;
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(JptJpaUiPlugin.instance().getPreferenceStore());
	}
	
	private final void unhideAllDialogs() {
		OptionalMessageDialog.clearAllRememberedStates();
	}
}
