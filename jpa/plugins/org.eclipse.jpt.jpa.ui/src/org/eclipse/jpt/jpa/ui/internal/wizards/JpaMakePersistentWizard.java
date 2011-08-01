/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards;

import java.util.List;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;

public class JpaMakePersistentWizard extends Wizard {	
	
	public static final String HELP_CONTEXT_ID = JptJpaUiPlugin.PLUGIN_ID + ".GenerateEntitiesFromSchemaWizard"; //$NON-NLS-1$


	final List<IType> selectedTypes;

	private JpaMakePersistentWizardPage makePersistentWizardPage;	
	
	protected final ResourceManager resourceManager;
	
	public JpaMakePersistentWizard(List<IType> selectedTypes) {
		super();
		this.selectedTypes = selectedTypes;
		this.resourceManager = new LocalResourceManager(JFaceResources.getResources());
		this.setWindowTitle(JptUiMessages.JpaMakePersistentWizardPage_title);
		this.setDefaultPageImageDescriptor(JptJpaUiPlugin.getImageDescriptor(JptUiIcons.ENTITY_WIZ_BANNER));
	}
	
	@Override
	public void addPages() {
		setForcePreviousAndNextButtons(true);

		JpaProject jpaProject = JptJpaCorePlugin.getJpaProject(this.selectedTypes.get(0).getResource().getProject());
		this.makePersistentWizardPage = new JpaMakePersistentWizardPage(jpaProject, this.selectedTypes, HELP_CONTEXT_ID);
		this.addPage(this.makePersistentWizardPage);
		return;
	}

	@Override
	public boolean performFinish() {
		this.makePersistentWizardPage.performFinish();
		return true;
	}

	protected PersistenceUnit getPersistenceUnit(JpaProject jpaProject) {
		PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
		if (persistenceXml != null) {
			Persistence persistence = persistenceXml.getPersistence();
			if (persistence != null && persistence.getPersistenceUnitsSize() > 0) {
				return persistence.getPersistenceUnits().iterator().next();
			}
		}
		return null;
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}
}
