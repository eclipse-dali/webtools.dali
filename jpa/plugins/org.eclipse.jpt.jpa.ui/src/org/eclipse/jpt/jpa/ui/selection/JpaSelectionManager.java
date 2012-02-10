/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.selection;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;

/**
 * Each {@link org.eclipse.ui.IWorkbench workbench}
 * and {@link org.eclipse.ui.IWorkbenchWindow workbench window} has a corresponding
 * {@link JpaSelectionManager JPA selection manager}
 * that can be used to set the JPA selection in that workbench or window.
 * <p>
 * To access a workbench's JPA selection manager:
 * <pre>
 *     JpaStructureNode structureNode = ...;
 *     IWorkbenchWindow window = ...;
 *     IWorkbench workbench = window.getWorkbench();
 *     // even though IWorkbench extends IAdaptable, it does not delegate to the
 *     // Platform adapter manager; so registered adapter factories are *not* used... :-(
 *     // JpaSelectionManager selectionManager = (JpaSelectionManager) workbench.getAdapter(JpaSelectionManager.class);
 *     JpaSelectionManager selectionManager = PlatformTools.getAdapter(workbench, JpaSelectionManager.class);
 *     selectionManager.setSelection(structureNode);
 * </pre>
 * <p>
 * To access a workbench window's JPA selection manager:
 * <pre>
 *     JpaStructureNode structureNode = ...;
 *     IWorkbenchWindow window = ...;
 *     // IWorkbenchWindow does not extend IAdaptable(!)
 *     JpaSelectionManager selectionManager = PlatformTools.getAdapter(window, JpaSelectionManager.class);
 *     selectionManager.setSelection(structureNode);
 * </pre>
 * 
 * @see org.eclipse.jpt.jpa.ui.internal.selection.WorkbenchAdapterFactory
 * @see org.eclipse.jpt.jpa.ui.internal.selection.WorkbenchWindowAdapterFactory
 * @see org.eclipse.jpt.jpa.ui.internal.selection.JpaWorkbenchManager
 */
public interface JpaSelectionManager {
	/**
	 * Set the JPA selection for the selection manager's subject.
	 */
	public void setSelection(JpaStructureNode selection);


	/**
	 * A "null" JPA selection manager does nothing.
	 */
	final class Null
		implements JpaSelectionManager, Serializable
	{
		public static final JpaSelectionManager INSTANCE = new Null();
		public static JpaSelectionManager instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void setSelection(JpaStructureNode selection) {
			// do nothing
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
