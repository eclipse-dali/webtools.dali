/***********************************************************************
 * Copyright (c) 2008, 2010 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation
 ***********************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.entity;

import java.io.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectJpaOrmMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.plugin.J2EEUIMessages;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;

public class EntityClassWizardPage
		extends NewJavaClassWizardPage {

	private static final String EMPTY = "";//$NON-NLS-1$

	private static final String SINGLE_TABLE = "SINGLE_TABLE";//$NON-NLS-1$
	private static final String TABLE_PER_CLASS = "TABLE_PER_CLASS";//$NON-NLS-1$
	private static final String JOINED = "JOINED";//$NON-NLS-1$
	private static final String[] INHERITANCE_STRATEGIES = 
			new String[] {EMPTY, SINGLE_TABLE, TABLE_PER_CLASS, JOINED };
	private Combo inheritanceStrategyCombo;
	private Button entityButton;
	private Button mapedAsSuperclassButton;
	private Button inheritanceButton;
	private Label displayNameLabel;
	private Button xmlSupportButton;	
	private boolean isFirstCheck = true;
	private Text ormXmlName;
	private Button browseButton;	
	
	
	public EntityClassWizardPage(
			IDataModel model, String pageName, String pageDesc, String pageTitle) {
		
		super(model, pageName, pageDesc, pageTitle, null);
	}
	
	
	@Override
	protected String[] getValidationPropertyNames() {
		return ArrayTools.addAll(
				super.getValidationPropertyNames(), 
				new String[] {IEntityDataModelProperties.XML_NAME, IEntityDataModelProperties.XML_SUPPORT});
	}

	private IProject getProject() {
		return (IProject) getDataModel().getProperty(INewJavaClassDataModelProperties.PROJECT);
	}

	/* Create top level composite (class properties) and add the entity's specific inheritance group
	 * @see org.eclipse.jst.j2ee.internal.wizard.NewJavaClassWizardPage#createTopLevelComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = super.createTopLevelComposite(parent);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.NEW_JPA_ENTITY_ENTITY_CLASS);
		
		createInheritanceControl(composite);
		this.inheritanceButton.addSelectionListener(new SelectionAdapter() {
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
		this.xmlSupportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isChecked = xmlSupportButton.getSelection();
				enableMappingXMLChooseGroup(isChecked);
				if (isFirstCheck) {
					ormXmlName.setText(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
					isFirstCheck = false;
				}
			}
		});		
		return composite;
	}

	protected JpaProject getTargetJpaProject() {
		IProject project = getProject();
		return project != null ? JptJpaCorePlugin.getJpaProject(project) : null;
	}
	
	@Override
	protected ISelectionStatusValidator getContainerDialogSelectionValidator() {
		return new ISelectionStatusValidator() {
			public IStatus validate(Object[] selection) {
				if (selection != null && selection[0] != null) {
					if (selection[0] instanceof IProject) {
						IProject project = (IProject) selection[0];
						IJavaProject javaProject = JavaCore.create(project);
						for (IPackageFragmentRoot root : JDTTools.getJavaSourceFolders(javaProject)) {
							if (project.equals(root.getResource())) {
								return WTPCommonPlugin.OK_STATUS;
							}
						}
					}
					else {
						return WTPCommonPlugin.OK_STATUS;
					}
				}
				return WTPCommonPlugin.createErrorStatus(J2EEUIMessages.CONTAINER_SELECTION_DIALOG_VALIDATOR_MESG);
			}
		};
	}
	
	@Override
	protected ViewerFilter getContainerDialogViewerFilter() {
		return new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parent, Object element) {
				String projectName = (String) model.getProperty(IEntityDataModelProperties.PROJECT_NAME);
				if (element instanceof IProject) {
					IProject project = (IProject) element;
					return project.getName().equals(projectName);
				}
				else if (element instanceof IFolder) {
					IFolder folder = (IFolder) element;
					// only show source folders
					IProject project = ProjectUtilities.getProject(projectName);
					IJavaProject javaProject = JavaCore.create(project);
					for (IPackageFragmentRoot root : JDTTools.getJavaSourceFolders(javaProject)) {
						if (folder.equals(root.getResource()))
							return true;
					}
				}
				return false;
			}
		};
	}
	
	/**
	 * Create the inheritance group
	 * @param parent the main composite
	 */
	private void createInheritanceControl(Composite parent) {
		Group group = createGroup(parent, EntityWizardMsg.INHERITANCE_GROUP);
		this.entityButton = createRadioButton(group, EntityWizardMsg.ENTITY, IEntityDataModelProperties.ENTITY);
		this.mapedAsSuperclassButton = createRadioButton(group, EntityWizardMsg.MAPPED_AS_SUPERCLASS, IEntityDataModelProperties.MAPPED_AS_SUPERCLASS);
		this.inheritanceButton = createCheckButton(group, GridData.HORIZONTAL_ALIGN_FILL, 1/*horizontal span*/, EntityWizardMsg.INHERITANCE_CHECK_BOX, IEntityDataModelProperties.INHERITANCE);
		createComboBox(group, IEntityDataModelProperties.INHERITANCE_STRATEGY);
	}
	
	/**
	 * Create the group, which manage entity mapping registration
	 * @param parent the main composite
	 */
	private void createXMLstorageControl(Composite parent) {
		Group group = createGroup(parent, EntityWizardMsg.XML_STORAGE_GROUP);
		this.xmlSupportButton = createCheckButton(group, GridData.FILL_HORIZONTAL, 3/*horizontal span*/, EntityWizardMsg.XML_SUPPORT, IEntityDataModelProperties.XML_SUPPORT);				
		createBrowseGroup(group, EntityWizardMsg.CHOOSE_XML, IEntityDataModelProperties.XML_NAME);
		this.ormXmlName.setEnabled(false);		
		this.browseButton.setEnabled(false);		
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
		this.synchHelper.synchRadio(button, property, /*dependentControls*/ null);		
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
		this.synchHelper.synchCheckbox(button, property, /*dependentControls*/ null);
		return button;
	}

	/**
	 * Create combo box, which presents the set of possible inheritance strategies
	 * @param parent the main composite - inheritance group
	 * @param property the related property to which this button will be synchronized
	 * @return
	 */
	private Combo createComboBox(Composite parent, String property) {
		this.inheritanceStrategyCombo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);		
		groupGridData.horizontalSpan = 2;
		this.inheritanceStrategyCombo.setLayoutData(groupGridData);
		this.inheritanceStrategyCombo.setItems(INHERITANCE_STRATEGIES);		
		this.synchHelper.synchCombo(this.inheritanceStrategyCombo, property, /*dependentControls*/ null);
		this.inheritanceStrategyCombo.setEnabled(false);
		return this.inheritanceStrategyCombo;		
		
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

		this.displayNameLabel = new Label(composite, SWT.LEFT);
		this.displayNameLabel.setText(label);
		this.displayNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

		this.ormXmlName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		this.ormXmlName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.browseButton = new Button(composite, SWT.PUSH);
		this.browseButton.setText(EntityWizardMsg.BROWSE_BUTTON_LABEL);
		GridData browseButtonData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		browseButtonData.horizontalSpan = 1;
		this.browseButton.setLayoutData(browseButtonData);		
		this.browseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleChooseXmlButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		this.synchHelper.synchText(this.ormXmlName, property, /*dependentControls*/null);
		
		enableMappingXMLChooseGroup(false);		
	}
	
	protected String getMappingFileName() {
		String mappingFileLocation = this.model.getStringProperty(IEntityDataModelProperties.XML_NAME);
		return new File(mappingFileLocation).getName();
	}

	/**
	 * Process browsing when the Browse... button have been pressed. Allow choosing of 
	 * XML for entity mapping registration
	 *  
	 */
	private void handleChooseXmlButtonPressed() {		
		IProject project = getProject();
		if (project == null) {
			return;
		}
		JpaProject jpaProject = JptJpaCorePlugin.getJpaProject(project);
		ViewerFilter filter = getDialogViewerFilter(jpaProject);
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		SelectJpaOrmMappingFileDialog dialog = new SelectJpaOrmMappingFileDialog(getShell(), project, labelProvider, contentProvider);
		dialog.setTitle(EntityWizardMsg.MAPPING_XML_TITLE);
		dialog.setMessage(EntityWizardMsg.CHOOSE_MAPPING_XML_MESSAGE);
		dialog.addFilter(filter);
			
		String ormFileName = this.ormXmlName.getText();
		JpaXmlResource resource = jpaProject.getMappingFileXmlResource(new Path(ormFileName));
		IFile initialSelection = (resource != null) ? resource.getFile() : null;
		dialog.setInput(project);

		if (initialSelection != null) {
			dialog.setInitialSelection(initialSelection);
		}
		if (dialog.open() == Window.OK) {
			boolean noNameChange = false;
			if (StringTools.stringsAreEqual(this.model.getStringProperty(IEntityDataModelProperties.XML_NAME), dialog.getChosenName())) {
				noNameChange = true;
			}
			this.model.setProperty(IEntityDataModelProperties.XML_NAME, dialog.getChosenName());
			if (noNameChange) {
				//if the xml name has not changed, the wizard page will not be validated (bug 345293)
				this.validatePage();
			}
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
		this.displayNameLabel.setEnabled(enabled);
		this.ormXmlName.setEnabled(enabled);
		this.browseButton.setEnabled(enabled);
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
		return (project.isAccessible() && JpaFacet.isInstalled(project));	
	}
	
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}
}
