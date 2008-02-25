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
 * A {@link JpaSelectionManager} stores the current {@link JpaSelection} and 
 * notifies <code>ISelectionListener</code>s when the selection changes.
 */
public class DefaultJpaSelectionManager
	implements JpaSelectionManager
{
	/* The window for which this object manages selections */
	private IWorkbenchWindow window;
	
	/* The set of pages for which this object is managing selections */
	private Set<IWorkbenchPage> pages;
	
	/* The active editor part */
	private IEditorPart activeEditor;
	
	private JpaSelection currentSelection;
	
	/* The map of <code>IJpaSelectionParticipant</code>s (keyed by part) */
	private Map<IWorkbenchPart, JpaSelectionParticipant> selectionParticipants;
	
	private IPageListener pageListener;
	
	private IPartListener2 partListener;
	
	
	public DefaultJpaSelectionManager() {
		super();
		pages = Collections.synchronizedSet(new HashSet<IWorkbenchPage>());
		selectionParticipants = Collections.synchronizedMap(new HashMap<IWorkbenchPart, JpaSelectionParticipant>());
		pageListener = new PageListener();
		partListener = new PartListener();
		currentSelection = DefaultJpaSelection.NULL_SELECTION;
	}
	
	void init(IWorkbenchWindow aWindow) {
		window = aWindow;
		aWindow.addPageListener(pageListener);
		initPage(aWindow.getActivePage());
	}
	
	private void initPage(IWorkbenchPage page) {
		if ((page != null) && (! pages.contains(page))) {
			page.addPartListener(partListener);
			pages.add(page);
			activateEditor(page.getActiveEditor());
		}
	}
	
	private void disposePage(IWorkbenchPage page) {
		if ((page != null) && (pages.contains(page))) {
			page.removePartListener(partListener);
			pages.remove(page);
		}
	}
	
	private void activateEditor(IEditorPart editor) {
		if (editor == activeEditor) {
			return;
		}
		if (activeEditor != null) {
			inactivateEditor(activeEditor);
		}
		initPart(editor);
		activeEditor = editor;
		selectEditor(activeEditor);
	}
	
	private void inactivateEditor(IEditorPart editor) {
		
	}
	
	void initPart(IWorkbenchPart part) {
		if (part != null) {
			if (selectionParticipants.get(part) == null) {
				JpaSelectionParticipant selectionParticipant = 
					(JpaSelectionParticipant) part.getAdapter(JpaSelectionParticipant.class);
				if (selectionParticipant != null) {
					selectionParticipants.put(part, selectionParticipant);
				}
			}
		}
	}
	
	private void selectEditor(IEditorPart editor) {
		selectPart(editor);
	}
	
	void selectPart(IWorkbenchPart part) {
		JpaSelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if (selectionParticipant != null) {
			select(selectionParticipant.getSelection());
		}
	}
	
	void hidePart(IWorkbenchPart part) {
		JpaSelectionParticipant selectionParticipant = getSelectionParticipant(part);
		if ((selectionParticipant != null) && (selectionParticipant.disposeOnHide())) {
			closePart(part);
		}
	}
	
	void closePart(IWorkbenchPart part) {
		JpaSelectionParticipant selectionParticipant = getSelectionParticipant(part);
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
			select(DefaultJpaSelection.NULL_SELECTION);
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
	public void select(JpaSelection newSelection) {
		if (currentSelection.equals(newSelection)) {
			return;
		}
		
		currentSelection = newSelection;
		fireSelectionChange(
			new JpaSelectionEvent(newSelection, JpaSelectionEvent.SELECTION, this)
		);
	}
	
	/**
	 * Not to be called lightly, this will affect the selection for all interested
	 * objects in a window.
	 * The oldSelection will be deselected, iff it matches the current selection.
	 */
	public void deselect(JpaSelection oldSelection) {
		if (currentSelection.equals(oldSelection)) {
			currentSelection = DefaultJpaSelection.NULL_SELECTION;
			fireSelectionChange(
				new JpaSelectionEvent(oldSelection, JpaSelectionEvent.DESELECTION, this)
			);
		}
	}
	
	private void fireSelectionChange(JpaSelectionEvent event) {
		for (JpaSelectionParticipant sp : selectionParticipants.values()) {
			sp.selectionChanged(event);
		}
	}
	
	private JpaSelectionParticipant getSelectionParticipant(IWorkbenchPart part) {
		return selectionParticipants.get(part);
	}
		
	public JpaSelection getCurrentSelection() {
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
			DefaultJpaSelectionManager.this.disposePage(page);
		}
		
		public void pageOpened(IWorkbenchPage page) {
			DefaultJpaSelectionManager.this.initPage(page);
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
				DefaultJpaSelectionManager.this.initPart(part);
				DefaultJpaSelectionManager.this.selectPart(part);
			}
		}
		
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			// nop
		}
		
		public void partClosed(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				DefaultJpaSelectionManager.this.closePart(part);
				DefaultJpaSelectionManager.this.disposePart(part);
				DefaultJpaSelectionManager.this.checkForNoEditors();
			}
		}
		
		public void partDeactivated(IWorkbenchPartReference partRef) {
			// nop
		}
		
		public void partHidden(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				DefaultJpaSelectionManager.this.hidePart(part);
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
