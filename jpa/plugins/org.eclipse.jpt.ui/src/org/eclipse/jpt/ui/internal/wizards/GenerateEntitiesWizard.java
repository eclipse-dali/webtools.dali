/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.util.Collection;
import java.util.Collections;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.gen.internal.EntityGenerator;
import org.eclipse.jpt.gen.internal.PackageGenerator;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class GenerateEntitiesWizard extends Wizard {	

	private JpaProject jpaProject;

	private IStructuredSelection selection;

	/** this page is only built when the project is not connected to the db */
	private DatabaseReconnectWizardPage dbSettingsPage;

	private GenerateEntitiesWizardPage generateEntitiesPage;

	private PackageGenerator.Config packageGeneratorConfig;

	private EntityGenerator.Config entityGeneratorConfig;

	private boolean synchronizePersistenceXml;
	
	private Collection<Table> selectedTables;

	public GenerateEntitiesWizard( JpaProject jpaProject, IStructuredSelection selection) {
		super();
		this.jpaProject = jpaProject;
		this.selection = selection;
		this.packageGeneratorConfig = new PackageGenerator.Config();
		this.entityGeneratorConfig = new EntityGenerator.Config();
		this.setWindowTitle( JptUiMessages.GenerateEntitiesWizard_generateEntities);
	}
	
	@Override
	public void addPages() {
		super.addPages();
		this.dbSettingsPage = new DatabaseReconnectWizardPage( this.jpaProject);
		this.addPage(this.dbSettingsPage);
		this.generateEntitiesPage = new GenerateEntitiesWizardPage();
		this.addPage( this.generateEntitiesPage);
		this.generateEntitiesPage.init( this.selection);
	}
	
	@Override
	public boolean performFinish() {
		this.packageGeneratorConfig.setPackageFragment( this.buildPackageFragment());

		this.entityGeneratorConfig.setConvertToCamelCase( this.generateEntitiesPage.convertToCamelCase());
		this.entityGeneratorConfig.setFieldAccessType( this.generateEntitiesPage.fieldAccessType());
		this.entityGeneratorConfig.setCollectionTypeName( this.generateEntitiesPage.getCollectionTypeName());
		this.entityGeneratorConfig.setFieldVisibility( this.generateEntitiesPage.getFieldVisibility());
		this.entityGeneratorConfig.setMethodVisibility( this.generateEntitiesPage.getMethodVisibility());
		this.entityGeneratorConfig.setGenerateGettersAndSetters( this.generateEntitiesPage.generateGettersAndSetters());
		this.entityGeneratorConfig.setGenerateDefaultConstructor( this.generateEntitiesPage.generateDefaultConstructor());
		this.entityGeneratorConfig.setSerializable( this.generateEntitiesPage.serializable());
		this.entityGeneratorConfig.setGenerateSerialVersionUID( this.generateEntitiesPage.generateSerialVersionUID());
		this.entityGeneratorConfig.setGenerateEmbeddedIdForCompoundPK( this.generateEntitiesPage.generateEmbeddedIdForCompoundPK());
		this.entityGeneratorConfig.setOverrideEntityNames( this.generateEntitiesPage.getOverrideEntityNames());

		this.synchronizePersistenceXml = this.generateEntitiesPage.synchronizePersistenceXml();
		
		this.selectedTables = this.generateEntitiesPage.getSelectedTables();
		return true;
	}
	
	private IPackageFragment buildPackageFragment() {
		IPackageFragmentRoot packageFragmentRoot = this.generateEntitiesPage.getPackageFragmentRoot();
		IPackageFragment packageFragment = this.generateEntitiesPage.getPackageFragment();
		
		if ( packageFragment == null) {
			packageFragment= packageFragmentRoot.getPackageFragment( ""); //$NON-NLS-1$
		}
		
		if ( packageFragment.exists()) {
			return packageFragment;
		}

		try {
			return packageFragmentRoot.createPackageFragment( packageFragment.getElementName(), true, null);
		} 
		catch ( JavaModelException ex) {
			throw new RuntimeException( ex);
		}
	}
	
	Collection<Table> getPossibleTables() {
		if ( this.dbSettingsPage != null) {
			return this.dbSettingsPage.getTables();
		}
		return ( this.projectDefaultSchemaExists()) ? CollectionTools.collection( this.getDefaultSchema().tables()) : Collections.<Table>emptyList();
	}
	
	ConnectionProfile getProjectConnectionProfile() {
		return this.jpaProject.connectionProfile();
	}
	
	JpaProject getJpaProject(){
		return this.jpaProject;
	}

	Schema getDefaultSchema() {
		return getProjectConnectionProfile().defaultSchema();
	}
	
	public PackageGenerator.Config getPackageGeneratorConfig() {
		return this.packageGeneratorConfig;
	}

	public EntityGenerator.Config getEntityGeneratorConfig() {
		return this.entityGeneratorConfig;
	}

	public Collection<Table> getSelectedTables() {
		return this.selectedTables;
	}
	
	public boolean synchronizePersistenceXml(){
		return this.synchronizePersistenceXml;
	}
	
    @Override
	public boolean canFinish() {
        boolean canFinish = true;
        if ( ! this.generateEntitiesPage.isPageComplete()) {
        	canFinish = false;
        }
        return canFinish;
    }

	private boolean projectDefaultSchemaExists() {
		return ( this.getDefaultSchema() != null);
	}
	
	/**
	 * updatePossibleTables is called when schema’s PossibleTables changed.
	 * The dbSettingsPage is mainly the source of changes, 
	 * and the generateEntitiesPage needs to be kept in sync.
	 */
	void updatePossibleTables( Collection<Table> possibleTables) {
		this.generateEntitiesPage.updateTablesListViewer( possibleTables);
	}

}
