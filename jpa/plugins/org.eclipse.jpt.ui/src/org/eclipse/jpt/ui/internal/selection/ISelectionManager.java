/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.ui.IWorkbenchPart;

public interface ISelectionManager 
{	
	/**
	 * Return the current selection.  
	 * This will never be null, but it may be <code>Selection.NULL_SELECTION</code>.
	 */
	public Selection getCurrentSelection();
	
	/**
	 * Not to be called lightly, this will affect the selection for all interested
	 * objects in a window.
	 * The newSelection will be selected.
	 */
	public void select(Selection newSelection);
	
	/**
	 * Not to be called lightly, this will affect the selection for all interested
	 * objects in a window.
	 * The oldSelection will be deselected, iff it matches the current selection.
	 */
	public void deselect(Selection oldSelection);
	
	/**
	 * This may be used to register a part with the selection manager if the part
	 * is known to need access to the selection manager before it is ever activated
	 * or in the case it may be activated prior to the selection manager being 
	 * created.
	 * 
	 * It should not be necessary to deregister a part, as that happens when the 
	 * part is closed.
	 */
	public void register(IWorkbenchPart part);	
}
