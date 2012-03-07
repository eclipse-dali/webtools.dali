/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.conversion.java.EclipseLinkJpaJavaQueryMetadataConversionWizard;
import org.eclipse.jpt.jpa.ui.internal.platform.base.AbstractJpaJavaGlobalMetadataConvertor;

public class EclipseLinkJpaJavaQueryMetadataConvertor extends
	AbstractJpaJavaGlobalMetadataConvertor {

	public EclipseLinkJpaJavaQueryMetadataConvertor(JpaProject jpaProject) {
		super(jpaProject);
	}
	
	public static void convert(JpaProject jpaProject) {
		new EclipseLinkJpaJavaQueryMetadataConvertor(jpaProject).convert();
	}
	
	@Override
	protected Wizard getWizard(){
		return new EclipseLinkJpaJavaQueryMetadataConversionWizard(super.jpaProject);
	}
}
