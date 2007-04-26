/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.ui.internal.views.JpaStructureView;

public class JpaStructureSelectionParticipant 
	extends AbstractSelectionParticipant 
{
	private JpaStructureView structureView;
	
	private ISelectionChangedListener structureViewListener;
	
	
	public JpaStructureSelectionParticipant(
				ISelectionManager theSelectionManager, JpaStructureView theOutlineView) {
		super(theSelectionManager);
		structureView = theOutlineView;
		structureViewListener = new StructureViewSelectionListener();
		structureView.addSelectionChangedListener(structureViewListener);
	}
	
	
	public Selection getSelection() {
		return structureView.getSelection();
	}
	
	public void selectionChanged(SelectionEvent evt) {
		structureView.select(evt.getSelection());
	}
	
	public boolean disposeOnHide() {
		return false;
	}
	
	public void dispose() {
		// no op
	}
	
	
	private class StructureViewSelectionListener 
		implements ISelectionChangedListener
	{
		public void selectionChanged(SelectionChangedEvent event) {
			selectionManager.select(structureView.getSelection());
		}
	}
}
