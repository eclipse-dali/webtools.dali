/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.ui.handlers.HandlerUtil;

public class RemovePersistentAttributeFromXmlHandler extends AbstractHandler
{	
	@Override
	@SuppressWarnings("unchecked")
	public Object execute(ExecutionEvent executionEvent) throws ExecutionException {
		IStructuredSelection selection = 
			(IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(executionEvent);
		
		// only applies for multiply selected OrmPersistentAttribute objects in a tree
		for (OrmPersistentAttribute attribute : (Iterable<OrmPersistentAttribute>) CollectionTools.iterable(selection.iterator())) {
			attribute.makeVirtual();
		}
		
		return null;
	}

}
