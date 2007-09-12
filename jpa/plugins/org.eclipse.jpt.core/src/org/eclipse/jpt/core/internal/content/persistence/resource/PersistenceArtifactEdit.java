package org.eclipse.jpt.core.internal.content.persistence.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.wst.common.componentcore.ArtifactEdit;

public class PersistenceArtifactEdit extends ArtifactEdit
{
	/**
	 * @param aProject
	 * @param fileURI - this must be in a deployment relevant form 
	 * 	(e.g "META-INF/persistence.xml" instead of "src/META-INF/persistence.xml")
	 * @return the persistence artifact for the file URI in project aProject.
	 * Opened only for read access (no write)
	 */
	public static PersistenceArtifactEdit getArtifactEditForRead(IProject aProject, String fileURI) {
		PersistenceArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new PersistenceArtifactEdit(aProject, URI.createURI(fileURI), true);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
    /**
	 * @param aProject
	 * @param fileURI - this must be in a deployment relevant form 
	 * 	(e.g "META-INF/persistence.xml" instead of "src/META-INF/persistence.xml")
	 * @return the persistence artifact for the file URI in project aProject.
     * Opened for both write and read access
     */	
	public static PersistenceArtifactEdit getArtifactEditForWrite(IProject aProject, String fileURI) {
		PersistenceArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new PersistenceArtifactEdit(aProject, URI.createURI(fileURI), false);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
    
	private URI fileURI;
	
	
	public PersistenceArtifactEdit(IProject aProject, URI aFileURI, boolean toAccessAsReadOnly) 
			throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly);
		fileURI = aFileURI;
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
