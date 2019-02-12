/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import java.util.Hashtable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
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
class JpaWindowSelectionManager
	implements JpaSelectionManager, SetJpaSelectionJob.Manager
{
	/**
	 * The manager's parent workbench manager.
	 */
	private final JpaWorkbenchSelectionManager workbenchManager;

	/**
	 * The manager's window.
	 */
	private final IWorkbenchWindow window;

	/**
	 * Map workbench pages to JPA workbench page selection managers.
	 * This is lazily populated and cleaned up by
	 * the page managers themselves.
	 */
	private final Hashtable<IWorkbenchPage, JpaPageSelectionManager> pageManagers = new Hashtable<IWorkbenchPage, JpaPageSelectionManager>();


	JpaWindowSelectionManager(JpaWorkbenchSelectionManager workbenchManager, IWorkbenchWindow window) {
		super();
		this.workbenchManager = workbenchManager;
		this.window = window;
	}


	// ********** JPA selection **********

	/**
	 * @see JpaWorkbenchSelectionManager#setSelection(JpaStructureNode)
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
			JpaPageSelectionManager manager = this.pageManagers.get(page);
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
	JpaPageSelectionManager getPageManager(IWorkbenchPage page) {
		return this.pageManagers.get(page);
	}

	/**
	 * <strong>NB:</strong> May trigger construction of page manager.
	 */
	JpaPageSelectionManager getPageManager_(IWorkbenchPage page) {
		synchronized (this.pageManagers) {
			JpaPageSelectionManager manager = this.pageManagers.get(page);
			if (manager == null) {
				JptJpaUiPlugin.instance().trace(TRACE_OPTION, "add page manager: {0}", page); //$NON-NLS-1$
				manager = new JpaPageSelectionManager(this, page);
				this.pageManagers.put(page, manager);
			}
			return manager;
		}
	}

	/**
	 * @see JpaPageSelectionManager#dispose()
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
		return ObjectTools.toString(this, this.window);
	}


	// ********** static methods **********

	/**
	 * Return <em>null</em> if a manager does not exist.
	 * @see WorkbenchWindowAdapterFactory
	 */
	static JpaWindowSelectionManager forWindow(IWorkbenchWindow window) {
		JpaWorkbenchSelectionManager manager = JpaWorkbenchSelectionManager.forWorkbench(window.getWorkbench());
		return (manager == null) ? null : manager.getWindowManager(window);
	}

	/**
	 * <strong>NB:</strong> May trigger construction of window manager.
	 */
	static JpaWindowSelectionManager forWindow_(IWorkbenchWindow window) {
		JpaWorkbenchSelectionManager wsm = JpaWorkbenchSelectionManager.forWorkbench_(window.getWorkbench());
		return (wsm == null) ? null : wsm.getWindowManager_(window);
	}


	// ********** tracing **********

	private static final String TRACE_OPTION = JpaSelectionManager.class.getSimpleName();
}
