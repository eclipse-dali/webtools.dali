/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.model.DynamicEntityDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard;

public class EclipseLinkDynamicEntityWizard extends DataModelWizard implements INewWizard{

	private String initialProjectName;
	private DynamicEntityClassWizardPage page1;
	private DynamicEntityFieldsWizardPage page2;

	public EclipseLinkDynamicEntityWizard(IDataModel model) {
		super(model);
        setWindowTitle(JptJpaEclipseLinkUiMessages.ECLIPSELINK_DYNAMIC_ENTITY_WIZARD_TITLE);
        setDefaultPageImageDescriptor(JptJpaUiImages.ENTITY_BANNER);
	}

	/**
	 * Empty constructor
	 */
	public EclipseLinkDynamicEntityWizard(){
    	this(null);
    }

	/* Adds the two pages of the new dynamic entity wizard 
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard#doAddPages()
	 */
	@Override
	protected void doAddPages() {
		page1 = new DynamicEntityClassWizardPage(getDataModel(), "pageOne");
		page1.setProjectName(this.initialProjectName);
		addPage(page1);
		page2 = new DynamicEntityFieldsWizardPage(getDataModel(), "pageTwo");
		addPage(page2);
	}

	/*
	 * Wizard cannot be finished on the first page 
	 * Wizard can be finished on the second page since it's reasonable 
	 * for users to create a dynamic entity without any field(s) and then
	 * add virtual attributes manually or through JPA structure view
	 */
	@Override
	public boolean canFinish() {
		return this.getContainer().getCurrentPage() == page1 ? false : page2.getErrorMessage() == null;
	}

	/* Return the default data model provider (DynamicEntityDataModelProvider in our case)
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard#getDefaultProvider()
	 */
	@Override
	protected IDataModelProvider getDefaultProvider() {
		return new DynamicEntityDataModelProvider();
	}

	/* 
	 * Override the parent method in order to open the generated dynamic entity in Java editor
	 * @see org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard#postPerformFinish()
	 */
	@Override
    protected void postPerformFinish() throws InvocationTargetException {
        try {
        	IProject project = (IProject) this.getDataModel().getProperty(INewJavaClassDataModelProperties.PROJECT);
        	JpaProject jpaProject = (JpaProject)(project).getAdapter(JpaProject.class);
        	if (jpaProject == null) {
        		return;
        	}
        	String xmlRuntimePath = this.getDataModel().getStringProperty(IEntityDataModelProperties.XML_NAME).trim();
        	JptXmlResource xmlResource = jpaProject.getMappingFileXmlResource(new Path(xmlRuntimePath));
            openEditor(xmlResource);
        } catch (Exception cantOpen) {
        	JptJpaEclipseLinkUiPlugin.instance().logError(cantOpen);
        }
    }

	/*
	 * Open the EclipseLink mapping file where the dynamic entity is created
	 */
	protected void openEditor(final JptXmlResource xmlResource) {
		SWTUtil.asyncExec(new Runnable() {
			public void run() {
				IFile file = xmlResource.getFile();
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void init(IWorkbench workbench, IStructuredSelection iSelection) {
    	this.initialProjectName = getProjectName(iSelection);
		this.getDataModel();
	}

	protected IProject extractProject(IStructuredSelection iSelection) {
		Object selectedObj = iSelection.getFirstElement();
		if (selectedObj == null) {
			return null;
		}

		IResource resource = PlatformTools.getAdapter(selectedObj, IResource.class);
		if (resource != null) {
			return resource.getProject();
		}
		IJavaElement javaElement = PlatformTools.getAdapter(selectedObj, IJavaElement.class);
		if (javaElement != null) {
			return javaElement.getJavaProject().getProject();
		}
		JpaContextModel node = PlatformTools.getAdapter(selectedObj, JpaContextModel.class);
		if (node != null) {
			return node.getJpaProject().getProject();
		}
		return null;
	}

    protected String getProjectName(IStructuredSelection iSelection) {
    	IProject iProject = this.extractProject(iSelection);
    	return iProject == null? 
    			JptJpaEclipseLinkUiMessages.DYNAMIC_ENTITY_CLASS_WIZARD_PAGE_EMPTY_STRING : 
    			iProject.getName();
	}
}
