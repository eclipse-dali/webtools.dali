/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import java.util.Hashtable;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Maintain a collection of
 * {@link JpaSelectionManager JPA selection managers}
 * keyed by {@link IWorkbenchPage workbench page}.
 * Forward the selection to the manager for the active page.
 */
class JpaWindowManager
	implements JpaSelectionManager
{
	/**
	 * The manager's parent workbench manager.
	 */
	private final JpaWorkbenchManager workbenchManager;

	/**
	 * The manager's window.
	 */
	private final IWorkbenchWindow window;

	/**
	 * Map workbench pages to JPA workbench page selection managers.
	 * This is lazily populated and cleaned up by
	 * the page managers themselves.
	 */
	private final Hashtable<IWorkbenchPage, JpaPageManager> pageManagers = new Hashtable<IWorkbenchPage, JpaPageManager>();


	JpaWindowManager(JpaWorkbenchManager workbenchManager, IWorkbenchWindow window) {
		super();
		if (window == null) {
			throw new NullPointerException();
		}
		this.workbenchManager = workbenchManager;
		this.window = window;
	}


	// ********** selection **********

	/**
	 * Forward to the manager for the window's active page.
	 */
	public void setSelection(JpaStructureNode selection) {
		this.getPageManager(this.window.getActivePage()).setSelection(selection);
	}


	// ********** page managers **********

	/**
	 * Return the JPA selection manager for the specified
	 * workbench page.
	 */
	private JpaSelectionManager getPageManager(IWorkbenchPage page) {
		return (page == null) ?
				JpaSelectionManager.Null.instance() :
				this.getPageManager_(page);
	}

	private JpaSelectionManager getPageManager_(IWorkbenchPage page) {
		JpaPageManager manager = this.pageManagers.get(page);
		return (manager == null) ?
				JpaSelectionManager.Null.instance() :
				manager;
	}

	/**
	 * <strong>NB:</strong> May trigger construction of page manager.
	 */
	JpaPageManager getPageManager(IViewPart view) {
		IWorkbenchPage page = view.getSite().getPage();
		synchronized (this.pageManagers) {
			JpaPageManager manager = this.pageManagers.get(page);
			if (manager == null) {
				JpaWorkbenchManager.debug("add page manager:", page); //$NON-NLS-1$
				manager = new JpaPageManager(this, page);
				this.pageManagers.put(page, manager);
			}
			return manager;
		}
	}

	/**
	 * @see JpaPageManager#dispose()
	 */
	void removePageManager(IWorkbenchPage page) {
		synchronized (this.pageManagers) {
			JpaWorkbenchManager.debug("remove page manager:", page); //$NON-NLS-1$
			this.pageManagers.remove(page);
			if (this.pageManagers.isEmpty()) {
				this.dispose();
			}
		}
	}


	// ********** misc **********

	private void dispose() {
		this.workbenchManager.removeWindowManager(this.window);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.window);
	}
}
