/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.ProblemsLabelDecorator;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.jaxb.core.internal.SchemaGenerator;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.filters.ContainerFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.EmptyInnerPackageFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.NonArchiveOrExternalElementFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.NonContainerFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.NonJavaElementFilter;
import org.eclipse.jpt.jaxb.ui.internal.wizards.ProjectWizardPage;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

public class SchemaGeneratorWizardPage extends AbstractJarDestinationWizardPage {

	private IStructuredSelection initialSelection;
	private IJavaProject targetProject;

	// widgets
	private SettingsGroup settingsGroup;
	private NonContainerFilter projectFilter;
	
	private Button usesMoxyCheckBox;
	private boolean usesMoxy;

	static public String JPT_ECLIPSELINK_UI_PLUGIN_ID = "org.eclipse.jpt.eclipselink.ui";   //$NON-NLS-1$

	// other constants
	private static final int SIZING_SELECTION_WIDGET_WIDTH = 480;
	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 150;

	public static final String HELP_CONTEXT_ID = JptUiPlugin.PLUGIN_ID + ".wizard_jaxbschema_classes"; //$NON-NLS-1$
	
	// ********** constructor **********

	public SchemaGeneratorWizardPage(IStructuredSelection selection) {
		super("JAXB Schema Generator", selection, null);	//$NON-NLS-1$
		
		this.initialSelection = selection;
		this.setUsesMoxy(false);
		this.setTitle(JptJaxbUiMessages.SchemaGeneratorWizardPage_title);
		this.setDescription(JptJaxbUiMessages.SchemaGeneratorWizardPage_desc);
	}

	// ********** IDialogPage implementation  **********
    @Override
	public void createControl(Composite parent) {
		this.setPageComplete(false);
		this.setControl(this.buildTopLevelControl(parent));
	}

	@Override
    public void setVisible(boolean visible) {
    	super.setVisible(visible);

    	this.updateTargetProject();
    	this.setDefaultSchemaFile();
    	this.updateInputGroupTreeFilter();

		// default usesMoxy to true only when JPT EclipseLink bundle exists and MOXy is on the classpath
		this.updateUsesMoxy(this.jptEclipseLinkBundleExists() && this.moxyIsOnClasspath());

		// checkbox visible only if jpt.eclipselink.ui plugin is available
		// and EclipseLink MOXy is not on the classpath
		this.usesMoxyCheckBox.setVisible(this.jptEclipseLinkBundleExists() && ! this.moxyIsOnClasspath());
		
		this.validateProjectClasspath();
		this.giveFocusToDestination();
    }

	 // default the schema name to the project name	
	protected void setDefaultSchemaFile() {
		String defaultSchemaName = this.targetProject.getProject().getName() + SchemaGeneratorWizard.XSD_EXTENSION;
    	this.settingsGroup.schemaFileText.setText(defaultSchemaName);
    	this.settingsGroup.schemaFileText.setSelection(0, defaultSchemaName.length());
	}

	// ********** IWizardPage implementation  **********

	@Override
	public boolean isPageComplete() {
		boolean complete = this.validateDestinationGroup();
		if(complete) {
			complete = this.validateSourceGroup();
			if(complete) {
				this.validateProjectClasspath();
			}
		}
		return complete;
	}

	@Override
	public void setPreviousPage(IWizardPage page) {
		super.setPreviousPage(page);
		if(this.getControl() != null)
			this.updatePageCompletion();
	}
	
	// ********** intra-wizard methods **********

	protected IJavaProject getJavaProject() {
		return this.targetProject;
	}

	/**
	 * @return The schema relative path to the project.
	 */
	protected String getSchemaPath() {
		return this.settingsGroup.getSchemaPath();
	}
	
	protected Object[] getAllCheckedItems() {
		return ArrayTools.array(this.getInputGroup().getAllCheckedListItems());
	}
	
	protected boolean usesMoxy() {
		return this.usesMoxy;
	}

	// ********** validation **********
	
	@Override
	@SuppressWarnings("restriction")
	protected void updatePageCompletion() {
		super.updatePageCompletion();
	}

	@Override
	protected boolean validateDestinationGroup() {
		boolean complete = this.targetSchemaIsEmpty();
		if( ! complete) {
				this.setErrorMessage(JptJaxbUiMessages.SchemaGeneratorWizardPage_errorNoSchema);
				return false;
		}
		this.setErrorMessage(null);
		return true;
	}

	@Override
	protected boolean validateSourceGroup() {
		if(this.getAllCheckedItems().length == 0) {
			if(this.getErrorMessage() == null) {
				this.setErrorMessage(JptJaxbUiMessages.SchemaGeneratorWizardPage_errorNoPackage);
			}
			return false;
		}
		this.setErrorMessage(null);
		return true;
	}
	
	private void validateProjectClasspath() {
		//this line will suppress the "default package" warning (which doesn't really apply here
		//as the JAXB gen uses an org.example.schemaName package by default) and will clear the classpath warnings when necessary
		setMessage(null);
		
		if( ! this.genericJaxbIsOnClasspath()) {
			this.displayWarning(JptJaxbUiMessages.SchemaGeneratorWizardPage_jaxbLibrariesNotAvailable);
		}
		else if(this.usesMoxy() && ! this.moxyIsOnClasspath()) {
			//this message is being truncated by the wizard width in some cases
			this.displayWarning(JptJaxbUiMessages.SchemaGeneratorWizardPage_moxyLibrariesNotAvailable);
		}

		//this code will intelligently remove our classpath warnings when they are present but no longer apply (as an alternative 
		//to setting the message to null continuously as is currently done)
//		else if( this.getMessage() != null){
//			if (this.getMessage().equals(JptJaxbUiMessages.ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable) ||
//					this.getMessage().equals(JptJaxbUiMessages.ClassesGeneratorWizardPage_moxyLibrariesNotAvailable)) { 
//				setMessage(null);
//			}
//		}
	}

	/**
	 * Test if the Jaxb compiler is on the classpath.
	 */
	private boolean genericJaxbIsOnClasspath() {
		try {
			String className = SchemaGenerator.JAXB_GENERIC_SCHEMA_GEN_CLASS;
			IType genClass = this.targetProject.findType(className);
			return (genClass != null);
		} 
		catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}

	// ********** internal methods **********

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, HELP_CONTEXT_ID);
		
		this.settingsGroup = new SettingsGroup(composite);

		this.usesMoxyCheckBox = this.buildUsesMoxyCheckBox(composite);
		
		Dialog.applyDialogFont(parent);
		return composite;
	}
	
	private void updateTargetProject() {
    	IWizardPage previousPage = this.getPreviousPage();
    	
		if(previousPage instanceof ProjectWizardPage) {
			// get project from previousPage
			this.targetProject = ((ProjectWizardPage)previousPage).getJavaProject();
		}
		else if(initialSelection != null && ! this.initialSelection.isEmpty()) {
			// no previousPage - get project from initialSelection
			this.targetProject = this.getProjectFromInitialSelection();
		}		
	}

    private void updateInputGroupTreeFilter() {
    	if(this.projectFilter != null) {
    		this.getInputGroup().removeTreeFilter(this.projectFilter);
    	}
    	this.projectFilter = new NonContainerFilter(this.targetProject.getProject().getName());
		this.getInputGroup().addTreeFilter(this.projectFilter);
    }

    private IJavaProject getProjectFromInitialSelection() {
    	IJavaProject project = null;

		Object firstElement = initialSelection.getFirstElement();
		if(firstElement instanceof IJavaElement) {
			IJavaElement javaElement = (IJavaElement)firstElement;
			int type = javaElement.getElementType();
			if(type == IJavaElement.JAVA_PROJECT) {
				project = (IJavaProject)javaElement;
			}
			else if(type == IJavaElement.PACKAGE_FRAGMENT) {
				project = ((IPackageFragment)javaElement).getJavaProject();
			}
		}
		return project;
    }
	
	private boolean targetSchemaIsEmpty() {
		return ! StringTools.stringIsEmpty(this.getSchemaPath());
	}
	
	private boolean jptEclipseLinkBundleExists() {
		return (this.getJptEclipseLinkBundle() != null);
	}
	
	private Bundle getJptEclipseLinkBundle() {
		return Platform.getBundle(JPT_ECLIPSELINK_UI_PLUGIN_ID);	// Cannot reference directly EL plugin.
	}

	private void setUsesMoxy(boolean usesMoxy){
		this.usesMoxy = usesMoxy;
	}
	
	private void updateUsesMoxy(boolean usesMoxy){
		this.setUsesMoxy(usesMoxy);
		this.usesMoxyCheckBox.setSelection(this.usesMoxy());
		this.validateProjectClasspath();
	}

	
	/**
	 * Test if the EclipseLink Jaxb compiler is on the classpath.
	 */
	private boolean moxyIsOnClasspath() {
		try {
			String className = SchemaGenerator.JAXB_ECLIPSELINK_SCHEMA_GEN_CLASS;
			IType genClass = this.targetProject.findType(className);
			return (genClass != null);
		}
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void displayWarning(String message) {
		this.setMessage(message, WARNING);
	}

	private CheckboxTreeAndListGroup getInputGroup() {
		return this.settingsGroup.inputGroup;
	}

	// ********** overrides **********
	/**
	 * Returns an iterator over this page's collection of currently-specified
	 * elements to be exported. This is the primary element selection facility
	 * accessor for subclasses.
	 *
	 * @return an iterator over the collection of elements currently selected for export
	 */
	@Override
	protected Iterator<?> getSelectedResourcesIterator() {
		return this.getInputGroup().getAllCheckedListItems();
	}

	@Override
	protected void update() {
		this.updatePageCompletion();
	}

	@Override
	public final void saveWidgetValues() {
		// do nothing
	}

	@Override
	protected void internalSaveWidgetValues() {
		// do nothing
	}

	@Override
	protected void restoreWidgetValues() {
		// do nothing
	}


	@Override
	protected void initializeJarPackage() {
		// do nothing
	}
	
	@Override
	protected void giveFocusToDestination() {
		this.settingsGroup.giveFocusToDestination();
	}

	// ********** UI components **********
	
	private Button buildUsesMoxyCheckBox(Composite parent) {

		Button checkBox = new Button(parent, SWT.CHECK);
		GridData gridData = new GridData();
		gridData.verticalIndent = 10;
		checkBox.setLayoutData(gridData);
		checkBox.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_usesMoxyImplementation);
		checkBox.setSelection(this.usesMoxy());
		checkBox.addSelectionListener(this.buildUsesMoxySelectionListener());
		
		return checkBox;
	}
	
	private SelectionListener buildUsesMoxySelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
				this.widgetSelected(event);
			}
			
			public void widgetSelected(SelectionEvent event) {
				updateUsesMoxy(usesMoxyCheckBox.getSelection());
				validateProjectClasspath();
			}
		};
	}
	
	// ********** SettingsGroup class **********

	private class SettingsGroup {

		private CheckboxTreeAndListGroup inputGroup;
		private Text schemaFileText;

		// ********** constructor **********

		private SettingsGroup(Composite parent) {
			super();
			initializeDialogUnits(parent);

			Composite composite = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			buildSchemaComposite(composite);

			
			// Input Tree
			createPlainLabel(composite, JptJaxbUiMessages.SchemaGeneratorWizardPage_packages);
			this.inputGroup = this.createInputGroup(composite);
	
			if(initialSelection != null)
				BusyIndicator.showWhile(parent.getDisplay(), new Runnable() {
					public void run() {
						setupBasedOnInitialSelections();
					}
				});
		}
		
		protected void buildSchemaComposite(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout(3, false);  // false = do not make columns equal width
			layout.marginWidth = 0;
			layout.marginTop = 0;
			layout.marginBottom = 10;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// Schema Location
			this.buildLabel(composite, JptJaxbUiMessages.SchemaGeneratorWizardPage_shemaLocation);
			this.schemaFileText = this.buildSchemaText(composite);
			this.buildBrowseButton(composite, 1);

		}
		// ********** intra-wizard methods **********
		
		/**
		 * @return The schema relative path to the project.
		 */
		protected String getSchemaPath() {
			return this.schemaFileText.getText();
		}

		protected void giveFocusToDestination() {
			this.schemaFileText.setFocus();
		}

		// ********** UI components **********
		
		private Label buildLabel(Composite parent, String text) {
			Label label = new Label(parent, SWT.LEFT);
			label.setText(text);
			label.setLayoutData(new GridData());
			return label;
		}
		
		private Text buildSchemaText(Composite parent) {
			
			Text text = new Text(parent, SWT.BORDER);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.grabExcessHorizontalSpace = true;
			text.setLayoutData(gridData);
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					updatePageCompletion();
				}
			});
			return text;
		}
		
		private Button buildBrowseButton(Composite parent, int horizontalSpan) {

			Button browseButton = new Button(parent, SWT.PUSH);
			browseButton.setText(JptJaxbUiMessages.SchemaGeneratorWizardPage_browse);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.horizontalSpan = horizontalSpan;
			browseButton.setLayoutData(gridData);
			browseButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}

				public void widgetSelected(SelectionEvent e) {

					String fileName = promptFile();
					if( ! StringTools.stringIsEmpty(fileName)) {
						schemaFileText.setText(makeRelativeToProjectPath(fileName));
					}
				}
			});
			return browseButton;
		}
		
		/**
		 * The browse button was clicked, its action invokes this action which should
		 * prompt the user to select a file and return it.
		 */
		private String promptFile() {
			FileDialog dialog = new FileDialog(getShell());
			dialog.setText(JptJaxbUiMessages.SchemaGeneratorWizardPage_chooseSchemaDialogTitle);
			dialog.setFilterPath(this.getFilterPath());
			dialog.setFilterExtensions(new String[] {"*.xsd"});   //$NON-NLS-1$
			String filePath = dialog.open();
			
			return (filePath != null) ? filePath : null;
		}
		
		/**
		 * Creates the checkbox tree and list for selecting resources.
		 *
		 * @param parent the parent control
		 */
		protected CheckboxTreeAndListGroup createInputGroup(Composite parent) {
			CheckboxTreeAndListGroup checkboxTreeGroup;
			
			int labelFlags = JavaElementLabelProvider.SHOW_BASICS
							| JavaElementLabelProvider.SHOW_OVERLAY_ICONS
							| JavaElementLabelProvider.SHOW_SMALL_ICONS;
			ITreeContentProvider treeContentProvider=
				new StandardJavaElementContentProvider() {
					@Override
					public boolean hasChildren(Object element) {
						// prevent the + from being shown in front of packages
						return !(element instanceof IPackageFragment) && super.hasChildren(element);
					}
				};
			final DecoratingLabelProvider provider = new DecoratingLabelProvider(new JavaElementLabelProvider(labelFlags), new ProblemsLabelDecorator(null));
			checkboxTreeGroup = new CheckboxTreeAndListGroup(
						parent,
						JavaCore.create(ResourcesPlugin.getWorkspace().getRoot()),
						treeContentProvider,
						provider,
						new StandardJavaElementContentProvider(),
						provider,
						SWT.NONE,
						SIZING_SELECTION_WIDGET_WIDTH,
						SIZING_SELECTION_WIDGET_HEIGHT);
			checkboxTreeGroup.addTreeFilter(new EmptyInnerPackageFilter());
			checkboxTreeGroup.setTreeComparator(new JavaElementComparator());
			checkboxTreeGroup.setListComparator(new JavaElementComparator());
			
			checkboxTreeGroup.addTreeFilter(new NonJavaElementFilter());
			checkboxTreeGroup.addTreeFilter(new NonArchiveOrExternalElementFilter());
			
			checkboxTreeGroup.addListFilter(new ContainerFilter());
			checkboxTreeGroup.addListFilter(new NonJavaElementFilter());
			
			checkboxTreeGroup.getTree().addListener(SWT.MouseUp, SchemaGeneratorWizardPage.this);
			checkboxTreeGroup.getTable().addListener(SWT.MouseUp, SchemaGeneratorWizardPage.this);

			ICheckStateListener listener  = new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent event) {
	                update();
	            }
	        };

	        checkboxTreeGroup.addCheckStateListener(listener);
	        return checkboxTreeGroup;
		}

		// ********** internal methods **********

		private String makeRelativeToProjectPath(String filePath) {
			Path path = new Path(filePath);
			IPath relativePath = path.makeRelativeTo(targetProject.getProject().getLocation());
			return relativePath.toOSString();
		}
		
		/**
		 * Returns the path that the dialog will use to filter the directories it shows to
		 * the argument, which may be null. 
		 * If the string is null, then the operating system's default filter path will be used. 
		 * <p>
		 * Note that the path string is platform dependent. For convenience, either
		 * '/' or '\' can be used as a path separator.
		 * </p>
		 *
		 * @return The filter path
		 */
		private String getFilterPath() {
			return targetProject.getProject().getLocation().toOSString();
		}

		private void setupBasedOnInitialSelections() {

			Iterator<?> iterator = initialSelection.iterator();
			while(iterator.hasNext()) {
				Object selectedElement = iterator.next();

				if(selectedElement instanceof IResource && !((IResource)selectedElement).isAccessible())
					continue;

				if(selectedElement instanceof IJavaElement && !((IJavaElement)selectedElement).exists())
					continue;

				if(selectedElement instanceof ICompilationUnit || selectedElement instanceof IClassFile || selectedElement instanceof IFile)
					this.inputGroup.initialCheckListItem(selectedElement);
				else {
					if(selectedElement instanceof IFolder) {
						// Convert resource to Java element if possible
						IJavaElement je = JavaCore.create((IResource)selectedElement);
						if(je != null && je.exists() &&  je.getJavaProject().isOnClasspath((IResource)selectedElement)) {
							
							selectedElement = je;
							je.toString();
						}
					}
					this.inputGroup.initialCheckTreeItem(selectedElement);
				}
			}

			TreeItem[] items = this.inputGroup.getTree().getItems();
			int i = 0;
			while(i < items.length && ! items[i].getChecked())
				i++;
			if(i < items.length) {
				this.inputGroup.getTree().setSelection(new TreeItem[] {items[i]});
				this.inputGroup.getTree().showSelection();
				this.inputGroup.populateListViewer(items[i].getData());
			}
		}
	}
}
