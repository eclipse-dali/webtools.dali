/*******************************************************************************
* Copyright (c) 2007, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.common.ui.gen.AbstractJptGenerateJob;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.OutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.core.internal.ddlgen.EclipseLinkDDLGenerator;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.DatabaseSchemaWizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  GenerateDDLWizard
 */
public class GenerateDDLWizard extends Wizard {	

	private static final String CR = StringTools.CR;
	
	private JpaProject jpaProject;
	private String puName;
	private JptGenerator ddlGenerator;
	
	private DatabaseSchemaWizardPage dbSettingsPage;
	private GenerationOutputModeWizardPage generationOutputModePage;

	// ********** constructor **********

	public GenerateDDLWizard(JpaProject jpaProject, String puName) {
		super();
		this.jpaProject = jpaProject;
		this.puName = puName;
		this.setWindowTitle(JptJpaUiMessages.GenerateDDLWizard_title); 
	}

	// ********** IWizard implementation  **********

	@Override
	public void addPages() {
		super.addPages();
		if(this.getJpaProjectConnectionProfile() == null) {
			this.dbSettingsPage = new DatabaseSchemaWizardPage(this.jpaProject);
			this.addPage(this.dbSettingsPage);
		}
		this.generationOutputModePage = new GenerationOutputModeWizardPage();
		this.addPage(this.generationOutputModePage);
	}
	
	@Override
	public boolean performFinish() {
		OutputMode outputMode = this.getOutputMode();

		if((outputMode != OutputMode.sql_script) && ( ! this.displayGeneratingDDLWarning())) {
			return false;
		}
		
		this.scheduleGenerateDDLJob(outputMode);
		return true;
	}

    @Override
	public boolean canFinish() {
    	return this.dbSettingsPageCanFinish() && this.generationOutputModePageCanFinish();
    }

	protected void scheduleGenerateDDLJob(OutputMode outputMode) {
			
		WorkspaceJob generateDDLJob = this.buildGenerateDDLJob(this.puName, this.jpaProject, outputMode);

		generateDDLJob.schedule();
	}
	
	protected WorkspaceJob buildGenerateDDLJob(String puName, JpaProject project, OutputMode outputMode) {
		return new GenerateDDLJob(puName, project, outputMode);
	}

	// ********** intra-wizard methods **********
    
    public OutputMode getOutputMode() {
		return this.generationOutputModePage.getOutputMode();
	}
	
	// ********** internal methods **********

    private boolean dbSettingsPageCanFinish() {
		return (this.dbSettingsPage != null) ? this.dbSettingsPage.isPageComplete() : true;
	}

	private boolean generationOutputModePageCanFinish() {
		return this.generationOutputModePage.isPageComplete();
	}
	
	private ConnectionProfile getJpaProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}

	private boolean displayGeneratingDDLWarning() {
		if (!OptionalMessageDialog.isDialogEnabled(OverwriteConfirmerDialog.ID)) {
			return true;
		} else {
			OverwriteConfirmerDialog dialog = new OverwriteConfirmerDialog(this.getCurrentShell());
			return dialog.open() == IDialogConstants.YES_ID;
		}
	}

	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}
	
	private JptGenerator getDDLGenerator() {
		return ddlGenerator;
	}

	protected void setDDLGenerator(JptGenerator ddlGenerator) {
		this.ddlGenerator = ddlGenerator;
	}

	// ********** overwrite dialog **********

	static class OverwriteConfirmerDialog extends OptionalMessageDialog {

		private static final String ID= "dontShowOverwriteExistingTablesFromClasses.warning"; //$NON-NLS-1$

		OverwriteConfirmerDialog(Shell parent) {
			super(ID, parent,
					JptJpaEclipseLinkUiMessages.ECLIPSELINK_DDL_GENERATOR_UI_GENERATING_DDL_WARNING_TITLE,
					NLS.bind(JptJpaEclipseLinkUiMessages.ECLIPSELINK_DDL_GENERATOR_UI_GENERATING_DDL_WARNING_MESSAGE, CR,  CR + CR),
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

	// ********** generate ddl job **********

	protected static class GenerateDDLJob extends AbstractJptGenerateJob {
		protected final String puName;
		protected final JpaProject jpaProject;
		protected final OutputMode outputMode;

		// ********** constructor **********

		protected GenerateDDLJob(String puName, JpaProject jpaProject, OutputMode outputMode) {
			
			super(JptJpaEclipseLinkUiMessages.ECLIPSELINK_GENERATE_TABLES_JOB, jpaProject.getJavaProject());

			this.puName = puName;
			this.jpaProject = jpaProject;
			this.outputMode = outputMode;
		}

		// ********** overwrite AbstractJptGenerateJob **********

		@Override
		protected JptGenerator buildGenerator() {
			return new EclipseLinkDDLGenerator(this.puName, this.jpaProject, this.outputMode);
		}

		@Override
		protected void postGenerate() {
			if(this.outputMode != OutputMode.database) {
				this.refreshProject();
				this.openGeneratedSqlFile();
			}
		}

		@Override
		protected String getJobName() {
			return JptJpaEclipseLinkUiMessages.ECLIPSELINK_GENERATE_TABLES_TASK;
		}

		@Override
		protected void jptPluginLogException(Exception exception) {
			JptJpaEclipseLinkUiPlugin.instance().logError(exception);
		}

		// ********** internal methods **********

		private void openGeneratedSqlFile() {

			IPath projecName = new Path(this.getJavaProject().getProject().getName());
			IContainer container = (IContainer)ResourcesPlugin.getWorkspace().getRoot().findMember(projecName);
			IFile sqlFile = container.getFile(new Path(SchemaGeneration.DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME));

			this.openEditor(sqlFile);
		}
	}	
}
