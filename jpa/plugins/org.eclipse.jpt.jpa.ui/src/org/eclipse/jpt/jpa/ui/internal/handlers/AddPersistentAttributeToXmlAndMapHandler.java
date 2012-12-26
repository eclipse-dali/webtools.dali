/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.ArrayList;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.internal.dialogs.AddPersistentAttributeToXmlAndMapDialog;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Convert a list of <code>orm.xml</code> <em>virtual</em> attributes to
 * <em>specified</em> and <em>mapped</em>.
 * This handler is only active if <em>all</em> the selected nodes are
 * virtual attributes.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class AddPersistentAttributeToXmlAndMapHandler
	extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.execute_(event);
		return null;
	}

	private void execute_(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ArrayList<OrmReadOnlyPersistentAttribute> newAttributes = new ArrayList<OrmReadOnlyPersistentAttribute>();
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		for (Object each : selection.toList()) {
			OrmReadOnlyPersistentAttribute attribute = (OrmReadOnlyPersistentAttribute) each;
			OrmReadOnlyPersistentAttribute newAttribute = this.addAndMap(attribute, window);
			if (newAttribute != null) {
				newAttributes.add(newAttribute);
			}
		}
		
		if (newAttributes.size() == 1) {
			JpaSelectionManager selectionManager = PlatformTools.getAdapter(window, JpaSelectionManager.class);
			selectionManager.setSelection(newAttributes.get(0));
		}
	}

	private OrmReadOnlyPersistentAttribute addAndMap(OrmReadOnlyPersistentAttribute attribute, IWorkbenchWindow window) {
		OrmPersistentType type = attribute.getOwningPersistentType();
		String attributeName = attribute.getName();
		
		AddPersistentAttributeToXmlAndMapDialog dialog = new AddPersistentAttributeToXmlAndMapDialog(window.getShell(), attribute);
		dialog.create();
		dialog.setBlockOnOpen(true);
		dialog.open();
		
		return type.getAttributeNamed(attributeName);
	}
}
