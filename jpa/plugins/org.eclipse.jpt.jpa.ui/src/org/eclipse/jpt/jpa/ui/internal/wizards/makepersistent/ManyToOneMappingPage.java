/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;


import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class ManyToOneMappingPage extends EntityMappingPage
{	
	public ManyToOneMappingPage(PersistenceUnit persistenceUnit, ResourceManager resourceManager, IProject project, EntityRefPropertyElem refElem, IWizardPage nextPage)
	{
		super(persistenceUnit, resourceManager, project, refElem, nextPage);
	}
	
	@Override
	protected Group createTargetEntityGroup(Composite parent)
	{
		return null;
	}
	
}
