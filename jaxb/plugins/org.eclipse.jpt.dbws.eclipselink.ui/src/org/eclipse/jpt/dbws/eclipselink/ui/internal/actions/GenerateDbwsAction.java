/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.DbwsGeneratorUi;

/**
 *  GenerateDbwsAction
 */
public class GenerateDbwsAction extends ObjectAction
{

	@Override
	protected void execute(IFile xmlFile) {

		DbwsGeneratorUi.generate(xmlFile);
	}

}
