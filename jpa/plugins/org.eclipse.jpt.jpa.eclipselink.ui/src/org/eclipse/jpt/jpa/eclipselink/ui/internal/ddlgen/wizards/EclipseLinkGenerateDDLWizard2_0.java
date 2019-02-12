/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.internal.ddlgen.EclipseLinkDDLGenerator2_0;
import org.eclipse.wst.validation.ValidationFramework;

/**
 *  EclipseLink2_0GenerateDDLWizard
 */
public class EclipseLinkGenerateDDLWizard2_0 extends EclipseLinkGenerateDDLWizard {

	public EclipseLinkGenerateDDLWizard2_0(JpaProject jpaProject, String puName) {
		super(jpaProject, puName);
	}

	@Override
	protected WorkspaceJob buildGenerateDDLJob(String puName, JpaProject project, EclipseLinkOutputMode outputMode) {
		return new Generate2_0DDLJob(puName, project, outputMode);
	}

	// ********** generate ddl job **********

	protected static class Generate2_0DDLJob extends EclipseLinkGenerateDDLWizard.GenerateDDLJob {

		// ********** constructor **********
		
		protected Generate2_0DDLJob(String puName, JpaProject jpaProject, EclipseLinkOutputMode outputMode) {
			super(puName, jpaProject, outputMode);
		}

		// ********** overwrite AbstractJptGenerateJob **********

		@Override
		protected JptGenerator buildGenerator() {
			return new EclipseLinkDDLGenerator2_0(this.puName, this.jpaProject, this.outputMode);
		}

		@Override
		protected void postGenerate() {
			super.postGenerate();

			if(this.outputMode != EclipseLinkOutputMode.database) {
				this.validateProject();
			}
		}

		// ********** internal methods **********

		/**
		 * Performs validation after tables have been generated
		 */
		private void validateProject() {
			IProject[] projects = new IProject[] {this.jpaProject.getProject()};
			try {
				ValidationFramework.getDefault().validate(projects, true, false, new NullProgressMonitor());
			}
			catch (CoreException e) {
				this.logException(e);
			}
		}
	}
	
}
