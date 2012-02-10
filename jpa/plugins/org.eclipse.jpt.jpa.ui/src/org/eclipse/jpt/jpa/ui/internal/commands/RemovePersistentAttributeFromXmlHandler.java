/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.commands;

import java.util.ArrayList;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Convert a list of <code>orm.xml</code> <em>specified</em> attributes to
 * <em>virtual</em>.
 * This handler is only active if <em>all</em> the selected nodes are
 * specified attributes.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class RemovePersistentAttributeFromXmlHandler
	extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.execute_(event);
		return null;
	}

	private void execute_(ExecutionEvent event) throws ExecutionException {
		ArrayList<OrmReadOnlyPersistentAttribute> virtualAttributes = new ArrayList<OrmReadOnlyPersistentAttribute>();
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		for (Object each : selection.toList()) {
			OrmPersistentAttribute attribute = (OrmPersistentAttribute) each;
			OrmReadOnlyPersistentAttribute newAttribute = attribute.convertToVirtual();
			if (newAttribute != null) {
				virtualAttributes.add(newAttribute);
			}
		}

		if (virtualAttributes.size() == 1) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			JpaSelectionManager selectionManager = PlatformTools.getAdapter(window, JpaSelectionManager.class);
			selectionManager.setSelection(virtualAttributes.get(0));
		}
	}
}
