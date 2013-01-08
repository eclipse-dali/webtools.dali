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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.internal.InternalJpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Maintain a collection of
 * {@link JpaSelectionManager JPA selection managers}
 * keyed by {@link IWorkbenchWindow workbench window}.
 * Forward any JPA selection to the manager for the active window.
 * 
 * @see WorkbenchAdapterFactory
 */
class JpaWorkbenchSelectionManager
	implements JpaSelectionManager, SetJpaSelectionJob.Manager
{
	/**
	 * The manager's workbench.
	 */
	private final IWorkbench workbench;

	/**
	 * Map workbench windows to JPA managers.
	 * This is lazily populated and cleaned up as necessary.
	 */
	private final Hashtable<IWorkbenchWindow, JpaWindowSelectionManager> windowManagers = new Hashtable<IWorkbenchWindow, JpaWindowSelectionManager>();


	/**
	 * @see #forWorkbench_(IWorkbench)
	 */
	private JpaWorkbenchSelectionManager(IWorkbench workbench) {
		super();
		this.workbench = workbench;
		JptJpaUiPlugin.instance().trace(TRACE_OPTION, "add workbench manager: {0}", workbench); //$NON-NLS-1$
	}


	// ********** JPA selection **********

	/**
	 * Dispatch to a job so the JPA selection is set after any outstanding
	 * updates etc; since setting the JPA selection will trigger UI changes.
	 * @see SetJpaSelectionJob
	 */
	public void setSelection(JpaStructureNode selection) {
		new SetJpaSelectionJob(this, selection).schedule();
	}

	/**
	 * Forward to the manager for the workbench's active window.
	 * <br>
	 * <strong>NB:</strong> {@link IWorkbench#getActiveWorkbenchWindow()}
	 * (and, thus, this method) must
	 * be called from the UI thread or it will return <code>null</code>.
	 * @see SetJpaSelectionJob.SetJpaSelectionRunnable#run()
	 */
	public void setSelection_(JpaStructureNode selection) {
		IWorkbenchWindow window = this.workbench.getActiveWorkbenchWindow();
		if (window != null) {
			JpaWindowSelectionManager manager = this.windowManagers.get(window);
			if (manager != null) {
				// use internal method since we are on the UI thread here
				manager.setSelection_(selection);
			}
		}
	}


	// ********** window managers **********

	/**
	 * Return <code>null</code> if a manager does not exist.
	 * @see #getWindowManager_(IWorkbenchWindow)
	 */
	JpaWindowSelectionManager getWindowManager(IWorkbenchWindow window) {
		return this.windowManagers.get(window);
	}

	/**
	 * <strong>NB:</strong> May trigger construction of window manager.
	 */
	JpaWindowSelectionManager getWindowManager_(IWorkbenchWindow window) {
		synchronized (this.windowManagers) {
			JpaWindowSelectionManager manager = this.windowManagers.get(window);
			if (manager == null) {
				JptJpaUiPlugin.instance().trace(TRACE_OPTION, "add window manager: {0}", window); //$NON-NLS-1$
				manager = new JpaWindowSelectionManager(this, window);
				this.windowManagers.put(window, manager);
			}
			return manager;
		}
	}

	/**
	 * @see JpaWindowSelectionManager#dispose()
	 */
	void removeWindowManager(IWorkbenchWindow window) {
		synchronized (this.windowManagers) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "remove window manager: {0}", window); //$NON-NLS-1$
			this.windowManagers.remove(window);
			if (this.windowManagers.isEmpty()) {
				this.dispose();
			}
		}
	}


	// ********** misc **********

	private void dispose() {
		JptJpaUiPlugin.instance().trace(TRACE_OPTION, "remove workbench manager: {0}", this.workbench); //$NON-NLS-1$
		getJpaWorkbench(this.workbench).setJpaSelectionManager(null);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workbench);
	}


	// ********** static methods **********

	/**
	 * Return <em>null</em> if a manager does not exist.
	 * @see WorkbenchAdapterFactory
	 */
	static JpaWorkbenchSelectionManager forWorkbench(IWorkbench workbench) {
		return (JpaWorkbenchSelectionManager) getJpaWorkbench(workbench).getJpaSelectionManager();
	}

	/**
	 * <strong>NB:</strong> May trigger construction of workbench selection manager.
	 */
	static JpaWorkbenchSelectionManager forWorkbench_(IWorkbench workbench) {
		InternalJpaWorkbench jpaWorkbench = getJpaWorkbench(workbench);
		synchronized (jpaWorkbench) {
			JpaWorkbenchSelectionManager manager = (JpaWorkbenchSelectionManager) jpaWorkbench.getJpaSelectionManager();
			if (manager == null) {
				manager = new JpaWorkbenchSelectionManager(workbench);
				jpaWorkbench.setJpaSelectionManager(manager);
			}
			return manager;
		}
	}

	private static InternalJpaWorkbench getJpaWorkbench(IWorkbench workbench) {
		// go directly to the plug-in because we need the internal JPA workbench
		return JptJpaUiPlugin.instance().getJpaWorkbench(workbench);
	}


	// ********** tracing **********

	private static final String TRACE_OPTION = JpaSelectionManager.class.getSimpleName();
}
