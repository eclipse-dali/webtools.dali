/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import java.util.Iterator;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality.  This is intended to work in conjunction with a core
 * JPA platform ({@link JpaPlatform}) implementation with the same ID.
 * <p>
 * Any implementation should be <i>stateless</i> in nature.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 *
 * See the extension point: org.eclipse.jpt.ui.jpaPlatform
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatformUi
{

	// ********** factory **********

	JpaUiFactory getJpaUiFactory();


	// ********** details providers **********

	JpaDetailsPage<? extends JpaStructureNode> buildJpaDetailsPage(
		Composite parent,
		JpaStructureNode structureNode,
		WidgetFactory widgetFactory);

	
	// ********** structure providers **********

	/**
	 * Return a structure provider for the specified JPA file.
	 */
	JpaStructureProvider getStructureProvider(JpaFile jpaFile);

	
	// ********** file ui definitions **********

	/**
	 * Return a file ui definition for the specified content type.
	 */
	FileUiDefinition getFileUiDefinition(IContentType contentType);

	
	// ********** navigator provider **********

	/**
	 * Return the {@link JpaNavigatorProvider} for this platform, 
	 * which determines Project Explorer content and look
	 */
	JpaNavigatorProvider getNavigatorProvider();

	
	// ********** type mappings **********
	
	JpaComposite buildTypeMappingComposite(
		IContentType contentType, 
		String key, 
		Composite parent, 
		PropertyValueModel<TypeMapping> mappingHolder,
		WidgetFactory widgetFactory);
	
	DefaultMappingUiDefinition<? extends TypeMapping> getDefaultTypeMappingUiDefinition(IContentType contentType);
	
	Iterator<? extends MappingUiDefinition<? extends TypeMapping>> typeMappingUiDefinitions(IContentType contentType);

	
	// ********** attribute mappings **********

	JpaComposite buildAttributeMappingComposite(
		IContentType contentType, 
		String key, 
		Composite parent,
		PropertyValueModel<AttributeMapping> mappingHolder,
		WidgetFactory widgetFactory);
	
	DefaultMappingUiDefinition<? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(IContentType contentType, String key);

	Iterator<? extends MappingUiDefinition<? extends AttributeMapping>> attributeMappingUiDefinitions(IContentType contentType);


	// ********** entity generation **********

	void generateEntities(JpaProject project, IStructuredSelection selection);


	// ********** DDL generation **********

	void generateDDL(JpaProject project, IStructuredSelection selection);

}
