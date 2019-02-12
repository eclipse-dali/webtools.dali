/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.navigator;

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
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.ui.JaxbProjectModel;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;

/**
 * See org.eclipse.jpt.jpa.ui.internal.navigator.JpaNavigatorItemContentProviderFactory
 */
public class JaxbNavigatorItemContentProviderFactory
	implements ItemTreeContentProvider.Factory
{
	/**
	 * Delegate factories, keyed by JAXB platform.
	 */
	private HashMap<JaxbPlatform, ItemTreeContentProvider.Factory> delegates = new HashMap<JaxbPlatform, ItemTreeContentProvider.Factory>();
	
	
	public JaxbNavigatorItemContentProviderFactory() {
		super();
	}
	
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

	public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
		return (item instanceof IProject) ?
				this.buildItemTreeContentProvider(item, parent, this.buildProjectChildrenModel((IProject) item), manager) :
				this.getDelegate(item).buildProvider(item, parent, manager);
	}
	
	protected ItemTreeContentProvider buildItemTreeContentProvider(Object item, Object parent, CollectionValueModel<?> childrenModel, ItemTreeContentProvider.Manager manager) {
		return new ModelItemTreeContentProvider(item, parent, childrenModel, manager);
	}


	// ********** project **********

	protected CollectionValueModel<JaxbContextRoot> buildProjectChildrenModel(IProject project) {
		return new PropertyCollectionValueModelAdapter<JaxbContextRoot>(this.buildProjectJaxbContextRootModel(project));
	}

	protected PropertyValueModel<JaxbContextRoot> buildProjectJaxbContextRootModel(IProject project) {
		return new TransformationPropertyValueModel<JaxbProject, JaxbContextRoot>(this.buildProjectJaxbProjectModel(project), TransformerTools.nullCheck(JaxbProject.CONTEXT_ROOT_TRANSFORMER));
	}

	protected PropertyValueModel<JaxbProject> buildProjectJaxbProjectModel(IProject project) {
		return (JaxbProjectModel) project.getAdapter(JaxbProjectModel.class);
	}


	// ********** delegates **********

	private ItemTreeContentProvider.Factory getDelegate(Object item) {
		return (item instanceof JaxbContextNode) ?
				this.getDelegate((JaxbContextNode) item) :
				NullItemTreeContentProviderFactory.instance();
	}
	
	private ItemTreeContentProvider.Factory getDelegate(JaxbContextNode contextNode) {
		JaxbPlatform jaxbPlatform = contextNode.getJaxbProject().getPlatform();
		ItemTreeContentProvider.Factory delegate = this.delegates.get(jaxbPlatform);
		if (delegate == null) {
			delegate = this.buildDelegate(jaxbPlatform);
			this.delegates.put(jaxbPlatform, delegate);
		}
		return delegate;
	}

	private ItemTreeContentProvider.Factory buildDelegate(JaxbPlatform jaxbPlatform) {
		JaxbPlatformUi platformUI = (JaxbPlatformUi) jaxbPlatform.getAdapter(JaxbPlatformUi.class);
		return (platformUI != null) ?
				platformUI.getNavigatorUi().getTreeItemContentProviderFactory() :
				NullItemTreeContentProviderFactory.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
