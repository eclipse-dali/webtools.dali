/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.jpa2.persistence.connection.GenericPersistenceUnit2_0ConnectionTab;
import org.eclipse.jpt.ui.internal.jpa2.persistence.options.GenericPersistenceUnit2_0OptionsTab;
import org.eclipse.jpt.ui.internal.persistence.details.GenericPersistenceUnitGeneralComposite;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitPropertiesComposite;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceXmlUiFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * The default implementation of the UI factory required to show the information
 * related to a JPA mapping (type or attribute).
 *
 * @see GenericPersistenceXmlUiFactory
 *
 * @version 1.0
 * @since 1.0
 */
public class Generic2_0PersistenceXmlUiFactory implements PersistenceXmlUiFactory
{
	
	// **************** persistence unit composites ****************************
	public ListIterator<JpaPageComposite> createPersistenceUnitComposites(
		PropertyValueModel<PersistenceUnit> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		ArrayList<JpaPageComposite> pages = new ArrayList<JpaPageComposite>(4);
		
		PropertyValueModel<JpaConnection2_0> connection2_0Holder = 
			this.buildJpaConnection2_0Holder(subjectHolder);
		PropertyValueModel<JpaOptions2_0> options2_0Holder = 
			this.buildJpaOptions2_0Holder(subjectHolder);
		
		pages.add(new GenericPersistenceUnitGeneralComposite(subjectHolder, parent, widgetFactory));
		pages.add(new GenericPersistenceUnit2_0ConnectionTab(connection2_0Holder, parent, widgetFactory));
		pages.add(new GenericPersistenceUnit2_0OptionsTab(options2_0Holder, parent, widgetFactory));
		pages.add(new PersistenceUnitPropertiesComposite(subjectHolder, parent, widgetFactory));

		return pages.listIterator();
	}

	// ********** private methods **********
	
	private PropertyValueModel<JpaConnection2_0> buildJpaConnection2_0Holder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, JpaConnection2_0>(subjectHolder) {
			@Override
			protected JpaConnection2_0 transform_(PersistenceUnit value) {
				return (JpaConnection2_0) ((PersistenceUnit2_0) value).getConnection();
			}
		};
	}
	
	private PropertyValueModel<JpaOptions2_0> buildJpaOptions2_0Holder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, JpaOptions2_0>(subjectHolder) {
			@Override
			protected JpaOptions2_0 transform_(PersistenceUnit value) {
				return (JpaOptions2_0) ((PersistenceUnit2_0) value).getOptions();
			}
		};
	}
}
