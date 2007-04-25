/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.prefs;

import java.io.IOException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.preferences.UserLibraryPreferencePage;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jpt.core.internal.JpaCorePlugin;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.ui.internal.JpaUiMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class JpaPreferencePage extends PreferencePage 
	implements IWorkbenchPreferencePage
{
	public static final String ID = "org.eclipse.jpt.ui.jpaPreferencePage";
	
	
	private IPersistentPreferenceStore preferences;
	
	private IPreferenceStore userLibPreferences;
	
	private Label jpaLibLabel;
		
	private Combo jpaLibCombo;
	
	private Link userLibsLink;
		
	
	public JpaPreferencePage() {
		super();
		preferences = 
			new ScopedPreferenceStore(
				new InstanceScope(),
				JpaCorePlugin.getPlugin().getBundle().getSymbolicName());
		userLibPreferences =
			new ScopedPreferenceStore(
				new InstanceScope(),
				JavaCore.getPlugin().getBundle().getSymbolicName());
	}
	
	public void init(IWorkbench workbench) {}
	
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		
		jpaLibLabel = createLabel(container, 1, JpaUiMessages.JpaPreferencePage_defaultJpaLib);
			
		jpaLibCombo = createCombo(container, true);
		
		userLibsLink =  new Link(container, SWT.NONE);
		GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
		data.horizontalSpan = 2;
		userLibsLink.setLayoutData(data);
		userLibsLink.setText(JpaUiMessages.JpaPreferencePage_userLibsLink);
		userLibsLink.addSelectionListener(
			new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					((IWorkbenchPreferenceContainer) getContainer())
						.openPage(UserLibraryPreferencePage.ID, null);
				}
			}
		);
			
		performDefaults();
		return container;
	}
	
	private Label createLabel(Composite container, int span, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		label.setLayoutData(gd);
		return label;
	}
	
	private Combo createCombo(Composite container, boolean fillHorizontal) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		if (fillHorizontal) {
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		else {
			combo.setLayoutData(new GridData());
		}
		return combo;
	}
	
	private void fillJpaLibs() {
		int index = jpaLibCombo.getSelectionIndex();
		String selectedJpaLib = null;
		if (index >= 0) {
			selectedJpaLib = jpaLibCombo.getItem(jpaLibCombo.getSelectionIndex());
		}
		
		jpaLibCombo.clearSelection();
		jpaLibCombo.setItems(JavaCore.getUserLibraryNames());
		
		if (selectedJpaLib != null) {
			int newIndex = CollectionTools.indexOf(jpaLibCombo.getItems(), selectedJpaLib);
			if (newIndex >= 0) {
				jpaLibCombo.select(newIndex);
			}
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			fillJpaLibs();
		}
	}
	
	public void performDefaults() {
		fillJpaLibs();
		String defaultLib = preferences.getString(JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB);
		int index = -1;
		if (! StringTools.stringIsEmpty(defaultLib)) {
			index = CollectionTools.indexOf(jpaLibCombo.getItems(), defaultLib);
		}
		if (index >= 0) {
			jpaLibCombo.select(index);
		}
		
		super.performDefaults();
	}

	public boolean performOk() {
		int index = jpaLibCombo.getSelectionIndex();
		String defaultLib = (index >= 0) ? jpaLibCombo.getItem(index) : null;
		if (! StringTools.stringIsEmpty(defaultLib)) {
			preferences.setValue(JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB, defaultLib);
		}
		try {
			preferences.save();
		}
		catch (IOException ioe) {
			JpaCorePlugin.log(ioe);
		}
		return true;
	}
	
	public void dispose() {
		// null pointer check - bug 168337
		if (jpaLibLabel != null) jpaLibLabel.dispose();
		if (jpaLibCombo != null) jpaLibCombo.dispose();
		super.dispose();
	}
	
	
		
//		private boolean libContainsJpaClasses() {
//			return true;
//			String jarLocation = getStringValue();
//			String errorMessage = JpaUiMessages.JpaPreferencePage_invalidJpaLib;
//			boolean hasError = false;
//			
//			try {
//				JarFile jarFile = new JarFile(jarLocation);
//				hasError = jarFile.getEntry("javax/persistence/EntityManager.class") == null;
//			}
//			catch (IOException ioe) {
//				hasError = true;
//			}
//			
//			if (hasError) {
//				showErrorMessage(errorMessage);
//			}
//			else {
//				clearErrorMessage();
//			}
//			
//			return ! hasError;
//		}
//	}
}
