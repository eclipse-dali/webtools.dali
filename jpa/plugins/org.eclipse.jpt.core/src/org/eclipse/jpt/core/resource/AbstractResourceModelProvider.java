/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
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
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.wst.common.componentcore.internal.impl.ModuleURIUtil;
import org.eclipse.wst.common.componentcore.internal.impl.PlatformURLModuleConnection;
import org.eclipse.wst.common.componentcore.internal.impl.WTPResourceFactoryRegistry;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateInputProvider;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidator;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidatorImpl;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidatorPresenter;

public abstract class AbstractResourceModelProvider 
	implements JpaResourceModelProvider, ResourceStateInputProvider, ResourceStateValidator
{
	protected IProject project;
	
	protected URI fileUri;
	
	protected JpaXmlResource resource;
	
	protected final ResourceAdapter resourceAdapter = new ResourceAdapter();
	
	protected final ListenerList<JpaResourceModelProviderListener> listenerList = new ListenerList<JpaResourceModelProviderListener>(JpaResourceModelProviderListener.class);
	
	protected ResourceStateValidator stateValidator;
	
	
	/**
	 * Create a new AbstractResourceModelProvider for the given project and 
	 * resourcePath.  The resourcePath may be either a) an absolute platform 
	 * resource path (e.g. "MyProject/src/META-INF/foobar.xml") or b) a relative 
	 * deploy path (e.g. "META-INF/foobar.xml".)  In either case, 
	 * {@link #buildFileUri(IPath)} will attempt to build an absolutely pathed 
	 * URI for the given path.
	 */
	public AbstractResourceModelProvider(IProject project, IPath resourcePath) {
		super();
		this.project = project;
		this.fileUri = buildFileUri(resourcePath);
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
	public JpaXmlResource getResource() {
		if (this.resource == null) {
			try {
				this.resource = 
					(JpaXmlResource) WorkbenchResourceHelper.getOrCreateResource(this.fileUri, getResourceSet());
			}
			catch (ClassCastException cce) {
				Resource.Factory resourceFactory = 
					WTPResourceFactoryRegistry.INSTANCE.getFactory(fileUri, getContentType(getContentTypeDescriber()));
				this.resource = 
					(JpaXmlResource)((FlexibleProjectResourceSet) getResourceSet()).createResource(fileUri, resourceFactory);
			}
		}
		return this.resource;
	}
	
	public JpaXmlResource createResource() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				JpaXmlResource aResource = getResource();
				if (! aResource.exists() && aResource.getContents().isEmpty()) {
					populateRoot(aResource);
					try {
						aResource.saveIfNecessary();
					}
					catch (Exception e) {
						JptCorePlugin.log(e);
					}
				}
			}
		};
		workspace.run(runnable, workspace.getRoot(), IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
		return this.resource;	
	}
	
	protected URI getModuleURI(URI uri) {
		URI moduleuri = ModuleURIUtil.fullyQualifyURI(project);
		IPath requestPath = new Path(moduleuri.path()).append(new Path(uri.path()));
		URI resourceURI = URI.createURI(PlatformURLModuleConnection.MODULE_PROTOCOL + requestPath.toString());
		return resourceURI;
	}
	
	protected IContentDescription getContentType(String contentTypeDescriber) {
		if (contentTypeDescriber != null) {
			return Platform.getContentTypeManager().getContentType(contentTypeDescriber).getDefaultDescription();
		} else {
			return null;
		}
	}
	
	/**
	 * Used to optionally define an associated content type for XML file creation
	 * @return
	 */
	protected String getContentTypeDescriber() {	
		return null;
	}
	
	/**
	 * Used to optionally fill in the root information of a resource if it does not 
	 * exist as a file
	 */
	protected void populateRoot(JpaXmlResource resource) {}
	

	/**
	 * minimize the scope of the suppressed warnings
	 */
	@SuppressWarnings("unchecked")
	protected EList<EObject> getResourceContents(JpaXmlResource resource) {
		return resource.getContents();
	}

	public void addListener(JpaResourceModelProviderListener listener) {
		if (this.listenerList.isEmpty()) {
			engageResource();
		}
		this.listenerList.add(listener);
	}
	
	public void removeListener(JpaResourceModelProviderListener listener) {
		listenerList.remove(listener);
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
			int eventType= newValue ? JpaResourceModelProviderEvent.RESOURCE_LOADED : JpaResourceModelProviderEvent.RESOURCE_UNLOADED;
			JpaResourceModelProviderEvent evt = new JpaResourceModelProviderEvent(this, eventType);
			notifyListeners(evt);
		}
	}
	
	protected void notifyListeners(JpaResourceModelProviderEvent event) {
		NotifyRunner notifier = new NotifyRunner(event); 
		for (JpaResourceModelProviderListener listener : this.listenerList.getListeners()) {
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
		else {
			return Status.OK_STATUS;
		}
	}
	
	public void modify(Runnable runnable) {
		try {
			runnable.run();
			try {
				if (resource != null) {
					resource.save(Collections.EMPTY_MAP);
				}
			} catch (IOException ioe) {
				JptCorePlugin.log(ioe);
			}
		} catch (Exception e) {
			JptCorePlugin.log(e);
		}
	}
	
	
	// **************** ResourceStateValidator impl ****************************
	
	public ResourceStateValidator getStateValidator() {
		if (stateValidator == null) {
			stateValidator = createStateValidator();
		}
		return stateValidator;
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
		return resource.isModified();
	}
	
	public List getNonResourceFiles() {
		return Collections.emptyList();
	}
	
	public List getNonResourceInconsistentFiles() {
		return Collections.emptyList();
	}
	
	public List getResources() {
		return Collections.singletonList(getResource());
	}
	
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
	
	
	public class NotifyRunner implements ISafeRunnable 
	{
		private final JpaResourceModelProviderEvent event;
		
		private JpaResourceModelProviderListener listener;
		
		
		public NotifyRunner(JpaResourceModelProviderEvent event) {
			Assert.isNotNull(event);
			this.event = event;
		}
		
		
		public void setListener(JpaResourceModelProviderListener listener) {
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
