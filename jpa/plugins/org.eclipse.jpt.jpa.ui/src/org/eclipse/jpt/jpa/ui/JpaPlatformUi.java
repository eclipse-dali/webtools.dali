/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import java.util.Iterator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPage;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.jpa.ui.structure.JpaStructureProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality.  This is intended to work in conjunction with a core
 * JPA platform ({@link JpaPlatform}) implementation with the same ID.
 * <p>
 * Any implementation should be <em>stateless</em> in nature.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 *
 * See the extension point: org.eclipse.jpt.jpa.ui.jpaPlatform
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatformUi
{
	// ********** navigator provider **********
	
	/**
	 * Return the {@link JpaNavigatorProvider} for this platform, 
	 * which determines Project Explorer content and look
	 */
	JpaNavigatorProvider getNavigatorProvider();
	
	
	// ********** structure providers **********
	
	/**
	 * Return a structure provider for the specified JPA file.
	 */
	JpaStructureProvider getStructureProvider(JpaFile jpaFile);
	
	
	// ********** details providers **********
	
	JpaDetailsPage<? extends JpaStructureNode> buildJpaDetailsPage(
			Composite parent,
			JpaStructureNode structureNode,
			WidgetFactory widgetFactory);
	
	
	// ********** file ui definitions **********
	
	/**
	 * Return a resource ui definition for the specified resource type.
	 */
	ResourceUiDefinition getResourceUiDefinition(JptResourceType resourceType);
	
	
	// ********** type mappings **********
	
	JpaComposite buildTypeMappingComposite(
			JptResourceType resourceType, 
			String mappingKey, 
			Composite parent, 
			PropertyValueModel<TypeMapping> mappingHolder,
			WidgetFactory widgetFactory);
	
	DefaultMappingUiDefinition<PersistentType, ? extends TypeMapping>
			getDefaultTypeMappingUiDefinition(JptResourceType resourceType);
	
	Iterator<MappingUiDefinition<PersistentType, ? extends TypeMapping>>
			typeMappingUiDefinitions(JptResourceType resourceType);
	
	
	// ********** attribute mappings **********
	
	JpaComposite buildAttributeMappingComposite(
			JptResourceType resourceType, 
			String mappingKey, 
			Composite parent,
			PropertyValueModel<AttributeMapping> mappingHolder,
			WidgetFactory widgetFactory);
	
	DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> 
			getDefaultAttributeMappingUiDefinition(JptResourceType resourceType, String mappingKey);
	
	Iterator<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>>
			attributeMappingUiDefinitions(JptResourceType resourceType);
	
	
	// ********** entity generation **********
	
	void generateEntities(JpaProject project, IStructuredSelection selection);
	
	
	// ********** DDL generation **********
	
	void generateDDL(JpaProject project, IStructuredSelection selection);
}
