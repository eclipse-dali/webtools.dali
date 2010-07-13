/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.ddlgen.wizards;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.ddlgen.EclipseLink2_0DDLGenerator;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.EclipseLinkDDLGeneratorUi;

/**
 *  EclipseLink2_0DDLGeneratorUi
 */
public class EclipseLink2_0DDLGeneratorUi extends EclipseLinkDDLGeneratorUi
{

	// ********** static method **********

	public static void generate(JpaProject project) {
		new EclipseLink2_0DDLGeneratorUi(project).generate();
	}

	// ********** constructors **********
	
	private EclipseLink2_0DDLGeneratorUi(JpaProject project) {
		super(project);
	}

	// ********** behavior **********

	@Override
	protected WorkspaceJob buildGenerateDDLJob(String puName, JpaProject project) {
		return new Generate2_0DDLJob(puName, project);
	}

	// ********** runnable **********

	protected static class Generate2_0DDLJob extends EclipseLinkDDLGeneratorUi.GenerateDDLJob {

		public Generate2_0DDLJob(String puName, JpaProject project) {
			super(puName, project);
		}

		@Override
		protected void ddlGeneratorGenerate(String puName, JpaProject project, IProgressMonitor monitor) {
			EclipseLink2_0DDLGenerator.generate(puName, project, monitor);
		}
	}
}

