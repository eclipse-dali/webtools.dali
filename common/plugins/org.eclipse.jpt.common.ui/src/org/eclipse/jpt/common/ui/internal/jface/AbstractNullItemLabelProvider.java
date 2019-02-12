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

import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;

abstract class AbstractNullItemLabelProvider
	implements ItemLabelProvider
{
	/* private-protected */ AbstractNullItemLabelProvider() {
		super();
	}

	public Image getImage() {
		return null;
	}

	public String getText() {
		return null;
	}

	public boolean isLabelProperty(String property) {
		return false;  // the label does not change
	}

	public void dispose() {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
