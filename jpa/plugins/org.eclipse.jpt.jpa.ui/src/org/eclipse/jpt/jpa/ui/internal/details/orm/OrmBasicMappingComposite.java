/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractBasicMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.OptionalTriStateCheckBox;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class OrmBasicMappingComposite
	extends AbstractBasicMappingComposite<BasicMapping>
{
	public OrmBasicMappingComposite(
			PropertyValueModel<? extends BasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeBasicSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Column widgets
		ColumnComposite columnComposite = new ColumnComposite(this, buildColumnModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		columnComposite.getControl().setLayoutData(gridData);

		// Name widgets
		this.addLabel(container, JptJpaUiDetailsOrmMessages.ORM_MAPPING_NAME_CHOOSER_NAME);
		new OrmMappingNameText(this, getSubjectHolder(), container);

		// Fetch type widgets
		this.addLabel(container, JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_FETCH_LABEL);
		new FetchTypeComboViewer(this, container);

		// Optional widgets
		OptionalTriStateCheckBox optionalCheckBox = new OptionalTriStateCheckBox(this, container);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		optionalCheckBox.getControl().setLayoutData(gridData);

		return container;
	}
}