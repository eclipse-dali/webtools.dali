/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.details;

import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TypeMappingUiProvider<T extends TypeMapping>
{
	/**
	 * The JpaComposite that correponds to this mapping type.  This will be displayed
	 * by the PersistentTypeDetailsPage when the mapping key matches the key given
	 * by this provider.  The composites will be stored in a Map with the mapping key as the key.
	 *
	 * @param parent
	 * @param widgetFactory
	 * @return
	 */
	JpaComposite<T> buildPersistentTypeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<T> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);

	/**
	 * A label to be displayed to the label as an option in the mapping type combo box
	 * @return
	 */
	String label();

	/**
	 * A unique string that corresponds to the key of a MappingProvider in the core
	 */
	String mappingKey();
}