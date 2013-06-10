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

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.navigator.NavigatorContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;

/**
 * This provider is invoked for:<ul>
 * <li>Eclipse projects with a JPA facet
 * <li>JPA root context node models
 * <li>JPA context nodes
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.ui.navigator.navigatorContent</code>.
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
 * provider as a result. Once this provider has been invoked for the first time,
 * it will listen for <em>any</em> added or removed JPA projects until disposed.
 */
public class JpaNavigatorContentProvider
	extends NavigatorContentProvider
{
	public JpaNavigatorContentProvider() {
		super();
	}

	@Override
	protected ResourceManager buildResourceManager() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench != null) ? jpaWorkbench.buildLocalResourceManager() : this.buildResourceManager_();
	}

	private ResourceManager buildResourceManager_() {
		return new LocalResourceManager(JFaceResources.getResources(WorkbenchTools.getDisplay()));
	}

	@Override
	protected ItemTreeContentProvider.Factory buildItemContentProviderFactory() {
		return new JpaNavigatorItemContentProviderFactory();
	}

	@Override
	protected ItemExtendedLabelProvider.Factory buildItemLabelProviderFactory() {
		return new JpaNavigatorItemLabelProviderFactory();
	}

	private JpaWorkbench getJpaWorkbench() {
		return WorkbenchTools.getAdapter(JpaWorkbench.class);
	}
}
