/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import java.util.HashMap;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel;

/**
 * @see JpaNavigatorItemContentProviderFactory
 */
public class JpaNavigatorItemLabelProviderFactory
	implements ItemExtendedLabelProviderFactory
{
	/**
	 * Delegate factories, keyed by JPA platform.
	 */
	private final HashMap<JpaPlatform, ItemExtendedLabelProviderFactory> delegates = new HashMap<JpaPlatform, ItemExtendedLabelProviderFactory>();


	public JpaNavigatorItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof JpaRootContextNodeModel) {
			return this.buildRootContextNodeModelProvider((JpaRootContextNodeModel) item, manager);
		}
		ItemExtendedLabelProviderFactory delegate = this.getDelegate(item);
		return (delegate == null) ? null : delegate.buildProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildRootContextNodeModelProvider(JpaRootContextNodeModel item, ItemExtendedLabelProvider.Manager manager) {
		return new RootContextNodeModelItemLabelProvider(item, manager);
	}

	private ItemExtendedLabelProviderFactory getDelegate(Object item) {
		return (item instanceof JpaContextNode) ? this.getDelegate((JpaContextNode) item) : null;
	}

	private synchronized ItemExtendedLabelProviderFactory getDelegate(JpaContextNode item) {
		JpaPlatform jpaPlatform = item.getJpaProject().getJpaPlatform();
		ItemExtendedLabelProviderFactory delegate = this.delegates.get(jpaPlatform);
		if (delegate == null) {
			if ( ! this.delegates.containsKey(jpaPlatform)) {  // null is an allowed value
				delegate = this.buildDelegate(jpaPlatform);
				this.delegates.put(jpaPlatform, delegate);
			}
		}
		return delegate;
	}

	private ItemExtendedLabelProviderFactory buildDelegate(JpaPlatform jpaPlatform) {
		JpaPlatformUi platformUI = (JpaPlatformUi) jpaPlatform.getAdapter(JpaPlatformUi.class);
		if (platformUI == null) {
			return null;
		}
		ItemTreeStateProviderFactoryProvider factoryProvider = platformUI.getNavigatorFactoryProvider();
		return (factoryProvider == null) ? null : factoryProvider.getItemLabelProviderFactory();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
