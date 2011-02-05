/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import java.util.ListIterator;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.swt.widgets.Composite;

public interface PersistenceXmlUiFactory
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