/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmAttributeMappingUiDefinition<T extends AttributeMapping>
	extends MappingUiDefinition<ReadOnlyPersistentAttribute, T>
{
	/**
	 * Creates <code>JpaComposite</code> that corresponds to this mapping type.
	 * This will be displayed by the <code>PersistentAttributeDetailsPage</code>
	 * when the mapping key matches the key given by this provider. The
	 * composites will be stored in a Map with the mapping key as the key.
	 *
	 * @param factory The UI factory responsible to create the right composite
	 * for any mapping type
	 * @param subjectHolder The holder of the subject being displayed
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the various widgets
	 * @return The composite displaying the information for a certain mapping
	 */
	JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<T> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory);
}
