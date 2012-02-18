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

/**
 * JPA manager implemented for any editor that can affect a page's
 * JPA file and/or selection.
 */
public interface JpaEditorManager {
	/**
	 * Return the manager's editor.
	 */
	IEditorPart getEditor();

	/**
	 * Return the manager's JPA file model.
	 * This can be monitored by the corresponding view managers.
	 */
	PropertyValueModel<JpaFile> getJpaFileModel();

	/**
	 * Return the manager's JPA selection model.
	 * This can be monitored and changed by the corresponding view managers.
	 */
	ModifiablePropertyValueModel<JpaStructureNode> getJpaSelectionModel();

	/**
	 * Dispose the manager. Remove any listeners from the manager's editor
	 * and the JPA selection model and JPA file model.
	 */
	void dispose();
}
