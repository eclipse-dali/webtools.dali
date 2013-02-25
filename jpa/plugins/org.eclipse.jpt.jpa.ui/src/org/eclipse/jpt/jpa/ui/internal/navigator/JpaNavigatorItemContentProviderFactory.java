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
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel;

/**
 * This factory can be used by a item tree content provider that must provide
 * content for a tree that can contain items from multiple JPA platforms (e.g.
 * the Project Explorer). This factory will delegate to the factory appropriate
 * to the item's JPA platform. The cache of JPA platform-specific factories is
 * populated as needed and is never purged. Theoretically, this could be a
 * singleton....
 */
public class JpaNavigatorItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	/**
	 * Delegate factories, keyed by JPA platform.
	 */
	private HashMap<JpaPlatform, ItemTreeContentProviderFactory> delegates = new HashMap<JpaPlatform, ItemTreeContentProviderFactory>();


	public JpaNavigatorItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
		// we hand JpaRootContextNodeModel differently because it can exist when the
		// JPA facet is present by the JPA project may not yet be present...
		if (item instanceof JpaRootContextNodeModel) {
			return this.buildRootContextNodeModelProvider((JpaRootContextNodeModel) item, manager);
		}
		ItemTreeContentProviderFactory delegate = this.getDelegate(item);
		return (delegate == null) ? null : delegate.buildProvider(item, manager);
	}

	protected ItemTreeContentProvider buildRootContextNodeModelProvider(JpaRootContextNodeModel item, Manager manager) {
		return new JpaRootContextNodeModelItemContentProvider(item, manager);
	}

	private ItemTreeContentProviderFactory getDelegate(Object element) {
		return (element instanceof JpaContextModel) ? this.getDelegate((JpaContextModel) element) : null;
	}

	private synchronized ItemTreeContentProviderFactory getDelegate(JpaContextModel contextNode) {
		JpaPlatform jpaPlatform = contextNode.getJpaPlatform();
		ItemTreeContentProviderFactory delegate = this.delegates.get(jpaPlatform);
		if (delegate == null) {
			if ( ! this.delegates.containsKey(jpaPlatform)) {  // null is an allowed value
				delegate = this.buildDelegate(jpaPlatform);
				this.delegates.put(jpaPlatform, delegate);
			}
		}
		return delegate;
	}

	private ItemTreeContentProviderFactory buildDelegate(JpaPlatform jpaPlatform) {
		JpaPlatformUi platformUI = (JpaPlatformUi) jpaPlatform.getAdapter(JpaPlatformUi.class);
		if (platformUI == null) {
			return null;
		}
		ItemTreeStateProviderFactoryProvider factoryProvider = platformUI.getNavigatorFactoryProvider();
		return (factoryProvider == null) ? null : factoryProvider.getItemContentProviderFactory();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
