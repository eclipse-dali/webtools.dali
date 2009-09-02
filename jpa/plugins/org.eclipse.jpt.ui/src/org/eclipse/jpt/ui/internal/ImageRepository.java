/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.util.HashMap;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

@SuppressWarnings("nls")
public final class ImageRepository {

	// ***** overlays *****
	public static Image getErrorOverlayImage() {
		return getImage(ERROR_OVERLAY_DESCRIPTOR);
	}
	private static final ImageDescriptor ERROR_OVERLAY_DESCRIPTOR = buildImageDescriptor("overlays/error.gif");

	public static Image getWarningOverlayImage() {
		return getImage(WARNING_OVERLAY_DESCRIPTOR);
	}
	private static final ImageDescriptor WARNING_OVERLAY_DESCRIPTOR = buildImageDescriptor("overlays/warning.png");

	// ***** buttons *****
	public static Image getAddButtonImage() {
		return getImage(ADD_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor ADD_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/add.png");

	public static Image getEditButtonImage() {
		return getImage(EDIT_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor EDIT_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/edit.png");

	public static Image getDeleteButtonImage() {
		return getImage(DELETE_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor DELETE_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/delete.png");

	public static Image getMoveUpButtonImage() {
		return getImage(MOVE_UP_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor MOVE_UP_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/move-up.png");

	public static Image getMoveDownButtonImage() {
		return getImage(MOVE_DOWN_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor MOVE_DOWN_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/move-down.png");

	public static Image getExpandAllButtonImage() {
		return getImage(EXPAND_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor EXPAND_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/expand-all.png");

	public static Image getCollapseAllButtonImage() {
		return getImage(COLLAPSE_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor COLLAPSE_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/collapse-all.png");

	public static Image getRestoreDefaultsButtonImage() {
		return getImage(RESTORE_DEFAULTS_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor RESTORE_DEFAULTS_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/restore-defaults.png");

	public static Image getBrowseButtonImage() {
		return getImage(BROWSE_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor BROWSE_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/browse.png");

	public static Image getMiniBrowseButtonImage() {
		return getImage(MINI_BROWSE_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor MINI_BROWSE_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/browse-mini.png");

	public static Image getSelectAllButtonImage() {
		return getImage(SELECT_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor SELECT_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/select-all.png");

	public static Image getDeselectAllButtonImage() {
		return getImage(DESELECT_ALL_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor DESELECT_ALL_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/deselect-all.png");

	public static Image getAddConnectionButtonImage() {
		return getImage(ADD_CONNECTION_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor ADD_CONNECTION_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/add-connection.gif");

	public static Image getReconnectButtonImage() {
		return getImage(RECONNECT_BUTTON_DESCRIPTOR);
	}
	private static final ImageDescriptor RECONNECT_BUTTON_DESCRIPTOR = buildImageDescriptor("buttons/reconnect.png");

	// ***** objects *****
	public static Image getFileImage() {
		return getImage(FILE_DESCRIPTOR);
	}
	private static final ImageDescriptor FILE_DESCRIPTOR = buildImageDescriptor("objects/file.png");

	public static Image getFolderImage() {
		return getImage(FOLDER_DESCRIPTOR);
	}
	private static final ImageDescriptor FOLDER_DESCRIPTOR = buildImageDescriptor("objects/folder.png");

	public static Image getPackageImage() {
		return getImage(PACKAGE_DESCRIPTOR);
	}
	private static final ImageDescriptor PACKAGE_DESCRIPTOR = buildImageDescriptor("objects/package.png");

	public static Image getTableImage() {
		return getImage(TABLE_DESCRIPTOR);
	}
	private static final ImageDescriptor TABLE_DESCRIPTOR = buildImageDescriptor("objects/table.gif");

	public static Image getTableObjImage() {
		return getImage(TABLE_OBJ_DESCRIPTOR);
	}
	private static final ImageDescriptor TABLE_OBJ_DESCRIPTOR = buildImageDescriptor("objects/table_obj.gif");

	public static Image getColumnImage() {
		return getImage(COLUMN_DESCRIPTOR);
	}
	private static final ImageDescriptor COLUMN_DESCRIPTOR = buildImageDescriptor("objects/column.gif");

	public static Image getKeyColumnImage() {
		return getImage(KEY_COLUMN_DESCRIPTOR);
	}
	private static final ImageDescriptor KEY_COLUMN_DESCRIPTOR = buildImageDescriptor("objects/columnKey.gif");


	private static ImageDescriptor buildImageDescriptor(String fileName) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(JptUiPlugin.PLUGIN_ID, "images/" + fileName);
	}

	// ***** cache *****
	private static final HashMap<ImageDescriptor, Image> CACHE = new HashMap<ImageDescriptor, Image>();

	private static Image getImage(ImageDescriptor descriptor) {
		synchronized (CACHE) {
			Image image = CACHE.get(descriptor);
			if (image == null) {
				image = descriptor.createImage();
				CACHE.put(descriptor, image);
			}
			return image;
		}
	}

}
