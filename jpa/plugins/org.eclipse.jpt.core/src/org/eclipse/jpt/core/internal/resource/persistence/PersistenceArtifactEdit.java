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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.wst.common.componentcore.ArtifactEdit;

public class PersistenceArtifactEdit extends ArtifactEdit
{
	/**
	 * @param aProject
	 * @param fileURI
	 * @return the persistence artifact for the file URI in project aProject.
	 * Opened only for read access (no write)
	 */
	public static PersistenceArtifactEdit getArtifactEditForRead(IProject aProject, String fileURI) {
		PersistenceArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new PersistenceArtifactEdit(aProject, fileURI, true);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}

    /**
	 * @param aProject
	 * @param fileURI
	 * @return the persistence artifact for the file URI in project aProject.
     * Opened for both write and read access
     */	
	public static PersistenceArtifactEdit getArtifactEditForWrite(IProject aProject, String fileURI) {
		PersistenceArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new PersistenceArtifactEdit(aProject, fileURI, false);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
	
	private URI fileURI;
	
	
	public PersistenceArtifactEdit(IProject aProject, String aFileURI, boolean toAccessAsReadOnly) 
			throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly);
		fileURI = URI.createURI(aFileURI);
	}
	
	public PersistenceResource getPersistenceResource() {
		try {
			return (PersistenceResource) getArtifactEditModel().getResource(fileURI);
		}
		catch (ClassCastException cce) {
			return null;
		}
	}
}
