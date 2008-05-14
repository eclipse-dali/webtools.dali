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

import static org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties.*;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.wizards.entity.data.model.EntityDataModelProvider;
import org.eclipse.jst.j2ee.internal.plugin.J2EEEditorUtility;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard;

public class EntityWizard extends DataModelWizard implements INewWizard {

	
	private static final String PAGE_ONE = "pageOne"; //$NON-NLS-1$
    private static final String PAGE_TWO = "pageTwo"; //$NON-NLS-1$
    
    /**
     * Constructs the Entity wizard
     * @param model the data model
     */
    public EntityWizard(IDataModel model) {
		super(model);
        setWindowTitle(EntityWizardMsg.ENTITY_WIZARD_TITLE);
        setDefaultPageImageDescriptor(JptUiPlugin.getImageDescriptor(JptUiIcons.ENTITY_WIZ_BANNER));        
	}    
 
	/**
	 * Empty constructor
	 */
	public EntityWizard(){
    	this(null);
    }
	
	/* Adds the two pages of the entity wizard 
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard#doAddPages()
	 */
	@Override
	protected void doAddPages() {
		EntityClassWizardPage page1 = new EntityClassWizardPage(
		        getDataModel(),
		        PAGE_ONE,
		        EntityWizardMsg.ADD_ENTITY_WIZARD_PAGE_DESCRIPTION,
		        EntityWizardMsg.ADD_ENTITY_WIZARD_PAGE_TITLE, 
				J2EEProjectUtilities.EJB);
		addPage(page1);
		EntityFieldsWizardPage page2 = new EntityFieldsWizardPage(getDataModel(), PAGE_TWO);
		addPage(page2);
	}

	/* Return the default data model provider (EntityDataModelProvider in our case)
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard#getDefaultProvider()
	 */
	@Override
	protected IDataModelProvider getDefaultProvider() {
		return new EntityDataModelProvider();
	}

	/* Check whether the mandatory information is set and wizard could finish
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		 return getDataModel().isValid();
	}
	
	/* 
	 * Override the parent method in order to open the generated entity class in java editor
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard#postPerformFinish()
	 */
	@Override
    protected void postPerformFinish() throws InvocationTargetException {      
        try {
            String className = getDataModel().getStringProperty(QUALIFIED_CLASS_NAME);
            IProject p = (IProject) getDataModel().getProperty(PROJECT);
            IJavaProject javaProject = J2EEEditorUtility.getJavaProject(p);
            IFile file = (IFile) javaProject.findType(className).getResource();
            openEditor(file);
        } catch (Exception cantOpen) {
        	JptUiPlugin.log(cantOpen);
        } 
    }
	
    /**
     * This method is intended for internal use only. It will open the file, sent as parameter
     * in its own java editor
     * @param file who should be opened
     */
    private void openEditor(final IFile file) {
    	if (getDataModel().getBooleanProperty(OPEN_IN_EDITOR)) {
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
    
    /* Implement the abstract method from IWorkbenchWizard
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
		getDataModel();
	}
    
}
