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

import java.util.List;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.common.ui.internal.wizards.JavaProjectWizardPage;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.SchemaLibrary;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorExtensionOptions;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorOptions;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.gen.GenerateJaxbClassesJob;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wst.xsd.contentmodel.internal.XSDImpl;
import org.eclipse.xsd.XSDSchema;

/**
 *  ClassesGeneratorWizard
 */
public class ClassesGeneratorWizard
		extends Wizard
		implements INewWizard {

	private IJavaProject javaProject;
	private IFile preselectedXsdFile;
	private ResourceManager resourceManager;
	protected IStructuredSelection selection;
	
	private String destinationFolder;
	private String targetPackage;
	private String catalog;
	private boolean usesMoxy;
	private String[] bindingsFileNames;
	
	private ClassesGeneratorOptions generatorOptions;
	private ClassesGeneratorExtensionOptions generatorExtensionOptions;
	
	private JavaProjectWizardPage projectWizardPage;
	private SchemaWizardPage schemaWizardPage;
	
	private ClassesGeneratorWizardPage settingsPage;
	private ClassesGeneratorOptionsWizardPage optionsPage;
	private ClassesGeneratorExtensionOptionsWizardPage extensionOptionsPage;
	private boolean performsGeneration;
	
	
	// ********** constructor **********
	
	public ClassesGeneratorWizard() {
		super();
		this.performsGeneration = true;
	}
	
	public ClassesGeneratorWizard(IJavaProject javaProject, IFile xsdFile) {
		super();
		this.javaProject = javaProject;
		this.preselectedXsdFile = xsdFile;
		this.performsGeneration = false;
	}
	
	
	// ********** IWorkbenchWizard implementation  **********
	
	public void init(IWorkbench workbench, IStructuredSelection sel) {
		this.resourceManager = new LocalResourceManager(JFaceResources.getResources(workbench.getDisplay()));
		this.selection = sel;
		
		this.setWindowTitle(JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_TITLE);
		this.setDefaultPageImageDescriptor(JptJaxbUiImages.CLASSES_GEN_BANNER);
		this.setNeedsProgressMonitor(true);
	}
	
	
	// ********** IWizard implementation  **********
	
	@Override
	public void addPages() {
		super.addPages();
		
		if (this.selection != null && ! this.selection.isEmpty()) {
			this.javaProject = this.getJavaProjectFromSelection(this.selection);
		}
		this.projectWizardPage = new JavaProjectWizardPage(this.javaProject);
		this.projectWizardPage.setTitle(JptJaxbUiMessages.CLASSES_GENERATOR_PROJECT_WIZARD_PAGE_TITLE);
		this.projectWizardPage.setDescription(JptJaxbUiMessages.CLASSES_GENERATOR_PROJECT_WIZARD_PAGE_DESC);
		this.projectWizardPage.setDestinationLabel(JptJaxbUiMessages.JAVA_PROJECT_WIZARD_PAGE_DESTINATION_PROJECT);
		this.addPage(this.projectWizardPage);
		
		// SchemaWizardPage
		if (this.preselectedXsdFile == null) {
			this.preselectedXsdFile = SchemaWizardPage.getSourceSchemaFromSelection(this.selection);
		}
		
		if (this.preselectedXsdFile == null) {
			this.schemaWizardPage = new SchemaWizardPage(this.selection, this.resourceManager);
			this.addPage(this.schemaWizardPage);
		}
		
		this.settingsPage = this.buildClassesGeneratorPage();
		this.optionsPage = this.buildClassesGeneratorOptionsPage();
		this.extensionOptionsPage = this.buildExtensionOptionsPage();
		
		this.addPage(this.settingsPage);
		this.addPage(this.optionsPage);
		this.addPage(this.extensionOptionsPage);
	}

    @Override
	public boolean canFinish() {
    	return this.settingsPage.isPageComplete() 
    			&& this.optionsPage.isPageComplete() 
    			&& this.extensionOptionsPage.isPageComplete();
    }
	
	@Override
	public boolean performFinish() {
		
		WizardPage currentPage = (WizardPage)getContainer().getCurrentPage();
		if (currentPage != null) {
			if (! currentPage.isPageComplete()) {
				return false;
			}
			this.retrieveGeneratorSettings();
			this.retrieveGeneratorOptions();
			this.retrieveGeneratorExtensionOptions();
			
			IFolder folder = this.getJavaProject().getProject().getFolder(this.destinationFolder);
			this.createFolderIfNotExist(folder);
		}
		
		if (this.performsGeneration) {
			if (displayOverwritingClassesWarning(this.generatorOptions)) {
				doGenerateJaxbClasses();
				doAddSchemaToLibrary();
			}
		}
		
		return true;
	}
    
	// ********** intra-wizard methods **********
    
	public IJavaProject getJavaProject() {
		if(this.projectWizardPage != null) {
			this.javaProject = this.projectWizardPage.getJavaProject();
		}
    	return this.javaProject;
    }
	
	/* may be null */
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
		return WorkbenchTools.getAdapter(JaxbWorkbench.class);
	}

	/* return the physical location of the schema */
	public URI getLocalSchemaUri() {
		if (this.preselectedXsdFile != null) {
			return URI.createFileURI(this.preselectedXsdFile.getLocation().toString());
		}
		else if (this.schemaWizardPage != null) {
			return this.schemaWizardPage.getLocalSchemaURI();
		}
		return null;
	}
	
	/* return the uri or file platform resource uri used for schema resolution */
	public String getSchemaLocation() {
		if (this.preselectedXsdFile != null) {
			return URI.createPlatformResourceURI(this.preselectedXsdFile.getFullPath().toString(), false).toString();
		}
		else if (this.schemaWizardPage != null) {
			return this.schemaWizardPage.getSchemaLocation();
		}
		return null;
	}
	

	// ********** public methods **********
    
    public String getDestinationFolder() {
		return this.destinationFolder;
	}
	
    public String getTargetPackage() {
		return this.targetPackage;
	}
	
    public String getCatalog() {
		return this.catalog;
	}
	
    public boolean usesMoxy() {
		return this.usesMoxy;
	}

    public String[] getBindingsFileNames() {
		return this.bindingsFileNames;
	}
	
    public ClassesGeneratorOptions getGeneratorOptions() {
		return this.generatorOptions;
	}
	
    public ClassesGeneratorExtensionOptions getGeneratorExtensionOptions() {
		return this.generatorExtensionOptions;
	}
	
	// ********** internal methods **********
    
	public IJavaProject getJavaProjectFromSelection(IStructuredSelection selection) {
    	if(selection == null || selection.isEmpty()) {
    		return null;
    	}
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof IJavaProject) {
			return (IJavaProject)firstElement;
		}
		else if(firstElement instanceof IResource) {
			IProject project = ((IResource) firstElement).getProject();
			return getJavaProjectFrom(project);
		}
		else if(firstElement instanceof IJavaElement) {
			return ((IJavaElement)firstElement).getJavaProject();
		}
		return null;
    }
	
    private ClassesGeneratorWizardPage buildClassesGeneratorPage() {
		return new ClassesGeneratorWizardPage(this.resourceManager);
	}
	
	private ClassesGeneratorOptionsWizardPage buildClassesGeneratorOptionsPage() {
		return new ClassesGeneratorOptionsWizardPage();
	}
	
	private ClassesGeneratorExtensionOptionsWizardPage buildExtensionOptionsPage() {
		return new ClassesGeneratorExtensionOptionsWizardPage();
	}
    
    public IJavaProject getJavaProjectFrom(IProject project) {
    	return ((IJavaElement) project.getAdapter(IJavaElement.class)).getJavaProject();
    }
    
	private boolean displayOverwritingClassesWarning(ClassesGeneratorOptions generatorOptions) {
		
		if( ! this.isOverridingClasses(generatorOptions) 
				|| !OptionalMessageDialog.isDialogEnabled(OverwriteConfirmerDialog.ID)) {
			return true;
		} else {
			OverwriteConfirmerDialog dialog = new OverwriteConfirmerDialog(this.getShell());
			return dialog.open() == IDialogConstants.YES_ID;			
		}
	}

	private boolean isOverridingClasses(ClassesGeneratorOptions generatorOptions) {
		if(generatorOptions == null) {
			throw new NullPointerException();
		}
		if(generatorOptions.showsVersion() || generatorOptions.showsHelp()) {
			return false;
		}
		return true;
	}

	private void retrieveGeneratorSettings() {
		this.destinationFolder = this.settingsPage.getTargetFolder();
		this.targetPackage = this.settingsPage.getTargetPackage();
		this.catalog = this.settingsPage.getCatalog();
		this.usesMoxy = this.settingsPage.usesMoxy();
		this.bindingsFileNames = this.settingsPage.getBindingsFileNames();
	}
	
	private void retrieveGeneratorOptions() {
		this.generatorOptions = new ClassesGeneratorOptions();

		this.generatorOptions.setProxy(this.optionsPage.getProxy());
		this.generatorOptions.setProxyFile(this.optionsPage.getProxyFile());
		
		this.generatorOptions.setUsesStrictValidation(this.optionsPage.usesStrictValidation());
		this.generatorOptions.setMakesReadOnly(this.optionsPage.makesReadOnly());
		this.generatorOptions.setSuppressesPackageInfoGen(this.optionsPage.suppressesPackageInfoGen());
		this.generatorOptions.setSuppressesHeaderGen(this.optionsPage.suppressesHeaderGen());
		this.generatorOptions.setIsVerbose(this.optionsPage.isVerbose());
		this.generatorOptions.setIsQuiet(this.optionsPage.isQuiet());
		
		this.generatorOptions.setTreatsAsXmlSchema(this.optionsPage.treatsAsXmlSchema());
		this.generatorOptions.setTreatsAsRelaxNg(this.optionsPage.treatsAsRelaxNg());
		this.generatorOptions.setTreatsAsRelaxNgCompact(this.optionsPage.treatsAsRelaxNgCompact());
		this.generatorOptions.setTreatsAsDtd(this.optionsPage.treatsAsDtd());
		this.generatorOptions.setTreatsAsWsdl(this.optionsPage.treatsAsWsdl());
		this.generatorOptions.setShowsVersion(this.optionsPage.showsVersion());
		this.generatorOptions.setShowsHelp(this.optionsPage.showsHelp());
	}
	
	private void retrieveGeneratorExtensionOptions() {

		this.generatorExtensionOptions = new ClassesGeneratorExtensionOptions();

		this.generatorExtensionOptions.setAllowsExtensions(this.extensionOptionsPage.allowsExtensions());
		this.generatorExtensionOptions.setClasspath(this.extensionOptionsPage.getClasspath());
		this.generatorExtensionOptions.setAdditionalArgs(this.extensionOptionsPage.getAdditionalArgs());
	}
	
	private void createFolderIfNotExist(IFolder folder) {
		if( folder.exists()) {
			return;
		}
		try {
			folder.create(true, true, null);
		}
		catch (CoreException e) {
			JptJaxbUiPlugin.instance().logError(e);
			
			this.logError(NLS.bind(
				JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_COULD_NOT_CREATE, 
				folder.getProjectRelativePath().toOSString()));
		}
	}
	
	private void doGenerateJaxbClasses() {
		try {
			WorkspaceJob generateJaxbClassesJob = 
					new GenerateJaxbClassesJob(
						this.getJavaProject(),
						this.getLocalSchemaUri().toString(),
						this.destinationFolder,
						this.targetPackage,
						this.catalog,
						this.usesMoxy,
						this.bindingsFileNames,
						this.generatorOptions,
						this.generatorExtensionOptions);
			generateJaxbClassesJob.schedule();
		}
		catch(RuntimeException re) {
			JptJaxbUiPlugin.instance().logError(re);
			
			String msg = re.getMessage();
			String message = (msg == null) ? re.toString() : msg;
			this.logError(message);
		}
	}
	
	private void doAddSchemaToLibrary() {
		try {
			JaxbProject jaxbProject = getJaxbProject();
			if (jaxbProject == null) {
				return;
			}
			WorkspaceJob addSchemaToLibraryJob = new AddSchemaToLibraryJob(jaxbProject, getSchemaLocation());
			addSchemaToLibraryJob.schedule();
		}
		catch(RuntimeException re) {
			JptJaxbUiPlugin.instance().logError(re);
			
			String msg = re.getMessage();
			String message = (msg == null) ? re.toString() : msg;
			this.logError(message);
		}
	}
	
	
	protected class AddSchemaToLibraryJob
			extends WorkspaceJob {
		
		private final JaxbProject jaxbProject;
		private final String schemaLocation;
		
		
		protected AddSchemaToLibraryJob(JaxbProject jaxbProject, String schemaLocation) {
			super(JptJaxbUiMessages.ADD_SCHEMA_TO_LIBRARY_JOB__NAME);
			this.jaxbProject = jaxbProject;
			this.schemaLocation = schemaLocation;
		}
		
		
		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			String resolvedUri = XsdUtil.getResolvedUri(this.schemaLocation);
			XSDSchema schema = XSDImpl.buildXSDModel(resolvedUri);
			if (schema != null) {
				SchemaLibrary schemaLib = this.jaxbProject.getSchemaLibrary();
				List<String> schemas = new Vector<String>(schemaLib.getSchemaLocations());
				if (! schemas.contains(this.schemaLocation)) {
					schemas.add(this.schemaLocation);
					schemaLib.setSchemaLocations(schemas);
				}
			}
			return Status.OK_STATUS;
		}
	}
	
	
	protected void logError(String message) {
			this.displayError(message);
	}
	
	private void displayError(String message) {
		MessageDialog.openError(
				this.getShell(),
				JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_ERROR_DIALOG_TITLE,
				message
			);
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}
	
	static class OverwriteConfirmerDialog extends OptionalMessageDialog {

		private static final String ID= "dontShowOverwriteJaxbClassesFromSchemas.warning"; //$NON-NLS-1$

		OverwriteConfirmerDialog(Shell parent) {
			super(ID, parent,
					JptJaxbUiMessages.CLASSES_GENERATOR_UI_GENERATING_CLASSES_WARNING_TITLE,
					JptJaxbUiMessages.CLASSES_GENERATOR_UI_GENERATING_CLASSES_WARNING_MESSAGE,
					MessageDialog.WARNING,
					new String[] {IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL},
					1);
		}
		
		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			this.createButton(parent, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, false);
			this.createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, true);
		}

	}

}
