/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.selection;

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;

/**
 * JPA view manager implemented for any view that can affect a page's
 * JPA selection.
 * <p>
 * Implementations are to be created by their corresponding views when the
 * views are created and {@link PageManager#addViewManager(JpaViewManager) added
 * to the page manager} if the view is to participate in the JPA selection
 * framework.
 * <p>
 * To retrieve the appropriate JPA page manager for a JPA view:
 * <pre>
 *     IViewPart view = ...;
 *     PageManager pageManager = (PageManager) view.getAdapter(PageManager.class);
 * </pre>
 * @see org.eclipse.jpt.jpa.ui.internal.selection.ViewPartAdapterFactory
 */
public interface JpaViewManager {
	/**
	 * Return the manager's view.
	 */
	IViewPart getView();


	// ********** page manager **********

	/**
	 * A view manager's parent page manager.
	 */
	interface PageManager {
		/**
		 * Add the specified view manager to the page manager. Each view
		 * manager must add itself to its page manager upon the construction
		 * of its view.
		 */
		void addViewManager(JpaViewManager viewManager);

		/**
		 * Remove the specified view manager from the page manager.
		 * Each view manager must remove itself from its page manager upon
		 * disposal of its view.
		 */
		void removeViewManager(JpaViewManager viewManager);

		/**
		 * Return the JPA editor manager for the specified editor.
		 * Return <code>null</code> if the specified editor does not have a
		 * corresponding manager.
		 * This method can be used by a view manager whose view maintains state
		 * for each editor (e.g. an "outline"-type view usually maintains a
		 * separate tree for each editor page, so the trees' states do not
		 * change as different editor pages are brought to the top of the editor
		 * page book).
		 * 
		 * @see #getJpaFileModel()
		 * @see #getJpaSelectionModel()
		 */
		JpaEditorManager getEditorManager(IEditorPart editor);

		/**
		 * Return the manager's JPA file model.
		 * This can be monitored by the corresponding view managers.
		 * This method can used by a view manager whose view maintains state
		 * only for the current editor page.
		 * 
		 * @see #getJpaSelectionModel()
		 * @see #getEditorManager(IEditorPart)
		 */
		PropertyValueModel<JpaFile> getJpaFileModel();

		/**
		 * Return the manager's JPA selection model.
		 * This can be monitored and changed by the corresponding view managers.
		 * This method can used by a view manager whose view maintains state
		 * only for the current editor page.
		 * 
		 * @see #getJpaFileModel()
		 * @see #getEditorManager(IEditorPart)
		 */
		ModifiablePropertyValueModel<JpaStructureNode> getJpaSelectionModel();
	}
}
