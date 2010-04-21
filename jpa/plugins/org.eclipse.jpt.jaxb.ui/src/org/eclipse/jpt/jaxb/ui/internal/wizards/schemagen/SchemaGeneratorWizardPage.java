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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Bundle;

public class SchemaGeneratorWizardPage extends AbstractJarDestinationWizardPage {

	private IStructuredSelection initialSelection;
	private IJavaProject targetProject;

	// widgets
	private SettingsGroup settingsGroup;
	
	private Button usesMoxyCheckBox;
	private boolean usesMoxy;

	static public String JPT_ECLIPSELINK_UI_PLUGIN_ID = "org.eclipse.jpt.eclipselink.ui";   //$NON-NLS-1$

	// other constants
	private static final int SIZING_SELECTION_WIDGET_WIDTH = 480;
	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 150;

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

    	this.updateInputGroupTreeFilter();

		// default usesMoxy to true only when JPT EclipseLink bundle exists and MOXy is on the classpath
		this.updateUsesMoxy(this.jptEclipseLinkBundleExists() && this.moxyIsOnClasspath()); 
		this.giveFocusToDestination();
    }

	// ********** IWizardPage implementation  **********

	@Override
	public boolean isPageComplete() {
		boolean complete = this.validateSourceGroup();
		complete = this.validateDestinationGroup() && complete;
		if(complete) {
			this.setErrorMessage(null);
		}
		return complete;
	}

	public void setPreviousPage(IWizardPage page) {
		super.setPreviousPage(page);
		if(this.getControl() != null)
			this.updatePageCompletion();
	}
	
	// ********** intra-wizard methods **********

	protected IJavaProject getJavaProject() {
		return this.targetProject;
	}

	protected String getTargetSchema() {
		return this.settingsGroup.getTargetSchema();
	}

	/**
	 * @return The schema relative path to the project.
	 */
	protected String getTargetSchemaPath() {
		return this.settingsGroup.getTargetSchemaPath();
	}
	
	protected Object[] getAllCheckedItems() {
		return ArrayTools.array(this.getInputGroup().getAllCheckedListItems());
	}
	
	protected boolean usesMoxy() {
		return this.usesMoxy;
	}

	// ********** validation **********
	
	@Override
	protected void updatePageCompletion() {
		boolean pageComplete = this.isPageComplete();
		this.setPageComplete(pageComplete);
		if(pageComplete) {
			this.setErrorMessage(null);
		}
	}

	@Override
	protected boolean validateDestinationGroup() {
		boolean complete = this.targetSchemaIsEmpty();
		if( ! complete) {
				this.setErrorMessage(JptJaxbUiMessages.SchemaGeneratorWizardPage_errorNoSchema);
		}
		return complete;
	}

	@Override
	protected boolean validateSourceGroup() {
		if(this.getAllCheckedItems().length == 0) {
			if(this.getMessage() == null) {
				this.setErrorMessage(JptJaxbUiMessages.SchemaGeneratorWizardPage_errorNoPackage);
			}
			return false;
		}
		return true;
	}
	
	private void validateProjectClasspath() {
		//this line will suppress the "default package" warning (which doesn't really apply here
		//as the JAXB gen uses an org.example.schemaName package by default) and will clear the classpath warnings when necessary
		setMessage(null);
		
		if( ! this.genericJaxbIsOnClasspath()) {
			this.displayWarning(JptJaxbUiMessages.ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable);
		}
		else if(this.usesMoxy() && ! this.moxyIsOnClasspath()) {
			//this message is being truncated by the wizard width in some cases
			this.displayWarning(JptJaxbUiMessages.ClassesGeneratorWizardPage_moxyLibrariesNotAvailable);
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
		
		this.settingsGroup = new SettingsGroup(composite);

		this.usesMoxyCheckBox = this.buildUsesMoxyCheckBox(composite);
		
		Dialog.applyDialogFont(parent);
		return composite;
	}
	
    private void updateInputGroupTreeFilter() {
    	IWizardPage previousPage = this.getPreviousPage();
    	
		if(previousPage instanceof ProjectWizardPage) {
			// get project from previousPage
			this.targetProject = ((ProjectWizardPage)previousPage).getProject();
		}
		else if(initialSelection != null && ! this.initialSelection.isEmpty()) {
			// no previousPage - get project from initialSelection
			this.targetProject = this.getProjectFromInitialSelection();
		}
		if(this.targetProject != null) {
			this.getInputGroup().addTreeFilter(new NonContainerFilter(this.targetProject.getProject().getName()));
		}
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
		return ! StringTools.stringIsEmpty(this.getTargetSchema());
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
			String className = SchemaGenerator.JAXB_GENERIC_SCHEMA_GEN_CLASS;
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
	};
	
	@Override
	protected void giveFocusToDestination() {
		this.settingsGroup.giveFocusToDestination();
	}

	// ********** UI components **********
	
	private Button buildUsesMoxyCheckBox(Composite parent) {

		 Button checkBox = new Button(parent, SWT.CHECK);
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
			}
		};
	}
	
	// ********** SettingsGroup class **********

	private class SettingsGroup {

		private CheckboxTreeAndListGroup inputGroup;
		private Text schemaLocationText;
		private Text targetSchemaText;

		// ********** constructor **********

		private SettingsGroup(Composite parent) {
			super();
			initializeDialogUnits(parent);

			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout());
			composite.setLayoutData(
				new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

			Group group = new Group(composite, SWT.NONE);
			group.setLayout(new GridLayout(4, false));  // false = do not make columns equal width
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setText(JptJaxbUiMessages.SchemaGeneratorWizardPage_settingsGoupTitle);

			// Schema Location
			this.buildLabel(group, 1, JptJaxbUiMessages.SchemaGeneratorWizardPage_shemaLocation);
			this.schemaLocationText = this.buildTargetSchemaText(group, 2);
			this.buildBrowseButton(group, 1);
			
			// Target Schema
			this.buildLabel(group, 1, JptJaxbUiMessages.SchemaGeneratorWizardPage_shema);
			this.targetSchemaText = this.buildTargetSchemaText(group, 2);

			new Label(composite, SWT.NONE); // vertical spacer
			
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

		// ********** intra-wizard methods **********
		
		protected String getTargetSchema() {
			return this.targetSchemaText.getText();
		}
		
		/**
		 * @return The schema relative path to the project.
		 */
		protected String getTargetSchemaPath() {
			return this.schemaLocationText.getText();
		}

		protected void giveFocusToDestination() {
			this.targetSchemaText.setFocus();
		}

		// ********** UI components **********
		
		private Label buildLabel(Composite parent, int horizontalSpan, String text) {
			Label label = new Label(parent, SWT.LEFT);
			label.setText(text);
			GridData gridData = new GridData();
			gridData.horizontalSpan = horizontalSpan;
			label.setLayoutData(gridData);
			return label;
		}
		
		private Text buildTargetSchemaText(Composite parent, int horizontalSpan) {
			
			Text text = new Text(parent, SWT.BORDER);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = horizontalSpan;
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

					String folderName = promptFolder();
					if( ! StringTools.stringIsEmpty(folderName)) {
						
						folderName = makeRelativeToProjectPath(folderName);
						schemaLocationText.setText(folderName);
					}
				}
			});
			return browseButton;
		}
		
		/**
		 * The browse button was clicked, its action invokes this action which should
		 * prompt the user to select a folder and set it.
		 */
		private String promptFolder() {

			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setMessage(JptJaxbUiMessages.SchemaGeneratorWizardPage_directoryDialogDesc);
			dialog.setText(JptJaxbUiMessages.SchemaGeneratorWizardPage_directoryDialogTitle);
			dialog.setFilterPath(this.getFilterPath());
			
			return dialog.open();
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
