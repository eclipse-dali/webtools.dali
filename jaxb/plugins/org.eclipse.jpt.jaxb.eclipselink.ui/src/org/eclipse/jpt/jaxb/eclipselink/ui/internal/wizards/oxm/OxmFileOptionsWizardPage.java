/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.wizards.oxm;

import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.CONTAINER_PATH;
import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.FILE_NAME;
import static org.eclipse.jpt.jaxb.eclipselink.core.internal.operations.OxmFileCreationDataModelProperties.PACKAGE_NAME;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;

public class OxmFileOptionsWizardPage
		extends DataModelWizardPage {
	
	private Label packageNameLabel;
	
	private Text packageNameText;
	
	
	public OxmFileOptionsWizardPage(
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
			PACKAGE_NAME
		};
	}
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		
		this.packageNameLabel = new Label(composite, SWT.NONE);
		this.packageNameLabel.setText(JptJaxbEclipseLinkUiMessages.OXM_FILE_WIZARD__FILE_OPTIONS_PAGE__PACKAGE_NAME_LABEL);
		data = new GridData();
		this.packageNameLabel.setLayoutData(data);
		
		this.packageNameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		this.packageNameText.setLayoutData(data);
		this.synchHelper.synchText(this.packageNameText, PACKAGE_NAME, null);
		
//		new Label(composite, SWT.NONE);
		
		WorkbenchTools.setHelp(composite, getInfopopID());
	    Dialog.applyDialogFont(parent);
		return composite;
	}
}

