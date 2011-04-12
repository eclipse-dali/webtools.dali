/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.internal.WorkbenchMessages;

/**
 * This handler adds a context menu onto a {@link StyledText} which have the default actions dealing
 * with text editing: Undo | Cut, Copy, Paste, Delete | Select All.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("restriction")
public final class TextTransferHandler {

	private MenuItem copyMenuItem;
	private MenuItem cutMenuItem;
	private MenuItem deleteMenuItem;
	private MenuItem pasteMenuItem;
	private Menu popup;
	private MenuItem selectAllMenuItem;
	private StyledText styledText;
	private IUndoManager undoManager;
	private MenuItem undoMenuItem;

	private TextTransferHandler(StyledText styledText, IUndoManager undoManager) {
		super();
		this.styledText  = styledText;
		this.undoManager = undoManager;
	}

	/**
	 * Installs a context menu onto the given {@link StyledText} which have the default actions
	 * dealing with text editing: Undo | Cut, Copy, Paste, Delete | Select All.
	 *
	 * @param styledText The widget for which a context menu will be added
	 * @param undoManager The manager to undo keystrokes
	 */
	public static void installContextMenu(StyledText styledText, IUndoManager undoManager) {
		TextTransferHandler handler = new TextTransferHandler(styledText, undoManager);
		handler.installPopup();
	}

	private SelectionAdapter buildCopySelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.copy();
			}
		};
	}

	private SelectionListener buildCutSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.cut();
			}
		};
	}

	private SelectionListener buildDeleteSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Point selection = styledText.getSelection();
				styledText.replaceTextRange(selection.x, selection.y - selection.x, StringTools.EMPTY_STRING);
			}
		};
	}

	private MenuListener buildMenuListener() {
		return new MenuListener() {
			public void menuHidden(MenuEvent e) {
			}
			public void menuShown(MenuEvent e) {
				update();
			}
		};
	}

	private SelectionListener buildPasteSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.paste();
			}
		};
	}

	private SelectionListener buildSelectAllSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.selectAll();
			}
		};
	}

	private SelectionListener buildUndoSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				undoManager.undo();
			}
		};
	}

	private boolean canCopy() {
		Point selection = styledText.getSelection();
		return selection.x != selection.y;
	}

	private boolean canCut() {
		Point selection = styledText.getSelection();
		return selection.x != selection.y;
	}

	private boolean canDelete() {
		Point selection = styledText.getSelection();
		return selection.x != selection.y;
	}

	private boolean canPaste() {

		Clipboard clipboard = new Clipboard(styledText.getDisplay());
		boolean canPaste = false;

		for (TransferData data : clipboard.getAvailableTypes()) {
			if (TextTransfer.getInstance().isSupportedType(data)) {
				canPaste = true;
				break;
			}
		}

		clipboard.dispose();
		return canPaste;
	}

	private boolean canSelectAll() {
		Point selection = styledText.getSelection();
		String text = styledText.getText();
		return (selection.y - selection.x) != text.length();
	}

	private void installPopup() {

		popup = new Menu(styledText.getShell(), SWT.POP_UP);
		popup.addMenuListener(buildMenuListener());
		styledText.setMenu(popup);

		populatePopup();
	}

	private void populatePopup() {

		// Undo
		undoMenuItem = new MenuItem(popup, SWT.PUSH);
		undoMenuItem.setText(WorkbenchMessages.Workbench_undo);
		undoMenuItem.addSelectionListener(buildUndoSelectionListener());

		new MenuItem(popup, SWT.SEPARATOR);

		// Cut
		cutMenuItem = new MenuItem(popup, SWT.PUSH);
		cutMenuItem.setText(WorkbenchMessages.Workbench_cut);
		cutMenuItem.addSelectionListener(buildCutSelectionListener());

		// Copy
		copyMenuItem = new MenuItem(popup, SWT.PUSH);
		copyMenuItem.setText(WorkbenchMessages.Workbench_copy);
		copyMenuItem.addSelectionListener(buildCopySelectionListener());

		// Paste
		pasteMenuItem = new MenuItem(popup, SWT.PUSH);
		pasteMenuItem.setText(WorkbenchMessages.Workbench_paste);
		pasteMenuItem.addSelectionListener(buildPasteSelectionListener());

		// Delete
		deleteMenuItem = new MenuItem(popup, SWT.PUSH);
		deleteMenuItem.setText(WorkbenchMessages.Workbench_delete);
		deleteMenuItem.addSelectionListener(buildDeleteSelectionListener());

		new MenuItem(popup, SWT.SEPARATOR);

		// Select All
		selectAllMenuItem = new MenuItem(popup, SWT.PUSH);
		selectAllMenuItem.setText(WorkbenchMessages.Workbench_selectAll);
		selectAllMenuItem.addSelectionListener(buildSelectAllSelectionListener());
	}

	private void update() {
		deleteMenuItem   .setEnabled(canDelete());
		copyMenuItem     .setEnabled(canCopy());
		cutMenuItem      .setEnabled(canCut());
		pasteMenuItem    .setEnabled(canPaste());
		selectAllMenuItem.setEnabled(canSelectAll());
		undoMenuItem     .setEnabled(undoManager.undoable());
	}
}