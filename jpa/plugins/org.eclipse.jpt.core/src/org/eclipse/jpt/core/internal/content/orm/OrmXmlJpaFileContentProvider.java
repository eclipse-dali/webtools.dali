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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmArtifactEdit;
import org.eclipse.jpt.core.internal.emfutility.ComponentUtilities;

public class OrmXmlJpaFileContentProvider implements IJpaFileContentProvider
{
	public static OrmXmlJpaFileContentProvider INSTANCE = new OrmXmlJpaFileContentProvider();
	
	
	private IFile file;
	
	
	/**
	 * Restrict access
	 */
	private OrmXmlJpaFileContentProvider() {
		
	}

	public IJpaRootContentNode buildRootContent(IFile resourceFile) {
		file = resourceFile;
		IPath deployPath = ComponentUtilities.computeDeployPath(resourceFile);
		OrmArtifactEdit artifactEdit = 
				OrmArtifactEdit.getArtifactEditForWrite(
						resourceFile.getProject(),
						deployPath.toString());
		OrmResource resource = artifactEdit.getOrmResource();
		XmlRootContentNode root = OrmFactory.eINSTANCE.createXmlRootContentNode();
			
		if (resourceFile.equals(resource.getFile())) {
			root.setArtifactEdit(artifactEdit);
			root.setEntityMappings(resource.getEntityMappings());
			resource.eAdapters().add(buildRootNodeListener(root));
		}
		else {
			artifactEdit.dispose();
		}
		
		return root;
	}
	
	private Adapter buildRootNodeListener(XmlRootContentNode root) {
		return new RootAdapter(root);
	}

	protected ResourceSet getResourceSet(IFile file) {
		return WorkbenchResourceHelperBase.getResourceSet(file.getProject());
	}

	public String contentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}
	
	
	private class RootAdapter extends AdapterImpl 
	{
		XmlRootContentNode root;
		
		private RootAdapter(XmlRootContentNode rootContentNode) {
			super();
			root = rootContentNode;
		}
		public void notifyChanged(Notification notification) {
			int featureId = notification.getFeatureID(Resource.class);
			if (featureId == Resource.RESOURCE__CONTENTS) {
				if (notification.getEventType() == Notification.ADD
						|| notification.getEventType() == Notification.REMOVE) {
					OrmResource resource = (OrmResource) notification.getNotifier();
					root.setEntityMappings(resource.getEntityMappings());
				}
			}
			// commenting out for now - this *was* a workaround for 202190, but with ArtifactEdit
			// usage, it no longer works
//			else if (featureId == Resource.RESOURCE__IS_LOADED) {
//				if (file.exists()) {
//					// dumb translator is unloading my resource, reload it
//					if (notification.getNewBooleanValue() == false) {
//						OrmResource resource = (OrmResource) notification.getNotifier();
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
