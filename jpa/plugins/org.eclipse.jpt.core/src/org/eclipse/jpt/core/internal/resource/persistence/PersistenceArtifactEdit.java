/*******************************************************************************
 *  Copyright (c) 2006, 2007  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.persistence;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.common.JpaArtifactEdit;

public class PersistenceArtifactEdit extends JpaArtifactEdit
{
	/**
	 * @param aProject
	 * @return a persistence artifact for project aProject.
	 * Opened only for read access (no write)
	 */
	public static PersistenceArtifactEdit getArtifactEditForRead(IProject aProject) {
		PersistenceArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new PersistenceArtifactEdit(aProject, true);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
    /**
	 * @param aProject
	 * @return a persistence artifact for the project aProject.
     * Opened for both write and read access
     */	
	public static PersistenceArtifactEdit getArtifactEditForWrite(IProject aProject) {
		PersistenceArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new PersistenceArtifactEdit(aProject, false);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
    
	public PersistenceArtifactEdit(IProject aProject, boolean toAccessAsReadOnly) 
			throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly);
	}
	
	
	@Override
	public PersistenceResource getResource(IFile file) {
		// This *seems* to do the same basic thing as below, but circumvents the
		// URI munging that ArtifactEditModel does (see bug 209093)
		try {
			PersistenceResource resource = 
					(PersistenceResource) getArtifactEditModel().createResource(URI.createPlatformResourceURI(file.getFullPath().toString()));
			if (! resource.isLoaded()) {
				resource.load(getArtifactEditModel().getResourceSet().getLoadOptions());
			}
			return resource;
		}
		catch (ClassCastException cce) {
			return null;
		}
		catch (IOException ioe) {
			JptCorePlugin.log(ioe);
			return null;
		}
	}
	
	@Override
	public PersistenceResource getResource(String fileURI) {
		try {
			return (PersistenceResource) getArtifactEditModel().getResource(URI.createURI(fileURI));
		}
		catch (ClassCastException cce) {
			return null;
		}
	}
	
	/**
	 * Return a persistence resource for the default deploy location
	 */
	public PersistenceResource getResource() {
		return getResource(JptCorePlugin.persistenceXmlDeploymentURI(getProject()));
	}
	
	/**
	 * Create a persistence resource with base defaults
	 */
	public PersistenceResource createDefaultResource() {
		PersistenceResource resource = getResource();
		XmlPersistence persistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
		persistence.setVersion("1.0");
		XmlPersistenceUnit pUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		pUnit.setName(getProject().getName());
		persistence.getPersistenceUnits().add(pUnit);
		resource.getContents().add(persistence);
		save(null);
		return resource;
	}
}
