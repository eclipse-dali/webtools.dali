/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandExecutor;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectJpaOrmMappingFileDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;

public abstract class JpaJavaGlobalMetadataConversionWizardPage<P extends PersistenceUnit>
extends WizardPage {

	private static String METADATA_CONVERSION_PAGE_NAME = "MetadataConversion"; //$NON-NLS-1$
	protected final P persistenceUnit;
	private final ModifiablePropertyValueModel<String> mappingFileModel;
	
	private final String helpContextId;

	public JpaJavaGlobalMetadataConversionWizardPage(
			final P persistenceUnit, SimplePropertyValueModel<String>  model, final String helpContextId) {
		super(METADATA_CONVERSION_PAGE_NAME);
		this.persistenceUnit = persistenceUnit;
		this.mappingFileModel = model;
		this.helpContextId = helpContextId;
		setTitle(this.getWizardPageTitle());
		setDescription(this.getWizardPageDescription());
	}

	public void createControl(Composite parent) {
				
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		composite.setLayout(layout);

		Label WizardPageLbl = new Label(composite, SWT.NONE);
		WizardPageLbl.setText(JptUiMessages.JpaGlobalMetadataConversionWizardPage_label);
		WizardPageLbl.setBounds(3, 230, 150, 12);

		Composite mappingFileComposite = this.createMappingFileControl(composite);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 58;
		data.grabExcessHorizontalSpace = true;
		mappingFileComposite.setLayoutData(data);
		setControl(composite);

		String message = org.eclipse.osgi.util.NLS.bind(
				JptUiMessages.JpaGlobalMetadataConversion_convertingJpaGlobalMetadataWarning, StringTools.CR);
		Text noteText = new Text(composite, SWT.READ_ONLY | SWT.MULTI);
		Font font = new Font(composite.getDisplay(), "Arial", 10, SWT.EMBEDDED);
		noteText.setText(message);
		noteText.setFont(font);
		noteText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		setPageComplete(this.mappingFileModel.getValue() != null);
	}

	private Composite createMappingFileControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns = 3;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		
		final Link mappingFileLink = new Link(composite, SWT.LEFT);
		mappingFileLink.setText(JptUiMessages.JpaGlobalMetadataConversionWizardPage_newMappingFileLink);
		mappingFileLink.setToolTipText(JptUiMessages.JpaGlobalMetadataConversionWizardPage_newMappingFileLinkToolTip);
		mappingFileLink.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		mappingFileLink.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						openNewMappingFileWizard();
					}
				});

		Text mappingFileNameTextField = createMappingFileNameText(composite, 1);
		SWTTools.bind(this.mappingFileModel, mappingFileNameTextField);
		this.mappingFileModel.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				validate();
			}
		});

		Button mappingFileBrowseBtn = new Button(composite, SWT.CENTER);
		mappingFileBrowseBtn.setToolTipText(JptUiMessages.JpaGlobalMetadataConversionWizardPage_browseMappingFileBtnToolTip);
		mappingFileBrowseBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		mappingFileBrowseBtn.setText(JptUiMessages.JpaGlobalMetadataConversionWizardPage_browseMappingFileBtn);
		mappingFileBrowseBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mappingFileBrowseBtnPressed();
			}
		});

		return composite;
	}
	
	private Text createMappingFileNameText(Composite parent, int span) {
		Text text = new Text(parent, SWT.SEARCH | SWT.SINGLE);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
			}
		});

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessVerticalSpace = true;
		gridData.widthHint = 486;
		gridData.heightHint = 14;
		gridData.horizontalSpan = span;
		gridData.grabExcessHorizontalSpace = true;

		text.setLayoutData(gridData);

		return text;
	}

	protected void mappingFileBrowseBtnPressed() {
		IProject project = this.getJpaProject().getProject();
		ViewerFilter filter = this.buildSelectMappingFileDialogViewerFilter();
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		SelectJpaOrmMappingFileDialog dialog = buildSelectMappingFileDialog(getShell(), project, labelProvider, contentProvider);
		dialog.setTitle(JptUiMessages.SelectJpaOrmMappingFileDialog_title);
		dialog.setMessage(JptUiMessages.SelectJpaOrmMappingFileDialog_message);
		dialog.addFilter(filter);
		dialog.setInput(project);

		JpaXmlResource resource = this.getJpaProject().getMappingFileXmlResource(new Path(getMappingFileRuntimePath()));
		IFile file = (resource == null) ? null : resource.getFile();
		if (file != null) {
			dialog.setInitialSelection(file);
		}
		if (dialog.open() == org.eclipse.jface.window.Window.OK) {
			this.mappingFileModel.setValue(dialog.getChosenName());
		}
	}

	protected JpaProject getJpaProject() {
		return this.persistenceUnit.getJpaProject();
	}

	protected void openNewMappingFileWizard() {
		IPath path = getMappingFilePath();
		if (path != null) {
			this.mappingFileModel.setValue(path.toString());
		}
	}

	protected String getMappingFileRuntimePath() {
		if (this.mappingFileModel.getValue() != null) {
			return this.mappingFileModel.getValue();
		}
		return getDefaultMappingFileRuntimPath();
	}

	protected String getDefaultOrmXmlResourceName() {
		return new File(getDefaultMappingFileRuntimPath()).getName();
	}

	protected JpaXmlResource getDefaultOrmXmlResource() {
		return getJpaProject().getMappingFileXmlResource(new Path(getDefaultMappingFileRuntimPath()));
	}

	protected String getOrmXmlResourceName() {
		return new File(getMappingFileRuntimePath()).getName();
	}
	
	protected JpaXmlResource getOrmXmlResource() {
		return getJpaProject().getMappingFileXmlResource(new Path(getMappingFileRuntimePath()));
	}

	@Override
	public final void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(this.helpContextId);
	}
	
	public void performFinish() throws InvocationTargetException {
		try {
			// true=fork; true=cancellable
			this.buildPerformFinishProgressMonitorDialog().run(true, true, this.buildPerformFinishRunableWithProgress());
		} 
		catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	protected ProgressMonitorDialog buildPerformFinishProgressMonitorDialog() {
		return new ProgressMonitorDialog(getControl().getShell());
	}
	
	protected IRunnableWithProgress buildPerformFinishRunableWithProgress() {
		return new IRunnableWithProgress() {
			public void run( final IProgressMonitor monitor) throws InvocationTargetException {
				IWorkspace ws = ResourcesPlugin.getWorkspace();
				ISchedulingRule rule = ws.getRuleFactory().modifyRule(ws.getRoot());
				try {
					ws.run(
							buildPerformFinishWorkspaceRunnable(),
							rule,
							IWorkspace.AVOID_UPDATE,
							monitor);
				} 
				catch (CoreException ex) {
					throw new InvocationTargetException(ex);
				}
			}
		};
	}

	private  IWorkspaceRunnable buildPerformFinishWorkspaceRunnable() {
		return new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				Command syncCommand = new SyncCommand(persistenceUnit, monitor);
				try {
					JpaJavaGlobalMetadataConversionWizardPage.this.getJpaProjectManager().
					execute(syncCommand, SynchronousUiCommandExecutor.instance());
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			}
		};
	}

	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) this.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	private IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	protected IDataModelProvider getDefaultProvider() {
		return new OrmFileCreationDataModelProvider();
	}

	protected String getMappingFileVersion(){
		return getOrmXmlResource().getVersion();
	}

	protected String getJpaProjectVersion(){
		return  getJpaProject().getJpaPlatform().getJpaVersion().getVersion();
	}

	protected void validate() {
		String errorMessage = null;
		JpaXmlResource ormXmlResource = getOrmXmlResource();
		if (ormXmlResource == null) {
			errorMessage = JptUiMessages.JpaGlobalMetadataConversion_mappingFileDoesNotExist;
		} else 
			if (!StringTools.stringsAreEqual(getMappingFileVersion(),getJpaProjectVersion())) {
				errorMessage = JptUiMessages.JpaGlobalMetadataConversion_mappingFileHasInvalidVersion;
			} 
			else
				if (getJpaProject().getJpaFile(ormXmlResource.getFile()).getRootStructureNodesSize() == 0) {
					errorMessage = JptUiMessages.JpaGlobalMetadataConversion_mappingFileNotListedInPersistenceXml;
				}
		if ( ! this.hasConvertibleJavaGlobalMetadata()) {
			String message = getWarningMessageForNonExistentGlobals();
			setMessage(message, 2);
		}
		setErrorMessage(errorMessage);
		setPageComplete(errorMessage == null);
	}

	// ********* abstract methods *************
	
	abstract protected String getWizardPageTitle();

	abstract protected String getWizardPageDescription();

	abstract protected ViewerFilter buildSelectMappingFileDialogViewerFilter();

	abstract protected SelectJpaOrmMappingFileDialog buildSelectMappingFileDialog 
	(Shell shell, IProject project, ILabelProvider lp, ITreeContentProvider cp);
	
	abstract protected IPath getMappingFilePath();

	abstract protected String getDefaultMappingFileRuntimPath();
	
	abstract protected void executeConversion(EntityMappings entityMappings, IProgressMonitor monitor);
	
	abstract protected boolean hasConvertibleJavaGlobalMetadata();
	
	abstract protected String getWarningMessageForNonExistentGlobals();
	
	// ********** sync command **********

	/**
	 * This is dispatched to the JPA project manager.
	 */
	class SyncCommand implements Command {
		private final PersistenceUnit persistenceUnit;
		private final IProgressMonitor monitor;

		SyncCommand(PersistenceUnit persistenceUnit, IProgressMonitor monitor) {
			super();
			this.persistenceUnit = persistenceUnit;
			this.monitor = monitor;
		}

		public void execute() {
			JpaXmlResource ormXmlResource = getOrmXmlResource();
			final EntityMappings entityMappings = getEntityMappings();
			execute(entityMappings).run();
			ormXmlResource.save();
			saveFiles(JpaJavaGlobalMetadataConversionWizardPage.this.getJpaProject());
			openEditor();
		}

		private Runnable execute(final EntityMappings entityMappings) {
			return (new Runnable() {
				public void run() {
					executeConversion(entityMappings, monitor);
				}});
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.persistenceUnit);
		}
	}
	
	protected EntityMappings getEntityMappings() {
		JpaXmlResource xmlResource = getOrmXmlResource();
		return (EntityMappings) getJpaProject().getJpaFile(xmlResource.getFile()).getRootStructureNodes().iterator().next();
	}
	
	protected void saveFiles(final JpaProject jpaProject) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IDE.saveAllEditors(new IResource[]{jpaProject.getProject()}, false);
			}
		});
	}

	protected void openEditor() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				getConversionCompletedAction().run();
			}
		});
	}

	protected Action getConversionCompletedAction() {
		return new Action() {
			@Override
			public void run() {
				JpaXmlResource resource = getJpaProject().getMappingFileXmlResource(new Path(getMappingFileRuntimePath()));
				IFile file = resource.getFile();
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
