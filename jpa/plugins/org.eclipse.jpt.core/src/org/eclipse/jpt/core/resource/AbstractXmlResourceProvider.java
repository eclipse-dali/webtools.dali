/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.resource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jem.util.emf.workbench.FlexibleProjectResourceSet;
import org.eclipse.jem.util.emf.workbench.ProjectResourceSet;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.wst.common.componentcore.internal.impl.ModuleURIUtil;
import org.eclipse.wst.common.componentcore.internal.impl.PlatformURLModuleConnection;
import org.eclipse.wst.common.componentcore.internal.impl.WTPResourceFactoryRegistry;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateInputProvider;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidator;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidatorImpl;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidatorPresenter;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public abstract class AbstractXmlResourceProvider
	implements JpaXmlResourceProvider, ResourceStateInputProvider, ResourceStateValidator
{
	protected IProject project;
	
	protected URI fileUri;
	
	protected JpaXmlResource resource;
	
	protected IContentType contentType;
	
	protected final ResourceAdapter resourceAdapter = new ResourceAdapter();
	
	protected final ListenerList<JpaXmlResourceProviderListener> listenerList = new ListenerList<JpaXmlResourceProviderListener>(JpaXmlResourceProviderListener.class);
	
	protected ResourceStateValidator stateValidator;
	
	
	/**
	 * Create a new AbstractResourceModelProvider for the given project and 
	 * resourcePath.  The resourcePath may be either a) an absolute platform 
	 * resource path (e.g. "MyProject/src/META-INF/foobar.xml") or b) a relative 
	 * deploy path (e.g. "META-INF/foobar.xml".)  In either case, 
	 * {@link #buildFileUri(IPath)} will attempt to build an absolutely pathed 
	 * URI for the given path.
	 */
	public AbstractXmlResourceProvider(IProject project, IPath resourcePath, IContentType contentType) {
		super();
		this.project = project;
		this.fileUri = buildFileUri(resourcePath);
		this.contentType = contentType;
	}
	
	protected URI buildFileUri(IPath resourcePath) {
		URI resourceUri = null;
		
		if (resourcePath.isAbsolute()) {
			resourceUri = URI.createPlatformResourceURI(resourcePath.toString(), false);
		}
		else {
			resourceUri = getModuleURI(URI.createURI(resourcePath.toString()));
		}
		
		URIConverter uriConverter = getResourceSet().getURIConverter();
		return uriConverter.normalize(resourceUri);
	}
	
	/**
	 * Return the resource, if it exists.  If no file exists for this resource, 
	 * this will return a stub resource.  You must call #createResource() to 
	 * create the file on the file system.
	 */
	public JpaXmlResource getXmlResource() {
		if (this.resource == null) {
			JpaXmlResource newResource = (JpaXmlResource) WorkbenchResourceHelper.getOrCreateResource(this.fileUri, getResourceSet());
			//EMF caches resources based on URI.  If the resource has changed content types (say the schema was changed
			//from orm to eclipselink-orm), then the resource will be of the wrong type and we need to create a new one.
			if (newResource.getContentType().equals(this.contentType)) {
				this.resource = newResource;
			}
			else {
				this.createResourceAndLoad();
			}
		}
		return this.resource;
	}
	
	protected JpaXmlResource createResourceAndLoad() {
		this.resource = createResource();
		this.loadResource();
		return this.resource;
	}
	
	protected JpaXmlResource createResource() {
		Resource.Factory resourceFactory = 
			WTPResourceFactoryRegistry.INSTANCE.getFactory(this.fileUri, this.contentType.getDefaultDescription());
		return (JpaXmlResource) ((FlexibleProjectResourceSet) getResourceSet()).createResource(this.fileUri, resourceFactory);		
	}
	
	protected void loadResource() {
		try {
			this.resource.load(((FlexibleProjectResourceSet) getResourceSet()).getLoadOptions());
		}
		catch (IOException e) {
			JptCorePlugin.log(e);
		}
	}
	
	protected void createResourceAndUnderlyingFile(Object config) {
		this.resource = createResource();
		if (this.resource.fileExists()) { //always possible that the file already exists when the jpa facet is added
			loadResource();
		}
		else {
			populateRoot(config);
			try {
				this.resource.saveIfNecessary(); //this writes out the file
			}
			catch (Exception e) {
				JptCorePlugin.log(e);
			}
		}
	}
	
	/**
	 * This will actually create the underlying file and the JpaXmlResource that corresponds to it.
	 * It also populates the root of the file.
	 * @param config - A configuration object used to specify options for creation of the resource
	 */
	public JpaXmlResource createFileAndResource(final Object config, IProgressMonitor monitor) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) {
				createResourceAndUnderlyingFile(config);
			}
		};
		workspace.run(runnable, this.project, IWorkspace.AVOID_UPDATE, monitor);
		return this.resource;	
	}
	
	protected URI getModuleURI(URI uri) {
		URI moduleuri = ModuleURIUtil.fullyQualifyURI(this.project);
		IPath requestPath = new Path(moduleuri.path()).append(new Path(uri.path()));
		URI resourceURI = URI.createURI(PlatformURLModuleConnection.MODULE_PROTOCOL + requestPath.toString());
		return resourceURI;
	}
	
	/**
	 * Used to optionally fill in the root information of a resource if it does not 
	 * exist as a file
	 */
	protected void populateRoot(Object config) {
		//TODO potentially call resource.populateRoot() instead of the resourceProvider doing this
	}
	

	/**
	 * minimize the scope of the suppressed warnings
	 */
	protected EList<EObject> getResourceContents() {
		return this.resource.getContents();
	}

	public void addListener(JpaXmlResourceProviderListener listener) {
		if (this.listenerList.isEmpty()) {
			engageResource();
		}
		this.listenerList.add(listener);
	}
	
	public void removeListener(JpaXmlResourceProviderListener listener) {
		this.listenerList.remove(listener);
		if (this.listenerList.isEmpty()) {
			disengageResource();
		}
	}
	
	private void engageResource() {
		if (this.resource != null) {
			this.resource.eAdapters().add(this.resourceAdapter);
		}
 	}
	
	private void disengageResource() {
		if (this.resource != null) {
			this.resource.eAdapters().remove(this.resourceAdapter);
		}
	}
	
	protected ProjectResourceSet getResourceSet() {
		return (ProjectResourceSet) WorkbenchResourceHelperBase.getResourceSet(project);
	}
	
	public IProject getProject() {
		return this.project;
	}
	
	protected void resourceIsLoadedChanged(Resource aResource, boolean oldValue, boolean newValue) {
		if ( ! this.listenerList.isEmpty()) {
			int eventType= newValue ? JpaXmlResourceProviderEvent.RESOURCE_LOADED : JpaXmlResourceProviderEvent.RESOURCE_UNLOADED;
			JpaXmlResourceProviderEvent evt = new JpaXmlResourceProviderEvent(this, eventType);
			notifyListeners(evt);
		}
	}
	
	protected void notifyListeners(JpaXmlResourceProviderEvent event) {
		NotifyRunner notifier = new NotifyRunner(event); 
		for (JpaXmlResourceProviderListener listener : this.listenerList.getListeners()) {
			notifier.setListener(listener);
			SafeRunner.run(notifier);
		}
	}
	
	public IStatus validateEdit(Object context) {
		IWorkspace work = ResourcesPlugin.getWorkspace();
		IFile file = WorkbenchResourceHelper.getFile(this.resource);
		if (file != null) {
			IFile[] files = { file };
			if (context == null) {
				context = IWorkspace.VALIDATE_PROMPT;
			}
			return work.validateEdit(files, context);
		} 
		return Status.OK_STATUS;
	}	
	
	// **************** ResourceStateValidator impl ****************************
	
	public ResourceStateValidator getStateValidator() {
		if (this.stateValidator == null) {
			this.stateValidator = createStateValidator();
		}
		return this.stateValidator;
	}
	
	private ResourceStateValidator createStateValidator() {
		return new ResourceStateValidatorImpl(this);
	}
	
	public void checkActivation(ResourceStateValidatorPresenter presenter) throws CoreException {
		getStateValidator().checkActivation(presenter);	
	}
	
	public void lostActivation(ResourceStateValidatorPresenter presenter) throws CoreException {
		getStateValidator().lostActivation(presenter);	
	}
	
	public IStatus validateState(ResourceStateValidatorPresenter presenter) throws CoreException {
		if (presenter == null) {
			return Status.OK_STATUS;
		}
		return getStateValidator().validateState(presenter);
	}
	
	public boolean checkSave(ResourceStateValidatorPresenter presenter) throws CoreException {
		return getStateValidator().checkSave(presenter);
	}
	
	public boolean checkReadOnly() {
		return getStateValidator().checkReadOnly();
	}
	
	
	// **************** ResourceStateInputProvider impl ************************
	
	public boolean isDirty() {	
		return this.resource.isModified();
	}
	
	@SuppressWarnings("unchecked")
	public List getNonResourceFiles() {
		return Collections.emptyList();
	}
	
	@SuppressWarnings("unchecked")
	public List getNonResourceInconsistentFiles() {
		return Collections.emptyList();
	}
	
	@SuppressWarnings("unchecked")
	public List getResources() {
		return Collections.singletonList(getXmlResource());
	}
	
	@SuppressWarnings("unchecked")
	public void cacheNonResourceValidateState(List roNonResourceFiles) {
		// do nothing
	}
	
	
	// **************** member classes *****************************************
	
	protected class ResourceAdapter extends AdapterImpl {
		@Override
		public void notifyChanged(Notification notification) {
			if ( notification.getEventType() == Notification.SET && notification.getFeatureID(null) == Resource.RESOURCE__IS_LOADED) {
				resourceIsLoadedChanged((Resource) notification.getNotifier(), notification.getOldBooleanValue(), notification.getNewBooleanValue());
			}
		}
	}
	
	
	public static class NotifyRunner implements ISafeRunnable 
	{
		private final JpaXmlResourceProviderEvent event;
		
		private JpaXmlResourceProviderListener listener;
		
		
		public NotifyRunner(JpaXmlResourceProviderEvent event) {
			Assert.isNotNull(event);
			this.event = event;
		}
		
		
		public void setListener(JpaXmlResourceProviderListener listener) {
			this.listener = listener;
		}
		
		public void run() throws Exception {
			if (listener != null) {
				listener.modelChanged(event);
			}
		}
		
		public void handleException(Throwable exception) { 
			JptCorePlugin.log(exception);
		}
	}
}
