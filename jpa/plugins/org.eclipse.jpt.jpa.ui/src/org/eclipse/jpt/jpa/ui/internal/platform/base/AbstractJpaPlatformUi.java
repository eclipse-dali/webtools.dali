/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.base;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.MappingResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.JavaMetadataConversionWizard;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.JavaMetadataConversionWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractJpaPlatformUi
	implements JpaPlatformUi
{
	private final ItemTreeStateProviderFactoryProvider navigatorFactoryProvider;

	private final JpaPlatformUiProvider platformUiProvider;


	protected AbstractJpaPlatformUi(
			ItemTreeStateProviderFactoryProvider navigatorFactoryProvider,
			JpaPlatformUiProvider platformUiProvider
	) {
		super();
		if ((navigatorFactoryProvider == null) || (platformUiProvider == null)) {
			throw new NullPointerException();
		}
		this.navigatorFactoryProvider = navigatorFactoryProvider;
		this.platformUiProvider = platformUiProvider;
	}


	// ********** navigator provider **********

	public ItemTreeStateProviderFactoryProvider getNavigatorFactoryProvider() {
		return this.navigatorFactoryProvider;
	}


	// ********** structure view factory providers **********

	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider(JpaFile jpaFile) {
		JptResourceType resourceType = jpaFile.getResourceModel().getResourceType();
		return (resourceType == null) ? null : this.getStructureViewFactoryProvider(resourceType);
	}

	protected ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider(JptResourceType resourceType) {
		try {
			return this.getResourceUiDefinition(resourceType).getStructureViewFactoryProvider();
		} catch (IllegalArgumentException iae) {
			JptJpaUiPlugin.instance().logError(iae);
			return null;
		}
	}


	// ********** details providers **********

	public JpaDetailsPageManager<? extends JpaStructureNode> buildJpaDetailsPageManager(
			Composite parent, JpaStructureNode structureNode, WidgetFactory widgetFactory) {

		JpaDetailsProvider jpaDetailsProvider = this.getDetailsProvider(structureNode);
		return jpaDetailsProvider == null ? null : jpaDetailsProvider.buildDetailsPageManager(parent, widgetFactory);
	}

	protected JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {
		for (JpaDetailsProvider provider : this.getDetailsProviders()) {
			if (provider.providesDetails(structureNode)) {
				return provider;
			}
		}
		return null;//return null, some structure nodes do not have a details page
	}

	protected Iterable<JpaDetailsProvider> getDetailsProviders() {
		return this.platformUiProvider.getDetailsProviders();
	}


	// ********** type mappings **********

	public JpaComposite buildTypeMappingComposite(
			JptResourceType resourceType,
			String mappingKey,
			Composite parent,
			PropertyValueModel<TypeMapping> mappingHolder,
			WidgetFactory widgetFactory
	) {
		return this.getMappingResourceUiDefinition(resourceType).buildTypeMappingComposite(mappingKey, mappingHolder, parent, widgetFactory);
	}

	public Iterable<MappingUiDefinition<PersistentType, ? extends TypeMapping>> getTypeMappingUiDefinitions(JptResourceType resourceType) {
		return this.getMappingResourceUiDefinition(resourceType).getTypeMappingUiDefinitions();
	}

	public MappingUiDefinition<PersistentType, ? extends TypeMapping> getTypeMappingUiDefinition(JptResourceType resourceType, String mappingKey) {
		return this.getMappingResourceUiDefinition(resourceType).getTypeMappingUiDefinition(mappingKey);
	}

	public DefaultMappingUiDefinition<PersistentType, ? extends TypeMapping> getDefaultTypeMappingUiDefinition(JptResourceType resourceType) {
		return this.getMappingResourceUiDefinition(resourceType).getDefaultTypeMappingUiDefinition();
	}


	// ********** attribute mappings **********

	public JpaComposite buildAttributeMappingComposite(
			JptResourceType resourceType,
			String mappingKey,
			Composite parent,
			PropertyValueModel<AttributeMapping> mappingHolder,
			PropertyValueModel<Boolean> enabledModel,
			WidgetFactory widgetFactory
	) {
		return this.getMappingResourceUiDefinition(resourceType).buildAttributeMappingComposite(mappingKey, mappingHolder, enabledModel, parent, widgetFactory);
	}

	public Iterable<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>> getAttributeMappingUiDefinitions(JptResourceType resourceType) {
		return this.getMappingResourceUiDefinition(resourceType).getAttributeMappingUiDefinitions();
	}

	public MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> getAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey) {
		return this.getMappingResourceUiDefinition(resourceType).getAttributeMappingUiDefinition(mappingKey);
	}

	public DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey) {
		return this.getMappingResourceUiDefinition(resourceType).getDefaultAttributeMappingUiDefinition(mappingKey);
	}


	// ********** resource ui definitions **********

	protected Iterable<ResourceUiDefinition> getResourceUiDefinitions() {
		return this.platformUiProvider.getResourceUiDefinitions();
	}

	public ResourceUiDefinition getResourceUiDefinition(JptResourceType resourceType) {
		for (ResourceUiDefinition definition : this.getResourceUiDefinitions()) {
			if (definition.providesUi(resourceType)) {
				return definition;
			}
		}
		// TODO (bug 313632) - return a null resource ui definition?
		throw new IllegalArgumentException("No resource UI definition for the resource type: " + resourceType); //$NON-NLS-1$
	}

	protected MappingResourceUiDefinition getMappingResourceUiDefinition(JptResourceType resourceType) {
		ResourceUiDefinition def = this.getResourceUiDefinition(resourceType);
		try {
			return (MappingResourceUiDefinition) def;
		} catch (ClassCastException cce) {
			// TODO (bug 313632) - return a null resource ui definition?
			throw new IllegalArgumentException("No mapping resource UI definition for the resource type: " + resourceType, cce); //$NON-NLS-1$
		}
	}


	// ********** entity generation **********

	public void generateEntities(JpaProject project, IStructuredSelection selection) {
		EntitiesGenerator.generate(project, selection);
	}


	// ********** convenience methods **********

	protected void displayMessage(String title, String message) {
	    MessageDialog.openInformation(SWTUtil.getShell(), title, message);
	}

	protected void openInDialog(JavaMetadataConversionWizardPage wizardPage) {
		this.openInDialog(new JavaMetadataConversionWizard(wizardPage));
	}

	protected void openInDialog(IWizard wizard) {
		new SizedWizardDialog(wizard).open();
	}


	// ********** wizard dialog **********

	public class SizedWizardDialog
		extends WizardDialog
	{
		private final int width;
		private final int height;

		public SizedWizardDialog(IWizard wizard) {
			this(wizard, 520, 460);
		}

		public SizedWizardDialog(IWizard wizard, int width, int height) {
			this(SWTUtil.getShell(), wizard, width, height);
		}

		public SizedWizardDialog(Shell shell, IWizard wizard, int width, int height) {
			super(shell, wizard);
			this.width = width;
			this.height = height;
		}

		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setSize(this.width, this.height);
			SWTUtil.center(shell, this.getParentShell());
		}
	}
}
