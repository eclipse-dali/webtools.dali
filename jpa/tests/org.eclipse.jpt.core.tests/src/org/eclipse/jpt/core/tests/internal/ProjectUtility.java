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
package org.eclipse.jpt.core.tests.internal;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
/**
 * Copied from org.eclipse.wst.common.tests
 */
public class ProjectUtility {
    public static IProject[] getAllProjects() {
    	IProject[] projects = new IProject[0];
    	try {
        projects =  ResourcesPlugin.getWorkspace().getRoot().getProjects();
    	} catch (AssertionFailedException ex) {
    		// Catch Malformed tree exception that occurs from time to time...
    	}
    	return projects;
    }
    public static void deleteAllProjects() throws Exception {
        //closing projects and tread work in here is a hack because of a BeanInfo bug holding
        //onto jars loaded in another VM
        
//        for (int i = 0; i < projects.length; i++) {
//            if (projects[i].exists()) {
//                projects[i].close(null); // This should signal the extra VM to kill itself
//            }
//        }
 //       Thread.yield(); // give the VM a chance to die
        IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) {
				IProject[] projects = getAllProjects();
				for (int i = 0; i < projects.length; i++) {
					IProject project = projects[i];
					boolean success = false;
					Exception lastException = null;
					// Don't make 2^12 is about 4 seconds which is the max we
					// will wait for the VM to die
					for (int j = 0; j < 13 && !success; j++) {
						try {
							if (project.exists()) {
								project.delete(true, true, null);
								ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
							}
							success = true;
						} catch (Exception e) {
							lastException = e;
							if (project.exists()) {
								try {
									project.close(null);
									project.open(null);
								} catch (Exception e2) {
									// do nothing
								}
							}
							try {
								Thread.sleep((int) Math.pow(2, j));
							} catch (InterruptedException e1) {
								// do nothing
							} // if the VM isn't dead, try sleeping
						}
					}
					if (!success && lastException != null) {
						//Logger.getLogger().log("Problem while deleting: " + lastException.getMessage());
//						 Assert.fail("Caught Exception=" +
//						 lastException.getMessage() + " when deleting project=" + project.getName());
					}
				}
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(runnable, null);
		} catch (CoreException ce) {
			// do nothing
		}
        //verifyNoProjects();
    }
}