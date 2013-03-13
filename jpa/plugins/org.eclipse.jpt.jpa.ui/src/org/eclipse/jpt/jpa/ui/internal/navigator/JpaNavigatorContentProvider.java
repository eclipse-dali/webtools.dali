/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import java.util.HashMap;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.core.internal.utility.ResourceChangeAdapter;
import org.eclipse.jpt.common.ui.internal.jface.NavigatorContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.ui.JpaContextModelRootModel;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;

/**
 * This provider is invoked for:<ul>
 * <li>Eclipse projects with a JPA facet
 * <li>JPA root context node models
 * <li>JPA context nodes
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 * <p>
 * <strong>NB:</strong> We can only refresh the navigator once the navigator
 * has instantiated and invoked this provider for the first time. At that point
 * we are listening for resource changes and can force a refresh of the
 * navigator as the JPA facet is added or removed from the projects.
 * In particular: If a project is already present and expanded in the navigator,
 * and this provider has never been invoked (i.e. there are no JPA projects in
 * the workspace yet), adding the JPA facet to the project will <em>not</em>
 * result in the "JPA Content" node showing up under the project's node in the
 * navigator tree. The navigator must be explicitly refreshed,
 * forcing it to re-examine the provider trigger points and invoking this
 * provider as a result.
 */
public class JpaNavigatorContentProvider
	extends NavigatorContentProvider
{
	private volatile IResourceChangeListener resourceChangeListener;
	private final HashMap<IProject, JpaContextModelRootModel[]> projectChildren = new HashMap<IProject, JpaContextModelRootModel[]>();


	public JpaNavigatorContentProvider() {
		super();
	}


	// ********** initialization **********

	@Override
	public void init(ICommonContentExtensionSite config) {
		super.init(config);
		this.resourceChangeListener = new ResourceChangeListener();
		this.getWorkspace().addResourceChangeListener(this.resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	protected ItemTreeContentProviderFactory buildItemContentProviderFactory() {
		return new JpaNavigatorItemContentProviderFactory();
	}

	@Override
	protected ItemExtendedLabelProvider.Factory buildItemLabelProviderFactory() {
		return new JpaNavigatorItemLabelProviderFactory();
	}

	@Override
	protected ResourceManager buildResourceManager() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench != null) ? jpaWorkbench.buildLocalResourceManager() : this.buildResourceManager_();
	}

	private ResourceManager buildResourceManager_() {
		return new LocalResourceManager(JFaceResources.getResources(this.getWorkbench().getDisplay()));
	}


	// ********** children **********

	/**
	 * @see #getChildren_(Object)
	 */
	@Override
	protected boolean hasChildren_(Object element) {
		return this.getChildren_(element) != null;
	}

	/**
	 * We handle the children for an {@link IProject} here and allow the
	 * superclass to delegate all others.
	 */
	@Override
	protected Object[] getChildren_(Object element) {
		return (element instanceof IProject) ?
				this.getChildren((IProject) element) :
				null;
	}

	private Object[] getChildren(IProject project) {
		synchronized (this.projectChildren) {
			return this.getChildren_(project);
		}
	}

	/**
	 * Pre-condition: {@link #projectChildren} is <code>synchronized</code>
	 */
	private JpaContextModelRootModel[] getChildren_(IProject project) {
		JpaContextModelRootModel[] children = this.projectChildren.get(project);
		if (children == null) {
			children = this.buildChildren(project);
			this.projectChildren.put(project, children);
		}
		return children;
	}

	private JpaContextModelRootModel[] buildChildren(IProject project) {
		return new JpaContextModelRootModel[] {this.buildChild(project)};
	}

	/**
	 * This provider should only be invoked for projects that have the
	 * JPA facet; so we return a JPA root context node model for <em>any</em>
	 * project passed in.
	 * <p>
	 * We return a JPA root context node <em>model</em> (as opposed to simply a
	 * JPA root context node) so a node appears in the navigator tree even if
	 * there is not yet a JPA project. Since this node is returned for any
	 * project with a JPA facet, we must listen for the JPA facet being removed
	 * so, when the JPA facet is removed, we can tell the delegate to
	 * dispose the node's content and label providers (which are listening to
	 * the node for changes etc.).
	 */
	private JpaContextModelRootModel buildChild(IProject project) {
		return (JpaContextModelRootModel) project.getAdapter(JpaContextModelRootModel.class);
	}


	// ********** facet file changes **********

	/* CU private */ void facetFileChanged(IProject project) {
		if (ProjectTools.hasFacet(project, JpaProject.FACET)) {
			this.jpaFacetIsPresent(project);
		} else {
			this.jpaFacetIsAbsent(project);
		}
	}

	private void jpaFacetIsPresent(IProject project) {
		synchronized (this.projectChildren) {
			this.jpaFacetIsPresent_(project);
		}
	}

	/**
	 * The specified project <em>has</em> the JPA facet;
	 * refresh the view if necessary.
	 * <p>
	 * Pre-condition: {@link #projectChildren} is <code>synchronized</code>
	 */
	private void jpaFacetIsPresent_(IProject project) {
		JpaContextModelRootModel[] children = this.projectChildren.get(project);
		if (children == null) {
			this.delegate.updateChildren(project);  // force the viewer to refresh the project
		}
	}

	private void jpaFacetIsAbsent(IProject project) {
		synchronized (this.projectChildren) {
			this.jpaFacetIsAbsent_(project);
		}
	}

	/**
	 * The specified project does <em>not</em> have the JPA facet;
	 * remove the JPA root context node model if necessary.
	 * <p>
	 * Pre-condition: {@link #projectChildren} is <code>synchronized</code>
	 */
	private void jpaFacetIsAbsent_(IProject project) {
		JpaContextModelRootModel[] children = this.projectChildren.remove(project);
		if (children != null) {
			for (JpaContextModelRootModel child : children) {  // should be only one...
				this.delegate.dispose(child);
			}
			this.delegate.updateChildren(project);  // force the viewer to refresh the project
		}
	}


	// ********** disposal **********

	@Override
	public void dispose() {
		// the delegate will dispose everything, including the JPA root context node models
		this.getWorkspace().removeResourceChangeListener(this.resourceChangeListener);
		super.dispose();
	}


	// ********** misc **********

	private IWorkspace getWorkspace() {
		return this.getJpaWorkbench().getJpaWorkspace().getWorkspace();
	}

	private JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(this.getWorkbench(), JpaWorkbench.class);
	}

	private IWorkbench getWorkbench() {
		return PlatformUI.getWorkbench();
	}


	// ********** resource change listener **********

	/**
	 * Listen for changes to the facet settings file.
	 * @see org.eclipse.jpt.jpa.core.internal.InternalJpaProjectManager#FACETED_PROJECT_FRAMEWORK_SETTINGS_FILE_NAME
	 */
	/* CU private */ class ResourceChangeListener
		extends ResourceChangeAdapter
	{
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			switch (event.getType()) {
				case IResourceChangeEvent.POST_CHANGE :
					this.processPostChangeDelta(event.getDelta());
					break;
				default :
					break;
			}
		}

		private void processPostChangeDelta(IResourceDelta delta) {
			IResource resource = delta.getResource();
			switch (resource.getType()) {
				case IResource.ROOT :
					this.processPostChangeDeltaChildren(delta);
					break;
				case IResource.PROJECT :
					this.processPostChangeDeltaChildren(delta);
					break;
				case IResource.FOLDER :
					if (((IFolder) resource).getName().equals(".settings")) { //$NON-NLS-1$
						this.processPostChangeDeltaChildren(delta);
					}
					break;
				case IResource.FILE :
					IFile file = (IFile) resource;
					if (file.getName().equals(JpaProjectManager.FACETED_PROJECT_FRAMEWORK_SETTINGS_FILE_NAME)) {
						this.checkForFacetFileChanges(file, delta);
					}
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

		private void checkForFacetFileChanges(IFile file, IResourceDelta delta) {
			switch (delta.getKind()) {
				case IResourceDelta.ADDED :
				case IResourceDelta.REMOVED :
				case IResourceDelta.CHANGED :
					JpaNavigatorContentProvider.this.facetFileChanged(file.getProject());
					break;
				default :
					break;
			}
		}
	}
}
