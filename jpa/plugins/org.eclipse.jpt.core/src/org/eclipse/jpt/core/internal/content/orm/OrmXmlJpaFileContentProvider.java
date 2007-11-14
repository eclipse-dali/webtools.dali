/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmArtifactEdit;
import org.eclipse.wst.common.internal.emfworkbench.integration.EditModelEvent;
import org.eclipse.wst.common.internal.emfworkbench.integration.EditModelListener;

public class OrmXmlJpaFileContentProvider implements IJpaFileContentProvider
{
	//singleton
	private static final OrmXmlJpaFileContentProvider INSTANCE = new OrmXmlJpaFileContentProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static IJpaFileContentProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Restrict access
	 */
	private OrmXmlJpaFileContentProvider() {
		super();
	}

	public IJpaRootContentNode buildRootContent(IFile resourceFile) {
		OrmArtifactEdit oae = 
				OrmArtifactEdit.getArtifactEditForRead(resourceFile.getProject());
		OrmResource resource = 
				oae.getOrmResource(resourceFile);
		oae.addListener(buildReloadListener(resource));
		XmlRootContentNode root = OrmFactory.eINSTANCE.createXmlRootContentNode();
		root.setArtifactEdit(oae);
		root.setEntityMappings(resource.getEntityMappings());
		resource.eAdapters().add(buildRootNodeListener(resourceFile, root));
		return root;
	}
	
	private Adapter buildRootNodeListener(IFile resourceFile, XmlRootContentNode root) {
		return new RootAdapter(resourceFile, root);
	}

	private EditModelListener buildReloadListener(OrmResource resource) {
		return new ReloadListener(resource);
	}

	public String contentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}
	
	
	private class RootAdapter extends AdapterImpl 
	{
		final IFile resourceFile;
		final XmlRootContentNode rootContentNode;
		
		RootAdapter(IFile resourceFile, XmlRootContentNode rootContentNode) {
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
					OrmResource resource = (OrmResource) notification.getNotifier();
					this.rootContentNode.setEntityMappings(resource.getEntityMappings());
				}
			}
		}
	}
	
	
	private class ReloadListener implements EditModelListener
	{
		final OrmResource resource;
		
		ReloadListener(OrmResource resource) {
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
