/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.jpt.core.JpaProject;

/**
 *  GenerateDDLAction
 */
public class GenerateDDLAction extends ProjectAction {
	
	public GenerateDDLAction() {
		super();
	}

	@Override
	protected void execute(JpaProject project) {
        this.getJpaPlatformUi(project).generateDDL(project, this.getCurrentSelection());
	}
}
