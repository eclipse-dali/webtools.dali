/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemContentProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

abstract class NullItemContentProvider
	implements ItemContentProvider
{
	/* private-protected */ NullItemContentProvider() {
		super();
	}

	public void dispose() {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
