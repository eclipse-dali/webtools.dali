/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation
 ***********************************************************************/
package org.eclipse.jpt.ui.internal.wizards.entity;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelEvent;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;

public class EntityFieldsWizardPage extends DataModelWizardPage {

	private Text entityNameText;
	private Text tableNameText;
	private Button tableNameCheckButton;	
	private Button fieldAccessButton;
	private Button propertyAccessButton;
	private boolean isNonEntity = true;
	private boolean isButtonsCreated = false;

	public EntityFieldsWizardPage(IDataModel model, String pageName) {
		super(model, pageName);
		setDescription(EntityWizardMsg.ADD_ENTITY_WIZARD_PAGE_DESCRIPTION);
		this.setTitle(EntityWizardMsg.ENTITY_PROPERTIES_TITLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jem.util.ui.wizard.WTPWizardPage#getValidationPropertyNames()
	 */
	protected String[] getValidationPropertyNames() {
		return new String[]{IEntityDataModelProperties.ENTITY_FIELDS};
	}
	
	/* Create the main composite and add to it the entity properties
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#createTopLevelComposite(org.eclipse.swt.widgets.Composite)
	 */
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 300;
		data.heightHint = 450;
		composite.setLayoutData(data);
		composite.pack();
				
		entityNameText = createNameGroup(composite, EntityWizardMsg.ENTITY_NAME, IEntityDataModelProperties.ENTITY_NAME);		
		Group group = createGroup(composite, EntityWizardMsg.TABLE_NAME);
		tableNameCheckButton= createCheckButton(group, EntityWizardMsg.USE_DEFAULT, IEntityDataModelProperties.TABLE_NAME_DEFAULT);		
		tableNameText = createNameGroup(group, EntityWizardMsg.TABLE_NAME, IEntityDataModelProperties.TABLE_NAME);
		tableNameText.setEnabled(!tableNameCheckButton.getSelection());
		isButtonsCreated = true;
		initNameGroup();
		EntityRowTableWizardSection initSection = new EntityRowTableWizardSection(composite, model, IEntityDataModelProperties.ENTITY_FIELDS);

		Group accessTypeGroup = createGroup(composite, EntityWizardMsg.ACCESS_TYPE);
		fieldAccessButton = createRadioButton(accessTypeGroup, EntityWizardMsg.FIELD_BASED, IEntityDataModelProperties.FIELD_ACCESS_TYPE);
		propertyAccessButton = createRadioButton(accessTypeGroup, EntityWizardMsg.PROPERTY_BASED, IEntityDataModelProperties.PROPERTY_ACCESS_TYPE);
		
		
		IStatus projectStatus = validateProjectName();
		if (!projectStatus.isOK()) {
			setErrorMessage(projectStatus.getMessage());
			composite.setEnabled(false);
		}
	    Dialog.applyDialogFont(parent);
		return composite;
	}

	/**
	 * @return the status of the project name correctness
	 */
	protected IStatus validateProjectName() {
		// check for empty
		if (model.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME) == null || model.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME).trim().length() == 0) {
			return WTPCommonPlugin.createErrorStatus(EntityWizardMsg.NO_JPA_PROJECTS);
		}
		return WTPCommonPlugin.OK_STATUS;
	}

	/**
	 * Create named group
	 * @param parent the main composite
	 * @param label the name of the group
	 * @param property the related property to which this group will be synchronized
	 * @return the created group
	 */
	protected Text createNameGroup(Composite parent, String label, String property) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	
		Label displayNameLabel = new Label(composite, SWT.LEFT);
		displayNameLabel.setText(label);
		displayNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		Text nameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		synchHelper.synchText(nameText, property, /*dependentControls*/null);		
		return nameText;
	}
	
	/**
	 * Create group
	 * @param parent the main composite
	 * @param text the name of the group	 
	 * @return the created group
	 */	
	private Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.NONE);		
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		group.setLayoutData(groupGridData);
		group.setLayout(new GridLayout(3, false));
		group.setText(text);		
		return group;
	}
	
	/**
	 * Create check button
	 * @param parent the main composite - inheritance group
	 * @param text the label of the button
	 * @param property the related property to which this button will be synchronized
	 * @return the created button
	 */	
	private Button createCheckButton(Composite parent, String text, String property) {
		final Button button = new Button(parent, SWT.CHECK);
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		button.setLayoutData(groupGridData);
		button.setText(text);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean isChecked = button.getSelection();
				if (tableNameText != null) {
					tableNameText.setEnabled(!isChecked);
				}
			}
		});		
		synchHelper.synchCheckbox(button, property, /*dependentControls*/ null);		
		return button;
	}		
	
	/**
	 * Create radio button
	 * @param parent the main composite - inheritance group
	 * @param text the label of the button
	 * @param property the related property to which this button will be synchronized
	 * @return the created button
	 */
	private Button createRadioButton(Composite parent, String text, String property) {
		Button button = new Button(parent, SWT.RADIO);		
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		button.setLayoutData(groupGridData);
		button.setText(text);
		synchHelper.synchRadio(button, property, /*dependentControls*/ null);		
		return button;
	}

	/*
	 * If a property changes that we want to validate, force validation on this page.
	 * 
	 * @see org.eclipse.wst.common.frameworks.internal.operation.WTPOperationDataModelListener#propertyChanged(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void propertyChanged(DataModelEvent event) {
		String propertyName = event.getPropertyName();
		if (IEntityDataModelProperties.MAPPED_AS_SUPERCLASS.equals(propertyName)) {
			initNameGroup();
		}		
		super.propertyChanged(event);
	}
	
	/**
	 * The methods is for the internal use only. It will set the entity and table name
	 * group to be disabled if the created artifact is not entity
	 */
	private void initNameGroup() {		
		isNonEntity = model.getBooleanProperty(IEntityDataModelProperties.MAPPED_AS_SUPERCLASS);		
		if (isButtonsCreated) {
			entityNameText.setEnabled(!isNonEntity);
			tableNameCheckButton.setEnabled(!isNonEntity);
			tableNameText.setEnabled(!tableNameCheckButton.getSelection());
		}		
	}	
}


