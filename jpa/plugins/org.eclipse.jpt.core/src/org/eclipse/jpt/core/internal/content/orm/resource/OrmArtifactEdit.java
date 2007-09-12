package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.orm.OrmResource;
import org.eclipse.wst.common.componentcore.ArtifactEdit;

public class OrmArtifactEdit extends ArtifactEdit
{
	/**
	 * @param aProject
	 * @param fileURI - this must be in a deployment relevant form 
	 * 	(e.g "META-INF/orm.xml" instead of "src/META-INF/orm.xml")
	 * @return the orm artifact for the file URI in project aProject.
	 * Opened only for read access (no write)
	 */
	public static OrmArtifactEdit getArtifactEditForRead(IProject aProject, String fileURI) {
		OrmArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new OrmArtifactEdit(aProject, URI.createURI(fileURI), true);
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
	 * 	(e.g "META-INF/orm.xml" instead of "src/META-INF/orm.xml")
	 * @return the orm artifact for the file URI in project aProject.
     * Opened for both write and read access
     */	
	public static OrmArtifactEdit getArtifactEditForWrite(IProject aProject, String fileURI) {
		OrmArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new OrmArtifactEdit(aProject, URI.createURI(fileURI), false);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
    
	private URI fileURI;
	
	
	public OrmArtifactEdit(IProject aProject, URI aFileURI, boolean toAccessAsReadOnly) 
			throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly);
		fileURI = aFileURI;
	}
	
	
	public OrmResource getOrmResource() {
		try {
			return (OrmResource) getArtifactEditModel().getResource(fileURI);
		}
		catch (ClassCastException cce) {
			return null;
		}
	}
}
