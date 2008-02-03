/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import java.util.Iterator;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.ui.actions.ActionDelegate;

public class AddPersistentAttributeToXmlAction extends ActionDelegate
{	
	
	private ISelection selection;
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		this.selection = selection;
	}
	
	@Override
	public void run(IAction action) {
		if (this.selection instanceof StructuredSelection) {
			for (Iterator<XmlPersistentAttribute> i = ((StructuredSelection) selection).iterator(); i.hasNext(); ) {
				XmlPersistentAttribute xmlPersistentAttribute = i.next();
				xmlPersistentAttribute.setVirtual(false);
			}
		}
	}

}
