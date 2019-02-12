/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import java.util.HashMap;
import org.eclipse.jpt.common.ui.internal.jface.NullItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;

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
		return this.getDelegate(item).buildProvider(item, manager);
	}


	// ********** delegates **********

	private ItemExtendedLabelProvider.Factory getDelegate(Object item) {
		return (item instanceof JpaContextModel) ?
				this.getDelegate((JpaContextModel) item) :
				NullItemExtendedLabelProviderFactory.instance();
	}

	private ItemExtendedLabelProvider.Factory getDelegate(JpaContextModel item) {
		JpaPlatform jpaPlatform = item.getJpaProject().getJpaPlatform();
		ItemExtendedLabelProvider.Factory delegate = this.delegates.get(jpaPlatform);
		if (delegate == null) {
			delegate = this.buildDelegate(jpaPlatform);
			this.delegates.put(jpaPlatform, delegate);
		}
		return delegate;
	}

	private ItemExtendedLabelProvider.Factory buildDelegate(JpaPlatform jpaPlatform) {
		JpaPlatformUi platformUI = (JpaPlatformUi) jpaPlatform.getAdapter(JpaPlatformUi.class);
		return (platformUI != null) ?
				platformUI.getNavigatorFactoryProvider().getItemLabelProviderFactory() :
				NullItemExtendedLabelProviderFactory.instance();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
