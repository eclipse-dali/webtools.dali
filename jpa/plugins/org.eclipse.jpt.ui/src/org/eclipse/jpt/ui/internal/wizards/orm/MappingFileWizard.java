/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards.orm;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard;

public class MappingFileWizard extends DataModelWizard 
	implements INewWizard 
{
	private MappingFileWizardPage page;
	
	
	public MappingFileWizard() {
		this(null);
	}
	
	public MappingFileWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(JptUiMessages.MappingFileWizard_title);
		setDefaultPageImageDescriptor(JptUiPlugin.getImageDescriptor(JptUiIcons.JPA_FILE_WIZ_BANNER));
	}
	
	
	@Override
	protected void doAddPages() {
		super.doAddPages();
		page = new MappingFileWizardPage(getDataModel(), "Page_1");
		addPage(page);
	}
	
	@Override
	protected IDataModelProvider getDefaultProvider() {
		return new OrmFileCreationDataModelProvider();
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}
	
	protected void postPerformFinish() throws InvocationTargetException {      
        try {
        	String projectName = (String) getDataModel().getProperty(OrmFileCreationDataModelProperties.PROJECT_NAME);
        	IProject project = ProjectUtilities.getProject(projectName);
        	String sourceFolder = getDataModel().getStringProperty(OrmFileCreationDataModelProperties.SOURCE_FOLDER);
        	String filePath = getDataModel().getStringProperty(OrmFileCreationDataModelProperties.FILE_PATH);
        	
        	IFile file = project.getWorkspace().getRoot().getFile(new Path(sourceFolder).append(filePath));
            openEditor(file);
        } 
        catch (Exception cantOpen) {
        	throw new InvocationTargetException(cantOpen);
        } 
    }
	
	private void openEditor(final IFile file) {
        if (file != null) {
            getShell().getDisplay().asyncExec(new Runnable() {
                public void run() {
                    try {
                        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                        IDE.openEditor(page, file, true);
                    }
                    catch (PartInitException e) {
                    	JptUiPlugin.log(e);
                    }
                }
            });
        }
    }
}
