/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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

public class ManyToManyMappingPage extends EntityMappingPage
{
	public ManyToManyMappingPage(PersistenceUnit persistenceUnit, ResourceManager resourceManager, IProject project, EntityRefPropertyElem refElem, IWizardPage nextPage)
	{
		super(persistenceUnit, resourceManager, project, refElem, nextPage);
	}

	
}
