/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * Null implementation of the ILabelProvider interface.
 * Implemented as a singleton.
 */
public final class NullLabelProvider 
	implements ILabelProvider 
{
	public static final NullLabelProvider INSTANCE = new NullLabelProvider();

	public static ILabelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure a single instance.
	 */
	private NullLabelProvider() {
		super();
	}

	public Image getImage(Object element) {
		return null;
	}

	public String getText(Object element) {
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// do nothing
	}

	public void dispose() {
		// do nothing
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// do nothing
	}

}