/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.orm;

import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.CONTAINER_PATH;
import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.FILE_NAME;
import static org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT;
import static org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProperties.PERSISTENCE_UNIT;
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
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.IDataModelPausibleOperation;
import org.eclipse.wst.common.frameworks.internal.dialog.ui.MessageDialog;
import org.eclipse.wst.common.frameworks.internal.ui.ErrorDialog;
import org.eclipse.wst.common.frameworks.internal.ui.WTPCommonUIResourceHandler;

public class MappingFileWizard extends Wizard
	implements INewWizard 
{
	protected IDataModel dataModel;
	
	protected IStructuredSelection initialSelection;
	
	protected IStructuredSelection mungedSelection;
	
	private IWizardPage firstPage;
	
	private IWizardPage secondPage;
	
	
	public MappingFileWizard() {
		this(null);
	}
	
	public MappingFileWizard(IDataModel dataModel) {
		super();
		this.dataModel = dataModel;
		setWindowTitle(JptUiMessages.MappingFileWizard_title);
		setDefaultPageImageDescriptor(JptJpaUiPlugin.getImageDescriptor(JptUiIcons.JPA_FILE_WIZ_BANNER));
	}
	
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.initialSelection = selection;
		this.mungedSelection = selection;
		
		if (selection == null || selection.isEmpty()) {	
			return;
		}
		
		Object firstSelection = selection.getFirstElement();
		
		PersistenceUnit pUnit = extractPersistenceUnit(firstSelection);
		IContainer container = extractContainer(pUnit, firstSelection);
		
		if (container != null) {
			this.mungedSelection = new StructuredSelection(container);
		}
		
		if (pUnit != null) {
			getDataModel().setBooleanProperty(ADD_TO_PERSISTENCE_UNIT, true);
			getDataModel().setStringProperty(PERSISTENCE_UNIT, pUnit.getName());
		}
	}
	
	private PersistenceUnit extractPersistenceUnit(Object selection) {
		if (selection instanceof JpaContextNode) {
			// may be null if node is above level of persistence unit, but in those cases
			// null is the expected result
			return ((JpaContextNode) selection).getPersistenceUnit();
		}

		// also get the persistence unit for a given jpa project selection
		JpaProject jpaProject = this.getJpaProject(selection);
		if (jpaProject != null) {
			PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
			if (persistenceXml != null){
				Persistence persistence = persistenceXml.getPersistence();
				if ((persistence != null) && (persistence.getPersistenceUnitsSize() > 0)) {
					return persistence.getPersistenceUnit(0);
				}
			}
		}
		return null;
	}
	
	private JpaProject getJpaProject(Object selection) {
		IProject project = this.getProject(selection);
		return (project == null) ? null : this.getJpaProject(project);
	}
	
	private IProject getProject(Object selection) {
		if (selection instanceof IJavaProject) {
			return ((IJavaProject) selection).getProject();
		}
		if (selection instanceof IProject) {
			return (IProject) selection;
		}
		return null;
	}
	
	private JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}
	
	private IContainer extractContainer(PersistenceUnit pUnit, Object selection) {
		if (pUnit != null) {
			return pUnit.getResource().getParent();
		}
		if (selection instanceof IProject) {
			return getDefaultContainer((IProject) selection);
		}
		if (selection instanceof IContainer) {
			return (IContainer) selection;
		}
		if (selection instanceof JpaContextNode) {
			return getDefaultContainer(((JpaContextNode) selection).getJpaProject().getProject());
		}
		
		IResource resource = PlatformTools.getAdapter(selection, IResource.class);
		if (resource != null) {
			if (resource instanceof IProject) {
				return getDefaultContainer((IProject) resource);
			}
			if (resource instanceof IContainer) {
				return (IContainer) resource;
			}
		}
		JpaContextNode node = PlatformTools.getAdapter(selection, JpaContextNode.class);
		if (node != null) {
			return getDefaultContainer(node.getJpaProject().getProject());
		}
		return null;
	}
	
	private IContainer getDefaultContainer(IProject project) {
		if (JpaFacet.isInstalled(project)) {
			ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
			return locator.getDefaultResourceLocation();
		}
		return project;
	}
	
	@Override
	public void addPages() {
		super.addPages();
		this.firstPage = buildMappingFileNewFileWizardPage();
		this.secondPage = buildMappingFileOptionsWizardPage();
		addPage(this.firstPage);
		addPage(this.secondPage);
	}
	
	protected MappingFileNewFileWizardPage buildMappingFileNewFileWizardPage() {
		return new MappingFileNewFileWizardPage(
				"Page_1", this.mungedSelection, getDataModel(),
				JptUiMessages.MappingFileWizardPage_newFile_title, 
				JptUiMessages.MappingFileWizardPage_newFile_desc);
	}
	
	protected MappingFileOptionsWizardPage buildMappingFileOptionsWizardPage() {
		return new MappingFileOptionsWizardPage(
				"Page_2", getDataModel(),
				JptUiMessages.MappingFileWizardPage_options_title, 
				JptUiMessages.MappingFileWizardPage_options_desc);
	}
	
	@Override
	public boolean canFinish() {
		// override so that visit to second page is not necessary
		return this.firstPage.isPageComplete() && getDataModel().isValid();
	}
	
	public IDataModel getDataModel() {
		if (this.dataModel == null) {
			this.dataModel = DataModelFactory.createDataModel(getDefaultProvider());
		}
		return this.dataModel;
	}
	
	protected IDataModelProvider getDefaultProvider() {
		return new OrmFileCreationDataModelProvider();
	}
	
	protected IDataModelPausibleOperation getOperation() {
		return (IDataModelPausibleOperation) getDataModel().getDefaultOperation();
	}
	
	@Override
	public boolean performFinish() {
		createMappingFile();
		try {
			postPerformFinish();
		} catch (Exception e) {
			JptJpaUiPlugin.log(e);
		}
		return true;
	}
	
	protected boolean createMappingFile() {
		try {
			final IStatus st = runOperations();
			
			if (st.getSeverity() == IStatus.ERROR) {
				JptJpaUiPlugin.log(st);
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
			JptJpaUiPlugin.log(e);
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
		else {
			JptJpaUiPlugin.log(runnable.caught);
			ErrorDialog.openError(
					getShell(), 
					WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_0, new Object[]{getWindowTitle()}), 
					WTPCommonUIResourceHandler.getString(WTPCommonUIResourceHandler.WTPWizard_UI_1, new Object[]{getWindowTitle()}), 
					runnable.caught, 0, false);
			return new Status(IStatus.ERROR, "id", 0, runnable.caught.getMessage(), runnable.caught); //$NON-NLS-1$
		}
	}
	
	protected void postPerformFinish()
			throws InvocationTargetException {      
		
		try {
			IPath containerPath = (IPath) getDataModel().getProperty(CONTAINER_PATH);
			String fileName = getDataModel().getStringProperty(FILE_NAME);
			IContainer container = PlatformTools.getContainer(containerPath);
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
                        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                        IDE.openEditor(page, file, true);
                    }
                    catch (PartInitException e) {
                    	JptJpaUiPlugin.log(e);
                    }
                }
            });
        }
    }
	
	@Override
	public void dispose() {
		super.dispose();
		getDataModel().dispose();
	}
	
	public static IPath createNewMappingFile(IStructuredSelection selection, String xmlFileName) {
		MappingFileWizard wizard = new MappingFileWizard(DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider()));
		return createMappingFile(selection, xmlFileName, wizard);
	}

	protected static IPath createMappingFile(IStructuredSelection selection, String xmlFileName, MappingFileWizard wizard) {
		wizard.getDataModel().setProperty(FILE_NAME, xmlFileName);
		wizard.init(PlatformUI.getWorkbench(), selection);
		WizardDialog dialog = new WizardDialog(getCurrentShell(), wizard);
		dialog.create();
		if (dialog.open() == Window.OK) {
			IPath containerPath = (IPath) wizard.getDataModel().getProperty(CONTAINER_PATH);
			String fileName = wizard.getDataModel().getStringProperty(FILE_NAME);
			IContainer container = PlatformTools.getContainer(containerPath);
			IPath filePath = container.getFullPath().append(fileName);
			IProject project = container.getProject();
			ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
			IPath runtimePath = locator.getRuntimePath(filePath);
			
			return runtimePath;
		}
		return null;
	}

	private static Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}


}
