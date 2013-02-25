/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.base;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.MappingResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
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

	public JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {
		for (JpaDetailsProvider provider : this.getDetailsProviders(structureNode.getResourceType())) {
			if (ObjectTools.equals(structureNode.getType(), provider.getType())) {
				return provider;
			}
		}
		return null; // some JPA structure nodes do not have a details page
	}

	protected Iterable<JpaDetailsProvider> getDetailsProviders(JptResourceType resourceType) {
		return this.getResourceUiDefinition(resourceType).getDetailsProviders();
	}


	// ********** type mappings **********

	public JpaComposite buildTypeMappingComposite(JptResourceType resourceType, String mappingKey, PropertyValueModel<TypeMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return this.getMappingResourceUiDefinition(resourceType).buildTypeMappingComposite(mappingKey, mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public Iterable<MappingUiDefinition> getTypeMappingUiDefinitions(PersistentType persistentType) {
		return IterableTools.filter(this.getTypeMappingUiDefinitions(persistentType.getResourceType()), new UiDefinitionFilter(persistentType));
	}

	public Iterable<MappingUiDefinition> getTypeMappingUiDefinitions(JptResourceType resourceType) {
		return this.getMappingResourceUiDefinition(resourceType).getTypeMappingUiDefinitions();
	}

	public MappingUiDefinition getTypeMappingUiDefinition(JptResourceType resourceType, String mappingKey) {
		return this.getMappingResourceUiDefinition(resourceType).getTypeMappingUiDefinition(mappingKey);
	}

	public DefaultMappingUiDefinition getDefaultTypeMappingUiDefinition(JptResourceType resourceType) {
		return this.getMappingResourceUiDefinition(resourceType).getDefaultTypeMappingUiDefinition();
	}


	// ********** attribute mappings **********

	public JpaComposite buildAttributeMappingComposite(JptResourceType resourceType, String mappingKey, Composite parentComposite, PropertyValueModel<AttributeMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return this.getMappingResourceUiDefinition(resourceType).buildAttributeMappingComposite(mappingKey, mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public Iterable<MappingUiDefinition> getAttributeMappingUiDefinitions(ReadOnlyPersistentAttribute persistentAttribute) {
		return IterableTools.filter(this.getAttributeMappingUiDefinitions(persistentAttribute.getResourceType()), new UiDefinitionFilter(persistentAttribute));
	}

	public Iterable<MappingUiDefinition> getAttributeMappingUiDefinitions(JptResourceType resourceType) {
		return this.getMappingResourceUiDefinition(resourceType).getAttributeMappingUiDefinitions();
	}

	public MappingUiDefinition getAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey) {
		return this.getMappingResourceUiDefinition(resourceType).getAttributeMappingUiDefinition(mappingKey);
	}

	public DefaultMappingUiDefinition getDefaultAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey) {
		return this.getMappingResourceUiDefinition(resourceType).getDefaultAttributeMappingUiDefinition(mappingKey);
	}


	/* CU private */ class UiDefinitionFilter
		extends Predicate.Adapter<MappingUiDefinition>
	{
		private final JpaContextModel node;
		
		public UiDefinitionFilter(JpaContextModel node) {
			super();
			this.node =  node;
		}
		@Override
		public boolean evaluate(MappingUiDefinition mappingUiDefinition) {
			return mappingUiDefinition.isEnabledFor(this.node);
		}
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
