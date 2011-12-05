/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.AbstractElementCollectionMapping2_0Composite;
import org.eclipse.swt.widgets.Composite;

public abstract class EclipseLinkElementCollectionMapping2_0Composite<T extends ElementCollectionMapping2_0>
	extends AbstractElementCollectionMapping2_0Composite<T>
{

	protected EclipseLinkElementCollectionMapping2_0Composite(PropertyValueModel<? extends T> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeElementCollectionCollapsibleSection(container);
		initializeValueCollapsibleSection(container);
		initializeKeyCollapsibleSection(container);
		initializeConvertersCollapsibleSection(container);
		initializeOrderingCollapsibleSection(container);
	}


	protected void initializeConvertersCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters
		);
		initializeConvertersSection(container, this.buildConverterHolderValueModel());
	}

	protected void initializeConvertersSection(Composite container, PropertyValueModel<EclipseLinkConverterContainer> converterHolder) {
		new EclipseLinkConvertersComposite(this, converterHolder, container);
	}

	protected PropertyValueModel<EclipseLinkConverterContainer> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<T, EclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterContainer buildValue_() {
				return ((EclipseLinkElementCollectionMapping2_0) this.subject).getConverterContainer();
			}
		};
	}
}