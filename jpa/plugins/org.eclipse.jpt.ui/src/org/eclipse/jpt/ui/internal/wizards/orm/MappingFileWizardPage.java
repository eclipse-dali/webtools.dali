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

import java.util.Iterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;

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
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.widthHint = 300;
		composite.setLayoutData(data);
		
		projectNameLabel = new Label(composite, SWT.NONE);
		projectNameLabel.setText(JptUiMessages.MappingFileWizardPage_projectLabel); //$NON-NLS-1$
		data = new GridData();
		projectNameLabel.setLayoutData(data);
		
		projectNameCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 300;
		data.horizontalSpan = 1;
		projectNameCombo.setLayoutData(data);
		projectNameCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// update source folder
//					if (folderText != null) {					
//						String sourceFolder = getDefaultJavaSourceFolder(ProjectUtilities.getProject(projectNameCombo.getText())).getFullPath().toOSString();					
//						if (sourceFolder != null)
//							folderText.setText(sourceFolder);
//					}
			}
		});
		synchHelper.synchCombo(projectNameCombo, PROJECT_NAME, null);
		fillProjects();
		new Label(composite, SWT.NONE);
		
		sourceFolderLabel = new Label(composite, SWT.NONE);
		sourceFolderLabel.setText(JptUiMessages.MappingFileWizardPage_sourceFolderLabel); //$NON-NLS-1$
		data = new GridData();
		sourceFolderLabel.setLayoutData(data);
		
		sourceFolderText = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 300;
		data.horizontalSpan = 1;
		sourceFolderText.setLayoutData(data);
		synchHelper.synchText(sourceFolderText, SOURCE_FOLDER, null);
		new Label(composite, SWT.NONE);
		
		filePathLabel = new Label(composite, SWT.NONE);
		filePathLabel.setText(JptUiMessages.MappingFileWizardPage_filePathLabel); //$NON-NLS-1$
		data = new GridData();
		filePathLabel.setLayoutData(data);
		
		filePathText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 300;
		data.horizontalSpan = 1;
		filePathText.setLayoutData(data);
		synchHelper.synchText(filePathText, FILE_PATH, null);
		fillProjects();
		new Label(composite, SWT.NONE);
		
		accessLabel = new Label(composite, SWT.NONE);
		accessLabel.setText(JptUiMessages.MappingFileWizardPage_accessLabel); //$NON-NLS-1$
		data = new GridData();
		accessLabel.setLayoutData(data);
		
		accessCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 300;
		data.horizontalSpan = 1;
		accessCombo.setLayoutData(data);
		new Label(composite, SWT.NONE);
		
		addToPersistenceUnitButton = new Button(composite, SWT.CHECK | SWT.BEGINNING);
		addToPersistenceUnitButton.setText(JptUiMessages.MappingFileWizardPage_addToPersistenceUnitButton);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		addToPersistenceUnitButton.setLayoutData(data);
		
		persistenceUnitLabel = new Label(composite, SWT.NONE);
		persistenceUnitLabel.setText(JptUiMessages.MappingFileWizardPage_persistenceUnitLabel); //$NON-NLS-1$
		data = new GridData();
		persistenceUnitLabel.setLayoutData(data);
		
		persistenceUnitCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 300;
		data.horizontalSpan = 1;
		persistenceUnitCombo.setLayoutData(data);
		new Label(composite, SWT.NONE);
		
//		classText.setFocus();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, getInfopopID());
	    Dialog.applyDialogFont(parent);
		return composite;
	}
	
	private void fillProjects() {
		projectNameCombo.setItems(new String[0]);
		for (Iterator<IProject> stream = jpaProjects(); stream.hasNext(); ) {
			projectNameCombo.add(stream.next().getName());
		}
	}
		
	private Iterator<IProject> jpaProjects() {
		return new FilteringIterator<IProject, IProject>(CollectionTools.iterator(ProjectUtilities.getAllProjects())) {
			@Override
			protected boolean accept(IProject project) {
				try {
					return FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID);
				}
				catch (CoreException ce) {
					return false;
				}
			}
		};
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
	
	void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}
}
