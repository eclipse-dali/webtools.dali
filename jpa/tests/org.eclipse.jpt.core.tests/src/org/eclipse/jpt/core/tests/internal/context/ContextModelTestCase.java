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

import junit.framework.TestCase;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

public abstract class ContextModelTestCase extends TestCase
{
	protected static final String PROJECT_NAME = "ContextModelTestProject";
	
	
	protected TestJpaProject jpaProject;
	
	
	protected ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ProjectUtility.deleteAllProjects();
		this.jpaProject = this.buildJpaProject(PROJECT_NAME, false);  // false = no auto-build
	}
	
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) 
			throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
	}
	
	@Override
	protected void tearDown() throws Exception {
//		this.dumpSource();
//		this.javaProject.dispose();
		this.jpaProject = null;
		super.tearDown();
	}
	
	protected void waitForUpdate() {
		// This job will not finish running until the update job has finished
		Job waitJob = new Job("Wait job") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					return Status.OK_STATUS;
				}
			};
		waitJob.setRule(jpaProject.getProject());
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
}
