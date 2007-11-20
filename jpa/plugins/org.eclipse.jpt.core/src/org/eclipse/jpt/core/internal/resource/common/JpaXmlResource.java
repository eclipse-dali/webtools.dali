package org.eclipse.jpt.core.internal.resource.common;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jem.util.plugin.JEMUtilPlugin;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IResourceModelListener;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;

public abstract class JpaXmlResource extends TranslatorResourceImpl
{
	protected final Collection<IResourceModelListener> resourceModelListeners;
	
	
	protected JpaXmlResource(Renderer aRenderer) {
		super(aRenderer);
		this.resourceModelListeners = new ArrayList<IResourceModelListener>();
	}
	
	protected JpaXmlResource(URI uri, Renderer aRenderer) {
		super(uri, aRenderer);
		this.resourceModelListeners = new ArrayList<IResourceModelListener>();
	}
	
	/**
	 * override to prevent notification when the object's state is unchanged
	 */
	@Override
	public void eNotify(Notification notification) {
		if (!notification.isTouch() && isLoaded()) {
			super.eNotify(notification);
			resourceChanged();
		}
	}

	/**
	 * @see TranslatorResourceImpl#getDefaultPublicId() 
	 */
	protected String getDefaultPublicId() {
		return null;
		// only applicable for DTD-based files
	}
	
	/**
	 * @see TranslatorResourceImpl#getDefaultSystemId() 
	 */
	protected String getDefaultSystemId() {
		return null;
		// only applicable for DTD-based files
	}
	
	/**
	 * @see TranslatorResourceImpl#getDefaultVersionId() 
	 */
	protected int getDefaultVersionID() {
		return 10;
		// this seems to be the default version of the spec for this doc
		// and the id 10 maps to the version 1.0
	}
	
	/**
	 * @see TranslatorResource#getDoctype() 
	 */
	public String getDoctype() {
		return null;
		// only applicable for DTD-based files
	}
	
	public IFile getFile() {
		IFile file = null;
		file = getFile(getURI());
		if (file == null) {
			if (getResourceSet() != null) {
				URIConverter converter = getResourceSet().getURIConverter();
				URI convertedUri = converter.normalize(getURI());
				if (! getURI().equals(convertedUri)) {
					file = getFile(convertedUri);
				}
			}
		}
		return file;
	}
	
	/**
	 * Return the IFile for the <code>uri</code> within the Workspace. This URI is assumed to be
	 * absolute in the following format: platform:/resource/....
	 */
	private IFile getFile(URI uri) {
		if (WorkbenchResourceHelperBase.isPlatformResourceURI(uri)) {
			String fileString = URI.decode(uri.path());
			fileString = fileString.substring(JEMUtilPlugin.PLATFORM_RESOURCE.length() + 1);
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileString));
		}
		return null;
	}
	
	public boolean exists() {
		return getFile().exists();
	}
	
	public abstract IJpaContentNode getContentNode(int offset);
	
	public abstract void handleJavaElementChangedEvent(ElementChangedEvent event);
	
	public void resourceChanged() {
		for (IResourceModelListener listener : this.resourceModelListeners) {
			listener.resourceModelChanged();
		}
	}
	
	public void addResourceModelChangeListener(IResourceModelListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null");
		}
		this.resourceModelListeners.add(listener);
	}
	
	public void removeResourceModelChangeListener(IResourceModelListener listener) {
		if (!this.resourceModelListeners.contains(listener)) {
			throw new IllegalArgumentException("Listener " + listener + " was never added");		
		}
		this.resourceModelListeners.add(listener);
	}
}
