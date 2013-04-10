/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jpa.core.JpaEntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextModelRoot;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.annotate.DatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;

public class JpaMakePersistentWizard
	extends Wizard
{
	private final JpaProject jpaProject;

	private final Set<IType> selectedTypes;

	private final ResourceManager resourceManager;

	private JpaMakePersistentWizardPage makePersistentWizardPage;
	
	private ClassMappingPage classMappingPage;
	
	private PropsMappingPage propsMappingPage;
	
	private JavaClassMapping[] javaClassMappings;

	private static final String HELP_CONTEXT_ID = JptJpaUiPlugin.instance().getPluginID() + ".GenerateEntitiesFromSchemaWizard"; //$NON-NLS-1$

	private DatabaseAnnotationNameBuilder databaseAnnotationNameBuilder;

	public JpaMakePersistentWizard(JpaProject jpaProject, Set<IType> selectedTypes) {
		super();
		this.jpaProject = jpaProject;
		this.selectedTypes = selectedTypes;
		this.resourceManager = this.buildResourceManager();
		this.javaClassMappings = this.buildJavaClassMappings(this.selectedTypes);
		this.setWindowTitle(JptJpaUiMessages.JpaMakePersistentWizardPage_title);
		this.setDefaultPageImageDescriptor(JptJpaUiImages.ENTITY_BANNER);
		this.databaseAnnotationNameBuilder = new LocalDatabaseAnnotationNameBuilder(this.jpaProject.getJpaPlatform().getEntityGeneratorDatabaseAnnotationNameBuilder());
	}

	protected ResourceManager buildResourceManager() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench != null) ? jpaWorkbench.buildLocalResourceManager() : new LocalResourceManager(JFaceResources.getResources(WorkbenchTools.getDisplay()));
	}

	protected JavaClassMapping[] buildJavaClassMappings(Set<IType> selectedJdtTypes) 
	{
		List<JavaClassMapping> mappings = new ArrayList<JavaClassMapping>();
		for (IType jdtType : selectedJdtTypes)
		{
			JpaFile jpaFile = this.jpaProject.getJpaFile((IFile) jdtType.getResource());
			if (jpaFile.getRootStructureNodesSize() == 0)
				mappings.add(new JavaClassMapping(jdtType));
		}
		return mappings.toArray(new JavaClassMapping[0]);
	}
	
	private JpaWorkbench getJpaWorkbench() {
		return WorkbenchTools.getAdapter(JpaWorkbench.class);
	}

	@Override
	public void addPages() {
		this.setForcePreviousAndNextButtons(true);
		this.makePersistentWizardPage = new JpaMakePersistentWizardPage(this.jpaProject, this.javaClassMappings, this.resourceManager, HELP_CONTEXT_ID);
		this.classMappingPage = new ClassMappingPage(this.jpaProject, this.javaClassMappings, this.resourceManager, this.makePersistentWizardPage);
		this.propsMappingPage = new PropsMappingPage(this.jpaProject, this.javaClassMappings, this.resourceManager, this.classMappingPage);
		this.addPage(this.makePersistentWizardPage);
		this.addPage(this.classMappingPage);
		this.addPage(this.propsMappingPage);
		return;
	}

	@Override
	public boolean performFinish() {
		try {
			this.makePersistentWizardPage.performFinish();
		} catch (InvocationTargetException ex) {
			JptJpaUiPlugin.instance().logError(ex);
		}
		return true;
	}
	
	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}
	
	protected Schema getSchema()
	{
		return this.classMappingPage.getSchema();
	}
	
	protected JavaClassMapping[] getJavaClassMappings()
	{
		return this.classMappingPage.getJavaClassMappings();
	}
	
	protected PersistenceUnit getPersistenceUnit()
	{
		JpaContextModelRoot rcn = this.jpaProject.getContextModelRoot();
		PersistenceXml pxml = (rcn == null) ? null : rcn.getPersistenceXml();
		Persistence persistence = (pxml == null) ? null : pxml.getRoot();
		if (persistence == null) 
		{
			return null;
		}
		ListIterator<PersistenceUnit> units = persistence.getPersistenceUnits().iterator();
		return units.hasNext() ? units.next() : null;		
	}
	
	protected DatabaseAnnotationNameBuilder getDatabaseAnnotationNameBuilder()
	{
		return this.databaseAnnotationNameBuilder;
	}
	
	protected static final class TypeComparator extends ViewerComparator {
		public final JavaElementComparator javaElementComparator = new JavaElementComparator();

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if ((e1 instanceof JavaClassMapping) && (e2 instanceof JavaClassMapping)) {
				return this.javaElementComparator.compare(viewer, ((JavaClassMapping) e1).getJDTType(),  ((JavaClassMapping) e2).getJDTType());
			}
			return 0;
		}
	}
	
	// ********** name builder adapter **********

	/**
	 * adapt the JPA platform-supplied builder to the builder interface
	 * expected by the entity generator
	 */
	private static class LocalDatabaseAnnotationNameBuilder implements DatabaseAnnotationNameBuilder {
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
		
}
