/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.Map;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.internal.dialogs.AddPersistentClassDialog;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Add an <code>orm.xml</code> type to the selected entity mappings.
 * This handler is only active if <em>one</em> entity mappings is selected.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class AddPersistentClassHandler
	extends JpaStructureViewHandler
{
	@Override
	protected void execute_(Object[] items, Map<String, String> parameters, IWorkbenchWindow window) {
		EntityMappings entityMappings = (EntityMappings) items[0];
		AddPersistentClassDialog dialog = new AddPersistentClassDialog(window.getShell(), entityMappings);
		dialog.create();
		dialog.setBlockOnOpen(true);
		OrmPersistentType type = dialog.openAndReturnType();
		if (type != null) {
			// a little hacky... :-)
			items[0] = type;
		}
	}
}
