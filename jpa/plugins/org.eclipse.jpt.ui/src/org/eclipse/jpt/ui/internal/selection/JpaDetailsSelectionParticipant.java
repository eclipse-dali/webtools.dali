/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.jpt.ui.internal.views.JpaDetailsView;


public class JpaDetailsSelectionParticipant extends AbstractSelectionParticipant 
{
	private JpaDetailsView detailsView;
	
	
	public JpaDetailsSelectionParticipant(
				ISelectionManager theSelectionManager, JpaDetailsView view) {
		super(theSelectionManager);
		detailsView = view;
	}
	
	
	public Selection getSelection() {
		return detailsView.getSelection();
	}

	public void selectionChanged(SelectionEvent evt) {
		detailsView.select(evt.getSelection());
	}
	
	public boolean disposeOnHide() {
		return false;
	}
	
	public void dispose() {
		// no op
	}
}
