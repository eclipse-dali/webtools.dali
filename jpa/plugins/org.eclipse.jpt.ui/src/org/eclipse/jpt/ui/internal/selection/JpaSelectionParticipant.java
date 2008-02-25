/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

public interface JpaSelectionParticipant 
{
	/**
	 * Return the current selection of the participant
	 */
	JpaSelection getSelection();
	
	/**
	 * The selection has changed in the central selection manager.
	 * Update this participant accordingly.
	 */
	void selectionChanged(JpaSelectionEvent evt);
	
	/**
	 * Return whether this selection participant should disconnect itself from
	 * its part when its part is hidden from view.
	 * <b>Typically</b> editor participants will return true and view participants will
	 * return false.
	 */
	boolean disposeOnHide();
	
	/**
	 * This participant is no longer needed (most likely because its part has 
	 * closed).  Dispose of it.
	 */
	void dispose();
}
