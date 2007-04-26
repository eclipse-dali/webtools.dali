/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import java.util.EventObject;

public class SelectionEvent extends EventObject 
{
	/**
	 * Serializable uid
	 * @since 0.5
	 */
	private static final long serialVersionUID = 1L;
    
	
	/**
	 * Indicates that the selection object is now selected
	 */
	public static int SELECTION = 1;
	
	/**
	 * Indicates that the selection object has now been deselected
	 */
	public static int DESELECTION = 2;
	
	
	/**
	 * The selection object whose selection status has changed
	 */
	private Selection selection;
	
	/**
	 * The type of the selection event, either a SELECTION or a DESELECTION
	 */
	private int type;
	
	
	public SelectionEvent(Selection theSelection, int theType, Object source) {
		super(source);
		selection = theSelection;
		type = theType;
	}
	
	/**
	 * Return the selection object whose selection status has changed
	 */
	public Selection getSelection() {
		return selection;
	}
	
	/**
	 * Return the type of selection event, either a SELECTION or a DESELECTION
	 */
	public int getType() {
		return type;
	}
}
