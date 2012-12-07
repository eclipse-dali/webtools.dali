/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class JpaMakePersistentWizard
	extends Wizard
{
	private final JpaProject jpaProject;

	private final Set<IType> selectedTypes;

	private final ResourceManager resourceManager;

	private JpaMakePersistentWizardPage makePersistentWizardPage;

	private static final String HELP_CONTEXT_ID = JptJpaUiPlugin.instance().getPluginID() + ".GenerateEntitiesFromSchemaWizard"; //$NON-NLS-1$


	public JpaMakePersistentWizard(JpaProject jpaProject, Set<IType> selectedTypes) {
		super();
		this.jpaProject = jpaProject;
		this.selectedTypes = selectedTypes;
		this.resourceManager = this.getJpaWorkbench().buildLocalResourceManager();
		this.setWindowTitle(JptUiMessages.JpaMakePersistentWizardPage_title);
		this.setDefaultPageImageDescriptor(JptJpaUiImages.ENTITY_BANNER);
	}

	private JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(this.getWorkbench(), JpaWorkbench.class);
	}

	private IWorkbench getWorkbench() {
		return PlatformUI.getWorkbench();
	}

	@Override
	public void addPages() {
		this.setForcePreviousAndNextButtons(true);
		this.makePersistentWizardPage = new JpaMakePersistentWizardPage(this.jpaProject, this.selectedTypes, this.resourceManager, HELP_CONTEXT_ID);
		this.addPage(this.makePersistentWizardPage);
		return;
	}

	@Override
	public boolean performFinish() {
		try {
			this.makePersistentWizardPage.performFinish();
		} catch (InvocationTargetException ex) {
			JptJpaUiPlugin.instance().logError(ex);
		}
		return true;
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}
}
