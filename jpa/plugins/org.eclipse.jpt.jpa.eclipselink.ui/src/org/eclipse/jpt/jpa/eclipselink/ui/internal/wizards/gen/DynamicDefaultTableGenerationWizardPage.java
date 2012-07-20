/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.SelectEclipseLinkMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.EntityWizardMsg;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.DefaultTableGenerationWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class DynamicDefaultTableGenerationWizardPage extends
		DefaultTableGenerationWizardPage {

	private Label xmlMappingFileLabel;
	private Text xmlMappingFileText;
	private Button xmlMappingFileBrowseButton;

	public DynamicDefaultTableGenerationWizardPage(JpaProject jpaProject) {
		super(jpaProject);
	}
	
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 4		;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_CUSTOMIZE_DEFAULT_ENTITY_GENERATION);

		createXmlMappingFileGroup(composite);
		createDomainJavaClassesPropertiesGroup(composite, 4);
		defaultTableGenPanel = new DynamicTableGenPanel(composite, 4, true, this);

		setControl(composite);
		
		this.setPageComplete( true );
	}

	@Override
	protected void createDomainJavaClassesPropertiesGroup(Composite composite, int columns) {
		Group parent = new Group( composite, SWT.NONE);
		parent.setText( JptJpaEclipseLinkUiEntityGenMessages.GenerateDynamicEntitiesWizard_defaultTablePage_domainJavaClass);
		parent.setLayout(new GridLayout( columns, false));
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		parent.setLayoutData(layoutData);

		createPackageControls(parent, columns);
	}
	
	private void createXmlMappingFileGroup(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	

		this.xmlMappingFileLabel = new Label(composite, SWT.LEFT);
		this.xmlMappingFileLabel.setText(JptJpaEclipseLinkUiEntityGenMessages.GenerateDynamicEntitiesWizard_defaultTablePage_xmlMappingFile);
		this.xmlMappingFileLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

		this.xmlMappingFileText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		this.xmlMappingFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		this.xmlMappingFileText.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				handleXmlMappingFileTextModified();
			}
		});
		
		this.xmlMappingFileBrowseButton = new Button(composite, SWT.PUSH);
		this.xmlMappingFileBrowseButton.setText(EntityWizardMsg.BROWSE_BUTTON_LABEL);
		GridData browseButtonData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		browseButtonData.horizontalSpan = 1;
		this.xmlMappingFileBrowseButton.setLayoutData(browseButtonData);		
		this.xmlMappingFileBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleXmlMappingFileButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	private void handleXmlMappingFileTextModified() {
		this.getCustomizer().setXmlMappingFile(this.xmlMappingFileText.getText());
//		validate();
	}

	private void handleXmlMappingFileButtonPressed() {		

		ViewerFilter filter = getDialogViewerFilter(this.jpaProject);
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		SelectMappingFileDialog dialog = new SelectEclipseLinkMappingFileDialog(getShell(), this.jpaProject.getProject(), labelProvider, contentProvider);
		dialog.setTitle(JptUiMessages.SelectMappingFileDialog_title);
		dialog.setMessage(JptUiMessages.SelectMappingFileDialog_message);
		dialog.addFilter(filter);
			
		String ormFileName = this.xmlMappingFileText.getText();
		JpaXmlResource resource = jpaProject.getMappingFileXmlResource(new Path(ormFileName));
		IFile initialSelection = (resource != null) ? resource.getFile() : null;
		dialog.setInput(this.jpaProject.getProject());

		if (initialSelection != null) {
			dialog.setInitialSelection(initialSelection);
		}
		if (dialog.open() == Window.OK) {
			String chosenName = dialog.getChosenName();
			this.xmlMappingFileText.setText(chosenName);
			this.getCustomizer().setXmlMappingFile(chosenName);
		}
//		validate();
	}
	
	protected ViewerFilter getDialogViewerFilter(JpaProject jpaProject) {
		return new XmlMappingFileViewerFilter(jpaProject, XmlEntityMappings.CONTENT_TYPE);
	}
	
	public void setVisible(boolean visible){
		super.setVisible(visible);
		if(visible){
			this.xmlMappingFileText.setText(this.getCustomizer().getXmlMappingFile());
//			validate();
		}
	}
	
//	private void validate() {
//		String errorMessage = null;
//		JpaXmlResource ormXmlResource = getOrmXmlResource();
//		if (ormXmlResource == null) {
//			errorMessage = JptUiMessages.JpaMakePersistentWizardPage_mappingFileDoesNotExistError;
//		}
//		setErrorMessage(errorMessage);
//		setPageComplete(errorMessage == null);
//	}
	
	protected JpaXmlResource getOrmXmlResource() {
		return this.jpaProject.getMappingFileXmlResource(new Path(this.xmlMappingFileText.getText()));
	}
	
}
