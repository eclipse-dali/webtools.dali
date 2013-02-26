/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality. This is intended to work in conjunction with a core
 * {@link JpaPlatform JPA platform} implementation with the same ID.
 * <p>
 * Any implementation's state should be <em>static</em>.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 * <p>
 * See the extension point: <code>org.eclipse.jpt.jpa.ui.jpaPlatform</code>
 * <p>
 * To retrieve the JPA UI platform corresponding to a JPA platform:
 * <pre>
 * JpaProject jpaProject = ...;
 * JpaPlatform jpaPlatform = jpaProject.getJpaPlatform();
 * JpaPlatformUi jpaPlatformUi = (JpaPlatformUi) jpaPlatform.getAdapter(JpaPlatformUi.class);
 * </pre>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatformUi {
	// ********** navigator provider **********

	/**
	 * Return the platform's Navigator factory provider.
	 * This is used by the Common Navigator to build and maintain the JPA
	 * content and labels.
	 */
	ItemTreeStateProviderFactoryProvider getNavigatorFactoryProvider();


	// ********** structure view factory providers **********

	/**
	 * Return a structure provider for the specified JPA file.
	 */
	ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider(JpaFile jpaFile);


	// ********** details provider **********

	/**
	 * Return a details provider for the specified JPA structure node.
	 */
	JpaDetailsProvider getDetailsProvider(JpaStructureNode node);


	// ********** file ui definitions **********

	/**
	 * Return a resource ui definition for the specified resource type.
	 */
	ResourceUiDefinition getResourceUiDefinition(JptResourceType resourceType);


	// ********** type mappings **********

	JpaComposite buildTypeMappingComposite(
			JptResourceType resourceType, 
			String mappingKey, 
			PropertyValueModel<TypeMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager);

	/**
	 * Return the list of possible type mapping ui definitions
	 * filtered using MappingUiDefinition#isEnabledFor(JpaContextNode).
	 * <p>
	 * @see #getTypeMappingUiDefinitions(JptResourceType)
	 */
	Iterable<MappingUiDefinition> getTypeMappingUiDefinitions(PersistentType persistentType);

	/**
	 * Return the list of all the possible type mapping ui definitions
	 * for the given JptResourceType.
	 * <p>
	 * @see #getTypeMappingUiDefinitions(PersistentType)
	 */
	Iterable<MappingUiDefinition> getTypeMappingUiDefinitions(JptResourceType resourceType);

	MappingUiDefinition getTypeMappingUiDefinition(JptResourceType resourceType, String mappingKey);

	DefaultMappingUiDefinition getDefaultTypeMappingUiDefinition(JptResourceType resourceType);


	// ********** attribute mappings **********

	JpaComposite buildAttributeMappingComposite(
			JptResourceType resourceType, 
			String mappingKey, 
			Composite parentComposite,
			PropertyValueModel<AttributeMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager);

	/**
	 * Return the list of possible attribute mapping ui definitions
	 * filtered using MappingUiDefinition#isEnabledFor(JpaContextNode).
	 * <p>
	 * @see #getAttributeMappingUiDefinitions(JptResourceType)
	 */
	Iterable<MappingUiDefinition> getAttributeMappingUiDefinitions(PersistentAttribute persistentAttribute);

	/**
	 * Return the list of all the possible attribute mapping ui definitions
	 * for the given JptResourceType.
	 * <p>
	 * @see #getAttributeMappingUiDefinitions(PersistentAttribute)
	 */
	Iterable<MappingUiDefinition> getAttributeMappingUiDefinitions(JptResourceType resourceType);

	MappingUiDefinition getAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey);

	DefaultMappingUiDefinition getDefaultAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey);


	// ********** metadata conversion **********

	void convertJavaQueryMetadataToGlobal(JpaProject project);

	void convertJavaGeneratorMetadataToGlobal(JpaProject project);


	// ********** entity generation **********

	void generateEntities(JpaProject project, IStructuredSelection selection);


	// ********** DDL generation **********

	void generateDDL(JpaProject project, IStructuredSelection selection);
}
