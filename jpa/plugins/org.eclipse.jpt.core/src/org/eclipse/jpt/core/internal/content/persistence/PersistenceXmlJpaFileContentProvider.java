/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceResource;
import org.eclipse.wst.common.internal.emfworkbench.integration.EditModelEvent;
import org.eclipse.wst.common.internal.emfworkbench.integration.EditModelListener;

public class PersistenceXmlJpaFileContentProvider implements IJpaFileContentProvider
{
	//singleton
	private static final PersistenceXmlJpaFileContentProvider INSTANCE = new PersistenceXmlJpaFileContentProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static IJpaFileContentProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Restrict access
	 */
	private PersistenceXmlJpaFileContentProvider() {
		super();
	}

	public IJpaRootContentNode buildRootContent(IJpaFile jpaFile) {
		IFile resourceFile = jpaFile.getFile();
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForRead(resourceFile.getProject());
		PersistenceResource resource = 
				pae.getPersistenceResource(resourceFile);
		PersistenceXmlRootContentNode root = PersistenceFactory.eINSTANCE.createPersistenceXmlRootContentNode();		
		root.setResource(resource);
		root.setPersistence(resource.getPersistence());
		resource.eAdapters().add(buildRootNodeListener(resourceFile, root));
		pae.addListener(buildReloadListener(resource));
		jpaFile.setContent(root);
		return root;
	}
	
	private Adapter buildRootNodeListener(IFile resourceFile, PersistenceXmlRootContentNode rootContentNode) {
		return new RootAdapter(resourceFile, rootContentNode);
	}
	
	private EditModelListener buildReloadListener(PersistenceResource resource) {
		return new ReloadListener(resource);
	}

	public String contentType() {
		return JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE;
	}
	
	
	private class RootAdapter extends AdapterImpl 
	{
		final IFile resourceFile;
		final PersistenceXmlRootContentNode rootContentNode;
		
		RootAdapter(IFile resourceFile, PersistenceXmlRootContentNode rootContentNode) {
			super();
			this.resourceFile = resourceFile;
			this.rootContentNode = rootContentNode;
		}
		
		@Override
		public void notifyChanged(Notification notification) {
			int featureId = notification.getFeatureID(Resource.class);
			if (featureId == Resource.RESOURCE__CONTENTS) {
				if (notification.getEventType() == Notification.ADD
						|| notification.getEventType() == Notification.REMOVE) {
					PersistenceResource resource = (PersistenceResource) notification.getNotifier();
					this.rootContentNode.setPersistence(resource.getPersistence());
				}
			}
		}
	}
	
	
	private class ReloadListener implements EditModelListener
	{
		final PersistenceResource resource;
		
		ReloadListener(PersistenceResource resource) {
			super();
			this.resource = resource;
		}
		
		public void editModelChanged(EditModelEvent anEvent) {
			switch (anEvent.getEventCode()) {
				case EditModelEvent.UNLOADED_RESOURCE :
					if (anEvent.getChangedResources().contains(resource)
							&& ! resource.isLoaded()) {
						try {
							resource.load(resource.getResourceSet().getLoadOptions());
						}
						catch (IOException ioe) {
							JptCorePlugin.log(ioe);
						}
					}
					break;
				case EditModelEvent.REMOVED_RESOURCE :
					if (anEvent.getChangedResources().contains(resource)) {
						anEvent.getEditModel().removeListener(this);
					}
					break;
//				case EditModelEvent.SAVE :
//				case EditModelEvent.PRE_DISPOSE :				
			}
			
		}
			// commenting out for now - this *was* a workaround for 202190, but with ArtifactEdit
			// usage, it no longer works
			// 
			// 11/07/07 - Actually, it has now been replaced by the above code
//			else if (featureId == Resource.RESOURCE__IS_LOADED) {
//				if (file.exists()) {
//					// dumb translator is unloading my resource, reload it
//					if (notification.getNewBooleanValue() == false) {
//						PersistenceResource resource = (PersistenceResource) notification.getNotifier();
//						try {
//							resource.load(Collections.EMPTY_MAP);
//						}
//						catch (IOException ioe) {
//							// hmmm, log for now
//							JptCorePlugin.log(ioe);
//						}
//					}
//				}
//			}
	}
}
