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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class JpaEntityGenPreferencePage extends PropertyPage {

	private EntityGenComposite entityGenComposite;
	
	// ********** constructors **********
	
	public JpaEntityGenPreferencePage() {
		super();
		this.setDescription(JptJpaUiMessages.JPA_ENTITY_GEN_PREFERENCE_PAGE_DESCRIPTION); 
	}
	
	// ********** overrides **********

	@Override
	public boolean performOk() {
		super.performOk();

		this.updateProjectEntityGenPreferences();
		
		return true;
	}

	@Override
	protected Control createContents(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		
		this.entityGenComposite = new EntityGenComposite(composite);
		
		return composite;
	}
	
	// ********** preferences **********
	
	private void updateProjectEntityGenPreferences() {
		String pkgName = this.getDefaultPackage();
		JpaPreferences.setEntityGenDefaultPackageName(this.getProject(), (StringTools.isNotBlank(pkgName) ? pkgName : null));
	}

	// ********** internal methods **********

	private IProject getProject() {
        IAdaptable adaptable= this.getElement();
		return adaptable == null ? null : (IProject)adaptable.getAdapter(IProject.class);
	}

	// ********** getters *********

	private String getDefaultPackage() {
		return this.entityGenComposite.getDefaultPackage();
	}
	
	// ********** queries *********

	private String getDefaultPackagePreference() {
		return JpaPreferences.getEntityGenDefaultPackageName(this.getProject());
	}
	
	// ********** EntityGenComposite **********

	class EntityGenComposite {
		private final Text defaultPackageText;

		// ********** constructor **********

		private EntityGenComposite(Composite parent) {
			super();
			// Entity Gen group box
			Group group = new Group(parent, SWT.NONE);
			group.setText(JptJpaUiMessages.JPA_ENTITY_GEN_PREFERENCE_PAGE_GENERAL_GROUP_TITLE);
			group.setLayout(new GridLayout(2, false));
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// default package
			this.buildLabel(group, 1, JptJpaUiMessages.JPA_ENTITY_GEN_PREFERENCE_PAGE_DEFAULT_PACKAGE_LABEL);
			this.defaultPackageText = this.buildText(group, 1);
			
			this.initializeFromPreferences();
		}

		// ********** UI controls **********

		private void initializeFromPreferences() {
			// source
			this.defaultPackageText.setText(getDefaultPackagePreference());
		}

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

		// ********** getters *********
		
		private String getDefaultPackage() {
			return this.defaultPackageText.getText();
		}
	}
}