/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.DbwsGeneratorUi;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *  GenerateDbwsHandler
 */
public class GenerateDbwsHandler extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// There is always only one element in the actual selection.
		IStructuredSelection selection = (IStructuredSelection)HandlerUtil.getCurrentSelectionChecked(event);

		IFile xmlFile = this.buildXmlFile(selection.getFirstElement());
		if(xmlFile != null) {
			DbwsGeneratorUi.generate(xmlFile);
		}
		return null;
	}

	private IFile buildXmlFile(Object selection) {
		if (selection instanceof IFile) {
			return (IFile) selection;
		}
		return null;
	}

}