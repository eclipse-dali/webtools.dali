/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.jst.j2ee.internal.wizard.NewJavaClassWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class EntityClassWizardPage extends NewJavaClassWizardPage{

	private static final String JPA_FACET = "jpt.jpa";//$NON-NLS-1$
	private static final String XML_EXTENSION = ".xml";//$NON-NLS-1$
	private static final String PERSISTENCE_XML = "persistence.xml";//$NON-NLS-1$
	private static final String META_INF = "META-INF";//$NON-NLS-1$
	private static final String EMPTY = "";//$NON-NLS-1$
	private static final char SLASH = '/'; //$NON-NLS-1$
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

	/* Create top level composite (class properties) and add the entity's specific inheritance group
	 * @see org.eclipse.jst.j2ee.internal.wizard.NewJavaClassWizardPage#createTopLevelComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = super.createTopLevelComposite(parent);
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
				ormXmlName.setEnabled(isChecked);	
				browseButton.setEnabled(isChecked);
				if (isFirstCheck) {
					ormXmlName.setText(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
					isFirstCheck = false;
				}
				//Disable the choose alternative XML : see enhancement request 152461
				//The creation of alternative mapping is problematic 
				disableMappingXMLChooseGroup();
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
		inheritanceButton = createCheckButton(group, GridData.HORIZONTAL_ALIGN_FILL, 1/*horizontal span*/, EntityWizardMsg.INHERITANCE_GROUP, IEntityDataModelProperties.INHERITANCE);
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
		ormXmlName.setEnabled(false);
		//Disable the choose alternative XML : see enhancement request 152461
		//The creation of alternative mapping is problematic
		disableMappingXMLChooseGroup();		
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
		ISelectionStatusValidator validator = getDialogSelectionValidator();		
		ViewerFilter filter = getDialogViewerFilter();
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new DecoratingLabelProvider(new WorkbenchLabelProvider(), PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());
		SelectMappingXMLDialog dialog = new SelectMappingXMLDialog(getShell(), labelProvider, contentProvider);
		dialog.setValidator(validator);
		dialog.setTitle(EntityWizardMsg.MAPPING_XML_TITLE);
		dialog.setMessage(EntityWizardMsg.CHOOSE_MAPPING_XML_MESSAGE);
		dialog.addFilter(filter);
				
		IFile rootFolder = project.getFile(J2EEProjectUtilities.getSourceContainers(project)[0].getResource().getName() + SLASH + ormXmlName.getText().trim());//TODO 
		dialog.setInput(J2EEProjectUtilities.getManifestFile(project).getParent().getParent());

		if (project != null)
			dialog.setInitialSelection(rootFolder);
		if (dialog.open() == Window.OK) {
			ormXmlName.setText(dialog.getChosenName());
			model.validateProperty(IEntityDataModelProperties.XML_NAME);
		}		
	}
	
	/**
	 * This method can be extended by subclasses, as it does some basic validation. 
	 * @return new instance of the Selection validator for the SelectMappingXMLDialog
	 */
	protected ISelectionStatusValidator getDialogSelectionValidator() {
		return new ISelectionStatusValidator() {
			public IStatus validate(Object[] selection) {
				if (selection != null && selection.length > 0 && selection[0] != null && !(selection[0] instanceof IProject))
					return WTPCommonPlugin.OK_STATUS;
				return WTPCommonPlugin.createErrorStatus(EntityWizardMsg.INCORRECT_XML_NAME);
			}
		};
	}
	
	/**
	 * This method create filter for the browse/add alternative mapping XML 
	 * @return new instance of viewer filter for the SelectMappingXMLDialog
	 */
	protected ViewerFilter getDialogViewerFilter() {
		return new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parent, Object element) {
				if (element instanceof IFolder) {
					IProject project = (IProject) getDataModel().getProperty(INewJavaClassDataModelProperties.PROJECT);
					IFolder folder = (IFolder)element;
					if (folder.contains(J2EEProjectUtilities.getManifestFile(project))) {
						return true;
					}
					return false;
				} else if (element instanceof IFile) {
					IFile file = (IFile) element;
					if (file.getName().endsWith(XML_EXTENSION) && !file.getName().equals(PERSISTENCE_XML)) {
						return true;
					}
				}
				return false;
			}
		};
	}
	
	private void disableMappingXMLChooseGroup() {
		//Disable the choose alternative XML : see enhancement request 152461
		//The creation of alternative mapping is problematic
		displayNameLabel.setEnabled(false);
		ormXmlName.setEnabled(false);
		browseButton.setEnabled(false);

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
	
	private class SelectMappingXMLDialog extends ElementTreeSelectionDialog{
		
		private Text newXmlName;
		private String xmlName = EMPTY;
		private IStatus currentStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID, IStatus.OK, EMPTY, null);
		
		public SelectMappingXMLDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
			super(parent, labelProvider, contentProvider);			
		}		
		
	    /*
	     * @see Dialog#createDialogArea(Composite)
	     */
	    @Override
		protected Control createDialogArea(Composite parent) {
	    	Composite composite = (Composite)super.createDialogArea(parent);	    	
			Label fileNameLabel = new Label(composite, SWT.LEFT);
			fileNameLabel.setText(EntityWizardMsg.XML_NAME_TITLE);
			fileNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
			newXmlName = new Text(composite, SWT.SINGLE | SWT.BORDER);
			newXmlName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));			
			newXmlName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {					
					super.keyReleased(e);					
					xmlName = newXmlName.getText();
					TreeSelection selection = (TreeSelection)getTreeViewer().getSelection();	
					IResource selectedResource = (IResource)selection.getFirstElement();
					if (selectedResource instanceof IFile) {
						getTreeViewer().setSelection(new TreeSelection(new TreePath(getSegments(selectedResource.getParent()))));
					}
					updateOKStatus();
				}
				
			});			
	        return composite;
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
			IResource selectedResource = (IResource)selection.getFirstElement();
			if (selectedResource instanceof IFile) {
				IFile file = (IFile)selectedResource;
				xmlName = file.getName();
				newXmlName.setText(xmlName);												
				currentStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID, IStatus.OK, EMPTY, null);				
			}
			if (!xmlName.endsWith(XML_EXTENSION)) {
				currentStatus = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.ERROR, EntityWizardMsg.INCORRECT_XML_NAME, null);
			} else {
				currentStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID, IStatus.OK, EMPTY, null);
			}		
			updateStatus(currentStatus);	    
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
		
		/**
		 * This method is for internal purposes only. It is intended to create correct
		 * selection of the resource resource
		 * @param resource
		 * @return the segments of the resource
		 */
		private Object[] getSegments(IResource resource) {
			Object[] segments = new Object[resource.getFullPath().segments().length];
			for (int i = segments.length - 1; i > -1; i--) {
				segments[i] = resource;
				resource = resource.getParent();
			}
			return segments;
		}	    
	}	
}
