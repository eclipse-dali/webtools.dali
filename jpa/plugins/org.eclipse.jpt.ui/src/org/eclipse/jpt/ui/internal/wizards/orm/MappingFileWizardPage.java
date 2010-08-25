/*******************************************************************************
 *  Copyright (c) 2008, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards.orm;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.AbstractJpaProject;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;

public class MappingFileWizardPage extends DataModelWizardPage
	implements OrmFileCreationDataModelProperties
{
	private Label projectNameLabel;
		
	private Combo projectNameCombo;	
	
	private Label sourceFolderLabel;
	
	private Text sourceFolderText;
	
	private Label filePathLabel;
	
	private Text filePathText;
	
	private Label accessLabel;
	
	private Combo accessCombo;
	
	private Button addToPersistenceUnitButton;
	
	private Label persistenceUnitLabel;
	
	private Combo persistenceUnitCombo;
	
	
	public MappingFileWizardPage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
		setTitle(JptUiMessages.MappingFileWizardPage_title);
		setDescription(JptUiMessages.MappingFileWizardPage_desc);
		setPageComplete(false);
	}
	
	
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[] {
			PROJECT_NAME,
			SOURCE_FOLDER,
			FILE_PATH,
			DEFAULT_ACCESS,
			ADD_TO_PERSISTENCE_UNIT,
			PERSISTENCE_UNIT
		};
	}
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		
		this.projectNameLabel = new Label(composite, SWT.NONE);
		this.projectNameLabel.setText(JptUiMessages.MappingFileWizardPage_projectLabel);
		data = new GridData();
		this.projectNameLabel.setLayoutData(data);
		
		this.projectNameCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		this.projectNameCombo.setLayoutData(data);
		this.synchHelper.synchCombo(this.projectNameCombo, PROJECT_NAME, null);
		new Label(composite, SWT.NONE);
		
		this.sourceFolderLabel = new Label(composite, SWT.NONE);
		this.sourceFolderLabel.setText(JptUiMessages.MappingFileWizardPage_sourceFolderLabel);
		data = new GridData();
		this.sourceFolderLabel.setLayoutData(data);
		
		this.sourceFolderText = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		this.sourceFolderText.setLayoutData(data);
		this.synchHelper.synchText(this.sourceFolderText, SOURCE_FOLDER, null);
		
		Button sourceFolderButton = new Button(composite, SWT.PUSH);
		sourceFolderButton.setText(JptUiMessages.General_browse);
		data = new GridData();
		data.horizontalSpan = 1;
		sourceFolderButton.addSelectionListener(
			new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
				
				public void widgetSelected(SelectionEvent e) {
					handleSourceFolderButtonPressed();
				}
			});
		
		Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1);
		data.verticalIndent = 5;
		separator.setLayoutData(data);
		
		this.filePathLabel = new Label(composite, SWT.NONE);
		this.filePathLabel.setText(JptUiMessages.MappingFileWizardPage_filePathLabel);
		data = new GridData();
		data.verticalIndent = 5;
		this.filePathLabel.setLayoutData(data);
		
		this.filePathText = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.verticalIndent = 5;
		data.grabExcessHorizontalSpace = true;
		this.filePathText.setLayoutData(data);
		this.synchHelper.synchText(this.filePathText, FILE_PATH, null);
		new Label(composite, SWT.NONE);
		
		this.accessLabel = new Label(composite, SWT.NONE);
		this.accessLabel.setText(JptUiMessages.MappingFileWizardPage_accessLabel);
		data = new GridData();
		this.accessLabel.setLayoutData(data);
		
		this.accessCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		this.accessCombo.setLayoutData(data);
		this.synchHelper.synchCombo(this.accessCombo, DEFAULT_ACCESS, null);
		new Label(composite, SWT.NONE);
		
		this.addToPersistenceUnitButton = new Button(composite, SWT.CHECK | SWT.BEGINNING);
		this.addToPersistenceUnitButton.setText(JptUiMessages.MappingFileWizardPage_addToPersistenceUnitButton);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		data.verticalIndent = 10;
		this.addToPersistenceUnitButton.setLayoutData(data);
		this.synchHelper.synchCheckbox(this.addToPersistenceUnitButton, ADD_TO_PERSISTENCE_UNIT, null);
		
		this.persistenceUnitLabel = new Label(composite, SWT.NONE);
		this.persistenceUnitLabel.setText(JptUiMessages.MappingFileWizardPage_persistenceUnitLabel);
		data = new GridData();
		data.horizontalIndent = 10;
		this.persistenceUnitLabel.setLayoutData(data);
		this.persistenceUnitLabel.setEnabled(false);
		this.addToPersistenceUnitButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				persistenceUnitLabel.setEnabled(addToPersistenceUnitButton.getSelection());
			}
			public void widgetDefaultSelected(SelectionEvent e) {/*not called*/}
		});
		
		this.persistenceUnitCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		this.persistenceUnitCombo.setLayoutData(data);
		this.synchHelper.synchCombo(this.persistenceUnitCombo, PERSISTENCE_UNIT, null);
		
		new Label(composite, SWT.NONE);
		
//		classText.setFocus();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, getInfopopID());
	    Dialog.applyDialogFont(parent);
		return composite;
	}
	
	private void handleSourceFolderButtonPressed() {
		ISelectionStatusValidator validator = getSourceFolderDialogSelectionValidator();
		ViewerFilter filter = getSourceFolderDialogViewerFilter();
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), labelProvider, contentProvider);
		dialog.setValidator(validator);
		dialog.setTitle(JptUiMessages.MappingFileWizardPage_accessLabel_sourceFolderDialogTitle);
		dialog.setMessage(JptUiMessages.MappingFileWizardPage_accessLabel_sourceFolderDialogDesc);
		dialog.addFilter(filter);
		String projectName = model.getStringProperty(PROJECT_NAME);
		if (projectName==null || projectName.length()==0) {
			return;
		}
		IProject project = ProjectUtilities.getProject(projectName);
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());

		if (project != null) {
			dialog.setInitialSelection(project);
		}
		if (dialog.open() == Window.OK) {
			Object element = dialog.getFirstResult();
			if (element instanceof IContainer) {
				IContainer container = (IContainer) element;
				model.setProperty(SOURCE_FOLDER, container.getFullPath().toPortableString());
			}
		}
	}
	
	private ISelectionStatusValidator getSourceFolderDialogSelectionValidator() {
		return new ISelectionStatusValidator() {
			public IStatus validate(Object[] selection) {
				if (selection != null && selection[0] != null) {
					if (selection[0] instanceof IProject) {
						IProject project = (IProject) selection[0];
						if (project.equals(AbstractJpaProject.getBundleRoot(project))) {
							return Status.OK_STATUS;
						}
					}
					else {
						return Status.OK_STATUS;
					}
				}
				return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, JptUiMessages.MappingFileWizardPage_incorrectSourceFolderError);
			}
		};
	}
	
	private ViewerFilter getSourceFolderDialogViewerFilter() {
		return new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parent, Object element) {
				if (element instanceof IProject) {
					IProject project = (IProject) element;
					return project.getName().equals(model.getProperty(PROJECT_NAME));
				} 
				else if (element instanceof IContainer) {
					IContainer container = (IContainer) element;
					// only show source folders
					IProject project = ProjectUtilities.getProject(model.getStringProperty(PROJECT_NAME));
					IJavaProject javaProject = JavaCore.create(project);
					if (JDTTools.packageFragmentRootIsSourceFolder(javaProject.getPackageFragmentRoot(container))) {
						return true;
					}
					// add bundle root, if it exists
					if (element.equals(AbstractJpaProject.getBundleRoot(project))) {
						return true;
					}
				}
				return false;
			}
		};
	}
	
	void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}
}
