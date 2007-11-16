/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.BaseJpaContent;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

public abstract class ContextModelTestCase extends AnnotationTestCase
{
	protected static final String PROJECT_NAME = "ContextModelTestProject";
		
	protected PersistenceResource persistenceResource;
	
	
	protected ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		waitForWorkspaceJobs();
	}
	
	@Override
	protected void deleteAllProjects()  throws Exception{
		//don't delete projects, creating a new one with a new name
		//workspace will be deleted next time tests are run.
		//not saying this is the ultimate solution, but it will work for now
		//until we can figure out how to properly delete projects in tests
	}
	
	@Override
	protected void tearDown() throws Exception {
		//at least delete the project from the workspace since, deleting from the file system doesn't work well.
		//tests run too slow otherwise because so many projects are created in the workspace
		getJavaProject().getProject().delete(false, true, null);
		this.persistenceResource = null;
		super.tearDown();
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJpaProject(PROJECT_NAME, autoBuild);
	}
	
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) 
			throws Exception {
		return TestJpaProject.buildJpaProject(projectName, autoBuild);  // false = no auto-build
	}
	
	protected IJpaProject jpaProject() {
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
		
		while (waitJob.getState() != Job.NONE) {
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException ie) {
				// do nothing
			}
		}
	}
	
	protected PersistenceResource persistenceResource() {
		if (this.persistenceResource != null) {
			return this.persistenceResource;
		}
		String persistenceXmlUri = JptCorePlugin.persistenceXmlDeploymentURI(getJavaProject().getProject());
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForWrite(getJavaProject().getProject());
		 this.persistenceResource = pae.getResource(persistenceXmlUri);
		 
		 return this.persistenceResource;
	}
	
	protected BaseJpaContent jpaContent() {
		return (BaseJpaContent) getJavaProject().getJpaProject().contextModel();
	}
	
	@Override
	protected TestJpaProject getJavaProject() {
		return (TestJpaProject) super.getJavaProject();
	}
	
	protected IType createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		return this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	protected IType createEnumAndMembers(String enumName, String enumBody) throws Exception {
		return this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}
}
