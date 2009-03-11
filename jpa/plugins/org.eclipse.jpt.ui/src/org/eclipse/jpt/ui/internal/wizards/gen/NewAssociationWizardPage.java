/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.HashMap;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.gen.internal2.ORMGenCustomizer;


public abstract class NewAssociationWizardPage extends WizardPage {

	protected ORMGenCustomizer customizer;
	
	public NewAssociationWizardPage(ORMGenCustomizer customizer, String name) {
		super(name);
		this.customizer = customizer ;
	}
	
	protected HashMap<String, Object> getWizardDataModel(){
		return ((NewAssociationWizard)this.getWizard()).getDataModel();
	}
	
	protected String getReferrerTableName(){
		return ((NewAssociationWizard)getWizard()).getReferrerTableName();
	}

	protected String getReferencedTableName(){
		return ((NewAssociationWizard)getWizard()).getReferencedTableName();
	}

	protected String getJoinTableName(){
		return ((NewAssociationWizard)getWizard()).getJoinTableName();
	}

	protected String getCardinality(){
		return ((NewAssociationWizard)getWizard()).getCardinality() ;
	}
	
	public void updateWithNewTables() {
	}
	
}
