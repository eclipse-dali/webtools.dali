/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetInstallDelegate 
	implements IDelegate, IJpaFacetDataModelProperties
{
	public void execute(IProject project, IProjectFacetVersion fv, 
				Object config, IProgressMonitor monitor) throws CoreException {
		
		if (monitor != null) {
			monitor.beginTask("", 1); //$NON-NLS-1$
		}

		// NB:  WTP Natures (including the JavaEMFNature)
		//  should already be added as this facet should 
		//  always coexist with a module facet.
		
		IJavaProject javaProject = JavaCore.create(project);
		
		String jpaLibrary = ((IDataModel) config).getStringProperty(JPA_LIBRARY);
		if (jpaLibrary != null && ! jpaLibrary.equals("")) {
			IClasspathEntry[] classpath = javaProject.getRawClasspath();
			int newLength = classpath.length + 1;
			IClasspathEntry jpaLibraryEntry = 
				JavaCore.newContainerEntry(
					new Path(JavaCore.USER_LIBRARY_CONTAINER_ID + "/" + jpaLibrary));
			IClasspathEntry[] newClasspath = new IClasspathEntry[newLength];
			System.arraycopy(classpath, 0, newClasspath, 0, newLength - 1);
			newClasspath[newLength - 1] = jpaLibraryEntry;
			
			javaProject.setRawClasspath(newClasspath, monitor);
		}
			
		if (monitor != null) {
			monitor.worked(1);
		}
	}
}
