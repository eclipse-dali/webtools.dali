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

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.ui.internal.views.JpaStructureView;

public class JpaStructureSelectionParticipant
	implements ISelectionParticipant 
{
	final JpaStructureView structureView;
	
	
	public JpaStructureSelectionParticipant(ISelectionManager selectionManager, JpaStructureView structureView) {
		super();
		this.structureView = structureView;
		structureView.addSelectionChangedListener(new StructureViewSelectionListener(selectionManager, structureView));
	}

	public Selection getSelection() {
		return this.structureView.getSelection();
	}
	
	public void selectionChanged(SelectionEvent evt) {
		this.structureView.select(evt.getSelection());
	}

	public boolean disposeOnHide() {
		return false;
	}
	
	public void dispose() {
		// NOP
	}
	

	// ********** listener **********

	private class StructureViewSelectionListener 
		implements ISelectionChangedListener
	{
		private final ISelectionManager selectionManager;

		StructureViewSelectionListener(ISelectionManager selectionManager, JpaStructureView structureView) {
			super();
			this.selectionManager = selectionManager;
		}

		public void selectionChanged(SelectionChangedEvent event) {
			this.selectionManager.select(this.structureViewSelection());
		}

		private Selection structureViewSelection() {
			return JpaStructureSelectionParticipant.this.structureView.getSelection();
		}

	}

}
