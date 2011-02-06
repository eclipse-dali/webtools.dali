/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

@SuppressWarnings("nls")
public final class ImageRepository {

	// ***** overlays *****
	public static Image getErrorOverlayImage(ResourceManager resourceManager) {
		return getImage(resourceManager, ERROR_OVERLAY_DESCRIPTOR);
	}
	private static final ImageDescriptor ERROR_OVERLAY_DESCRIPTOR = buildImageDescriptor("overlays/error.gif");

	public static Image getWarningOverlayImage(ResourceManager resourceManager) {
		return getImage(resourceManager, WARNING_OVERLAY_DESCRIPTOR);
	}
	private static final ImageDescriptor WARNING_OVERLAY_DESCRIPTOR = buildImageDescriptor("overlays/warning.png");

	// ***** buttons *****
	public static Image getAddButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, ADD_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor ADD_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/add.png");

	public static Image getEditButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, EDIT_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor EDIT_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/edit.png");

	public static Image getDeleteButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, DELETE_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor DELETE_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/delete.png");

	public static Image getMoveUpButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, MOVE_UP_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor MOVE_UP_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/move-up.png");

	public static Image getMoveDownButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, MOVE_DOWN_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor MOVE_DOWN_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/move-down.png");

	public static Image getExpandAllButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, EXPAND_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor EXPAND_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/expand-all.png");

	public static Image getCollapseAllButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, COLLAPSE_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor COLLAPSE_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/collapse-all.png");

	public static Image getRestoreDefaultsButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, RESTORE_DEFAULTS_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor RESTORE_DEFAULTS_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/restore-defaults.png");

	public static Image getBrowseButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, BROWSE_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor BROWSE_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/browse.png");

	public static Image getMiniBrowseButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, MINI_BROWSE_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor MINI_BROWSE_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/browse-mini.png");

	public static Image getSelectAllButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, SELECT_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor SELECT_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/select-all.png");

	public static Image getDeselectAllButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, DESELECT_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor DESELECT_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/deselect-all.png");

	public static Image getAddConnectionButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, ADD_CONNECTION_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor ADD_CONNECTION_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/add-connection.gif");

	public static Image getReconnectButtonImage(ResourceManager resourceManager) {
		return getImage(resourceManager, RECONNECT_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor RECONNECT_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/reconnect.png");

	// ***** objects *****
	public static Image getFileImage(ResourceManager resourceManager) {
		return getImage(resourceManager, FILE_DESCRIPTOR);
	}
	private static final ImageDescriptor FILE_DESCRIPTOR = buildImageDescriptor("objects/file.png");

	public static Image getFolderImage(ResourceManager resourceManager) {
		return getImage(resourceManager, FOLDER_DESCRIPTOR);
	}
	private static final ImageDescriptor FOLDER_DESCRIPTOR = buildImageDescriptor("objects/folder.png");

	public static Image getPackageImage(ResourceManager resourceManager) {
		return getImage(resourceManager, PACKAGE_DESCRIPTOR);
	}
	private static final ImageDescriptor PACKAGE_DESCRIPTOR = buildImageDescriptor("objects/package.png");

	public static Image getTableImage(ResourceManager resourceManager) {
		return getImage(resourceManager, TABLE_DESCRIPTOR);
	}
	private static final ImageDescriptor TABLE_DESCRIPTOR = buildImageDescriptor("objects/table.gif");

	public static Image getTableObjImage(ResourceManager resourceManager) {
		return getImage(resourceManager, TABLE_OBJ_DESCRIPTOR);
	}
	private static final ImageDescriptor TABLE_OBJ_DESCRIPTOR = buildImageDescriptor("objects/table_obj.gif");

	public static Image getColumnImage(ResourceManager resourceManager) {
		return getImage(resourceManager, COLUMN_DESCRIPTOR);
	}
	private static final ImageDescriptor COLUMN_DESCRIPTOR = buildImageDescriptor("objects/column.gif");

	public static Image getKeyColumnImage(ResourceManager resourceManager) {
		return getImage(resourceManager, KEY_COLUMN_DESCRIPTOR);
	}
	private static final ImageDescriptor KEY_COLUMN_DESCRIPTOR = buildImageDescriptor("objects/columnKey.gif");


	private static ImageDescriptor buildImageDescriptor(String fileName) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(JptJpaUiPlugin.PLUGIN_ID, "images/" + fileName);
	}


	private static Image getImage(ResourceManager resourceManager, ImageDescriptor descriptor) {
		return resourceManager.createImage(descriptor);
	}

}
