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
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Maintain a collection of
 * {@link JpaSelectionManager JPA selection managers}
 * keyed by {@link IWorkbenchPage workbench page}.
 * Forward the JPA selection to the manager for the active page.
 */
class JpaWindowManager
	implements JpaSelectionManager, SetJpaSelectionJob.Manager
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
		this.workbenchManager = workbenchManager;
		this.window = window;
	}


	// ********** JPA selection **********

	/**
	 * @see JpaWorkbenchManager#setSelection(JpaStructureNode)
	 */
	public void setSelection(JpaStructureNode selection) {
		new SetJpaSelectionJob(this, selection).schedule();
	}

	/**
	 * Forward to the manager for the window's active page.
	 * @see SetJpaSelectionJob.SetJpaSelectionRunnable#run()
	 */
	public void setSelection_(JpaStructureNode selection) {
		IWorkbenchPage page = this.window.getActivePage();
		if (page != null) {
			JpaPageManager manager = this.pageManagers.get(page);
			if (manager != null) {
				// use internal method since we are on the UI thread here
				manager.setSelection_(selection);
			}
		}
	}


	// ********** page managers **********

	/**
	 * Return <code>null</code> if a manager does not exist.
	 * @see #getPageManager_(IWorkbenchPage)
	 */
	JpaPageManager getPageManager(IWorkbenchPage page) {
		return this.pageManagers.get(page);
	}

	/**
	 * <strong>NB:</strong> May trigger construction of page manager.
	 */
	JpaPageManager getPageManager_(IWorkbenchPage page) {
		synchronized (this.pageManagers) {
			JpaPageManager manager = this.pageManagers.get(page);
			if (manager == null) {
				JptJpaUiPlugin.instance().trace(TRACE_OPTION, "add page manager: {0}", page); //$NON-NLS-1$
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
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "remove page manager: {0}", page); //$NON-NLS-1$
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


	// ********** static methods **********

	/**
	 * Return <em>null</em> if a manager does not exist.
	 * @see WorkbenchWindowAdapterFactory
	 */
	static JpaWindowManager forWindow(IWorkbenchWindow window) {
		JpaWorkbenchManager manager = JpaWorkbenchManager.forWorkbench(window.getWorkbench());
		return (manager == null) ? null : manager.getWindowManager(window);
	}

	/**
	 * <strong>NB:</strong> May trigger construction of window manager.
	 */
	static JpaWindowManager forWindow_(IWorkbenchWindow window) {
		return JpaWorkbenchManager.forWorkbench_(window.getWorkbench()).getWindowManager_(window);
	}


	// ********** tracing **********

	private static final String TRACE_OPTION = JpaSelectionManager.class.getSimpleName();
}
