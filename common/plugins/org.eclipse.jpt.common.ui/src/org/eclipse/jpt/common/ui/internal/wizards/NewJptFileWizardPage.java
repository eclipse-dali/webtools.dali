/*******************************************************************************
 *  Copyright (c) 2010, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.ui.internal.wizards;

import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class NewJptFileWizardPage
		extends WizardNewFileCreationPage {
	
	protected IDataModel dataModel;
	
	
	public NewJptFileWizardPage(
			String pageName, IStructuredSelection selection, IDataModel dataModel,
			String title, String description) {
		
		super(pageName, selection);
		init(dataModel);
		setTitle(title);
		setDescription(description);
	}
	
	
	protected void init(IDataModel dataModel) {
		this.dataModel = dataModel;
		IPath containerPath = (IPath) this.dataModel.getProperty(CONTAINER_PATH);
		if (containerPath != null) {
			setContainerFullPath(containerPath);
		}
		String fileName = (String) this.dataModel.getProperty(FILE_NAME);
		if (fileName != null) {
			setFileName(fileName);
		}
	}
	
	@Override
	protected boolean validatePage() {
		this.dataModel.setProperty(CONTAINER_PATH, getContainerFullPath());
		String fileName = getFileName();
		if( fileName != null && !fileName.toLowerCase().endsWith(".xml")) { //$NON-NLS-1$
			fileName = fileName + ".xml"; //$NON-NLS-1$
		}
		this.dataModel.setProperty(FILE_NAME, fileName);
		
		boolean valid = super.validatePage();
		if (! valid) {
			return valid;
		}
		
		IStatus validationStatus = this.dataModel.validateProperty(CONTAINER_PATH);
		if (validationStatus.isOK()) {
			validationStatus = this.dataModel.validateProperty(FILE_NAME);
		}
		if (validationStatus.isOK()) {
			setErrorMessage(null);
			return true;
		}
		else if (validationStatus.getSeverity() == IStatus.WARNING) {
			setErrorMessage(null);
			setMessage(validationStatus.getMessage(), IStatus.WARNING);
			return true;
		}
		else {
			setErrorMessage(validationStatus.getMessage());
			return false;
		}
	}
}
