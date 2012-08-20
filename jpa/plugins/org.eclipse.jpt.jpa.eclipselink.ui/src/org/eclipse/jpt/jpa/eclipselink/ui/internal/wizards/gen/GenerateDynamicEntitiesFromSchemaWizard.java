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

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.GenerateEntitiesFromSchemaWizard;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.PromptJPAProjectWizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.TableAssociationsWizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.TablesSelectorWizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class GenerateDynamicEntitiesFromSchemaWizard extends GenerateEntitiesFromSchemaWizard 
	implements INewWizard  {	
	
	public static final String HELP_CONTEXT_ID = JptJpaEclipseLinkUiPlugin.instance().getPluginID() + ".GenerateEntitiesFromSchemaWizard"; //$NON-NLS-1$

	// ********** constructor **********

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
		
		//open file after generation
		String xmlMappingFileLocation = getCustomizer().getXmlMappingFile();
		JptXmlResource jpaXmlResource = this.jpaProject.getMappingFileXmlResource(new Path(xmlMappingFileLocation));
		IFile mappingFile;
		if(jpaXmlResource!=null){
			mappingFile = jpaXmlResource.getFile();
		}
		else{
			IProject project = jpaProject.getProject();
			IContainer container = ((ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class)).getDefaultResourceLocation();
			mappingFile = container.getFile(new Path(xmlMappingFileLocation.substring(xmlMappingFileLocation.lastIndexOf("/")))); //$NON-NLS-1$
		}
		OpenXmlMappingFileJob openXmlMappingFileJob = new OpenXmlMappingFileJob(this.jpaProject, mappingFile);
		openXmlMappingFileJob.schedule();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		
		this.setWindowTitle(JptJpaEclipseLinkUiEntityGenMessages.GenerateDynamicEntitiesWizard_generateEntities);
	}

	@Override
	protected PromptJPAProjectWizardPage buildProjectWizardPage() {
		return new PromptEclipseLinkProjectWizardPage(HELP_CONTEXT_ID);
	}

	@Override
	protected boolean projectIsValidSelection(JpaProject jpaProject) {
		if(jpaProject == null) {
			return false;
		}
		return jpaProject.getJpaPlatform().getDescription().getGroupDescription().getId().
			equals(EclipseLinkPlatform.GROUP.getId());
	}
	
	@Override
	public ORMGenCustomizer createORMGenCustomizer(Schema schema) {
		ORMGenCustomizer customizer = super.createORMGenCustomizer(schema);
		customizer.setPlatformVersion(getJpaProject().getJpaPlatform().getJpaVersion().getVersion());
		return customizer;
	}
	
	
	// ********** open xml mapping file job **********
	
	public static class OpenXmlMappingFileJob extends WorkspaceJob {
		private final JpaProject jpaProject;
		private final IFile mappingFile;

		public OpenXmlMappingFileJob(JpaProject jpaProject, IFile mappingFile) {
			super("Open XML File");
			this.jpaProject = jpaProject;
			this.mappingFile = mappingFile;
			IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
			this.setRule(ruleFactory.modifyRule(jpaProject.getProject()));
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			try {
				postGeneration(this.jpaProject,this.mappingFile);
			} catch (InvocationTargetException e) {
				throw new CoreException(JptJpaEclipseLinkUiPlugin.instance().buildErrorStatus());
			}
			return Status.OK_STATUS;
		}
		
		private void postGeneration(JpaProject jpaProject, IFile mappingFile) throws InvocationTargetException {
			try {
				openEditor(mappingFile);
			}
			catch (Exception cantOpen) {
				throw new InvocationTargetException(cantOpen);
			} 
		}
		
		private void openEditor(final IFile file) {
			if (file != null) {
				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						try {
							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							IDE.openEditor(page, file, true);
						}
						catch (PartInitException e) {
							JptJpaEclipseLinkUiPlugin.instance().logError(e);
						}
					}
				});
			}
		}
	}
	
}
