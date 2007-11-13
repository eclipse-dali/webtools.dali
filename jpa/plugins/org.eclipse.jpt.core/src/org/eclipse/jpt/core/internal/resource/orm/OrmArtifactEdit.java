package org.eclipse.jpt.core.internal.resource.orm;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.wst.common.componentcore.ArtifactEdit;

public class OrmArtifactEdit extends ArtifactEdit
{
	/**
	 * @param aProject
	 * @return an orm artifact for the project aProject.
	 * Opened only for read access (no write)
	 */
	public static OrmArtifactEdit getArtifactEditForRead(IProject aProject) {
		OrmArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new OrmArtifactEdit(aProject, true);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
    /**
	 * @param aProject
	 * @return an orm artifact for the project aProject.
     * Opened for both write and read access
     */	
	public static OrmArtifactEdit getArtifactEditForWrite(IProject aProject) {
		OrmArtifactEdit artifactEdit = null;
		try {
			artifactEdit = new OrmArtifactEdit(aProject, false);
		} 
		catch (IllegalArgumentException iae) {
            // suppress illegal argument exception
            JptCorePlugin.log(iae);
		}
		return artifactEdit;
	}
	
    
	public OrmArtifactEdit(IProject aProject, boolean toAccessAsReadOnly) 
			throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly);
	}
	
	
	/**
	 * @return an orm resource for the given file
	 */
	public OrmResourceModel getOrmResource(IFile file) {
		// This *seems* to do the same basic thing as below, but circumvents the
		// URI munging that ArtifactEditModel does (see bug 209093)
		try {
			OrmResourceModel resource = 
					(OrmResourceModel) getArtifactEditModel().createResource(URI.createPlatformResourceURI(file.getFullPath().toString()));
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
	 * 	(e.g "META-INF/orm.xml" instead of "src/META-INF/orm.xml")
	 * @return an orm resource for the given deployment file URI
	 */
	public OrmResourceModel getOrmResource(String fileURI) {
		try {
			return (OrmResourceModel) getArtifactEditModel().getResource(URI.createURI(fileURI));
		}
		catch (ClassCastException cce) {
			return null;
		}
	}
}
