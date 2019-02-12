/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.EclipseLinkDDLGeneratorUi;

/**
 *  EclipseLink2_0DDLGeneratorUi
 */
public class EclipseLinkDDLGeneratorUi2_0 extends EclipseLinkDDLGeneratorUi
{

	// ********** static method **********

	public static void generate(JpaProject project) {
		new EclipseLinkDDLGeneratorUi2_0(project).generate();
	}

	// ********** constructors **********
	
	private EclipseLinkDDLGeneratorUi2_0(JpaProject project) {
		super(project);
	}

	// ********** behavior **********

	@Override
	protected EclipseLinkGenerateDDLWizard buildGenerateDDLWizard(JpaProject project, String puName) {
		return new EclipseLinkGenerateDDLWizard2_0(project, puName);
	}
}

