/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;

/**
 * Treat the JAXB project as an "aspect" of the Eclipse project (IProject);
 * but the JAXB project is stored in the JAXB model, not the Eclipse project
 * itself....
 * We also need to listen for the JAXB project to be rebuilt if the user
 * changes the Eclipse project's JAXB platform (which is stored in the
 * Eclipse project's preferences).
 */
class JaxbProjectModel
		extends AspectPropertyValueModelAdapter<IProject, JaxbProject> {
	
	/**
	 * The JAXB project may also change via another page (notably, the project facets page).
	 * In that case, the preference change occurs before we actually have another project,
	 * so we must listen to the projects manager
	 */
	private final JaxbProjectManager jaxbProjectManager;
	private final CollectionChangeListener projectManagerListener;
	
	
	JaxbProjectModel(PropertyValueModel<IProject> projectModel) {
		super(projectModel);
		this.jaxbProjectManager = this.getJaxbProjectManager();
		this.projectManagerListener = buildProjectManagerListener();
	}
	
	private CollectionChangeListener buildProjectManagerListener() {
		return new CollectionChangeAdapter() {
			// we are only looking for the project rebuild *add* event here so we can
			// determine if the platform has changed.
			// the other events are unimportant in this case
			@Override
			public void itemsAdded(CollectionAddEvent event) {
				platformChanged();
			}
		};
	}
	
	void platformChanged() {
		this.aspectChanged();
	}
	
	@Override
	protected void engageSubject_() {
		if (this.jaxbProjectManager != null) {
			this.jaxbProjectManager.addCollectionChangeListener(JaxbProjectManager.JAXB_PROJECTS_COLLECTION, this.projectManagerListener);
		}
	}
	
	@Override
	protected void disengageSubject_() {
		if (this.jaxbProjectManager != null) {
			this.jaxbProjectManager.removeCollectionChangeListener(JaxbProjectManager.JAXB_PROJECTS_COLLECTION, this.projectManagerListener);
		}
	}
	
	@Override
	protected JaxbProject buildValue_() {
		return (this.jaxbProjectManager == null) ? null : this.jaxbProjectManager.getJaxbProject(this.subject);
	}

	private JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		JaxbWorkbench jaxbWorkbench = this.getJaxbWorkbench();
		return (jaxbWorkbench == null) ? null : jaxbWorkbench.getJaxbWorkspace();
	}

	private JaxbWorkbench getJaxbWorkbench() {
		return WorkbenchTools.getAdapter(JaxbWorkbench.class);
	}
}
