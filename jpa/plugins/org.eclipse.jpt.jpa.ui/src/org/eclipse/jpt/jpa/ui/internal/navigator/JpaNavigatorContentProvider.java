/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.internal.jface.NavigatorContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This provider is invoked for:<ul>
 * <li>Eclipse projects with a JPA facet
 * <li>JPA root context node models
 * <li>JPA context nodes
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class JpaNavigatorContentProvider
	extends NavigatorContentProvider
{
	public JpaNavigatorContentProvider() {
		super();
	}

	@Override
	protected ItemTreeContentProviderFactory buildItemContentProviderFactory() {
		return new JpaNavigatorItemContentProviderFactory();
	}

	@Override
	protected ItemExtendedLabelProviderFactory buildItemLabelProviderFactory() {
		return new JpaNavigatorItemLabelProviderFactory();
	}

	@Override
	protected ResourceManager buildResourceManager() {
		return this.getJpaWorkbench().buildLocalResourceManager();
	}

	private JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(PlatformUI.getWorkbench(), JpaWorkbench.class);
	}

	@Override
	protected boolean hasChildren_(Object element) {
		return this.getRootContextNodeModel(element) != null;
	}

	/**
	 * We handle the children for an {@link IProject} here and delegate
	 * all others.
	 */
	@Override
	protected Object[] getChildren_(Object element) {
		JpaRootContextNodeModel child = this.getRootContextNodeModel(element);
		return (child == null) ? null : new Object[] {child};
	}

	/**
	 * This provider should only be invoked for projects that have the
	 * JPA facet; so we return a JPA root context node model for any
	 * project passed in.
	 * <p>
	 * <strong>NB:</strong> There is no way to refresh the navigator when the
	 * JPA facet is added to a project. The view must be explicitly refreshed,
	 * forcing it to re-examine the provider trigger points.
	 */
	private JpaRootContextNodeModel getRootContextNodeModel(Object element) {
		return (element instanceof IProject) ?
				(JpaRootContextNodeModel) ((IProject) element).getAdapter(JpaRootContextNodeModel.class) :
				null;
	}
}
