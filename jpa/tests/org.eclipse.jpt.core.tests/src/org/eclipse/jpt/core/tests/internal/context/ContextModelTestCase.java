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
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.BaseJpaContent;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

public abstract class ContextModelTestCase extends AnnotationTestCase
{
	protected static final String PROJECT_NAME = "ContextModelTestProject";
		
	
	protected ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		waitForWorkspaceJobs();
		waitForProjectUpdate();
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJpaProject(PROJECT_NAME, autoBuild);
	}
	
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) 
			throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
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
	
	protected void waitForProjectUpdate() {
		// This job will not finish running until the update job has finished
		Job waitJob = new Job("Wait job") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					return Status.OK_STATUS;
				}
			};
		waitJob.setRule(getJavaProject().getProject());
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
	
	protected PersistenceResourceModel persistenceResourceModel() {
		String persistenceXmlUri = JptCorePlugin.persistenceXmlDeploymentURI(getJavaProject().getProject());
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForWrite(getJavaProject().getProject(), persistenceXmlUri);
		return pae.getPersistenceResource();
	}
	
	protected BaseJpaContent jpaContent() {
		return (BaseJpaContent) getJavaProject().getJpaProject().contextModel();
	}
	
	@Override
	protected TestJpaProject getJavaProject() {
		return (TestJpaProject) super.getJavaProject();
	}
}
