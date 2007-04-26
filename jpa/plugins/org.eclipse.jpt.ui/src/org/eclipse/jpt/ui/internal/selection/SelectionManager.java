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
	private Set pages;
	
	/* The map of <code>ISelectionParticipant</code>s (keyed by part) this object
	   is using to manage selections */
	private Map selectionParticipants;
	
	private IPageListener pageListener;
	
	private IPartListener2 partListener;
	
	/* The window for which this object manages selections */
	private IWorkbenchWindow window;
	
	private Selection currentSelection;
	
	
	public SelectionManager() {
		super();
		pages = Collections.synchronizedSet(new HashSet());
		selectionParticipants = Collections.synchronizedMap(new HashMap());
		pageListener = new PageListener();
		partListener = new PartListener();
		currentSelection = Selection.NULL_SELECTION;
	}
	
	public void init(IWorkbenchWindow aWindow) {
		window = aWindow;
		aWindow.addPageListener(pageListener);
		initPage(aWindow.getActivePage());
	}
	
	private void initPage(IWorkbenchPage page) {
		if ((page != null) && (! pages.contains(page))) {
			page.addPartListener(partListener);
			pages.add(page);
			IEditorPart activeEditor = page.getActiveEditor();
			initPart(activeEditor);
			selectPart(activeEditor);
		}
	}
	
	private void disposePage(IWorkbenchPage page) {
		if ((page != null) && (pages.contains(page))) {
			page.removePartListener(partListener);
			pages.remove(page);
		}
	}
	
	private void initPart(IWorkbenchPart part) {
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
	
	private void selectPart(IWorkbenchPart part) {
		ISelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if (selectionParticipant != null) {
			select(selectionParticipant.getSelection());
		}
	}
	
	private void hidePart(IWorkbenchPart part) {
		ISelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if ((selectionParticipant != null) && (selectionParticipant.disposeOnHide())) {
			closePart(part);
		}
	}
	
	private void closePart(IWorkbenchPart part) {
		ISelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if (selectionParticipant != null) {
			disposePart(part);
			checkForNoEditors();
		}
	}
	
	private void disposePart(IWorkbenchPart part) {
		if ((part != null) && (selectionParticipants.containsKey(part))) {
			ISelectionParticipant selectionParticipant = 
				(ISelectionParticipant) selectionParticipants.remove(part);
			selectionParticipant.dispose();
		}
	}
	
	private void checkForNoEditors() {
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
		for (Iterator stream = selectionParticipants.values().iterator(); stream.hasNext(); ) {
			((ISelectionParticipant) stream.next()).selectionChanged(event);
		}
	}
	
	private ISelectionParticipant getSelectionParticipant(IWorkbenchPart part) {
		return (ISelectionParticipant) selectionParticipants.get(part);
	}
		
	public Selection getCurrentSelection() {
		return currentSelection;
	}
	
	public void dispose() {
		window.removePageListener(pageListener);
		selectionParticipants.clear();
		
		for (Iterator stream = new CloneIterator(pages); stream.hasNext(); ) {
			disposePage((IWorkbenchPage) stream.next());
		}
		
		for (Iterator stream = new CloneIterator(selectionParticipants.keySet()); stream.hasNext(); ) {
			disposePart((IWorkbenchPart) stream.next());
		}
	}
	
	
	private class PageListener implements IPageListener
	{
		public void pageActivated(IWorkbenchPage page) {}
		
		public void pageClosed(IWorkbenchPage page) {
			disposePage(page);
		}
		
		public void pageOpened(IWorkbenchPage page) {
			initPage(page);
		}
	}
	
	
	private class PartListener implements IPartListener2
	{
		public void partActivated(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				initPart(part);
				selectPart(part);
			}
		}
		
		public void partBroughtToTop(IWorkbenchPartReference partRef) {}
		
		public void partClosed(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				closePart(part);
				disposePart(part);
				checkForNoEditors();
			}
		}
		
		public void partDeactivated(IWorkbenchPartReference partRef) {}
		
		public void partHidden(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				hidePart(part);
			}
		}
		
		public void partInputChanged(IWorkbenchPartReference partRef) {}
		
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
