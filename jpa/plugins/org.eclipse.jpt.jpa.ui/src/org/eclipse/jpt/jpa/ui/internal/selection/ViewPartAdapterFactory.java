/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.ui.selection.JpaViewManager;
import org.eclipse.ui.IViewPart;

/**
 * Factory to build JPA selection adapters for a {@link IViewPart}:<ul>
 * <li>{@link org.eclipse.jpt.jpa.ui.selection.JpaViewManager.PageManager}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class ViewPartAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JpaViewManager.PageManager.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IViewPart) {
			return this.getAdapter((IViewPart) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(IViewPart view, Class<?> adapterType) {
		if (adapterType == JpaViewManager.PageManager.class) {
			return this.getPageManager(view);
		}
		return null;
	}

	/**
	 * This will trigger the creation of the appropriate page manager if it does
	 * not already exist.
	 */
	private JpaViewManager.PageManager getPageManager(IViewPart view) {
		return JpaWorkbenchManager.getPageManager(view);
	}
}
