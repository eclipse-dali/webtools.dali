/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;

public class DynamicEntityFieldsWizardPage extends DataModelWizardPage {

	public DynamicEntityFieldsWizardPage(IDataModel model, String pageName) {
		super(model, pageName);
		this.setTitle(JptJpaEclipseLinkUiMessages.DYNAMIC_ENTITY_FIELDS_WIZARD_PAGE_TITLE);
		this.setDescription(JptJpaEclipseLinkUiMessages.DYNAMIC_ENTITY_FIELDS_WIZARD_PAGE_DESC);
	}

	/*
	 * @see org.eclipse.jem.util.ui.wizard.WTPWizardPage#getValidationPropertyNames()
	 */
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[]{IEntityDataModelProperties.ENTITY_FIELDS, 
							IEntityDataModelProperties.PK_FIELDS};
	}

	/* Create the main composite and add to it the entity properties
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage#createTopLevelComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 300;
		data.heightHint = 450;
		composite.setLayoutData(data);
		composite.pack();

		createEntityFieldsGroup(composite);

		IStatus projectStatus = validateProjectName();
		if (!projectStatus.isOK()) {
			setErrorMessage(projectStatus.getMessage());
			composite.setEnabled(false);
		}
		WorkbenchTools.setHelp(composite, EclipseLinkHelpContextIds.DYNAMIC_ENTITY_FIELD);
	    Dialog.applyDialogFont(parent);
		return composite;
	}

	protected void createEntityFieldsGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);		
		GridData groupGridData = new GridData(GridData.FILL_BOTH);
		groupGridData.horizontalSpan = 3;
		group.setLayoutData(groupGridData);
		group.setLayout(new GridLayout(3, false));
		group.setText(JptJpaEclipseLinkUiMessages.DYNAMIC_ENTITY_FIELDS_WIZARD_PAGE_ENTITY_FIELD_GROUP);
		new EclipseLinkDynamicEntityFieldTableSection(group, model, IEntityDataModelProperties.ENTITY_FIELDS);
	}

	/**
	 * @return the status of the project name correctness
	 */
	protected IStatus validateProjectName() {
		// check for empty
		if (model.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME) == null || model.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME).trim().length() == 0) {
			return JptJpaEclipseLinkUiPlugin.instance().buildErrorStatus(JptJpaEclipseLinkUiMessages.DYNAMIC_ENTITY_FIELDS_WIZARD_PAGE_NO_JPA_PROJECTS);
		}
		return Status.OK_STATUS;
	}
}
