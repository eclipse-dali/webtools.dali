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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * A <code>SelectionManager</code> stores the current <code>Selection</code> and 
 * notifies <code>ISelectionListener</code>s when the selection changes.
 */
public class SelectionManager
	implements ISelectionManager
{
	/* The set of pages for which this object is managing selections */
	private Set<IWorkbenchPage> pages;
	
	/* The map of <code>ISelectionParticipant</code>s (keyed by part) this object
	   is using to manage selections */
	private Map<IWorkbenchPart, ISelectionParticipant> selectionParticipants;
	
	private IPageListener pageListener;
	
	private IPartListener2 partListener;
	
	/* The window for which this object manages selections */
	private IWorkbenchWindow window;
	
	private Selection currentSelection;
	
	
	public SelectionManager() {
		super();
		pages = Collections.synchronizedSet(new HashSet<IWorkbenchPage>());
		selectionParticipants = Collections.synchronizedMap(new HashMap<IWorkbenchPart, ISelectionParticipant>());
		pageListener = new PageListener();
		partListener = new PartListener();
		currentSelection = Selection.NULL_SELECTION;
	}
	
	public void init(IWorkbenchWindow aWindow) {
		window = aWindow;
		aWindow.addPageListener(pageListener);
		initPage(aWindow.getActivePage());
	}
	
	void initPage(IWorkbenchPage page) {
		if ((page != null) && (! pages.contains(page))) {
			page.addPartListener(partListener);
			pages.add(page);
			IEditorPart activeEditor = page.getActiveEditor();
			initPart(activeEditor);
			selectPart(activeEditor);
		}
	}
	
	void disposePage(IWorkbenchPage page) {
		if ((page != null) && (pages.contains(page))) {
			page.removePartListener(partListener);
			pages.remove(page);
		}
	}
	
	void initPart(IWorkbenchPart part) {
		if (part != null) {
			if (selectionParticipants.get(part) == null) {
				ISelectionParticipant selectionParticipant = 
					(ISelectionParticipant) part.getAdapter(ISelectionParticipant.class);
				if (selectionParticipant != null) {
					selectionParticipants.put(part, selectionParticipant);
				}
			}
		}
	}
	
	void selectPart(IWorkbenchPart part) {
		ISelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if (selectionParticipant != null) {
			select(selectionParticipant.getSelection());
		}
	}
	
	void hidePart(IWorkbenchPart part) {
		ISelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if ((selectionParticipant != null) && (selectionParticipant.disposeOnHide())) {
			closePart(part);
		}
	}
	
	void closePart(IWorkbenchPart part) {
		ISelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if (selectionParticipant != null) {
			disposePart(part);
			checkForNoEditors();
		}
	}
	
	void disposePart(IWorkbenchPart part) {
		if ((part != null) && (selectionParticipants.containsKey(part))) {
			selectionParticipants.remove(part).dispose();
		}
	}
	
	void checkForNoEditors() {
		IWorkbenchPage activePage = window.getActivePage();
		if ((activePage == null)
				|| (activePage.getActiveEditor() == null)) {
			select(Selection.NULL_SELECTION);
		}
	}
	
	/**
	 * This may be used to register a part with the selection manager if the part
	 * is known to need access to the selection manager before it is ever activated
	 * or in the case it may be activated prior to the selection manager being 
	 * created.
	 * 
	 * It should not be necessary to deregister a part, as that happens when the 
	 * part is closed.
	 */
	public void register(IWorkbenchPart part) {
		initPart(part);
	}
	
	/**
	 * Not to be called lightly, this will affect the selection for all interested
	 * objects in a window.
	 * The newSelection will be selected.
	 */
	public void select(Selection newSelection) {
		if (currentSelection.equals(newSelection)) {
			return;
		}
		
		currentSelection = newSelection;
		fireSelectionChange(
			new SelectionEvent(newSelection, SelectionEvent.SELECTION, this)
		);
	}
	
	/**
	 * Not to be called lightly, this will affect the selection for all interested
	 * objects in a window.
	 * The oldSelection will be deselected, iff it matches the current selection.
	 */
	public void deselect(Selection oldSelection) {
		if (currentSelection.equals(oldSelection)) {
			currentSelection = Selection.NULL_SELECTION;
			fireSelectionChange(
				new SelectionEvent(oldSelection, SelectionEvent.DESELECTION, this)
			);
		}
	}
	
	private void fireSelectionChange(SelectionEvent event) {
		for (ISelectionParticipant sp : selectionParticipants.values()) {
			sp.selectionChanged(event);
		}
	}
	
	private ISelectionParticipant getSelectionParticipant(IWorkbenchPart part) {
		return selectionParticipants.get(part);
	}
		
	public Selection getCurrentSelection() {
		return currentSelection;
	}
	
	public void dispose() {
		window.removePageListener(pageListener);
		selectionParticipants.clear();
		
		for (Iterator<IWorkbenchPage> stream = new CloneIterator<IWorkbenchPage>(this.pages); stream.hasNext(); ) {
			this.disposePage(stream.next());
		}

		for (Iterator<IWorkbenchPart> stream = new CloneIterator<IWorkbenchPart>(selectionParticipants.keySet()); stream.hasNext(); ) {
			this.disposePart(stream.next());
		}
	}
	
	
	private class PageListener implements IPageListener
	{
		public void pageActivated(IWorkbenchPage page) {
			// nop
		}
		
		PageListener() {
			super();
		}
		
		public void pageClosed(IWorkbenchPage page) {
			SelectionManager.this.disposePage(page);
		}
		
		public void pageOpened(IWorkbenchPage page) {
			SelectionManager.this.initPage(page);
		}
	}
	
	
	private class PartListener implements IPartListener2
	{
		PartListener() {
			super();
		}
		
		public void partActivated(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				SelectionManager.this.initPart(part);
				SelectionManager.this.selectPart(part);
			}
		}
		
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			// nop
		}
		
		public void partClosed(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				SelectionManager.this.closePart(part);
				SelectionManager.this.disposePart(part);
				SelectionManager.this.checkForNoEditors();
			}
		}
		
		public void partDeactivated(IWorkbenchPartReference partRef) {
			// nop
		}
		
		public void partHidden(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				SelectionManager.this.hidePart(part);
			}
		}
		
		public void partInputChanged(IWorkbenchPartReference partRef) {
			// nop
		}
		
		public void partOpened(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				initPart(part);
			}
		}
		
		public void partVisible(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				initPart(part);
			}
		}
	}
}
