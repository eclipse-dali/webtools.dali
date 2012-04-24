/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.viewsupport.IViewPartInputProvider;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
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
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;

public class DynamicEntityClassWizardPage extends DataModelWizardPage{
	protected Text packageText;
	protected Button packageButton;
	protected Label packageLabel;
	protected Text classText;
	protected Label classLabel;
	protected Label projectNameLabel;
	private Combo projectNameCombo;	
	private String projectName;
	
	private Text entityNameText;
	private Label entityNameLabel;
	private Text tableNameText;
	private Button tableNameCheckButton;

	private Label ormXmlNameLabel;
	private Text ormXmlNameText;
	private Button browseButton;
	
	/**
	 * @param model
	 * @param pageName
	 */
	public DynamicEntityClassWizardPage(IDataModel model, String pageName) {
		super(model, pageName);
		setDescription(EclipseLinkUiMessages.DynamicEntityClassWizardPage_desc);
		this.setTitle(EclipseLinkUiMessages.DynamicEntityClassWizardPage_title);
		setPageComplete(false);
		this.projectName = null;
	}

	@Override
	protected String[] getValidationPropertyNames() {
		return new String[]{IArtifactEditOperationDataModelProperties.PROJECT_NAME, 
				IArtifactEditOperationDataModelProperties.COMPONENT_NAME, 
				INewJavaClassDataModelProperties.JAVA_PACKAGE,
				INewJavaClassDataModelProperties.CLASS_NAME,
				IEntityDataModelProperties.XML_NAME};
	}

	
	private IProject getProject() {
		return (IProject) getDataModel().getProperty(INewJavaClassDataModelProperties.PROJECT);
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

		addProjectNameGroup(composite);
		addPackageGroup(composite);
		addClassNameGroup(composite);
		addEntityNameGroup(composite);
		addEntityTableGroup(composite);
		addMappingXMLGroup(composite);

		// set the cursor focus
		//   - to the "Java package" if it is empty
		//   - to the "Class name" - otherwise
		if (packageText.getText().trim().length() == 0) {
			packageText.setFocus();
		} else {
			classText.setFocus();
		}
			    
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, EclipseLinkHelpContextIds.DYNAMIC_ENTITY_CLASS);
	    Dialog.applyDialogFont(parent);
		return composite;
	}
	
	/**
	 * Add project group to the top level composite
	 * This group is used to specify project for the dynamic entity
	 */
	private void addProjectNameGroup(Composite parent) {
		// set up project name label
		projectNameLabel = new Label(parent, SWT.NONE);
		projectNameLabel.setText(EclipseLinkUiMessages.DynamicEntityClassWizardPage_projectNameLabel); 
		GridData data = new GridData();
		projectNameLabel.setLayoutData(data);
		// set up project name entry field
		projectNameCombo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 300;
		data.horizontalSpan = 1;
		projectNameCombo.setLayoutData(data);
		projectNameCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				IProject project = ProjectUtilities.getProject(projectNameCombo.getText());
				validateProjectRequirements(project);
			}
		});
		synchHelper.synchCombo(projectNameCombo, IArtifactEditOperationDataModelProperties.PROJECT_NAME, null);
		initializeProjectList();
		new Label(parent, SWT.NONE);
	}

	/**
	 * Add package group to the top level composite
	 * This group is used to specify the package for the dynamic entity
	 */
	private void addPackageGroup(Composite composite) {
		packageLabel = new Label(composite, SWT.LEFT);
		packageLabel.setText(EclipseLinkUiMessages.DynamicEntityClassWizardPage_javaPackageLabel);
		packageLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		packageText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		packageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		synchHelper.synchText(packageText, INewJavaClassDataModelProperties.JAVA_PACKAGE, null);
		
		IPackageFragment packageFragment = getSelectedPackageFragment();
		String targetProject = model.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME); 
		if (packageFragment != null && packageFragment.exists() && 
				packageFragment.getJavaProject().getElementName().equals(targetProject)) {
			IPackageFragmentRoot root = getPackageFragmentRoot(packageFragment);
			model.setProperty(INewJavaClassDataModelProperties.JAVA_PACKAGE, packageFragment.getElementName());
		}

		packageButton = new Button(composite, SWT.PUSH);
		packageButton.setText(EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_browseBttonLabel);
		packageButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		packageButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handlePackageButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// Do nothing
			}
		});
	}
	
	/**
	 * Add class name group to the top level composite
	 * This group is used to specify the class name for the dynamic entity
	 */
	private void addClassNameGroup(Composite composite) {
		classLabel = new Label(composite, SWT.LEFT);
		classLabel.setText(EclipseLinkUiMessages.DynamicEntityClassWizardPage_classNameLabel);
		classLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		classText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		classText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		synchHelper.synchText(classText, INewJavaClassDataModelProperties.CLASS_NAME, null);

		new Label(composite, SWT.LEFT);
	}

	/**
	 * Add entity name group to the top level composite
	 * This group is used to manage the entity name settings of the dynamic entity
	 */
	private void addEntityNameGroup(Composite composite) {
		entityNameLabel = new Label(composite, SWT.LEFT);
		entityNameLabel.setText(EclipseLinkUiMessages.DynamicEntityClassWizardPage_entityName);
		entityNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		entityNameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		entityNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		synchHelper.synchText(entityNameText, IEntityDataModelProperties.ENTITY_NAME, null);

		new Label(composite, SWT.LEFT);
		
	}
	
	/**
	 * Add entity table group to the top level composite
	 * This group is used to manage the table settings for the dynamic entity
	 */
	private void addEntityTableGroup(Composite composite) {
		Group group = createGroup(composite, EclipseLinkUiMessages.DynamicEntityClassWizardPage_tableNameGroup);
		tableNameCheckButton= createTableNameCheckButton(group, EclipseLinkUiMessages.DynamicEntityClassWizardPage_useDefult, IEntityDataModelProperties.TABLE_NAME_DEFAULT);		
		tableNameText = createNameGroup(group, EclipseLinkUiMessages.DynamicEntityClassWizardPage_tableName, IEntityDataModelProperties.TABLE_NAME);
		tableNameText.setEnabled(!tableNameCheckButton.getSelection());
	}
	
	/**
	 * Add mapping file group to the top level composite
	 * this group is used to manage entity mapping registration
	 * where the dynamic entity will be created
	 */
	private void addMappingXMLGroup(Composite parent) {
		Group group = createGroup(parent, EclipseLinkUiMessages.DynamicEntityClassWizardPage_xmlGroup);
		createBrowseGroup(group, EclipseLinkUiMessages.DynamicEntityClassWizardPage_xmlNameLabel, IEntityDataModelProperties.XML_NAME);
		this.ormXmlNameText.setText(JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.toString());
	}

	/**
	 * Create named group
	 * @param parent the main composite
	 * @param label the name of the group
	 * @param property the related property to which this group will be synchronized
	 * @return the created group
	 */
	protected Text createNameGroup(Composite parent, String label, String property) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	
		Label nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText(label);
		nameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		Text nameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		synchHelper.synchText(nameText, property, /*dependentControls*/null);		
		return nameText;
	}
		
	/**
	 * Create check button
	 * @param parent the main composite - entity table group
	 * @param text the label of the button
	 * @param property the related property to which this button will be synchronized
	 * @return the created button
	 */	
	private Button createTableNameCheckButton(Composite parent, String text, String property) {
		final Button button = new Button(parent, SWT.CHECK);
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		button.setLayoutData(groupGridData);
		button.setText(text);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isChecked = button.getSelection();
				if (tableNameText != null) {
					tableNameText.setEnabled(!isChecked);
				}
			}
		});		
		synchHelper.synchCheckbox(button, property, /*dependentControls*/ null);		
		return button;
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
	 * Create mapping file group
	 * @param parent the main composite - mapping XML group
	 * @param label the name of the group
	 * @param property the related property to which this group will be synchronized
	 * @return the created group
	 */
	private void createBrowseGroup(Composite parent, String label, String property) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	

		this.ormXmlNameLabel = new Label(composite, SWT.LEFT);
		this.ormXmlNameLabel.setText(label);
		this.ormXmlNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

		this.ormXmlNameText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.SEARCH);
		this.ormXmlNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.browseButton = new Button(composite, SWT.PUSH);
		this.browseButton.setText(EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_browseBttonLabel);
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
		
		this.synchHelper.synchText(this.ormXmlNameText, property, /*dependentControls*/null);
	}
	
	/**
	 * Process browsing when the Browse... button have been pressed. 
	 * Allow choosing of EclipseLink XML for entity mapping registration
	 */
	private void handleChooseXmlButtonPressed() {		
		IProject project = getProject();
		if (project == null) {
			return;
		}
		JpaProject jpaProject = this.getJpaProject(project);
		if (jpaProject == null) {
			return;
		}
		ViewerFilter filter = getDialogViewerFilter(jpaProject);
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		SelectMappingFileDialog dialog = new SelectEclipseLinkMappingFileDialog(getShell(), project, labelProvider, contentProvider);
		dialog.setTitle(EclipseLinkUiMessages.DynamicEntityClassWizardPage_chooseXMLDialog_title);
		dialog.setMessage(EclipseLinkUiMessages.DynamicEntityClassWizardPage_chooseXMLDialog_msg);
		dialog.addFilter(filter);
		dialog.setInput(project);
			
		String ormFileName = this.ormXmlNameText.getText();
		JpaXmlResource resource = jpaProject.getMappingFileXmlResource(new Path(ormFileName));
		IFile initialSelection = (resource != null) ? resource.getFile() : null;

		if (initialSelection != null) {
			dialog.setInitialSelection(initialSelection);
		}
		if (dialog.open() == Window.OK) {
			this.model.setProperty(IEntityDataModelProperties.XML_NAME, dialog.getChosenName());
			this.validatePage();
		}
	}
	
	/**
	 * This method create filter for the browse/add alternative EclipseLink mapping XML 
	 * @return new instance of viewer filter for the SelectEcliplseLinkMappingFileDialog
	 */
	protected ViewerFilter getDialogViewerFilter(JpaProject jpaProject) {
		return new XmlMappingFileViewerFilter(jpaProject, JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE);
	}

	protected JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}
	
	/**
	 * This method is used by the project list initializer. The method checks 
	 * if the specified project is valid to be included in the project list.
	 * 
	 * <p>Subclasses of this wizard page should override this method to 
	 * adjust filtering of the projects to their needs. </p>
	 * 
	 * @param project reference to the project to be checked
	 * 
	 * @return <code>true</code> if the project is accessible and is an EclipseLink 
	 * JPA project with 2.1 or above version, <code>false</code> - otherwise.
	 */
	protected boolean isProjectValid(IProject project) {
		return (project.isAccessible() && JpaFacet.isInstalled(project) && this.getJpaPlatformGroupId(project).equals("eclipselink")
				&& this.projectIsJpa2_1Compatible(project));
	}

	protected String getJpaPlatformGroupId(IProject project) {
		JpaPlatformDescription desc = (JpaPlatformDescription) project.getAdapter(JpaPlatformDescription.class);
		return desc.getGroup().getId();
	}
	
	private boolean projectIsJpa2_1Compatible(IProject project) {
		return JptJpaEclipseLinkCorePlugin.nodeIsEclipseLinkVersionCompatible(this.getJpaProject(project), "2.1");
	}
	
	private void initializeProjectList() {
		IProject[] workspaceProjects = ProjectUtilities.getAllProjects();
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < workspaceProjects.length; i++) {
			IProject project = workspaceProjects[i];
			if (isProjectValid(project))
				items.add(project.getName());
		}
		if (items.isEmpty()) return;
		String[] names = new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			names[i] = items.get(i);
		}
		projectNameCombo.setItems(names);
		IProject selectedProject = null;
		try {
			if (model !=null) {
				String projectNameFromModel = model.getStringProperty(IArtifactEditOperationDataModelProperties.COMPONENT_NAME);
				if (projectNameFromModel!=null && projectNameFromModel.length()>0)
					selectedProject = ProjectUtilities.getProject(projectNameFromModel);
			}
		} catch (Exception e) {}
		try {
			if (selectedProject == null)
				selectedProject = getSelectedProject();
			if (selectedProject != null && selectedProject.isAccessible()
					&& selectedProject.hasNature(IModuleConstants.MODULE_NATURE_ID)) {
				projectNameCombo.setText(selectedProject.getName());
				validateProjectRequirements(selectedProject);
				model.setProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME, selectedProject.getName());
			}
		} catch (CoreException ce) {
			// Ignore
		}
		if (projectName == null && names.length > 0)
			projectName = names[0];

		if ((projectNameCombo.getText() == null || projectNameCombo.getText().length() == 0) && projectName != null) {
			projectNameCombo.setText(projectName);
			validateProjectRequirements(ProjectUtilities.getProject(projectName));
			model.setProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME, projectName);
		}
	}
	
	protected IPackageFragmentRoot getPackageFragmentRoot(IPackageFragment packageFragment) {
		if (packageFragment == null)
			return null;
		else if (packageFragment.getParent() instanceof IPackageFragment)
			return getPackageFragmentRoot((IPackageFragment) packageFragment.getParent());
		else if (packageFragment.getParent() instanceof IPackageFragmentRoot)
			return (IPackageFragmentRoot) packageFragment.getParent();
		else
			return null;
	}

	/**
	 * Process browsing when the Browse... button have been pressed.
	 * Allow choosing of package for the dynamic entity
	 */
	protected void handlePackageButtonPressed() {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), new JavaElementLabelProvider(
				JavaElementLabelProvider.SHOW_DEFAULT));
		dialog.setTitle(EclipseLinkUiMessages.DynamicEntityClassWizardPage_packageSelectionDialog_title);
		dialog.setMessage(EclipseLinkUiMessages.DynamicEntityClassWizardPage_packageSelectionDialog_desc);
		dialog.setEmptyListMessage(EclipseLinkUiMessages.DynamicEntityClassWizardPage_packageSelectionDialog_msg);
		dialog.setElements(this.getPackages());
		if (dialog.open() == Window.OK) {
			IPackageFragment fragment = (IPackageFragment) dialog.getFirstResult();
			if (fragment != null) {
				packageText.setText(fragment.getElementName());
			} else {
				packageText.setText(EclipseLinkUiMessages.DynamicEntityClassWizardPage_emptyString);
			}
		}
	}

	/**
	 * Return existing packages cross the selected project
	 * Only one of these packages that have the same name will be returned
	 */
	protected IJavaElement[] getPackages() {
		IPackageFragmentRoot[] packRoots = (IPackageFragmentRoot[]) model.getProperty(INewJavaClassDataModelProperties.JAVA_PACKAGE_FRAGMENT_ROOT);
		if (packRoots.length <= 0) return new IJavaElement[0];
		IJavaElement[] packages = new IJavaElement[0];
		try {
			ArrayList<IJavaElement> pkList = new ArrayList<IJavaElement>();
			for (IPackageFragmentRoot packageRoot : packRoots) {
				for (IJavaElement element : packageRoot.getChildren()) {
					// eliminate the duplicate package names
					if (!CollectionTools.contains(getJavaElementNames(pkList), element.getElementName())) {
						pkList.add(element);
					}
				}
			}
			packages = new IJavaElement[pkList.size()];
			pkList.toArray(packages);
		} catch (JavaModelException e) {
			// Do nothing
		}
		return packages;
	}
	
	/**
	 * Returns the names of the given list of elements
	 */
	private Iterable<String> getJavaElementNames(List<IJavaElement> elements) {
		return new TransformationIterable<IJavaElement, String>(elements) {
			@Override
			protected String transform(IJavaElement element) {
				return element.getElementName();
			}
		};
	}
	
	private IProject getSelectedProject() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return null;
		ISelection selection = window.getSelectionService().getSelection();
		if (selection == null)
			return null;
		if (!(selection instanceof IStructuredSelection)) 
			return null;
		IJavaElement element = getInitialJavaElement(selection);
		if (element != null && element.getJavaProject() != null)
			return element.getJavaProject().getProject();
		IStructuredSelection stucturedSelection = (IStructuredSelection) selection;
		if (stucturedSelection.getFirstElement() instanceof EObject)
			return ProjectUtilities.getProject(stucturedSelection.getFirstElement());
		IProject project = getExtendedSelectedProject(stucturedSelection.getFirstElement());
		if(project != null) {
			return project;
		}
		if(selection instanceof TreeSelection && (((TreeSelection)selection).getPaths().length > 0)){
			TreePath path = (((TreeSelection)selection).getPaths()[0]);
			if(path.getSegmentCount() > 0 && path.getSegment(0) instanceof IProject) {
				return (IProject) path.getSegment(0);
			}
		}
		return null;
	}
	
	protected IProject getExtendedSelectedProject(Object selection) {
		return null;
	}

	private IPackageFragment getSelectedPackageFragment() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return null;
		ISelection selection = window.getSelectionService().getSelection();
		if (selection == null)
			return null;
		IJavaElement element = getInitialJavaElement(selection);
		if (element != null) {
			if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
				return (IPackageFragment) element;
			} else if (element.getElementType() == IJavaElement.COMPILATION_UNIT) { 
				IJavaElement parent = ((ICompilationUnit) element).getParent();
				if (parent.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
					return (IPackageFragment) parent;
				}
			} else if (element.getElementType() == IJavaElement.TYPE) {
				return ((IType) element).getPackageFragment();
			}
		}
		return null;
	}

	/**
	 * Utility method to inspect a selection to find a Java element.
	 * 
	 * @param selection the selection to be inspected
	 * @return a Java element to be used as the initial selection, or
	 *         <code>null</code>, if no Java element exists in the given
	 *         selection
	 */
	protected IJavaElement getInitialJavaElement(ISelection selection) {
		IJavaElement jelem = null;
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
			jelem = getJavaElement(selectedElement);
			if (jelem == null) {
				IResource resource = getResource(selectedElement);
				if (resource != null && resource.getType() != IResource.ROOT) {
					while (jelem == null && resource.getType() != IResource.PROJECT) {
						resource = resource.getParent();
						jelem = (IJavaElement) resource.getAdapter(IJavaElement.class);
					}
					if (jelem == null) {
						jelem = JavaCore.create(resource); // java project
					}
				}
			}
		}
		if (jelem == null) {
			IWorkbenchWindow window= PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window == null)
				return null;
			IWorkbenchPart part = window.getActivePage().getActivePart();
			if (part instanceof ContentOutline) {
				part = window.getActivePage().getActiveEditor();
			}

			if (part instanceof IViewPartInputProvider) {
				Object elem = ((IViewPartInputProvider) part).getViewPartInput();
				if (elem instanceof IJavaElement) {
					jelem = (IJavaElement) elem;
				}
			}
		}

		if (jelem == null || jelem.getElementType() == IJavaElement.JAVA_MODEL) {
			try {
				IJavaProject[] projects = JavaCore.create(getWorkspaceRoot()).getJavaProjects();
				if (projects.length == 1) {
					jelem = projects[0];
				}
			} catch (JavaModelException e) {
				JptJpaEclipseLinkUiPlugin.log(e);
			}
		}
		return jelem;
	}

	protected IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	protected void validateProjectRequirements(IProject project)
	{
		// nothing to do in most cases
	}
	
	protected IJavaElement getJavaElement(Object obj) {
		if (obj == null)
			return null;
		
		if (obj instanceof IJavaElement) 
			return (IJavaElement) obj;
		
		if (obj instanceof IAdaptable) 
			return (IJavaElement) ((IAdaptable) obj).getAdapter(IJavaElement.class);
			
		return (IJavaElement) Platform.getAdapterManager().getAdapter(obj, IJavaElement.class);
	}
	
	protected IResource getResource(Object obj) {
		if (obj == null)
			return null;
		
		if (obj instanceof IResource) 
			return (IResource) obj;
		
		if (obj instanceof IAdaptable) 
			return (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
			
		return (IResource) Platform.getAdapterManager().getAdapter(obj, IResource.class);
	}
}
