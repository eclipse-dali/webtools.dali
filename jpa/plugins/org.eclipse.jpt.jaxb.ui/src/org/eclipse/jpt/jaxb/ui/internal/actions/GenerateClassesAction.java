/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.jaxb.ui.internal.ClassesGeneratorUi;

/**
 *  GenerateClassesAction
 */
public class GenerateClassesAction extends ObjectAction
{

	@Override
	protected void execute(IFile xsdFile) {

		ClassesGeneratorUi.generate(xsdFile);
	}

}
