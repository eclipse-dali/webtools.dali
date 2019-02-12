/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.Map;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.internal.dialogs.AddPersistentAttributeToXmlAndMapDialog;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Convert a list of <code>orm.xml</code> <em>virtual</em> attributes to
 * <em>specified</em> and <em>mapped</em>.
 * This handler is only active if <em>all</em> the selected nodes are
 * virtual attributes.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class AddPersistentAttributeToXmlAndMapHandler
	extends JpaStructureViewHandler
{
	@Override
	protected void execute_(Object[] items, Map<String, String> parameters, IWorkbenchWindow window) {
		for (int i = 0; i < items.length; i++) {
			OrmPersistentAttribute attribute = (OrmPersistentAttribute) items[i];
			OrmPersistentAttribute newAttribute = this.addAndMap(attribute, window);
			if (newAttribute != null) {
				// a little hacky... :-)
				items[i] = newAttribute;
			}
		}
	}

	private OrmPersistentAttribute addAndMap(OrmPersistentAttribute attribute, IWorkbenchWindow window) {
		OrmPersistentType type = attribute.getDeclaringPersistentType();
		String attributeName = attribute.getName();
		
		AddPersistentAttributeToXmlAndMapDialog dialog = new AddPersistentAttributeToXmlAndMapDialog(window.getShell(), attribute);
		dialog.create();
		dialog.setBlockOnOpen(true);
		dialog.open();
		
		return type.getAttributeNamed(attributeName);
	}
}
