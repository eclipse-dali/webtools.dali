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
import java.util.Map;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.gen.internal.old.EntityGenerator;
import org.eclipse.jpt.gen.internal.old.PackageGenerator;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * two pages:
 *   - a Database Schema page that allows the user to select a schema
 *   - a Generate Entities page that allows the user to select which tables in
 *     the selected schema are to be used and to configure Entity names
 */
public class GenerateEntitiesWizard extends Wizard {	

	private final JpaProject jpaProject;

	private final IStructuredSelection selection;

	private DatabaseSchemaWizardPage dbSchemaPage;
	
	private GenerateEntitiesWizardPage generateEntitiesPage;

	private final PackageGenerator.Config packageGeneratorConfig;

	private final EntityGenerator.Config entityGeneratorConfig;

	private boolean synchronizePersistenceXml;


	public GenerateEntitiesWizard(JpaProject jpaProject, IStructuredSelection selection) {
		super();
		this.jpaProject = jpaProject;
		this.selection = selection;
		this.packageGeneratorConfig = new PackageGenerator.Config();
		this.entityGeneratorConfig = new EntityGenerator.Config();
		this.setWindowTitle(JptUiMessages.GenerateEntitiesWizard_generateEntities);
	}

	@Override
	public void addPages() {
		super.addPages();
		this.dbSchemaPage = new DatabaseSchemaWizardPage(this.jpaProject);
		this.dbSchemaPage.addListener(new SchemaPageListener());
		this.addPage(this.dbSchemaPage);
		
		this.generateEntitiesPage = new GenerateEntitiesWizardPage();
		this.addPage(this.generateEntitiesPage);
		this.generateEntitiesPage.init(this.selection);
	}
	
	@Override
	public boolean canFinish() {
		return this.generateEntitiesPage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		this.packageGeneratorConfig.setPackageFragment(this.buildPackageFragment());

		this.entityGeneratorConfig.setConvertToJavaStyleIdentifiers(this.generateEntitiesPage.convertToJavaStyleIdentifiers());
		this.entityGeneratorConfig.setFieldAccessType(this.generateEntitiesPage.fieldAccessType());
		this.entityGeneratorConfig.setCollectionTypeName(this.generateEntitiesPage.getCollectionTypeName());
		this.entityGeneratorConfig.setCollectionAttributeNameSuffix(this.generateEntitiesPage.getCollectionAttributeNameSuffix());
		this.entityGeneratorConfig.setFieldVisibility(this.generateEntitiesPage.getFieldVisibility());
		this.entityGeneratorConfig.setMethodVisibility(this.generateEntitiesPage.getMethodVisibility());
		this.entityGeneratorConfig.setGenerateGettersAndSetters(this.generateEntitiesPage.generateGettersAndSetters());
		this.entityGeneratorConfig.setGenerateDefaultConstructor(this.generateEntitiesPage.generateDefaultConstructor());
		this.entityGeneratorConfig.setSerializable(this.generateEntitiesPage.serializable());
		this.entityGeneratorConfig.setGenerateSerialVersionUID(this.generateEntitiesPage.generateSerialVersionUID());
		this.entityGeneratorConfig.setGenerateEmbeddedIdForCompoundPK(this.generateEntitiesPage.generateEmbeddedIdForCompoundPK());
		this.entityGeneratorConfig.setEmbeddedIdAttributeName(this.generateEntitiesPage.getEmbeddedIdAttributeName());
		this.entityGeneratorConfig.setPrimaryKeyMemberClassName(this.generateEntitiesPage.getPrimaryKeyMemberClassName());
		for (Map.Entry<Table, String> entry : this.generateEntitiesPage.getSelectedTables().entrySet()) {
			this.entityGeneratorConfig.addTable(entry.getKey(), entry.getValue());
		}
		// the name builder comes from the JPA platform
		this.entityGeneratorConfig.setDatabaseAnnotationNameBuilder(this.buildDatabaseAnnotationNameBuilder());

		this.synchronizePersistenceXml = this.generateEntitiesPage.synchronizePersistenceXml();
		
		return true;
	}

	private EntityGenerator.DatabaseAnnotationNameBuilder buildDatabaseAnnotationNameBuilder() {
		return new LocalDatabaseAnnotationNameBuilder(this.jpaProject.getJpaPlatform().getEntityGeneratorDatabaseAnnotationNameBuilder());
	}

	private IPackageFragment buildPackageFragment() {
		IPackageFragmentRoot packageFragmentRoot = this.generateEntitiesPage.getPackageFragmentRoot();
		IPackageFragment packageFragment = this.generateEntitiesPage.getPackageFragment();
		
		if (packageFragment == null) {
			packageFragment= packageFragmentRoot.getPackageFragment(""); //$NON-NLS-1$
		}
		
		if (packageFragment.exists()) {
			return packageFragment;
		}

		try {
			return packageFragmentRoot.createPackageFragment(packageFragment.getElementName(), true, null);
		} 
		catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}


	// ********** intra-wizard methods **********

	Collection<Table> getPossibleTables() {
		return this.buildTables(this.dbSchemaPage.getSelectedSchema());
	}

	JpaProject getJpaProject() {
		return this.jpaProject;
	}

	void selectedSchemaChanged(Schema schema) {
		this.generateEntitiesPage.setPossibleTables(this.buildTables(schema));
	}

	private Collection<Table> buildTables(Schema schema) {
		return (schema == null) ?
						Collections.<Table>emptySet()
					:
						CollectionTools.collection(schema.tables());
	}


	// ********** public methods - settings **********

	public PackageGenerator.Config getPackageGeneratorConfig() {
		return this.packageGeneratorConfig;
	}

	public EntityGenerator.Config getEntityGeneratorConfig() {
		return this.entityGeneratorConfig;
	}

	public boolean synchronizePersistenceXml(){
		return this.synchronizePersistenceXml;
	}


	// ********** name builder adapter **********

	/**
	 * adapt the JPA platform-supplied builder to the builder interface
	 * expected by the entity generator
	 */
	static class LocalDatabaseAnnotationNameBuilder implements EntityGenerator.DatabaseAnnotationNameBuilder {
		private EntityGeneratorDatabaseAnnotationNameBuilder builder;
		LocalDatabaseAnnotationNameBuilder(EntityGeneratorDatabaseAnnotationNameBuilder builder) {
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


	// ********** schema page listener **********

	/**
	 * listen for when the Database Connection page changes its selected schema
	 * so we can keep the Generate Entities page in synch
	 */
	class SchemaPageListener implements DatabaseSchemaWizardPage.Listener {
		public void selectedSchemaChanged(Schema schema) {
			GenerateEntitiesWizard.this.selectedSchemaChanged(schema);
		}
	}
}
