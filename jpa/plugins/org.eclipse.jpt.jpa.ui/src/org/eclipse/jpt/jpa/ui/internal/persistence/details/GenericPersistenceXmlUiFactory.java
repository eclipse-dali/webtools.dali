/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence.details;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.details.JpaPageComposite;
import org.eclipse.swt.widgets.Composite;

public class GenericPersistenceXmlUiFactory implements PersistenceXmlUiFactory
{
	// **************** persistence unit composites ****************************
	
	public ListIterator<JpaPageComposite> createPersistenceUnitComposites(
		PropertyValueModel<PersistenceUnit> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		ArrayList<JpaPageComposite> pages = new ArrayList<JpaPageComposite>(3);

		pages.add(new GenericPersistenceUnitGeneralComposite(subjectHolder, parent, widgetFactory));
		pages.add(new PersistenceUnitConnectionComposite(subjectHolder, parent, widgetFactory));
		pages.add(new PersistenceUnitPropertiesComposite(subjectHolder, parent, widgetFactory));

		return pages.listIterator();
	}

}
