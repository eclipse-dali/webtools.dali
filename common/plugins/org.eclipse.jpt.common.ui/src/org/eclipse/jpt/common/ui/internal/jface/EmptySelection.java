/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Empty implementation of {@link ISelection}.
 * Implemented as a singleton.
 */
public class EmptySelection
	implements ISelection
{
	public static final ISelection INSTANCE = new EmptySelection();

	public static ISelection instance() {
		return INSTANCE;
	}

	/**
	 * Ensure a single instance.
	 */
	private EmptySelection() {
		super();
	}

	public boolean isEmpty() {
		return true;
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
