/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.base;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.GenericJpaJavaQueryMetadataConversionWizard;

public class GenericJpaJavaQueryMetadataConvertor extends 
	AbstractJpaJavaGlobalMetadataConvertor {

	public GenericJpaJavaQueryMetadataConvertor(JpaProject jpaProject) {
		super(jpaProject);
	}

	public static void convert(JpaProject jpaProject) {
		new GenericJpaJavaQueryMetadataConvertor(jpaProject).convert();
	}
	
	@Override
	protected Wizard getWizard(){
		return new GenericJpaJavaQueryMetadataConversionWizard(super.jpaProject);
	}
}
