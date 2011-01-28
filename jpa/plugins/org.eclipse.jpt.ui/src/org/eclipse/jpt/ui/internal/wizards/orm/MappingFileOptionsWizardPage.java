/*******************************************************************************
 *  Copyright (c) 2008, 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards.orm;

import static org.eclipse.jpt.core.internal.operations.JptFileCreationDataModelProperties.*;
import static org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;

public class MappingFileOptionsWizardPage
		extends DataModelWizardPage {
	
	private Label accessLabel;
	
	private Combo accessCombo;
	
	private Button addToPersistenceUnitButton;
	
	private Label persistenceUnitLabel;
	
	private Combo persistenceUnitCombo;
	
	
	public MappingFileOptionsWizardPage(
			String pageName, IDataModel dataModel,
			String title, String description) {
		
		super(dataModel, pageName);
		setTitle(title);
		setDescription(description);
		setPageComplete(false);
	}
	
	
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[] {
			CONTAINER_PATH,
			FILE_NAME,
			DEFAULT_ACCESS,
			ADD_TO_PERSISTENCE_UNIT,
			PERSISTENCE_UNIT
		};
	}
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		
		this.accessLabel = new Label(composite, SWT.NONE);
		this.accessLabel.setText(JptUiMessages.MappingFileWizardPage_accessLabel);
		data = new GridData();
		this.accessLabel.setLayoutData(data);
		
		this.accessCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		this.accessCombo.setLayoutData(data);
		this.synchHelper.synchCombo(this.accessCombo, DEFAULT_ACCESS, null);
		new Label(composite, SWT.NONE);
		
		this.addToPersistenceUnitButton = new Button(composite, SWT.CHECK | SWT.BEGINNING);
		this.addToPersistenceUnitButton.setText(JptUiMessages.MappingFileWizardPage_addToPersistenceUnitButton);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		data.verticalIndent = 10;
		this.addToPersistenceUnitButton.setLayoutData(data);
		this.synchHelper.synchCheckbox(this.addToPersistenceUnitButton, ADD_TO_PERSISTENCE_UNIT, null);
		
		this.persistenceUnitLabel = new Label(composite, SWT.NONE);
		this.persistenceUnitLabel.setText(JptUiMessages.MappingFileWizardPage_persistenceUnitLabel);
		data = new GridData();
		data.horizontalIndent = 10;
		this.persistenceUnitLabel.setLayoutData(data);
		this.persistenceUnitLabel.setEnabled(false);
		this.addToPersistenceUnitButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				persistenceUnitLabel.setEnabled(addToPersistenceUnitButton.getSelection());
			}
			public void widgetDefaultSelected(SelectionEvent e) {/*not called*/}
		});
		
		this.persistenceUnitCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		this.persistenceUnitCombo.setLayoutData(data);
		this.synchHelper.synchCombo(this.persistenceUnitCombo, PERSISTENCE_UNIT, null);
		
		new Label(composite, SWT.NONE);
		
//		classText.setFocus();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, getInfopopID());
	    Dialog.applyDialogFont(parent);
		return composite;
	}
}
