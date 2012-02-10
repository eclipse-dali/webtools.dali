/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Convenience implementation of {@link ILabelProviderListener}.
 */
public class LabelProviderAdapter
	implements ILabelProviderListener
{
	public void labelProviderChanged(LabelProviderChangedEvent event) {
		// do nothing
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
