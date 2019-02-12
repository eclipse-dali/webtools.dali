/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jaxb.ui.internal.ClassesGeneratorUi;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *  GenerateClassesHandler
 */
public class GenerateClassesHandler extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// There is always only one element in the actual selection.
		IStructuredSelection selection = (IStructuredSelection)HandlerUtil.getCurrentSelectionChecked(event);
		
		IFile xsdFile = this.buildXsdFile(selection.getFirstElement());
		if(xsdFile != null) {
			ClassesGeneratorUi.generate(xsdFile);
		}
		return null;
	}

	private IFile buildXsdFile(Object selection) {
		if (selection instanceof IFile) {
			return (IFile) selection;
		}
		return null;
	}

}