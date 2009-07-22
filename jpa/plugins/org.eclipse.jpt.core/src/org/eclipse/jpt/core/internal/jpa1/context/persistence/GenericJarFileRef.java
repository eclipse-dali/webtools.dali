/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;

/**
 * Context JAR file reference (from the persistence unit)
 */
public class GenericJarFileRef 
	extends AbstractJarFileRef
{
	// **************** construction/initialization ****************************
	
	public GenericJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		super(parent, xmlJarFileRef);
	}
	
	
	// **************** overrides **********************************************
	
	@Override
	protected IPath[] resolveDeploymentJarFilePath(IPath jarFilePath) {
		IProject project = getJpaProject().getProject();
		IPath rootPath = JptCorePlugin.getJarDeploymentRootPath(project);
		
		if (JptCorePlugin.projectHasWebFacet(project)) {
			return new IPath[] {
				// first path assumes form "../lib/other.jar"
				rootPath.append(jarFilePath.removeFirstSegments(1)),
				// second path assumes form of first, without ".." ("lib/other.jar")
				rootPath.append(jarFilePath)
			};
		}
		return new IPath[] {
			rootPath.append(jarFilePath)
		};
	}
}
