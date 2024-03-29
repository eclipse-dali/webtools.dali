/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.jaxbprops;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;


public class JaxbPropertiesResourceModelProvider
		implements JaxbResourceModelProvider {
	
	// singleton
	private static final JaxbPropertiesResourceModelProvider INSTANCE = new JaxbPropertiesResourceModelProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPropertiesResourceModelProvider instance() {
		return INSTANCE;
	}
	
	
	private final Map<IFile, JaxbPropertiesResourceImpl> models = new HashMap<IFile, JaxbPropertiesResourceImpl>();
	
	private final IResourceChangeListener resourceChangeListener;
	
	
	/**
	 * Enforce singleton usage
	 */
	private JaxbPropertiesResourceModelProvider() {
		super();
		this.resourceChangeListener = buildResourceChangeListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this.resourceChangeListener);
	}
	
	
	protected IResourceChangeListener buildResourceChangeListener() {
		return new IResourceChangeListener() {	
			public void resourceChanged(IResourceChangeEvent event) {
				handleResourceChangeEvent(event);
			}
		};
	}
	
	public IContentType getContentType() {
		return JaxbPropertiesResource.CONTENT_TYPE;
	}
	
	public JptResourceModel buildResourceModel(JaxbProject jaxbProject, IFile file) {
		return buildResourceModel(file);
	}
	
	public JaxbPropertiesResource buildResourceModel(IFile file) {
		JaxbPropertiesResourceImpl resource = this.models.get(file);
		if (resource != null) {
			return resource;
		}
		resource = new JaxbPropertiesResourceImpl(file);
		this.models.put(file, resource);
		return resource;
	}
	
	protected void handleResourceChangeEvent(IResourceChangeEvent event) {
		switch (event.getType()) {
			case IResourceChangeEvent.PRE_CLOSE :
			case IResourceChangeEvent.PRE_DELETE :
				removeProjectResources((IProject) event.getResource());
				return;
			
			case IResourceChangeEvent.POST_CHANGE :
				updateResources(event.getDelta());
		}
	}
	
	protected void removeProjectResources(IProject removedProject) {
		for (IFile file : IterableTools.cloneSnapshot(this.models.keySet())) {
			if (file.getProject().equals(removedProject)) {
				removeResource(file);
			}
		}
	}
	
	protected void updateResources(IResourceDelta delta) {
		try {
			delta.accept(new ResourceDeltaVisitor());
		}
		catch (CoreException ce) {
			// shouldn't happen
			JptJaxbCorePlugin.instance().logError(ce);
		}
	}
	
	protected void removeResource(IFile file) {
		this.models.remove(file);
	}
	
	protected void updateResource(IFile file) {
		JaxbPropertiesResourceImpl resource = this.models.get(file);
		if (resource != null) {
			resource.update();
		}
	}
	
	
	protected class ResourceDeltaVisitor
			implements IResourceDeltaVisitor {
		
		public boolean visit(IResourceDelta delta) {
			IResource res = delta.getResource();
			switch (res.getType()) {
				case IResource.ROOT :
					return true;  // visit children
				case IResource.PROJECT :
					return true;  // visit children
				case IResource.FOLDER :
					return true;  // visit children
				case IResource.FILE :
					fileChanged((IFile) res, delta.getKind());
					return false;  // no children
				default :
					return false;  // no children (probably shouldn't get here...)
			}
		}
		
		protected void fileChanged(IFile file, int deltaKind) {
			if (deltaKind == IResourceDelta.REMOVED) {
				JaxbPropertiesResourceModelProvider.this.removeResource(file);
			}
			else if (deltaKind == IResourceDelta.CHANGED) {
				JaxbPropertiesResourceModelProvider.this.updateResource(file);
			}
		}
	}
}
