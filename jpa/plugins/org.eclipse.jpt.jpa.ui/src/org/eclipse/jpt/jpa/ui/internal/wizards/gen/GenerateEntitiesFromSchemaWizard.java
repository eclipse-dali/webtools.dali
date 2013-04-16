/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import java.io.File;
import java.io.IOException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaEntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.gen.internal.BaseEntityGenCustomizer;
import org.eclipse.jpt.jpa.gen.internal.DatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.jpa.gen.internal.ORMGenTable;
import org.eclipse.jpt.jpa.gen.internal.PackageGenerator;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class GenerateEntitiesFromSchemaWizard
	extends Wizard 
	implements INewWizard
{	
	private static final String HELP_CONTEXT_ID = JptJpaUiPlugin.instance().getPluginID() + ".GenerateEntitiesFromSchemaWizard"; //$NON-NLS-1$

	protected JpaProject jpaProject;

	protected IStructuredSelection selection;

	private ORMGenCustomizer customizer = null;	

	private PromptJPAProjectWizardPage projectPage;	

	protected TablesSelectorWizardPage tablesSelectorPage;

	protected TableAssociationsWizardPage tableAssociationsPage;
	
	protected DefaultTableGenerationWizardPage defaultTableGenerationPage;
	
	protected TablesAndColumnsCustomizationWizardPage tablesAndColumnsCustomizationPage;
	
	protected final ResourceManager resourceManager;
	
	public GenerateEntitiesFromSchemaWizard() {
		this.resourceManager = this.buildResourceManager();
		this.setWindowTitle( JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_generateEntities);
	}
	
	public GenerateEntitiesFromSchemaWizard( JpaProject jpaProject, IStructuredSelection selection) {
		super();
		this.jpaProject = jpaProject;
		this.selection = selection;
		this.resourceManager = this.buildResourceManager();
		this.setWindowTitle( JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_generateEntities);
		this.setDefaultPageImageDescriptor(JptJpaUiImages.ENTITY_BANNER);
	}

	protected ResourceManager buildResourceManager() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench != null) ? jpaWorkbench.buildLocalResourceManager() : new LocalResourceManager(JFaceResources.getResources(this.getWorkbench().getDisplay()));
	}

	protected JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(this.getWorkbench(), JpaWorkbench.class);
	}
	
	protected IWorkbench getWorkbench() {
		return PlatformUI.getWorkbench();
	}
	
	@Override
	public void addPages() {
		this.setForcePreviousAndNextButtons(true);

		if(this.jpaProject == null && this.selection != null) {
			this.jpaProject = this.getJpaProjectFromSelection(this.selection);
		}
			
		//If this.jpaProject is not initialized because user didn't select a JPA project
		if( ! this.projectIsValidSelection(this.jpaProject)) {
			this.projectPage = this.buildProjectWizardPage();
			this.addPage(this.projectPage);
			return;
		}
		this.addMainPages();
	}

	protected void addMainPages() {
		this.tablesSelectorPage = new TablesSelectorWizardPage(this.jpaProject, this.resourceManager, false);
		this.addPage(this.tablesSelectorPage);
		
		this.tableAssociationsPage = new TableAssociationsWizardPage(this.jpaProject, this.resourceManager);
		this.addPage(this.tableAssociationsPage);

		this.defaultTableGenerationPage = new DefaultTableGenerationWizardPage(this.jpaProject);
		this.addPage(this.defaultTableGenerationPage);
		this.defaultTableGenerationPage.init(this.selection);
		
		this.tablesAndColumnsCustomizationPage = new TablesAndColumnsCustomizationWizardPage(this.jpaProject, this.resourceManager);
		this.addPage(this.tablesAndColumnsCustomizationPage);
		this.tablesAndColumnsCustomizationPage.init(this.selection);		
	}
	
	public ORMGenCustomizer getORMGenCustomizer(){
		return this.customizer;
	}

	/**
	 * Create the ORMGenCustomizer when user selects a new connection profile and schema 
	 * 
	 * JpaPlatform implementor can provide a custom ORMGenCustomizer specific to a platform
	 * with AdapterFactory through Eclipse org.eclipse.core.runtime.adapters extension point:
	 * <pre> 
	 *  
	 *<extension
     *    point="org.eclipse.core.runtime.adapters">
     * <factory
     *       adaptableType="org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkPlatform"
     *       class="oracle.eclipse.tools.orm.internal.EclipseLinkORMGenCustomizerAdapterFactory">
     *    <adapter
     *          type="oracle.eclipse.tools.orm.internal.ORMGenCustomizer">
     *    </adapter>
     * </factory>
     *</extension>
	 *</pre> 
	 * 
	 * @param schema 
	 */
	public ORMGenCustomizer createORMGenCustomizer(Schema schema) {
		JpaPlatform jpaPlatform = this.jpaProject.getJpaPlatform();
		ORMGenCustomizer obj = PlatformTools.getAdapter(jpaPlatform, ORMGenCustomizer.class);
		
		if (obj != null) {
			this.customizer = (ORMGenCustomizer) obj; 
			this.customizer.init(this.getCustomizationFile(), schema);  
		} 
		else {
			this.customizer = new BaseEntityGenCustomizer();
			this.customizer.init(this.getCustomizationFile(), schema);  
		}

		ORMGenTable newDefaultTable = this.getCustomizer().createGenTable(null);
		if ( this.selection!=null && this.selection.getFirstElement() instanceof IPackageFragment ) {
			IPackageFragment packageFrag = (IPackageFragment)this.selection.getFirstElement();
			newDefaultTable.setPackage( packageFrag.getElementName() );
			for (IPackageFragmentRoot root : JavaProjectTools.getSourceFolders(this.jpaProject.getJavaProject())) {
				String srcFolder = root.getPath().toPortableString();
				if( packageFrag.getPath().toPortableString().startsWith( srcFolder +'/' )) {
					newDefaultTable.setSourceFolder(srcFolder.substring(1));
				}
			}
		}
		else if (newDefaultTable.getPackage().equals(StringTools.EMPTY_STRING)) {
			newDefaultTable.setPackage(JpaPreferences.getEntityGenDefaultPackageName(this.jpaProject.getProject()));
		}
			
		return this.customizer;
	}
	
	protected String getCustomizationFileName() {
		ConnectionProfile profile = getProjectConnectionProfile();
		String connection = profile == null ? "" : profile.getName();
		String name = "org.eclipse.jpt.entitygen." + (connection == null ? "" :connection.replace(' ', '-'));  //$NON-NLS-1$
		Schema schema = getDefaultSchema();
		if ( schema!= null  ) {
			name += "." + schema.getName();//$NON-NLS-1$
		}
		return name.toLowerCase();
	}
	
	/**
	 * Returns the nodes state file. 
	 */
	private File getCustomizationFile() {
		String projectPath = this.jpaProject.getProject().getLocation().toPortableString();
		File genDir = new File(projectPath + "/.settings");//$NON-NLS-1$
        genDir.mkdirs();
		return new File(genDir, getCustomizationFileName());
	}

	@Override
	public boolean performFinish() {
		if (this.jpaProject == null) {
			return true;
		}
		// the customizer will be null if the user presses "Finish" on the
		// Select JPA Project page (Don't ask....)
		if (this.customizer == null) {
			return false;
		}
		try {
			this.customizer.setDatabaseAnnotationNameBuilder(this.buildDatabaseAnnotationNameBuilder());
			this.customizer.save();
		} catch (IOException e) {
			JptJpaUiPlugin.instance().logError(e);
		}
		OverwriteConfirmer overwriteConfirmer = null;
		if (showOverwriteWarning()) {
			overwriteConfirmer = new OverwriteConfirmer();
		}
		
		scheduleGenerateEntitiesJob(overwriteConfirmer);
		return true;
	}

	@Override
    public boolean canFinish() {
		boolean canFinish = super.canFinish();
		return canFinish && this.tablesSelectorPage.hasTablesSelected();
    }

	protected void scheduleGenerateEntitiesJob(
			OverwriteConfirmer overwriteConfirmer) {
		WorkspaceJob genEntitiesJob = new GenerateEntitiesJob(this.jpaProject, getCustomizer(), overwriteConfirmer, false);
		genEntitiesJob.schedule();
	}

	// ********** generate entities job **********

	public static class GenerateEntitiesJob extends WorkspaceJob {
		final JpaProject jpaProject;
		final ORMGenCustomizer customizer;
		final OverwriteConfirmer confirmer;
		final boolean generateXml;
		public GenerateEntitiesJob(JpaProject jpaProject, ORMGenCustomizer customizer, OverwriteConfirmer confirmer, boolean generateXml) {
			super(JptJpaUiMessages.EntitiesGenerator_jobName);
			this.customizer = customizer;
			this.jpaProject = jpaProject;
			this.confirmer = confirmer;
			this.generateXml = generateXml;
			IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
			this.setRule(ruleFactory.modifyRule(jpaProject.getProject()));
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			PackageGenerator.generate(this.jpaProject,this.customizer, this.confirmer, monitor, this.generateXml);
			return Status.OK_STATUS;
		}
	}

    public static boolean showOverwriteWarning(){
    	return OptionalMessageDialog.isDialogEnabled(OverwriteConfirmerDialog.ID);
    }
    
    // ********** overwrite confirmer **********

	public static class OverwriteConfirmer implements org.eclipse.jpt.jpa.gen.internal.OverwriteConfirmer {
		private boolean overwriteAll = false;
		private boolean skipAll = false;

		OverwriteConfirmer() {
		}

		public boolean overwrite(final String className) {
			if (this.overwriteAll) {
				return true;
			}
			if (this.skipAll) {
				return false;
			}
			return this.promptUser(className);
		}

		private boolean promptUser(final String className) {
			// get on the UI thread synchronously, need feedback before continuing
			final boolean ret[]=new boolean[1];
			org.eclipse.jpt.common.ui.internal.util.SWTUtil.syncExec(new Runnable() {
				public void run() {
					final OverwriteConfirmerDialog dialog = new OverwriteConfirmerDialog(Display.getCurrent().getActiveShell(), className);
					dialog.open();
					if (dialog.getReturnCode() == Window.CANCEL) {
						//throw new OperationCanceledException();
						skipAll = true;
						ret[0] = false;
						return;
					}
					if (dialog.yes()) {
						ret[0] = true;
					}
					if (dialog.yesToAll()) {
						overwriteAll = true;
						ret[0] = true;
					}
					if (dialog.no()) {
						ret[0] = false;
					}
					if (dialog.noToAll()) {
						skipAll = true;
						ret[0] = false;
					}
				}
			});
			return ret[0];
		}

	}


	// ********** overwrite dialog **********

	static class OverwriteConfirmerDialog extends OptionalMessageDialog {
		private boolean yes = false;
		private boolean yesToAll = false;
		private boolean no = false;
		private boolean noToAll = false;

		private static final String ID= "dontShowOverwriteEntitesFromSchemas.warning"; //$NON-NLS-1$

		OverwriteConfirmerDialog(Shell parent, String className) {
			super(ID, parent,
					JptJpaUiMessages.OverwriteConfirmerDialog_title,
					NLS.bind(JptJpaUiMessages.OverwriteConfirmerDialog_text, className),
					MessageDialog.WARNING,
					new String[] {IDialogConstants.YES_LABEL, 
									IDialogConstants.YES_TO_ALL_LABEL, 
									IDialogConstants.NO_LABEL, 
									IDialogConstants.NO_TO_ALL_LABEL, 
									IDialogConstants.CANCEL_LABEL},
					2);
		}
		
		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			this.createButton(parent, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, false);
			this.createButton(parent, IDialogConstants.YES_TO_ALL_ID, IDialogConstants.YES_TO_ALL_LABEL, false);
			this.createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, true);
			this.createButton(parent, IDialogConstants.NO_TO_ALL_ID, IDialogConstants.NO_TO_ALL_LABEL, false);
			this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		}

		@Override
		protected void buttonPressed(int buttonId) {
			switch (buttonId) {
				case IDialogConstants.YES_ID :
					this.yesPressed();
					break;
				case IDialogConstants.YES_TO_ALL_ID :
					this.yesToAllPressed();
					break;
				case IDialogConstants.NO_ID :
					this.noPressed();
					break;
				case IDialogConstants.NO_TO_ALL_ID :
					this.noToAllPressed();
					break;
				case IDialogConstants.CANCEL_ID :
					this.cancelPressed();
					break;
				default :
					break;
			}
		}

		private void yesPressed() {
			this.yes = true;
			this.setReturnCode(OK);
			this.close();
		}

		private void yesToAllPressed() {
			this.yesToAll = true;
			this.setReturnCode(OK);
			this.close();
		}

		private void noPressed() {
			this.no = true;
			this.setReturnCode(OK);
			this.close();
		}

		private void noToAllPressed() {
			this.noToAll = true;
			this.setReturnCode(OK);
			this.close();
		}

		boolean yes() {
			return this.yes;
		}

		boolean yesToAll() {
			return this.yesToAll;
		}

		boolean no() {
			return this.no;
		}

		boolean noToAll() {
			return this.noToAll;
		}
	}


	private DatabaseAnnotationNameBuilder buildDatabaseAnnotationNameBuilder() {
		return new LocalDatabaseAnnotationNameBuilder(this.jpaProject.getJpaPlatform().getEntityGeneratorDatabaseAnnotationNameBuilder());
	}

	// ********** name builder adapter **********

	/**
	 * adapt the JPA platform-supplied builder to the builder interface
	 * expected by the entity generator
	 */
	static class LocalDatabaseAnnotationNameBuilder implements DatabaseAnnotationNameBuilder {
		private JpaEntityGeneratorDatabaseAnnotationNameBuilder builder;
		LocalDatabaseAnnotationNameBuilder(JpaEntityGeneratorDatabaseAnnotationNameBuilder builder) {
			super();
			this.builder = builder;
		}
		public String buildTableAnnotationName(String entityName, Table table) {
			return this.builder.buildTableAnnotationName(entityName, table);
		}
		public String buildColumnAnnotationName(String attributeName, Column column) {
			return this.builder.buildColumnAnnotationName(attributeName, column);
		}
		public String buildJoinColumnAnnotationName(String attributeName, ForeignKey foreignKey) {
			return this.builder.buildJoinColumnAnnotationName(attributeName, foreignKey);
		}
		public String buildJoinColumnAnnotationName(Column column) {
			return this.builder.buildJoinColumnAnnotationName(column);
		}
		public String buildJoinTableAnnotationName(Table table) {
			return this.builder.buildJoinTableAnnotationName(table);
		}
	}
	
	@Override
    public IWizardPage getStartingPage() {
		if (this.projectPage != null) {
			if (this.tablesSelectorPage != null) {
				return this.tablesSelectorPage;
			}
			return this.projectPage;
		}
		return super.getStartingPage();
    }
	
	public ORMGenCustomizer getCustomizer() {
		return customizer;
	} 
	
	public ConnectionProfile getProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}
	
	public JpaProject getJpaProject() {
		return this.jpaProject;
	}
	
	public void setJpaProject(JpaProject jpaProject) {
		if ( ! this.projectIsValidSelection(this.jpaProject)) {
			this.jpaProject = jpaProject;
			IWizardPage currentPage = this.getContainer().getCurrentPage();
			if (this.projectPage != null && currentPage.equals(this.projectPage)) {
				this.addMainPages();
			}
		}
	}

	public Schema getDefaultSchema() {
		return this.getJpaProject().getDefaultDbSchema();
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object sel = selection.getFirstElement();
		if ( sel instanceof IResource ) {
			IProject proj = ((IResource) sel).getProject();
			JpaProject jpaProj = this.getJpaProject(proj);
			this.jpaProject = jpaProj;
		} else if( sel instanceof org.eclipse.jdt.core.IPackageFragmentRoot ) {
			org.eclipse.jdt.core.IPackageFragmentRoot root = (org.eclipse.jdt.core.IPackageFragmentRoot) sel;
			IProject proj = root.getJavaProject().getProject();
			JpaProject jpaProj = this.getJpaProject(proj);
			this.jpaProject = jpaProj;
		} else if( sel instanceof org.eclipse.jdt.core.IPackageFragment) {
			org.eclipse.jdt.core.IPackageFragment frag = (org.eclipse.jdt.core.IPackageFragment) sel;
			IProject proj = frag.getJavaProject().getProject();
			JpaProject jpaProj = this.getJpaProject(proj);
			this.jpaProject = jpaProj;
		}
		
		this.selection = selection;
		this.setWindowTitle(JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_generateEntities);
	}

	protected JpaProject getJpaProjectFromSelection(IStructuredSelection selection) {
    	if(selection == null || selection.isEmpty()) {
    		return null;
    	}
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof IProject) {
			return this.getJpaProject((IProject)firstElement);
		}
		else if(firstElement instanceof IJavaElement) {
			IProject project = ((IJavaElement)firstElement).getJavaProject().getProject();
			return this.getJpaProject(project);
		}
		return null;
    }
	
	protected boolean projectIsValidSelection(JpaProject jpaProj) {
		return (jpaProj != null);
	}

	protected PromptJPAProjectWizardPage buildProjectWizardPage() {
		return new PromptJPAProjectWizardPage(this.getHelpContextID());
	}
	public String getHelpContextID() {
		return HELP_CONTEXT_ID;
	}

	protected JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}
}
