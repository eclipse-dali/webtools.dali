/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.common;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.ResourceModelListener;
import org.eclipse.jpt.core.internal.AbstractResourceModel;
import org.eclipse.wst.common.internal.emfworkbench.integration.EditModelEvent;
import org.eclipse.wst.common.internal.emfworkbench.integration.EditModelListener;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class JpaXmlResourceModel extends AbstractResourceModel
{
	protected JpaArtifactEdit artifactEdit;
	
	protected JpaXmlResource resource;
	
	
	protected JpaXmlResourceModel(IFile file) {
		super();
		this.artifactEdit = buildArtifactEdit(file.getProject());
		this.resource = this.artifactEdit.getResource(file);
		this.resource.setResourceModel(this);
		this.artifactEdit.addListener(buildReloadListener(this.resource));
	}
	
	
	protected abstract JpaArtifactEdit buildArtifactEdit(IProject project);
	
	private EditModelListener buildReloadListener(JpaXmlResource resource) {
		return new ReloadListener(resource);
	}
	
	@Override
	public JpaXmlResource getResource() {
		return this.resource;
	}
	
	public void javaElementChanged(ElementChangedEvent event) {
		getResource().javaElementChanged(event);
	}
	
	public void addResourceModelChangeListener(ResourceModelListener listener) {
		getResource().addResourceModelChangeListener(listener);
	}
	
	public void removeResourceModelChangeListener(ResourceModelListener listener) {
		getResource().removeResourceModelChangeListener(listener);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.artifactEdit.dispose();
	}
	
	public void resolveTypes() {
		//nothing to do here, JavaResourceModel needs this
	}
	
	private class ReloadListener implements EditModelListener
	{
		final JpaXmlResource resource;
		
		
		ReloadListener(JpaXmlResource resource) {
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
	}
}
