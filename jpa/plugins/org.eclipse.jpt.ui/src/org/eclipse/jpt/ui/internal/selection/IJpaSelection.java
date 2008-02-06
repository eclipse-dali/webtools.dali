/*******************************************************************************
 *  Copyright (c) 2007 Oracle.
 *  All rights reserved.  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jpt.core.internal.context.base.IJpaStructureNode;

@SuppressWarnings("nls")
public interface IJpaSelection extends ISelection
{
	static IJpaSelection NULL_SELECTION =
		new IJpaSelection() {
			public IJpaStructureNode getSelectedNode() {
				return null;
			}

			public boolean isEmpty() {
				return true;
			}

			@Override
			public String toString() {
				return "NULL SELECTION";
			}
		};


	IJpaStructureNode getSelectedNode();
}
