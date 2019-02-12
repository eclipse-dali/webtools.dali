/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.model.value;

import java.util.Iterator;
import java.util.Vector;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jpt.common.core.internal.utility.ResourceChangeAdapter;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.value.AbstractCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * Adapt the Eclipse workspace list of projects to a collection value model,
 * notifying listeners whenever projects are added or removed.
 */
public class WorkspaceProjectsModel
	extends AbstractCollectionValueModel
	implements CollectionValueModel<IProject>
{
	private final IWorkspace workspace;
	private final IResourceChangeListener resourceChangeListener = new ResourceChangeListener();
	private final Vector<IProject> projects = new Vector<IProject>();


	public WorkspaceProjectsModel(IWorkspaceRoot workspaceRoot) {
		this(workspaceRoot.getWorkspace());
	}

	public WorkspaceProjectsModel(IWorkspace workspace) {
		super();
		if (workspace == null) {
			throw new NullPointerException();
		}
		this.workspace = workspace;
	}

	public Iterator<IProject> iterator() {
		return IteratorTools.clone(this.projects);
	}

	public int size() {
		return this.projects.size();
	}

	@Override
	protected void engageModel() {
		this.workspace.addResourceChangeListener(this.resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
		CollectionTools.addAll(this.projects, this.workspace.getRoot().getProjects());
	}

	@Override
	protected void disengageModel() {
		this.workspace.removeResourceChangeListener(this.resourceChangeListener);
		this.projects.clear();
	}

	/* CU private */ void projectAdded(IProject project) {
		this.addItemToCollection(project, this.projects, VALUES);
	}

	/* CU private */ void projectRemoved(IProject project) {
		this.removeItemFromCollection(project, this.projects, VALUES);
	}


	// ********** resource change listener **********

	/**
	 * Listen for changes to the workspace's list of projects
	 */
	/* CU private */ class ResourceChangeListener
		extends ResourceChangeAdapter
	{
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				this.processPostChangeDelta(event.getDelta());
			}
		}

		private void processPostChangeDelta(IResourceDelta delta) {
			IResource resource = delta.getResource();
			switch (resource.getType()) {
				case IResource.ROOT :
					this.processPostChangeDeltaChildren(delta);
					break;
				case IResource.PROJECT :
					this.processProject((IProject) resource, delta);
					break;
				default :
					break;
			}
		}

		private void processPostChangeDeltaChildren(IResourceDelta delta) {
			for (IResourceDelta child : delta.getAffectedChildren()) {
				this.processPostChangeDelta(child);  // recurse
			}
		}

		private void processProject(IProject project, IResourceDelta delta) {
			switch (delta.getKind()) {
				case IResourceDelta.ADDED :
					WorkspaceProjectsModel.this.projectAdded(project);
					break;
				case IResourceDelta.REMOVED :
					WorkspaceProjectsModel.this.projectRemoved(project);
					break;
				default :
					break;
			}
		}
	}
}
