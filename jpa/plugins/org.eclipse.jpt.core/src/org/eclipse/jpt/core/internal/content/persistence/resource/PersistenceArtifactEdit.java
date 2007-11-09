package org.eclipse.jpt.core.internal.content.persistence.resource;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.wst.common.componentcore.ArtifactEdit;

public class PersistenceArtifactEdit extends ArtifactEdit
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
	
	
	/**
	 * @return a persistence resource for the given file
	 */
	public PersistenceResource getPersistenceResource(IFile file) {
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
	
	/**
	 * @param fileURI - this must be in a deployment relevant form 
	 * 	(e.g "META-INF/persistence.xml" instead of "src/META-INF/persistence.xml")
	 * @return a persistence resource for the given deployment file URI
	 */
	public PersistenceResource getPersistenceResource(String fileURI) {
		try {
			return (PersistenceResource) getArtifactEditModel().getResource(URI.createURI(fileURI));
		}
		catch (ClassCastException cce) {
			return null;
		}
	}
}
