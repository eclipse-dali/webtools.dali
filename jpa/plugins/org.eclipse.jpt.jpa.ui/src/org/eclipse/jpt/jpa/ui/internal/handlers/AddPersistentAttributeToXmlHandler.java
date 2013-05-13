/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.Map;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Convert a list of <code>orm.xml</code> <em>virtual</em> attributes to
 * <em>specified</em>.
 * This handler is only active if <em>all</em> the selected nodes are
 * virtual attributes that have some sort of Java mapping (either specified
 * or default).
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class AddPersistentAttributeToXmlHandler
	extends JpaStructureViewHandler
{
	@Override
	protected void execute_(Object[] items, Map<String, String> parameters, IWorkbenchWindow window) {
		for (int i = 0; i < items.length; i++) {
			OrmPersistentAttribute attribute = (OrmPersistentAttribute) items[i];
			// a little hacky... :-)
			items[i] = attribute.addToXml();
		}
	}
}
