/*******************************************************************************
 *  Copyright (c) 2007, 2009 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.commands;

import java.util.Iterator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.ui.handlers.HandlerUtil;

public class RemovePersistentClassHandler extends AbstractHandler
{
	@SuppressWarnings("unchecked")
	public Object execute(ExecutionEvent executionEvent) throws ExecutionException {
		IStructuredSelection selection = 
			(IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(executionEvent);
		
		// only applies for multiply selected OrmPersistentType objects in a tree
		for (Iterator<OrmPersistentType> stream = selection.iterator(); stream.hasNext(); ) {
			OrmPersistentType persistentType = stream.next();
			((EntityMappings) persistentType.getMappingFileRoot()).removePersistentType(persistentType);
		}
		
		return null;
	}
}
