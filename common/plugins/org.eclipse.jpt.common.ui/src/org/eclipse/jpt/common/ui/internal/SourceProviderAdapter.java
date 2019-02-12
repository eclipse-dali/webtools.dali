/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal;

import java.util.Map;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.ui.ISourceProviderListener;

/**
 * Convenience implementation of {@link ISourceProviderListener}.
 */
public class SourceProviderAdapter
	implements ISourceProviderListener
{
	public void sourceChanged(int sourcePriority, @SuppressWarnings("rawtypes") Map sourceValuesByName) {
		// NOP
	}

	public void sourceChanged(int sourcePriority, String sourceName, Object sourceValue) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
