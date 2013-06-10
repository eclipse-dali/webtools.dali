/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import java.util.HashMap;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemTreeContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemTreeContentProviderFactory;
import org.eclipse.jpt.common.ui.internal.model.value.WorkspaceProjectsModel;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModelRoot;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaProjectModel;

/**
 * This factory can be used by a item tree content provider that must provide
 * content for a tree that can contain items from multiple JPA platforms (e.g.
 * the Project Explorer). This factory will delegate to the factory appropriate
 * to the item's JPA platform. The cache of JPA platform-specific factories is
 * populated as needed and is never purged. Theoretically, this could be a
 * singleton....
 * <p>
 * <strong>NB:</strong> Project Explorer? Navigator? CommonViewer? It's just
 * not clear what the thing is called!
 */
public class JpaNavigatorItemContentProviderFactory
	implements ItemTreeContentProvider.Factory
{
	/**
	 * Delegate factories, keyed by JPA platform.
	 */
	private HashMap<JpaPlatform, ItemTreeContentProvider.Factory> delegates = new HashMap<JpaPlatform, ItemTreeContentProvider.Factory>();


	public JpaNavigatorItemContentProviderFactory() {
		super();
	}

	/**
	 * Neither the Eclipse workspace root nor its projects are associated with
	 * a JPA platform, so we handle them here.
	 */
	public ItemStructuredContentProvider buildProvider(Object input, ItemStructuredContentProvider.Manager manager) {
		if (input instanceof IWorkspaceRoot) {
			return this.buildItemStructuredContentProvider(input, this.buildWorkspaceRootProjectsModel((IWorkspaceRoot) input), manager);
		}
		if (input instanceof IProject) {
			return this.buildItemStructuredContentProvider(input, this.buildProjectChildrenModel((IProject) input), manager);
		}
		return this.getDelegate(input).buildProvider(input, manager);
	}

	protected ItemStructuredContentProvider buildItemStructuredContentProvider(Object input, CollectionValueModel<?> elementsModel, ItemStructuredContentProvider.Manager manager) {
		return new ModelItemStructuredContentProvider(input, elementsModel, manager);
	}

	protected CollectionValueModel<IProject> buildWorkspaceRootProjectsModel(IWorkspaceRoot workspaceRoot) {
		return new WorkspaceProjectsModel(workspaceRoot);
	}

	/**
	 * @see #buildProvider(Object, ItemStructuredContentProvider.Manager)
	 */
	public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
		return (item instanceof IProject) ?
				this.buildItemTreeContentProvider(item, parent, this.buildProjectChildrenModel((IProject) item), manager) :
				this.getDelegate(item).buildProvider(item, parent, manager);
	}

	protected ItemTreeContentProvider buildItemTreeContentProvider(Object item, Object parent, CollectionValueModel<?> childrenModel, ItemTreeContentProvider.Manager manager) {
		return new ModelItemTreeContentProvider(item, parent, childrenModel, manager);
	}


	// ********** project **********

	protected CollectionValueModel<JpaContextModelRoot> buildProjectChildrenModel(IProject project) {
		return new PropertyCollectionValueModelAdapter<JpaContextModelRoot>(this.buildProjectJpaContextModelRootModel(project));
	}

	protected PropertyValueModel<JpaContextModelRoot> buildProjectJpaContextModelRootModel(IProject project) {
		return new TransformationPropertyValueModel<JpaProject, JpaContextModelRoot>(this.buildProjectJpaProjectModel(project), TransformerTools.nullCheck(JpaProject.CONTEXT_MODEL_ROOT_TRANSFORMER));
	}

	protected PropertyValueModel<JpaProject> buildProjectJpaProjectModel(IProject project) {
		return (JpaProjectModel) project.getAdapter(JpaProjectModel.class);
	}


	// ********** delegates **********

	protected ItemTreeContentProvider.Factory getDelegate(Object item) {
		return (item instanceof JpaContextModel) ?
				this.getDelegate((JpaContextModel) item) :
				NullItemTreeContentProviderFactory.instance();
	}

	protected ItemTreeContentProvider.Factory getDelegate(JpaContextModel contextNode) {
		JpaPlatform jpaPlatform = contextNode.getJpaPlatform();
		ItemTreeContentProvider.Factory delegate = this.delegates.get(jpaPlatform);
		if (delegate == null) {
			delegate = this.buildDelegate(jpaPlatform);
			this.delegates.put(jpaPlatform, delegate);
		}
		return delegate;
	}

	protected ItemTreeContentProvider.Factory buildDelegate(JpaPlatform jpaPlatform) {
		JpaPlatformUi platformUI = (JpaPlatformUi) jpaPlatform.getAdapter(JpaPlatformUi.class);
		return (platformUI != null) ?
				platformUI.getNavigatorFactoryProvider().getItemContentProviderFactory() :
				NullItemTreeContentProviderFactory.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
