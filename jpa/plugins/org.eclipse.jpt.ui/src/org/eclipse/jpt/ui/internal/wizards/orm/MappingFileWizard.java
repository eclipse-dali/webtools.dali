/*******************************************************************************
 *  Copyright (c) 2008, 2010  Oracle. 
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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
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
		page = buildMappingFileWizardPage();
		addPage(page);
	}
	
	protected MappingFileWizardPage buildMappingFileWizardPage() {
		return new MappingFileWizardPage(getDataModel(), "Page_1");
	}
	
	@Override
	protected IDataModelProvider getDefaultProvider() {
		return new OrmFileCreationDataModelProvider();
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// check for project, source folder, persistence unit?
		if (selection == null || selection.isEmpty()) {
			return;
		}
		
		Object firstSelection = selection.getFirstElement();
		
		PersistenceUnit pUnit = extractPersistenceUnit(firstSelection);
		IFolder sourceFolder = extractSourceFolder(pUnit, firstSelection);
		IProject project = extractProject(pUnit, sourceFolder, firstSelection);
		
		if (project != null) {
			getDataModel().setStringProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, project.getName());
		}
		if (sourceFolder != null) {
			getDataModel().setStringProperty(OrmFileCreationDataModelProperties.SOURCE_FOLDER, sourceFolder.getFullPath().toPortableString());
		}
		if (pUnit != null) {
			getDataModel().setBooleanProperty(OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT, true);
			getDataModel().setStringProperty(OrmFileCreationDataModelProperties.PERSISTENCE_UNIT, pUnit.getName());
		}
	}
	
	private PersistenceUnit extractPersistenceUnit(Object selection) {
		if (selection instanceof PersistenceUnit) {
			return (PersistenceUnit) selection;
		}
		PersistenceUnit pUnit = null;
		if (selection instanceof JpaContextNode) {
			try {
				pUnit = ((JpaContextNode) selection).getPersistenceUnit();
			}
			catch (Exception e) { /* do nothing, just continue */ }
		}
		if (pUnit == null && selection instanceof IAdaptable) {
			pUnit = (PersistenceUnit) ((IAdaptable) selection).getAdapter(PersistenceUnit.class);
			
		}
		return pUnit;
	}
	
	private IFolder extractSourceFolder(PersistenceUnit pUnit, Object selection) {
		IJavaProject javaProject = null;
		IFolder srcFolder = null;
		if (pUnit != null) {
			javaProject = pUnit.getJpaProject().getJavaProject();
			srcFolder = findSourceFolder(javaProject, pUnit.getResource());
			if (srcFolder != null) {
				return srcFolder;
			}
			
		}
		if (selection instanceof IResource) {
			javaProject = JavaCore.create(((IResource) selection).getProject());
			if (javaProject.exists()) {
				srcFolder = findSourceFolder(javaProject, (IResource) selection);
				if (srcFolder != null) {
					return srcFolder;
				}
			}
		}
		
		if (selection instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) selection).getAdapter(IResource.class);
			if (resource != null) {
				javaProject = JavaCore.create((resource).getProject());
				if (javaProject.exists()) {
					srcFolder = findSourceFolder(javaProject, resource);	
					if (srcFolder != null) {
						return srcFolder;
					}
				}
			}
		}
		return null;
	}
	
	private IFolder findSourceFolder(IJavaProject javaProject, IResource resource) {
		if (JptCorePlugin.getJpaProject(javaProject.getProject()) == null) {
			// not even a jpa project
			return null;
		}
		while (resource != null && ! (resource instanceof IFolder)) {
			resource = resource.getParent();
		}
		if (resource == null) {
			return null;
		}
		IFolder folder = (IFolder) resource;
		try {
			IPackageFragmentRoot packageFragmentRoot = null;
			while (packageFragmentRoot == null && folder != null) {
				packageFragmentRoot = javaProject.findPackageFragmentRoot(folder.getFullPath());
				if (packageFragmentRoot == null) {
					IPackageFragment packageFragment = javaProject.findPackageFragment(folder.getFullPath());
					if (packageFragment != null) {
						packageFragmentRoot = (IPackageFragmentRoot) packageFragment.getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
					}
				}
				if (packageFragmentRoot == null) {
					try {
						folder = (IFolder) folder.getParent();
					}
					catch (ClassCastException cce) {
						folder = null;
					}
				}
			}
			if (packageFragmentRoot == null) {
				return null;
			}
			if (JDTTools.packageFragmentRootIsSourceFolder(packageFragmentRoot)) {
				return (IFolder) packageFragmentRoot.getResource();
			}
		}
		catch (JavaModelException jme) { /* do nothing, return null */ }
		return null;
	}
	
	private IProject extractProject(PersistenceUnit pUnit, IFolder sourceFolder, Object selection) {
		if (pUnit != null) {
			return pUnit.getJpaProject().getProject();
		}
		if (sourceFolder != null) {
			return sourceFolder.getProject();
		}
		
		IProject project = null;
		if (selection instanceof IResource) {
			project = ((IResource) selection).getProject();
		}
		if (project == null && selection instanceof IJavaElement) {
			project = ((IJavaElement) selection).getJavaProject().getProject();
		}
		if (project == null && selection instanceof JpaContextNode) {
			project = ((JpaContextNode) selection).getJpaProject().getProject();
		}
		if (project == null && selection instanceof IAdaptable) {
			project = (IProject) ((IAdaptable) selection).getAdapter(IProject.class);
			if (project == null) {
				IResource resource = (IResource) ((IAdaptable) selection).getAdapter(IResource.class);
				if (resource != null) {
					project = resource.getProject();
				}
			}
			if (project == null) {
				IJavaElement javaElement = (IJavaElement) ((IAdaptable) selection).getAdapter(IJavaElement.class);
				if (javaElement != null) {
					project = javaElement.getJavaProject().getProject();
				}
			}
		}
		
		if (project != null && JptCorePlugin.getJpaProject(project) != null) {
			return project;
		}
		
		return null;
	}
	
	@Override
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
