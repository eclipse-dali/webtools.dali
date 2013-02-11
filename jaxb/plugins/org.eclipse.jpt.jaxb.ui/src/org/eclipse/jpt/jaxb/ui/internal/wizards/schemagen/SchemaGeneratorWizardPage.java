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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
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
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jaxb.core.internal.gen.SchemaGenerator;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.filters.ContainerFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.EmptyInnerPackageFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.NonArchiveOrExternalElementFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.NonContainerFilter;
import org.eclipse.jpt.jaxb.ui.internal.filters.NonJavaElementFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

/**
 *  SchemaGeneratorWizardPage
 */
public class SchemaGeneratorWizardPage extends AbstractJarDestinationWizardPage {

	private IJavaProject targetProject;

	// widgets
	private SettingsGroup settingsGroup;
	private NonContainerFilter projectFilter;
	
	private Button usesMoxyCheckBox;
	private boolean usesMoxy;

	static public String JPT_ECLIPSELINK_UI_PLUGIN_ID = "org.eclipse.jpt.jpa.eclipselink.ui";   //$NON-NLS-1$

	// other constants
	private static final int SIZING_SELECTION_WIDGET_WIDTH = 480;
	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 150;

	public static final String HELP_CONTEXT_ID = "org.eclipse.jpt.jaxb.ui.wizard_jaxbschema_classes"; //$NON-NLS-1$
	
	// ********** constructor **********

	public SchemaGeneratorWizardPage(IStructuredSelection selection) {
		super("JAXB Schema Generator", selection, null);	//$NON-NLS-1$

		this.setUsesMoxy(false);
		this.setTitle(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_PAGE_TITLE);
		this.setDescription(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_PAGE_DESC);
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

    	if(visible) {
	    	this.updateTargetProject();
	    	this.updateInputGroupTreeFilter();
	
			// default usesMoxy to true only when JPT EclipseLink bundle exists and MOXy is on the classpath
			this.updateUsesMoxy(this.jptEclipseLinkBundleExists() && this.moxyIsOnClasspath());
	
			// checkbox visible only if jpt.eclipselink.ui plugin is available
			// and EclipseLink MOXy is not on the classpath
			this.usesMoxyCheckBox.setVisible(this.jptEclipseLinkBundleExists() && ! this.moxyIsOnClasspath());
			
			this.validateProjectClasspath();
    	}
    }

	// ********** IWizardPage implementation  **********

	@Override
	public boolean isPageComplete() {
		boolean complete = this.validateSourceGroup();
			if(complete) {
				this.validateProjectClasspath();
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
		// do nothing
		return true;
	}

	@Override
	protected boolean validateSourceGroup() {
		if(this.getAllCheckedItems().length == 0) {
			if(this.getErrorMessage() == null) {
				this.setErrorMessage(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_PAGE_ERROR_NO_PACKAGE);
			}
			return false;
		}
		this.setErrorMessage(null);
		return true;
	}
	
	private void validateProjectClasspath() {
		if(this.targetProject == null) {		// project selected available yet
			return;
		}
		//this line will suppress the "default package" warning (which doesn't really apply here
		//as the JAXB gen uses an org.example.schemaName package by default) and will clear the classpath warnings when necessary
		setMessage(null);
		
		if( ! this.genericJaxbIsOnClasspath()) {
			this.displayWarning(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_PAGE_JAXB_LIBRARIES_NOT_AVAILABLE);
		}
		else if(this.usesMoxy() && ! this.moxyIsOnClasspath()) {
			//this message is being truncated by the wizard width in some cases
			this.displayWarning(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_PAGE_MOXY_LIBRARIES_NOT_AVAILABLE);
		}

		//this code will intelligently remove our classpath warnings when they are present but no longer apply (as an alternative 
		//to setting the message to null continuously as is currently done)
//		else if( this.getMessage() != null){
//			if (this.getMessage().equals(JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_PAGE_jaxbLibrariesNotAvailable) ||
//					this.getMessage().equals(JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_PAGE_moxyLibrariesNotAvailable)) { 
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
    	
		this.targetProject = ((SchemaGeneratorWizard)this.getWizard()).getJavaProject();
	}

    private void updateInputGroupTreeFilter() {
    	this.getInputGroup().setAllSelections(false);
    	
    	if(this.projectFilter != null) {
    		this.getInputGroup().removeTreeFilter(this.projectFilter);
    	}
    	this.projectFilter = new NonContainerFilter(this.targetProject.getProject().getName());
		this.getInputGroup().addTreeFilter(this.projectFilter);
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
		// do nothing
	}

	// ********** UI components **********
	
	private Button buildUsesMoxyCheckBox(Composite parent) {

		Button checkBox = new Button(parent, SWT.CHECK);
		GridData gridData = new GridData();
		gridData.verticalIndent = 10;
		checkBox.setLayoutData(gridData);
		checkBox.setText(JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_PAGE_USES_MOXY_IMPLEMENTATION);
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
			createPlainLabel(composite, JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_PAGE_PACKAGES);
			this.inputGroup = this.createInputGroup(composite);
	
		}
		
		protected void buildSchemaComposite(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout(3, false);  // false = do not make columns equal width
			layout.marginWidth = 0;
			layout.marginTop = 0;
			layout.marginBottom = 10;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		}

		// ********** UI components **********
		
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
	}
}
