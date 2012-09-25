/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.swt.widgets.Composite;

public class JpaFacetVersionChangePage
	extends JpaFacetActionPage
{
	public JpaFacetVersionChangePage() {
		super("jpt.jpa.facet.version-change.page"); //$NON-NLS-1$
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = super.createTopLevelComposite(parent);
		// TODO
		//this.getHelpSystem().setHelp(composite, JpaHelpContextIds.NEW_JPA_PROJECT_JPA_FACET);
		return composite;
	}
	
	@Override
	protected void addSubComposites(Composite composite) {
		new PlatformGroup(composite);
		new ClasspathConfigGroup(composite);
		//new ConnectionGroup(composite);
		//new PersistentClassManagementGroup(composite);
	}
	
	@Override
	protected String[] getValidationPropertyNames() {
		// nothing new here *just* yet
		return super.getValidationPropertyNames();
	}
}
