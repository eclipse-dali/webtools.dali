/***********************************************************************
 * Copyright (c) 2008, 2009 by SAP AG, Walldorf. 
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

import java.io.File;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.wizard.NewJavaClassWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class EntityClassWizardPage extends NewJavaClassWizardPage{

	private static final String JPA_FACET = "jpt.jpa";//$NON-NLS-1$
	private static final String META_INF = "META-INF";//$NON-NLS-1$
	private static final String EMPTY = "";//$NON-NLS-1$
	private static final char SLASH = '/';
	private static final String SINGLE_TABLE = "SINGLE_TABLE";//$NON-NLS-1$
	private static final String TABLE_PER_CLASS = "TABLE_PER_CLASS";//$NON-NLS-1$
	private static final String JOINED = "JOINED";//$NON-NLS-1$
	private static final String[] INHERITANCE_STRATEGIES = new String[] {
											EMPTY, 
											SINGLE_TABLE,
											TABLE_PER_CLASS,
											JOINED };
	private Combo inheritanceStrategyCombo;
	private Button entityButton;
	private Button mapedAsSuperclassButton;
	private Button inheritanceButton;
	private Label displayNameLabel;
	private Button xmlSupportButton;	
	private boolean isFirstCheck = true;
	private Text ormXmlName;
	private Button browseButton;	
	
	public EntityClassWizardPage(IDataModel model, String pageName,
			String pageDesc, String pageTitle, String moduleType) {
		super(model, pageName, pageDesc, pageTitle, moduleType);
	}
	
	
	@Override
	protected String[] getValidationPropertyNames() {
		return CollectionTools.addAll(
				super.getValidationPropertyNames(), 
				new String[] {IEntityDataModelProperties.XML_NAME, IEntityDataModelProperties.XML_SUPPORT});
	}

	/* Create top level composite (class properties) and add the entity's specific inheritance group
	 * @see org.eclipse.jst.j2ee.internal.wizard.NewJavaClassWizardPage#createTopLevelComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = super.createTopLevelComposite(parent);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.NEW_JPA_ENTITY_ENTITY_CLASS);
		
		createInheritanceControl(composite);
		inheritanceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isChecked = inheritanceButton.getSelection();
				if (isChecked) {
					entityButton.setSelection(true);
					mapedAsSuperclassButton.setSelection(false);
				}
				inheritanceStrategyCombo.setEnabled(isChecked);
				model.setBooleanProperty(IEntityDataModelProperties.ENTITY, true);
				model.setBooleanProperty(IEntityDataModelProperties.MAPPED_AS_SUPERCLASS, false);				
				entityButton.setEnabled(!isChecked);
				mapedAsSuperclassButton.setEnabled(!isChecked);				
			}
		});		
		createXMLstorageControl(composite);
		xmlSupportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isChecked = xmlSupportButton.getSelection();
				enableMappingXMLChooseGroup(isChecked);
				if (isFirstCheck) {
					ormXmlName.setText(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
					isFirstCheck = false;
				}
			}
		});		
		return composite;
	}
	
	/**
	 * Create the inheritance group
	 * @param parent the main composite
	 */
	private void createInheritanceControl(Composite parent) {
		Group group = createGroup(parent, EntityWizardMsg.INHERITANCE_GROUP);
		entityButton = createRadioButton(group, EntityWizardMsg.ENTITY, IEntityDataModelProperties.ENTITY);
		mapedAsSuperclassButton = createRadioButton(group, EntityWizardMsg.MAPPED_AS_SUPERCLASS, IEntityDataModelProperties.MAPPED_AS_SUPERCLASS);
		inheritanceButton = createCheckButton(group, GridData.HORIZONTAL_ALIGN_FILL, 1/*horizontal span*/, EntityWizardMsg.INHERITANCE_CHECK_BOX, IEntityDataModelProperties.INHERITANCE);
		createComboBox(group, IEntityDataModelProperties.INHERITANCE_STRATEGY);
	}
	
	/**
	 * Create the group, which manage entity mapping registration
	 * @param parent the main composite
	 */
	private void createXMLstorageControl(Composite parent) {
		Group group = createGroup(parent, EntityWizardMsg.XML_STORAGE_GROUP);
		xmlSupportButton = createCheckButton(group, GridData.FILL_HORIZONTAL, 3/*horizontal span*/, EntityWizardMsg.XML_SUPPORT, IEntityDataModelProperties.XML_SUPPORT);				
		createBrowseGroup(group, EntityWizardMsg.CHOOSE_XML, IEntityDataModelProperties.XML_NAME);
		ormXmlName.setEnabled(false);		
		browseButton.setEnabled(false);		
	}
	
	/**
	 * @param parent the main composite
	 * @param text the name/title of the group
	 * @return the created group
	 */
	private Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.NONE);		
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		group.setLayoutData(groupGridData);
		group.setLayout(new GridLayout(3, false));
		group.setText(text);		
		return group;
	}
	
	/**
	 * Create radio button
	 * @param parent the main composite - inheritance group
	 * @param text the label of the button
	 * @param property the related property to which this button will be synchronized
	 * @return the created button
	 */
	private Button createRadioButton(Composite parent, String text, String property) {
		Button button = new Button(parent, SWT.RADIO);		
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		button.setLayoutData(groupGridData);
		button.setText(text);
		synchHelper.synchRadio(button, property, /*dependentControls*/ null);		
		return button;
	}
	
	/**
	 * Create check button
	 * @param parent the main composite - inheritance group
	 * @param text the label of the button
	 * @param property the related property to which this button will be synchronized
	 * @return the created button
	 */
	private Button createCheckButton(Composite parent, int fillStrategy, int horizontalSpan, String text, String property) {
		final Button button = new Button(parent, SWT.CHECK);		
		GridData groupGridData = new GridData(fillStrategy);
		groupGridData.horizontalSpan = horizontalSpan;
		button.setLayoutData(groupGridData);
		button.setText(text);		
		synchHelper.synchCheckbox(button, property, /*dependentControls*/ null);
		return button;
	}

	/**
	 * Create combo box, which presents the set of possible inheritance strategies
	 * @param parent the main composite - inheritance group
	 * @param property the related property to which this button will be synchronized
	 * @return
	 */
	private Combo createComboBox(Composite parent, String property) {
		inheritanceStrategyCombo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);		
		groupGridData.horizontalSpan = 2;
		inheritanceStrategyCombo.setLayoutData(groupGridData);
		inheritanceStrategyCombo.setItems(INHERITANCE_STRATEGIES);		
		synchHelper.synchCombo(inheritanceStrategyCombo, property, /*dependentControls*/ null);
		inheritanceStrategyCombo.setEnabled(false);
		return inheritanceStrategyCombo;		
		
	}	
	
	/**
	 * Create XML group
	 * @param parent the main composite
	 * @param label the name of the group
	 * @param property the related property to which this group will be synchronized
	 * @return the created group
	 */
	private void createBrowseGroup(Composite parent, String label, String property) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	
		displayNameLabel = new Label(composite, SWT.LEFT);
		displayNameLabel.setText(label);
		displayNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		ormXmlName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		ormXmlName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(EntityWizardMsg.BROWSE_BUTTON_LABEL);
		GridData browseButtonData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		browseButtonData.horizontalSpan = 1;
		browseButton.setLayoutData(browseButtonData);		
		browseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleChooseXmlButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// Do nothing
			}
		});
		synchHelper.synchText(ormXmlName, property, /*dependentControls*/null);
		
		enableMappingXMLChooseGroup(false);		
	}
	
	/**
	 * Process browsing when the Browse... button have been pressed. Allow choosing of 
	 * XML for entity mapping registration
	 *  
	 */
	private void handleChooseXmlButtonPressed() {		
		IProject project = (IProject) getDataModel().getProperty(INewJavaClassDataModelProperties.PROJECT);
		if (project == null) {
			return;
		}
		JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
		ViewerFilter filter = getDialogViewerFilter(jpaProject);
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		SelectMappingXMLDialog dialog = new SelectMappingXMLDialog(getShell(), labelProvider, contentProvider);
		dialog.setTitle(EntityWizardMsg.MAPPING_XML_TITLE);
		dialog.setMessage(EntityWizardMsg.CHOOSE_MAPPING_XML_MESSAGE);
		dialog.addFilter(filter);
			
		String ormFileName = this.ormXmlName.getText();
		JpaXmlResource resource = jpaProject.getMappingFileXmlResource(ormFileName);
		IFile initialSelection = (resource != null) ? resource.getFile() : null;
		dialog.setInput(project);

		if (initialSelection != null) {
			dialog.setInitialSelection(initialSelection);
		}
		if (dialog.open() == Window.OK) {
			this.ormXmlName.setText(dialog.getChosenName());
			this.model.validateProperty(IEntityDataModelProperties.XML_NAME);
		}		
	}
	
	/**
	 * This method create filter for the browse/add alternative mapping XML 
	 * @return new instance of viewer filter for the SelectMappingXMLDialog
	 */
	protected ViewerFilter getDialogViewerFilter(JpaProject jpaProject) {
		return new XmlMappingFileViewerFilter(jpaProject);
	}
	
	private void enableMappingXMLChooseGroup(boolean enabled) {
		displayNameLabel.setEnabled(enabled);
		ormXmlName.setEnabled(enabled);
		browseButton.setEnabled(enabled);

	}
	
	/**
	 * This method is used by the project list initializer. The method checks 
	 * if the specified project is valid to include it in the project list.
	 * 
	 * <p>Subclasses of this wizard page should override this method to 
	 * adjust filtering of the projects to their needs. </p>
	 * 
	 * @param project reference to the project to be checked
	 * 
	 * @return <code>true</code> if the project is valid to be included in 
	 * 		   the project list, <code>false</code> - otherwise. 
	 */
	@Override
	protected boolean isProjectValid(IProject project) {		
		IProjectFacet jpaFacet = ProjectFacetsManager.getProjectFacet(JPA_FACET);
		IFacetedProject fProject = null; 
		try {
			fProject = ProjectFacetsManager.create(project);
		} catch (CoreException e) {
			return false;
		}		
		return (project.isAccessible() && fProject != null && fProject.hasProjectFacet(jpaFacet));	
	}
	
	private class SelectMappingXMLDialog extends ElementTreeSelectionDialog
	{	
		private String xmlName = EMPTY;
		
		
		public SelectMappingXMLDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
			super(parent, labelProvider, contentProvider);			
		}
		
		
		/**
	     * @return the name of the alternative mapping XML
	     */
	    public String getChosenName() {
	    	String result = EMPTY;
			Object element = getFirstResult();
			if (element instanceof IContainer) {
				IContainer container = (IContainer) element;
				result = container.getFullPath().toString() + File.separatorChar + xmlName;					
			} else {
				IFile f = (IFile) element;
				result = f.getFullPath().toOSString();
			}
			result = removeRedundantSegmentFromName(result);
			return result;
	    }

		@Override
	    /*
	     * @see ElementTreeSelectionDialog#updateOKStatus(Composite)
	     */
		protected void updateOKStatus() {
			super.updateOKStatus();
			TreeSelection selection = (TreeSelection)getTreeViewer().getSelection();
			IResource selectedResource = (IResource) selection.getFirstElement();
			if (selectedResource instanceof IFile) {
				updateStatus(new Status(IStatus.OK, JptUiPlugin.PLUGIN_ID, ""));
			}
			else {
				updateStatus(new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, ""));
			}
		}
		
		/** 
		 * This method is for internal purposes only
		 * @param input non formated path to the mapping XML
		 * @return the formated path to the mapping XML
		 */
		private String removeRedundantSegmentFromName(String input) {
			String output = input.substring(input.indexOf(META_INF));			 
			output = output.replace(File.separatorChar, SLASH);
			return output;
		}
	}
	
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}
}
