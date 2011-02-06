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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
 *  |- Errors/Warnings
 *
 * @version 2.2
 * @since 2.2
 */
public class JpaPreferencesPage extends PreferencePage
                                implements IWorkbenchPreferencePage {

	/**
	 * Creates a new <code>JpaPreferencesPage</code>.
	 */
	public JpaPreferencesPage() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		parent = new Composite(parent, SWT.NONE);
		parent.setLayout(new GridLayout(1, false));

		// Message label
		Label label = new Label(parent, SWT.NONE);
		label.setText(JptUiMessages.JpaPreferencesPage_Description);
		label.setData(new GridData(GridData.BEGINNING, GridData.BEGINNING, true, true));

		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(JptJpaUiPlugin.instance().getPreferenceStore());
	}
}