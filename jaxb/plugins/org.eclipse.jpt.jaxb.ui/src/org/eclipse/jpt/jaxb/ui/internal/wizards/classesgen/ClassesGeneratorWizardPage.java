/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.util.TableLayoutComposite;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.JaxbPreferences;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGenerator;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.osgi.framework.Bundle;

/**
 *  ClassesGeneratorWizardPage
 */
public class ClassesGeneratorWizardPage extends NewTypeWizardPage {
	static public final String JPT_ECLIPSELINK_UI_PLUGIN_ID = "org.eclipse.jpt.jpa.eclipselink.ui";   //$NON-NLS-1$
	static public final String XML_FILTER = "*.xml";   //$NON-NLS-1$
	static public final String[] bindingdFilesFilterExtensions = {".xjb",".xml",".xbd"}; //$NON-NLS-1$
	public static final String ECLIPSELINK_PLATFORM_GROUP_ID = "eclipselink";   //$NON-NLS-1$

	public static final String HELP_CONTEXT_ID = "org.eclipse.jpt.jaxb.ui.configure_jaxb_class_generation_dialog"; //$NON-NLS-1$

	final ResourceManager resourceManager;
	private SettingsGroup settingsGroup;
	
	private String targetFolder;
	private String targetPackage;
	
	private Button usesMoxyCheckBox;
	private boolean usesMoxy;

	// ********** constructor **********
	
	public ClassesGeneratorWizardPage(ResourceManager resourceManager) {
		super(true, "Classes Generator"); //$NON-NLS-1$

		this.resourceManager = resourceManager;
		this.setDescription(JptJaxbUiMessages.ClassesGeneratorWizardPage_desc);
	}

	// ********** UI components **********

	public void createControl(Composite parent) {
		this.setPageComplete(false);
		this.setControl(this.buildTopLevelControl(parent));
	}

	// ********** intra-wizard methods **********
	
	protected String getTargetFolder() {
		return this.targetFolder;
	}

	protected String getTargetPackage() {
		return this.targetPackage;
	}

	protected String getCatalog() {
		return this.settingsGroup.getCatalog();
	}

	protected String[] getBindingsFileNames() {
		return this.settingsGroup.getBindingsFileNames();
	}
	
	protected boolean usesMoxy() {
		return this.usesMoxy;
	}
	
	private void setUsesMoxy(boolean usesMoxy){
		this.usesMoxy = usesMoxy;
	}
	
	// ********** overrides **********

	@Override
	protected IStatus packageChanged() {
		IStatus status = super.packageChanged(); 
		IPackageFragment packageFragment = this.getPackageFragment();
		if( ! status.matches(IStatus.ERROR)) {
			String newPackageName = packageFragment.getElementName();

			if( ! this.isPackageInitialInitialization(newPackageName)) {
				IProject project = this.getJavaProject().getProject();
				if( ! newPackageName.equals(JaxbPreferences.getClassGenPackage(project))) {
					JaxbPreferences.setClassGenPackage(project, newPackageName);
				}
			}
			this.targetPackage = packageFragment.getElementName();
		}
		return status;
	}
	
	@Override
	protected IStatus containerChanged() {
		IStatus status = super.containerChanged();
		String srcFolder = getPackageFragmentRootText();
		if( !status.matches(IStatus.ERROR) ){
				this.targetFolder = srcFolder.substring(srcFolder.indexOf("/") + 1);
		}
		return status;
	}
	
	@Override
	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);
		if(this.fContainerStatus.matches(IStatus.ERROR)) {
			this.updateStatus(fContainerStatus);
		}
		else if( ! this.fPackageStatus.matches(IStatus.OK) ) {
			this.updateStatus(fPackageStatus);
		} 
		else {
			this.updateStatus(Status.OK_STATUS);
		}
		this.validateProjectClasspath();
	}
	
	/**
	 * Override setVisible to insure that our more important warning
	 * message about classpath problems is displayed to the user first.
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible) {
			this.initContainerPage(((ClassesGeneratorWizard)this.getWizard()).getJavaProject());

			if(this.projectPlatformIsJaxb()) {
				this.setUsesMoxy(this.projectJaxbPlatformIsEclipseLink());
				this.usesMoxyCheckBox.setVisible(false);
			}
			else {
				// default usesMoxy to true only when JPT EclipseLink bundle exists and MOXy is on the classpath
				this.setUsesMoxy((this.jptEclipseLinkBundleExists() && this.moxyIsOnClasspath())); 
		
				// checkbox is visible only if jpt.eclipselink.ui plugin is available
				// and EclipseLink MOXy is not on the classpath
				this.usesMoxyCheckBox.setVisible(this.jptEclipseLinkBundleExists() && ! this.moxyIsOnClasspath());
			}
			this.validateProjectClasspath();

			if(this.getPackageText().equals("")) {  //$NON-NLS-1$
				String packagePref = (JaxbPreferences.getClassGenPackage(this.getJavaProject().getProject()));
				if( ! StringTools.isBlank(packagePref)) {
					this.setPackageName(this.getPackageFragmentRoot(), packagePref);
				}
			}

			String schemaName = ((ClassesGeneratorWizard) getWizard()).getLocalSchemaUri().lastSegment();
			this.setTitle(NLS.bind(JptJaxbUiMessages.ClassesGeneratorWizardPage_title, schemaName));
		}
	}

	/** 
	 * Override to allow selection of source folder in current project only
	 * @see org.eclipse.jdt.ui.wizards.NewContainerWizardPage#chooseContainer()
	 * Only 1 line in this code is different from the parent
	 */
	@Override
	protected IPackageFragmentRoot chooseContainer() {
		Class<?>[] acceptedClasses = new Class[] { IPackageFragmentRoot.class, IJavaProject.class };
		TypedElementSelectionValidator validator= new TypedElementSelectionValidator(acceptedClasses, false) {
			@Override
			public boolean isSelectedValid(Object element) {
				try {
					if(element instanceof IJavaProject) {
						IJavaProject jproject= (IJavaProject)element;
						IPath path= jproject.getProject().getFullPath();
						return (jproject.findPackageFragmentRoot(path) != null);
					} 
					else if(element instanceof IPackageFragmentRoot) {
						return (((IPackageFragmentRoot)element).getKind() == IPackageFragmentRoot.K_SOURCE);
					}
					return true;
				} 
				catch (JavaModelException e) {
					JptJaxbUiPlugin.instance().logError(e); // just log, no UI in validation
				}
				return false;
			}
		};

		acceptedClasses= new Class[] { IJavaModel.class, IPackageFragmentRoot.class, IJavaProject.class };
		ViewerFilter filter= new TypedViewerFilter(acceptedClasses) {
			@Override
			public boolean select(Viewer viewer, Object parent, Object element) {
				if(element instanceof IPackageFragmentRoot) {
					try {
						return (((IPackageFragmentRoot)element).getKind() == IPackageFragmentRoot.K_SOURCE);
					} 
					catch (JavaModelException e) {
						JptJaxbUiPlugin.instance().logError(e); // just log, no UI in validation
						return false;
					}
				}
				return super.select(viewer, parent, element);
			}
		};

		StandardJavaElementContentProvider provider= new StandardJavaElementContentProvider();
		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(getShell(), labelProvider, provider);
		dialog.setValidator(validator);
		dialog.setComparator(new JavaElementComparator());
		dialog.setTitle(JptJaxbUiMessages.ClassesGeneratorWizardPage_sourceFolderSelectionDialog_title);
		dialog.setMessage(JptJaxbUiMessages.ClassesGeneratorWizardPage_chooseSourceFolderDialog_desc);
		dialog.addFilter(filter);
		//set the java project as the input instead of the workspace like the NewContainerWizardPage was doing
		//******************************************************//
		dialog.setInput(this.getJavaProject());     			//
		//******************************************************//
		dialog.setInitialSelection(getPackageFragmentRoot());
		dialog.setHelpAvailable(false);

		if(dialog.open() == Window.OK) {
			Object element= dialog.getFirstResult();
			if(element instanceof IJavaProject) {
				IJavaProject jproject= (IJavaProject)element;
				return jproject.getPackageFragmentRoot(jproject.getProject());
			}
			else if(element instanceof IPackageFragmentRoot) {
				return (IPackageFragmentRoot)element;
			}
			return null;
		}
		return null;
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
	
	private Button buildUsesMoxyCheckBox(Composite parent) {

		Button checkBox = new Button(parent, SWT.CHECK);
		GridData gridData = new GridData();
		gridData.horizontalIndent = 5;
		gridData.verticalIndent = 5;
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
				setUsesMoxy(usesMoxyCheckBox.getSelection());
				validateProjectClasspath();
			}
		};
	}
	
	private boolean jptEclipseLinkBundleExists() {
		return (this.getJptEclipseLinkBundle() != null);
	}
	
	private Bundle getJptEclipseLinkBundle() {
		return Platform.getBundle(JPT_ECLIPSELINK_UI_PLUGIN_ID);	// Cannot reference directly EL plugin.
	}
	
	private void validateProjectClasspath() {
		//this line will suppress the "default package" warning (which doesn't really apply here
		//as the JAXB gen uses an org.example.schemaName package by default) and will clear the classpath warnings when necessary
		setMessage(null);
		
		if( ! this.genericJaxbIsOnClasspath()) {
			this.displayWarning(JptJaxbUiMessages.ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable);
		}
		else if(this.usesMoxy() && ! this.eclipseLinkMoxyIsOnClasspath()) {
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
		if(this.genericJaxbNonJdkIsOnClasspath()) {
			return true;
		}
		else {
			if(this.projectJre15OrLower()) {
				return false;
			}
			else if(this.genericJaxbJdkIsOnClasspath()) {
				return true;
			}
			else {
				return this.toolsJarExists();
			}
		}
	}

	private boolean projectJre15OrLower() {
		try {
			IVMInstall vm = this.getVMInstall();
			if(vm == null) {
				return false;
			}
			if (vm instanceof IVMInstall2) {
				String javaVersion = ((IVMInstall2) vm).getJavaVersion();
				if(javaVersion != null) {
					String versionString = javaVersion.substring(0, javaVersion.lastIndexOf('.'));
					Float javaVersionNumber = Float.valueOf(versionString);
					if( javaVersionNumber <= 1.5) {
						return true;
					}
				}
			}
			return false;
		}
		catch (CoreException e) {
			return false;
		}
	}

	/**
	 * Test if the JDK Jaxb compiler is on the classpath.
	 */
	private boolean genericJaxbJdkIsOnClasspath() {
		return ClassesGenerator.genericJaxbJdkIsOnClasspath(this.getJavaProject());
	}

	/**
	 * Test if the non-JDK Jaxb compiler is on the classpath.
	 */
	private boolean genericJaxbNonJdkIsOnClasspath() {
		return ClassesGenerator.genericJaxbNonJdkIsOnClasspath(this.getJavaProject());
	}
	
	private boolean toolsJarExists() {
		return ClassesGenerator.toolsJarExists(this.getJavaProject());
	}
	
	private IVMInstall getVMInstall() throws CoreException {
		return ClassesGenerator.getVMInstall(this.getJavaProject());
	}
	
	/**
	 * Test if the EclipseLink Jaxb compiler is on the classpath.
	 */
	private boolean moxyIsOnClasspath() {
		try {
			IType genClass = this.getJavaProject().findType(ClassesGenerator.JAXB_ECLIPSELINK_GEN_CLASS);
			return (genClass != null);
		} 
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean eclipseLinkMoxyIsOnClasspath() {
		return (this.moxyIsOnClasspath() && this.genericJaxbNonJdkIsOnClasspath());
	}

	private boolean projectPlatformIsJaxb() {
		return this.getJaxbProject() != null;
	}

	private JaxbPlatformConfig getJaxbPlatformConfig(String jaxbPlatformID) {
		JaxbPlatformManager jaxbPlatformManager = this.getJaxbPlatformManager();
		return (jaxbPlatformManager == null) ? null : jaxbPlatformManager.getJaxbPlatformConfig(jaxbPlatformID);
	}

	private JaxbPlatformManager getJaxbPlatformManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbPlatformManager();
	}

	private JaxbProject getJaxbProject() {
		JaxbProjectManager jaxbProjectManager = this.getJaxbProjectManager();
		return (jaxbProjectManager == null) ? null : jaxbProjectManager.getJaxbProject(getJavaProject().getProject());
	}
	
	private JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		JaxbWorkbench jaxbWorkbench = this.getJaxbWorkbench();
		return (jaxbWorkbench == null) ? null : jaxbWorkbench.getJaxbWorkspace();
	}

	private JaxbWorkbench getJaxbWorkbench() {
		return PlatformTools.getAdapter(PlatformUI.getWorkbench(), JaxbWorkbench.class);
	}

	// huh? why is this here???
	private boolean projectJaxbPlatformIsEclipseLink() {
		String jaxbPlatformID = JaxbPreferences.getJaxbPlatformID(this.getJavaProject().getProject());
		JaxbPlatformConfig jaxbPlatformConfig = this.getJaxbPlatformConfig(jaxbPlatformID);
		JaxbPlatformGroupConfig jaxbPlatformGroupConfig = (jaxbPlatformConfig == null) ? null : jaxbPlatformConfig.getGroupConfig();
		return (jaxbPlatformGroupConfig != null) && jaxbPlatformGroupConfig.getId().equals(ECLIPSELINK_PLATFORM_GROUP_ID);
	}
	
	private boolean isPackageInitialInitialization(String newPackageName) {
		return this.targetPackage == null && newPackageName.equals("");  //$NON-NLS-1$
	}

	private void setPackageName(IPackageFragmentRoot packageFragmentRoot, String packageName) {
		if( packageName == null || packageName.length() == 0 || packageFragmentRoot == null) {
			return;
		}
		IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(packageName);
		this.setPackageFragment(packageFragment, true);
		
		JaxbPreferences.setClassGenPackage(this.getJavaProject().getProject(), packageName);
	}
	
	private void displayWarning(String message) {
		this.setMessage(message, WARNING);
	}


	// ********** inner class **********
	
	// ********** SettingsGroup class **********

	private class SettingsGroup {

		private final Text catalogText;

		private final ArrayList<String> bindingsFileNames;
		
		// ********** constructor **********

		private SettingsGroup(Composite parent) {
			super();
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(4, false); //must be 4 for the package controls
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, HELP_CONTEXT_ID);

			// Source folder
			createContainerControls(composite, 4);
			
			// Package
			createPackageControls(composite, 4);
			
			Label label = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
			GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 4, 1);
			gridData.verticalIndent = 5;
			label.setLayoutData(gridData);
			
			// Catalog
			Label catalogLabel = new Label(composite, SWT.NONE);
			catalogLabel.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_catalog);
			gridData = new GridData();
			gridData.verticalIndent = 5;
			catalogLabel.setLayoutData(gridData);
			this.catalogText = this.buildCatalogText(composite);
			this.buildBrowseButton(composite);
			
			// Bindings files
			this.bindingsFileNames = new ArrayList<String>();
			Label bindingsFileLabel = new Label(composite, SWT.NONE);
			bindingsFileLabel.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_bindingsFiles);
			bindingsFileLabel.setLayoutData(new GridData());
			this.buildBindingsFileTable(composite);
		}

		// ********** intra-wizard methods **********

		protected String getCatalog() {
			return this.catalogText.getText();
		}

		protected String[] getBindingsFileNames() {
			return ArrayTools.array(this.bindingsFileNames.iterator(), new String[0]);
		}
		
		// ********** UI components **********

		private Text buildCatalogText(Composite parent) {
			Text text = new Text(parent, SWT.BORDER);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			gridData.verticalIndent = 5;
			text.setLayoutData(gridData);
			return text;
		}

		private void buildBrowseButton(Composite parent) {
			
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(gridData);

			// Browse buttons
			Button browseButton = new Button(buttonComposite, SWT.PUSH);
			browseButton.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_browseButton);
			gridData = new GridData();
			gridData.horizontalAlignment= GridData.FILL;
			gridData.verticalIndent = 5;
			gridData.grabExcessHorizontalSpace= true;
			browseButton.setLayoutData(gridData);
			
			browseButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {

					String filePath = promptXmlFile();
					if( ! StringTools.isBlank(filePath)) {
						
						catalogText.setText(makeRelativeToProjectPath(filePath));
					}
				}
			});
		}

		private TableViewer buildBindingsFileTable(Composite parent) {
			
			TableViewer tableViewer = this.buildTableViewer(parent, this.bindingsFileNames);
			
			this.buildAddRemoveButtons(parent, tableViewer, this.bindingsFileNames);
			return tableViewer;
		}
		
		private TableViewer buildTableViewer(Composite parent, ArrayList<String> tableDataModel) {	
		
			TableLayoutComposite tableLayout = new TableLayoutComposite(parent, SWT.NONE);
			this.addColumnsData(tableLayout);
		
			final Table table = new Table(tableLayout, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
			table.setLinesVisible(false);
			
			TableColumn column = new TableColumn(table, SWT.NONE, 0);
			column.setResizable(true);
		
			GridData gridData= new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 2;
			gridData.heightHint= SWTUtil.getTableHeightHint(table, 3);
			tableLayout.setLayoutData(gridData);
		
			TableViewer tableViewer = new TableViewer(table);
			tableViewer.setUseHashlookup(true);
			tableViewer.setLabelProvider(this.buildLabelProvider());
			tableViewer.setContentProvider(this.buildContentProvider());
			
			tableViewer.setInput(tableDataModel);
			return tableViewer;
		}
		
		private void buildAddRemoveButtons(Composite parent, final TableViewer tableViewer, final ArrayList<String> tableDataModel) {
		
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(gridData);
			// Add button
			Button addButton = new Button(buttonComposite, SWT.PUSH);
			addButton.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_addButton);
			gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace= true;
			addButton.setLayoutData(gridData);
			addButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}

				public void widgetSelected(SelectionEvent e) {
					String filePath = addBindingsFileDialog();
					if( ! StringTools.isBlank(filePath)) {
						addBindingsFile(filePath, tableDataModel);
						tableViewer.refresh();
					}
				}
			});
			// Remove button
			Button removeButton = new Button(buttonComposite, SWT.PUSH);
			removeButton.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_removeButton);
			gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace= true;
			removeButton.setLayoutData(gridData);
			removeButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {
					StructuredSelection selection = (StructuredSelection)tableViewer.getSelection();
					if(selection.isEmpty()) {
						return;
					}
					String bindingsFileName = (String)selection.getFirstElement();
					removeBindingsFile(bindingsFileName);
		
					tableViewer.refresh();
				}
			});
			addButton.setFocus();
		}

		// ********** internal methods **********
		
		private String  addBindingsFileDialog() {
			PromptBindingsFilesDialog dialog = new PromptBindingsFilesDialog(getShell());
			
			// opens the dialog - just returns if the user cancels it
			if(dialog.open() == Window.CANCEL) {
				return null;
			}
			
			String location = dialog.getLocation();
			return location;
		}

		private String makeRelativeToProjectPath(String filePath) {
			Path path = new Path(filePath);
			IPath relativePath = path.makeRelativeTo(getJavaProject().getProject().getLocation());
			return relativePath.toOSString();
		}

		private void addBindingsFile(String relativePath, final ArrayList<String> tableDataModel) {
			if( ! tableDataModel.contains(relativePath)) {
				tableDataModel.add(relativePath);
			}
		}
		
		private void removeBindingsFile(String bindingsName) {
			this.bindingsFileNames.remove(bindingsName);
		}

		private IBaseLabelProvider buildLabelProvider() {
			return new TableLabelProvider();
		}
		
		private IContentProvider buildContentProvider() {
			return new TableContentProvider();
		}

		/**
		 * The Add button was clicked, its action invokes this action which should
		 * prompt the user to select a file and return it.
		 */
		private String promptXmlFile() {
			String projectPath= getJavaProject().getProject().getLocation().toString();

			FileDialog dialog = new FileDialog(getShell());
			dialog.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_chooseACatalog);
			dialog.setFilterPath(projectPath);
			dialog.setFilterExtensions(new String[] {XML_FILTER});

			return dialog.open();
		}

		private void addColumnsData(TableLayoutComposite layout) {
			layout.addColumnData(new ColumnWeightData(50, true));
		}
		
	}

	// ********** TableLabelProvider **********
	
	private class TableLabelProvider extends LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
			return (String)element;
		}
	}

	// ********** TableContentProvider **********
	
	private class TableContentProvider implements IStructuredContentProvider {

		// ********** constructor **********
		TableContentProvider() {
			super();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		
		public void dispose() {}
		
		public Object[] getElements(Object inputElement) {
			return ((Collection<?>) inputElement).toArray();
		}
	}

	// ********** PromptBindingsFilesDialog **********
	
	private class PromptBindingsFilesDialog extends TrayDialog {
		
		private SelectFileOrXMLCatalogIdPanel locationPanel;
		
		private String location;

		// ********** constructor **********
		public PromptBindingsFilesDialog(Shell shell) {
			super(shell);
		}
		
		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_chooseABindingsFile);
		}
		
		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			
			this.locationPanel = new SelectFileOrXMLCatalogIdPanel(composite, StructuredSelection.EMPTY, resourceManager);
			this.locationPanel.setFilterExtensions(bindingdFilesFilterExtensions);
			this.locationPanel.update();
			this.locationPanel.setVisibleHelper(true);
			
			return composite;
		}
		
		@Override
		protected boolean isResizable() {
			return true;
		}
		
		@Override
		protected void okPressed() {
			IFile file = this.locationPanel.getFile();
			if (file != null) {
				this.location = file.getProjectRelativePath().toOSString();
			}
			super.okPressed();
		}
		
		public String getLocation() {
			return this.location;
		}
	}
}
