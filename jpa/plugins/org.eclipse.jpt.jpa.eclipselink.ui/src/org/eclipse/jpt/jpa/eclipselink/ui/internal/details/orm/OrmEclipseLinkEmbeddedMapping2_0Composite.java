/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameText;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EmbeddedMappingOverridesComposite2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class OrmEclipseLinkEmbeddedMapping2_0Composite
	extends AbstractEmbeddedMappingComposite<EmbeddedMapping2_0>
{
	public OrmEclipseLinkEmbeddedMapping2_0Composite(
			PropertyValueModel<? extends EmbeddedMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeEmbeddedSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Name widgets
		this.addLabel(container, JptJpaUiDetailsOrmMessages.ORM_MAPPING_NAME_CHOOSER_NAME);
		new OrmMappingNameText(this, getSubjectHolder(), container);

		// Attribute type widgets
		Hyperlink attributeTypeHyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiDetailsMessages.ORM_ATTRIBUTE_TYPE_COMPOSITE_ATTRIBUTE_TYPE);
		new OrmAttributeTypeClassChooser(this, getSubjectHolder(), container, attributeTypeHyperlink);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Overrides widgets
		EmbeddedMappingOverridesComposite2_0 overridesComposite = new EmbeddedMappingOverridesComposite2_0(
			this,
			container
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		overridesComposite.getControl().setLayoutData(gridData);

		return container;
	}
}