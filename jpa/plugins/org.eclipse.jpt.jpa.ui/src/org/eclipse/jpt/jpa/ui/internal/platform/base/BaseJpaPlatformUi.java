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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
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
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.MappingResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BaseJpaPlatformUi
	implements JpaPlatformUi
{
	private final ItemTreeStateProviderFactoryProvider navigatorFactoryProvider;
	
	private final JpaPlatformUiProvider platformUiProvider;
	
	
	protected BaseJpaPlatformUi(
			ItemTreeStateProviderFactoryProvider navigatorFactoryProvider,
			JpaPlatformUiProvider platformUiProvider
	) {
		super();
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
			JptJpaUiPlugin.log(iae);
			return null;
		}
	}
	
	
	// ********** details providers **********
	
	public JpaDetailsPageManager<? extends JpaStructureNode> buildJpaDetailsPageManager(
			Composite parent, JpaStructureNode structureNode, WidgetFactory widgetFactory) {
		
		JpaDetailsProvider jpaDetailsProvider = getDetailsProvider(structureNode);
		return jpaDetailsProvider == null ? null : jpaDetailsProvider.buildDetailsPageManager(parent, widgetFactory);
	}
	
	protected JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {
		for (JpaDetailsProvider provider : CollectionTools.iterable(this.detailsProviders())) {
			if (provider.providesDetails(structureNode)) {
				return provider;
			}
		}
		return null;//return null, some structure nodes do not have a details page
	}
	
	protected ListIterator<JpaDetailsProvider> detailsProviders() {
		return this.platformUiProvider.detailsProviders();
	}
	
	
	// ********** mapping ui definitions **********
	
	public JpaComposite buildTypeMappingComposite(
			JptResourceType resourceType, 
			String mappingKey, 
			Composite parent, 
			PropertyValueModel<TypeMapping> mappingHolder, 
			WidgetFactory widgetFactory) {
		
		return getMappingResourceUiDefinition(resourceType).buildTypeMappingComposite(
				mappingKey, mappingHolder, parent, widgetFactory);
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JptResourceType resourceType, 
			String mappingKey, 
			Composite parent, 
			PropertyValueModel<AttributeMapping> mappingHolder, 
			WidgetFactory widgetFactory) {
		
		return getMappingResourceUiDefinition(resourceType).buildAttributeMappingComposite(
				mappingKey, mappingHolder, parent, widgetFactory);
	}
	
	public DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey) {
		return getMappingResourceUiDefinition(resourceType).getDefaultAttributeMappingUiDefinition(mappingKey);
	}
	
	public Iterator<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>> attributeMappingUiDefinitions(JptResourceType resourceType) {
		return getMappingResourceUiDefinition(resourceType).attributeMappingUiDefinitions();
	}
	
	public DefaultMappingUiDefinition<PersistentType, ? extends TypeMapping> getDefaultTypeMappingUiDefinition(JptResourceType resourceType) {
		return getMappingResourceUiDefinition(resourceType).getDefaultTypeMappingUiDefinition();
	}
	
	public Iterator<MappingUiDefinition<PersistentType, ? extends TypeMapping>> typeMappingUiDefinitions(JptResourceType resourceType) {
		return getMappingResourceUiDefinition(resourceType).typeMappingUiDefinitions();
	}
	
	
	// ********** resource ui definitions **********
	
	protected ListIterator<ResourceUiDefinition> resourceUiDefinitions() {
		return this.platformUiProvider.resourceUiDefinitions();
	}
	
	public ResourceUiDefinition getResourceUiDefinition(JptResourceType resourceType) {
		for (ResourceUiDefinition definition : CollectionTools.iterable(this.resourceUiDefinitions())) {
			if (definition.providesUi(resourceType)) {
				return definition;
			}
		}
		// TODO (bug 313632) - return a null resource ui definition?
		throw new IllegalArgumentException("No resource UI definition for the resource type: " + resourceType); //$NON-NLS-1$
	}
	
	public MappingResourceUiDefinition getMappingResourceUiDefinition(JptResourceType resourceType) {
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
	    Shell currentShell = Display.getCurrent().getActiveShell();
	    MessageDialog.openInformation(currentShell, title, message);
	}
}
