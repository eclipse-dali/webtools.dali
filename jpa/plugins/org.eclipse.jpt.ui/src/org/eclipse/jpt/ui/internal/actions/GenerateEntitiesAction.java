/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.PlatformRegistry;

/**
 *  GenerateEntitiesAction
 */
public class GenerateEntitiesAction extends ProjectAction {
	public GenerateEntitiesAction() {
		super();
	}

	protected void execute(IJpaProject project) {
        String vendorId = project.getPlatform().getId();
		
        this.jpaPlatformUI(vendorId).generateEntities(project, this.getCurrentSelection());
	}
	
	private IJpaPlatformUi jpaPlatformUI(String vendorId) {
        return PlatformRegistry.instance().getJpaPlatform(vendorId); 
	}

}
