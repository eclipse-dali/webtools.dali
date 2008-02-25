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
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.ui.actions.ActionDelegate;

public class RemovePersistentAttributeFromXmlAction extends ActionDelegate
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
			for (Iterator<OrmPersistentAttribute> i = ((StructuredSelection) this.selection).iterator(); i.hasNext(); ) {
				OrmPersistentAttribute ormPersistentAttribute = i.next();
				ormPersistentAttribute.setVirtual(true);
			}
		}
	}

}
