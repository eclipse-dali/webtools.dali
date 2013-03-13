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
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider.Factory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaContextModelRootModel;

/**
 * @see JpaNavigatorItemContentProviderFactory
 */
public class JpaNavigatorItemLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory
{
	/**
	 * Delegate factories, keyed by JPA platform.
	 */
	private final HashMap<JpaPlatform, ItemExtendedLabelProvider.Factory> delegates = new HashMap<JpaPlatform, ItemExtendedLabelProvider.Factory>();


	public JpaNavigatorItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		// we hand JpaContextModelRootModel differently because it can exist when the
		// JPA facet is present by the JPA project may not yet be present...
		if (item instanceof JpaContextModelRootModel) {
			return this.buildContextModelRootModelProvider((JpaContextModelRootModel) item, manager);
		}
		ItemExtendedLabelProvider.Factory delegate = this.getDelegate(item);
		return (delegate == null) ? null : delegate.buildProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildContextModelRootModelProvider(JpaContextModelRootModel item, ItemExtendedLabelProvider.Manager manager) {
		return new JpaContextModelRootModelItemLabelProvider(item, manager);
	}

	private ItemExtendedLabelProvider.Factory getDelegate(Object item) {
		return (item instanceof JpaContextModel) ? this.getDelegate((JpaContextModel) item) : null;
	}

	private synchronized ItemExtendedLabelProvider.Factory getDelegate(JpaContextModel item) {
		JpaPlatform jpaPlatform = item.getJpaProject().getJpaPlatform();
		ItemExtendedLabelProvider.Factory delegate = this.delegates.get(jpaPlatform);
		if (delegate == null) {
			if ( ! this.delegates.containsKey(jpaPlatform)) {  // null is an allowed value
				delegate = this.buildDelegate(jpaPlatform);
				this.delegates.put(jpaPlatform, delegate);
			}
		}
		return delegate;
	}

	private ItemExtendedLabelProvider.Factory buildDelegate(JpaPlatform jpaPlatform) {
		JpaPlatformUi platformUI = (JpaPlatformUi) jpaPlatform.getAdapter(JpaPlatformUi.class);
		if (platformUI == null) {
			return null;
		}
		ItemTreeStateProviderFactoryProvider factoryProvider = platformUI.getNavigatorFactoryProvider();
		return (factoryProvider == null) ? null : factoryProvider.getItemLabelProviderFactory();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
