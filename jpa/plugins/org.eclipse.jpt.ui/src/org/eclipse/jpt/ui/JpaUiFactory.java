/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import java.util.ListIterator;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Use {@link JpaFactory} to create any {@link JavaTypeMapping} or
 * {@link JavaAttributeMapping}s. This is necessary so that platforms can
 * extend the java model with their own annotations.
 * {@link JavaTypeMappingDefinition} and {@link JavaAttributeMappingDefinition} use
 * this factory. See {@link JpaPlatform#javaTypeMappingDefinitions()} and
 * {@link JpaPlatform#javaAttributeMappingDefinitions() for creating new mappings
 * types.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 *
 * @see org.eclipse.jpt.ui.internal.BaseJpaUiFactory
 *
 * @version 2.0
 * @since 1.0
 */
public interface JpaUiFactory
{
	// **************** persistence unit composites ****************************
	
	/**
	 * Creates the list of <code>JpaComposite</code>s used to edit a
	 * <code>PersistenceUnit</code>. The properties can be regrouped into
	 * sections that will be shown in the editor as pages.
	 *
	 * @param subjectHolder The holder of the pertistence unit
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	ListIterator<JpaPageComposite> createPersistenceUnitComposites(
		PropertyValueModel<PersistenceUnit> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
}