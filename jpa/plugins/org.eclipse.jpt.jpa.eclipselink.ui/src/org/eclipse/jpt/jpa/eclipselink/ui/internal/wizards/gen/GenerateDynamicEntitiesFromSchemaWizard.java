/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.GenerateEntitiesFromSchemaWizard;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.TableAssociationsWizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.TablesSelectorWizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class GenerateDynamicEntitiesFromSchemaWizard extends GenerateEntitiesFromSchemaWizard 
	implements INewWizard  {	
	
	public static final String HELP_CONTEXT_ID = JptJpaUiPlugin.PLUGIN_ID + ".GenerateEntitiesFromSchemaWizard"; //$NON-NLS-1$


	public GenerateDynamicEntitiesFromSchemaWizard() {
		super();
		this.setWindowTitle(JptJpaEclipseLinkUiEntityGenMessages.GenerateDynamicEntitiesWizard_generateEntities);
	}
	
	public GenerateDynamicEntitiesFromSchemaWizard( JpaProject jpaProject, IStructuredSelection selection) {
		super(jpaProject, selection);
		this.setWindowTitle(JptJpaEclipseLinkUiEntityGenMessages.GenerateDynamicEntitiesWizard_generateEntities);
	}

	@Override
	protected void addMainPages() {
		this.tablesSelectorPage = new TablesSelectorWizardPage(this.jpaProject, this.resourceManager, true);
		this.addPage(this.tablesSelectorPage);
		
		this.tableAssociationsPage = new TableAssociationsWizardPage(this.jpaProject, this.resourceManager);
		this.addPage(this.tableAssociationsPage);

		this.defaultTableGenerationPage = new DynamicDefaultTableGenerationWizardPage(this.jpaProject);
		this.addPage(this.defaultTableGenerationPage);
		this.defaultTableGenerationPage.init(this.selection);
		
		this.tablesAndColumnsCustomizationPage = new DynamicTablesAndColumnsCustomizationWizardPage(this.jpaProject, this.resourceManager);
		this.addPage(this.tablesAndColumnsCustomizationPage);
		this.tablesAndColumnsCustomizationPage.init(this.selection);		
	}
	
	@Override
	protected String getCustomizationFileName() {
		ConnectionProfile profile = getProjectConnectionProfile();
		String connection = profile == null ? "" : profile.getName();
		String name = "org.eclipse.jpt.jpa.gen.dynamic." + (connection == null ? "" :connection.replace(' ', '-'));  //$NON-NLS-1$
		Schema schema = getDefaultSchema();
		if ( schema!= null  ) {
			name += "." + schema.getName();//$NON-NLS-1$
		}
		return name.toLowerCase();
	}
	
	@Override
	protected void scheduleGenerateEntitiesJob(
			OverwriteConfirmer overwriteConfirmer) {
		WorkspaceJob genEntitiesJob = new GenerateEntitiesJob(this.jpaProject, getCustomizer(), overwriteConfirmer, true);
		genEntitiesJob.schedule();
		
		//TODO need to open file after generation
//		JpaXmlResource jpaXmlResource = this.jpaProject.getMappingFileXmlResource(new Path(getCustomizer().getXmlMappingFile()));
//		OpenXmlMappingFileJob openXmlMappingFileJob = new OpenXmlMappingFileJob(this.jpaProject, jpaXmlResource, getShell());
//		openXmlMappingFileJob.schedule();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		
		this.setWindowTitle(JptJpaEclipseLinkUiEntityGenMessages.GenerateDynamicEntitiesWizard_generateEntities);
	}
	
//	public static class OpenXmlMappingFileJob extends WorkspaceJob {
//		final JpaProject jpaProject;
//		final JpaXmlResource jpaXmlResource;
//		final Shell shell;
//
//		public OpenXmlMappingFileJob(JpaProject jpaProject, JpaXmlResource jpaXmlResource, Shell shell) {
//			super("Open XML File");
//			this.jpaProject = jpaProject;
//			this.jpaXmlResource = jpaXmlResource;
//			this.shell = shell;
//			IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
//			this.setRule(ruleFactory.modifyRule(jpaProject.getProject()));
//		}
//
//		@Override
//		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
//			try {
//				postPerformFinish(this.jpaProject,this.jpaXmlResource, this.shell);
//			} catch (InvocationTargetException e) {
//				throw new CoreException(new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, "error", e));
//			}
//			return Status.OK_STATUS;
//		}
//		
//		private void postPerformFinish(JpaProject jpaProject, JpaXmlResource jpaXmlResource, Shell shell) throws InvocationTargetException {
//			try {
//				IFile file = jpaXmlResource.getFile();
//				openEditor(file, shell);
//			}
//			catch (Exception cantOpen) {
//				throw new InvocationTargetException(cantOpen);
//			} 
//		}
//		
//		private void openEditor(final IFile file, Shell shell) {
//			if (file != null) {
//				shell.getDisplay().asyncExec(new Runnable() {
//					public void run() {
//						try {
//							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//							IDE.openEditor(page, file, true);
//						}
//						catch (PartInitException e) {
//							JptJpaUiPlugin.log(e);
//						}
//					}
//				});
//			}
//		}
//	}
	
}
