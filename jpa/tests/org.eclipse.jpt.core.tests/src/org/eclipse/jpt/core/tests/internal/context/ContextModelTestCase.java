/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

public abstract class ContextModelTestCase extends AnnotationTestCase
{
	protected static final String BASE_PROJECT_NAME = "ContextModelTestProject";
	
	protected PersistenceResourceModelProvider persistenceResourceModelProvider;
	
	protected OrmResourceModelProvider ormResourceModelProvider;
	
	
	protected ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.persistenceResourceModelProvider = 
			PersistenceResourceModelProvider.getDefaultModelProvider(getJavaProject().getProject());
		this.ormResourceModelProvider = 
			OrmResourceModelProvider.getDefaultModelProvider(getJavaProject().getProject());
		waitForWorkspaceJobs();
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.persistenceResourceModelProvider = null;
		this.ormResourceModelProvider = null;
		super.tearDown();
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJpaProject(BASE_PROJECT_NAME, autoBuild, buildJpaConfigDataModel());
	}
	
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild, IDataModel jpaConfig) 
			throws Exception {
		return TestJpaProject.buildJpaProject(projectName, autoBuild, jpaConfig);
	}
	
	protected IDataModel buildJpaConfigDataModel() {
		return null;
	}
	
	protected JpaProject jpaProject() {
		return getJavaProject().getJpaProject();
	}
	
	protected void waitForWorkspaceJobs() {
		// This job will not finish running until the workspace jobs are done
		Job waitJob = new Job("Wait job") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					return Status.OK_STATUS;
				}
			};
		waitJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
		waitJob.schedule();
		try {
			waitJob.join();
		} catch (InterruptedException ex) {
			// the job thread was interrupted during a wait - ignore
		}
	}
	
	protected PersistenceResource persistenceResource() {
		return this.persistenceResourceModelProvider.getResource();
	}
	
	protected OrmResource ormResource() {
		return this.ormResourceModelProvider.getResource();
	}
	
	protected XmlPersistence xmlPersistence() {
		return persistenceResource().getPersistence();
	}
	
	protected EntityMappings entityMappings() {
		return persistenceUnit().mappingFileRefs().next().getMappingFile().getEntityMappings();
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		return persistenceResource().getPersistence().getPersistenceUnits().get(0);
	}
	
	protected PersistenceUnit persistenceUnit() {
		return getRootContextNode().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	protected ClassRef classRef() {
		return persistenceUnit().specifiedClassRefs().next();
	}
	
	protected JavaPersistentType javaPersistentType() {
		return classRef().getJavaPersistentType();
	}
	
	protected Entity javaEntity() {
		return (Entity) javaPersistentType().getMapping();
	}
	
	protected void addXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(className);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
	}
	
	protected void removeXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		XmlJavaClassRef xmlJavaClassRefToRemove  = null;
		for (XmlJavaClassRef xmlJavaClassRef : xmlPersistenceUnit.getClasses()) {
			if (xmlJavaClassRef.getJavaClass().equals(className)) {
				xmlJavaClassRefToRemove = xmlJavaClassRef;
			}
		}
		if (xmlJavaClassRefToRemove == null) {
			throw new IllegalArgumentException();
		}
		xmlPersistenceUnit.getClasses().remove(xmlJavaClassRefToRemove);
	}

	protected JpaRootContextNode getRootContextNode() {
		return getJavaProject().getJpaProject().getRootContextNode();
	}
	
	@Override
	protected TestJpaProject getJavaProject() {
		return (TestJpaProject) super.getJavaProject();
	}
	
	protected void deleteResource(Resource resource) throws CoreException {
		WorkbenchResourceHelper.deleteResource(resource);
	}

}
