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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceResource;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

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
		
		PersistenceResource resource = 
				(PersistenceResource) WorkbenchResourceHelper.getResource(resourceFile, true);
		PersistenceXmlRootContentNode root = PersistenceFactory.eINSTANCE.createPersistenceXmlRootContentNode();
		
		if (resourceFile.equals(resource.getFile())) {
			resource.accessForWrite();
			root.setResource(resource);
			root.setPersistence(resource.getPersistence());
			resource.eAdapters().add(buildRootNodeListener(resourceFile, root));
		}
		
		jpaFile.setContent(root);
		return root;
	}
	
	private Adapter buildRootNodeListener(IFile resourceFile, PersistenceXmlRootContentNode rootContentNode) {
		return new RootAdapter(resourceFile, rootContentNode);
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
			// commenting out for now - this *was* a workaround for 202190, but with ArtifactEdit
			// usage, it no longer works
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
}
