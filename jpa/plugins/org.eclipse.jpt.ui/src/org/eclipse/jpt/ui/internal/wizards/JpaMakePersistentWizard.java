/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards;

import java.util.List;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;

public class JpaMakePersistentWizard extends Wizard {	
	
	public static final String HELP_CONTEXT_ID = JptUiPlugin.PLUGIN_ID + ".GenerateEntitiesFromSchemaWizard"; //$NON-NLS-1$


	final List<IType> selectedTypes;

	private JpaMakePersistentWizardPage makePersistentWizardPage;	
	
	protected final ResourceManager resourceManager;
	
	public JpaMakePersistentWizard(List<IType> selectedTypes) {
		super();
		this.selectedTypes = selectedTypes;
		this.resourceManager = new LocalResourceManager(JFaceResources.getResources());
		this.setWindowTitle(JptUiMessages.JpaMakePersistentWizardPage_title);
		this.setDefaultPageImageDescriptor(JptUiPlugin.getImageDescriptor(JptUiIcons.ENTITY_WIZ_BANNER));
	}
	
	@Override
	public void addPages() {
		setForcePreviousAndNextButtons(true);

		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.selectedTypes.get(0).getResource().getProject());
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
			if (persistence != null && persistence.persistenceUnitsSize() > 0) {
				return persistence.persistenceUnits().next();
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
