/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.EclipseLinkPersistenceUnit2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.options.PersistenceXmlOptions2_0Tab;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink2_0PersistenceXmlUiFactory extends EclipseLinkPersistenceXmlUiFactory
{
	public EclipseLink2_0PersistenceXmlUiFactory() {
		super();
	}

	@Override
	protected PersistenceXmlOptionsTab<Options2_0> buildOptionsTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Options2_0> optionsHolder = this.buildOptionsHolder(subjectHolder);

		return new PersistenceXmlOptions2_0Tab(optionsHolder, parent, widgetFactory);
	}

	private PropertyValueModel<Options2_0> buildOptionsHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Options2_0>(subjectHolder) {
			@Override
			protected Options2_0 transform_(PersistenceUnit value) {

				return ((EclipseLinkPersistenceUnit2_0)value).getOptions();
			}
		};
	}
}
