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
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceResource;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceXmlResourceFactory;

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
		PersistenceXmlResourceFactory.register();
		URI fileURI = URI.createPlatformResourceURI(resourceFile.getFullPath().toString(), true);
		PersistenceResource resource = (PersistenceResource) getResourceSet(resourceFile).getResource(fileURI, true);
		PersistenceXmlRootContentNode root = PersistenceFactory.eINSTANCE.createPersistenceXmlRootContentNode();
		jpaFile.setContent(root);
		root.setPersistence(resource.getPersistence());
		resource.eAdapters().add(buildRootNodeListener(resourceFile, root));
		return root;
	}
	
	private Adapter buildRootNodeListener(IFile resourceFile, PersistenceXmlRootContentNode rootContentNode) {
		return new RootAdapter(resourceFile, rootContentNode);
	}

	protected ResourceSet getResourceSet(IFile file) {
		return WorkbenchResourceHelperBase.getResourceSet(file.getProject());
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
			else if (featureId == Resource.RESOURCE__IS_LOADED) {
				if (this.resourceFile.exists()) {
					// dumb translator is unloading my resource, reload it
					if (notification.getNewBooleanValue() == false) {
						PersistenceResource resource = (PersistenceResource) notification.getNotifier();
						try {
							resource.load(Collections.EMPTY_MAP);
						}
						catch (IOException ioe) {
							// hmmm, log for now
							JptCorePlugin.log(ioe);
						}
					}
				}
			}
		}
	}
}
