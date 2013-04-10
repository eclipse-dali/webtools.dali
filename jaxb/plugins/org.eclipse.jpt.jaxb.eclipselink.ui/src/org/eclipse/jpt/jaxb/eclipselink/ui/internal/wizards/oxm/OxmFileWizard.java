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
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.core.internal.utility.PathTools;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.wizards.NewJptFileWizardPage;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.operations.OxmFileCreationDataModelProperties;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.operations.OxmFileCreationDataModelProvider;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiImages;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiMessages;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.plugin.JptJaxbEclipseLinkUiPlugin;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.IDataModelPausibleOperation;
import org.eclipse.wst.common.frameworks.internal.dialog.ui.MessageDialog;
import org.eclipse.wst.common.frameworks.internal.ui.ErrorDialog;
import org.eclipse.wst.common.frameworks.internal.ui.WTPCommonUIResourceHandler;

public class OxmFileWizard
		extends Wizard
		implements INewWizard  {
	
	protected IDataModel config;
	
	protected IStructuredSelection initialSelection;
	
	protected IStructuredSelection mungedSelection;
	
	private IWizardPage firstPage;
	
	private IWizardPage secondPage;
	
	
	public OxmFileWizard() {
		super();
		this.config = DataModelFactory.createDataModel(new OxmFileCreationDataModelProvider());
		setWindowTitle(JptJaxbEclipseLinkUiMessages.OXM_FILE_WIZARD__TITLE);
		setDefaultPageImageDescriptor(JptJaxbEclipseLinkUiImages.OXM_FILE_BANNER);
	}
	
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.initialSelection = selection;
		this.mungedSelection = selection;
		
		if (selection == null || selection.isEmpty()) {	
			return;
		}
		
		Object firstSelection = selection.getFirstElement();
		
		IPackageFragment pkg = extractPackage(firstSelection);
		IContainer container = extractContainer(pkg, firstSelection);
		
		while (container != null && ! container.exists()) {
			container = container.getParent();  // default container sometimes is a non-existent folder
		}
		if (container != null) {
			this.mungedSelection = new StructuredSelection(container);
		}
		
		if (pkg != null) {
			this.config.setProperty(OxmFileCreationDataModelProperties.PACKAGE_NAME, pkg.getElementName());
		}
	}
	
	private IPackageFragment extractPackage(Object selection) {
		return PlatformTools.getAdapter(selection, IPackageFragment.class);
	}
	
	private JaxbProject getJaxbProject(Object selection) {
		IProject project = getProject(selection);
		return (project == null) ? null : getJaxbProject(project);
	}
	
	private IProject getProject(Object selection) {
		return PlatformTools.getAdapter(selection, IProject.class);
	}
	
	private JaxbProject getJaxbProject(IProject project) {
		return (JaxbProject) project.getAdapter(JaxbProject.class);
	}
	
	private IContainer extractContainer(IPackageFragment pkg, Object selection) {
		if (pkg != null) {
			IResource resource = pkg.getResource();
			if (resource instanceof IContainer) {
				return (IContainer) resource;
			}
		}
		IResource resource = PlatformTools.getAdapter(selection, IResource.class);
		if (resource == null) {
			return null;
		}
		if (resource instanceof IProject) {
			return getDefaultContainer((IProject) resource);
		}
		if (resource instanceof IContainer) {
			return (IContainer) resource;
		}
		return resource.getParent();
	}
	
	private IContainer getDefaultContainer(IProject project) {
		if (ProjectTools.hasFacet(project, JaxbProject.FACET)) {
			ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
			return locator.getDefaultLocation();
		}
		return project;
	}
	
	@Override
	public void addPages() {
		super.addPages();
		this.firstPage = buildOxmFileNewFileWizardPage();
		this.secondPage = buildOxmFileOptionsWizardPage();
		addPage(this.firstPage);
		addPage(this.secondPage);
	}
	
	protected NewJptFileWizardPage buildOxmFileNewFileWizardPage() {
		return new NewJptFileWizardPage(
				"Page_1", this.mungedSelection, this.config,
				JptJaxbEclipseLinkUiMessages.OXM_FILE_WIZARD__NEW_FILE_PAGE__TITLE, 
				JptJaxbEclipseLinkUiMessages.OXM_FILE_WIZARD__NEW_FILE_PAGE__DESC);
	}
	
	protected OxmFileOptionsWizardPage buildOxmFileOptionsWizardPage() {
		return new OxmFileOptionsWizardPage(
				"Page_2", this.config,
				JptJaxbEclipseLinkUiMessages.OXM_FILE_WIZARD__FILE_OPTIONS_PAGE__TITLE, 
				JptJaxbEclipseLinkUiMessages.OXM_FILE_WIZARD__FILE_OPTIONS_PAGE__DESC);
	}
	
	@Override
	public boolean canFinish() {
		// override so that visit to second page is not necessary
		// allow warnings
		return this.firstPage.isPageComplete() && this.config.validate().getSeverity() != IStatus.ERROR;  
	}
	
	protected IDataModelPausibleOperation getOperation() {
		return (IDataModelPausibleOperation) this.config.getDefaultOperation();
	}
	
	@Override
	public boolean performFinish() {
		createOxmFile();
		try {
			postPerformFinish();
		} catch (Exception e) {
			JptJaxbEclipseLinkUiPlugin.instance().logError(e);
		}
		return true;
	}
	
	protected boolean createOxmFile() {
		try {
			final IStatus st = runOperations();
			
			if (st.getSeverity() == IStatus.ERROR) {
				JptJaxbEclipseLinkUiPlugin.instance().getLog().log(st);
				Throwable t = st.getException() == null ? new CoreException(st) : st.getException();
				ErrorDialog.openError(
						getShell(), 
						WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_0, new Object[]{getWindowTitle()}), 
						WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_1, new Object[]{getWindowTitle()}), 
						t, 0, false);
			} 
			else if(st.getSeverity() == IStatus.WARNING){
				MessageDialog.openWarning(
						getShell(), 
						WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_2, new Object[]{getWindowTitle()}), 
						st.getMessage(), 
						st, IStatus.WARNING);
			}
		}
		catch (Exception e) {
			JptJaxbEclipseLinkUiPlugin.instance().logError(e);
			ErrorDialog.openError(
					getShell(), 
					WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_0, new Object[]{getWindowTitle()}), 
					WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_1, new Object[]{getWindowTitle()}), 
					e, 0, false);
		}
		return true;
	}
	
	protected IStatus runOperations() {
		
		class CatchThrowableRunnableWithProgress
				implements IRunnableWithProgress {
			
			public IStatus status = null;
			public Throwable caught = null;
			
			public void run(IProgressMonitor pm) {
				try {
					status = getOperation().execute(pm, null);
				} 
				catch (Throwable e) {
					caught = e;
				}
			}
		}
		
		CatchThrowableRunnableWithProgress runnable = new CatchThrowableRunnableWithProgress();
		
		try {
			getContainer().run(true, false, runnable);
		} 
		catch (Throwable e) {
			runnable.caught = e;
		}
		
		if (runnable.caught == null) {
			return runnable.status;
		}

		JptJaxbEclipseLinkUiPlugin.instance().logError(runnable.caught);
		ErrorDialog.openError(
				getShell(), 
				WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_0, new Object[]{getWindowTitle()}), 
				WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_1, new Object[]{getWindowTitle()}), 
				runnable.caught, 0, false);
		return JptJaxbEclipseLinkUiPlugin.instance().buildErrorStatus(runnable.caught);
	}
	
	protected void postPerformFinish()
			throws InvocationTargetException {      
		
		try {
			IPath containerPath = (IPath) this.config.getProperty(CONTAINER_PATH);
			String fileName = this.config.getStringProperty(FILE_NAME);
			IContainer container = PathTools.getContainer(containerPath);
			IFile file = container.getFile(new Path(fileName));
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
                        IWorkbenchPage page = WorkbenchTools.getActivePage();
                        if (page != null) {
                        	IDE.openEditor(page, file, true);
                        }
                    }
                    catch (PartInitException e) {
                    	JptJaxbEclipseLinkUiPlugin.instance().logError(e);
                    }
                }
            });
        }
    }
	
	@Override
	public void dispose() {
		super.dispose();
		this.config.dispose();
	}
}
