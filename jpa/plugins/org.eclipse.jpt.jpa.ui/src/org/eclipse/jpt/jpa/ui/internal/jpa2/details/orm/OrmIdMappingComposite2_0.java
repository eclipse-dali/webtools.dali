/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractIdMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameText;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.IdMappingMappedByRelationshipPane2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.IdMappingGenerationComposite2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class OrmIdMappingComposite2_0
	extends AbstractIdMappingComposite<IdMapping2_0>
{
	public OrmIdMappingComposite2_0(
			PropertyValueModel<? extends IdMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	
	@Override
	protected Control initializeIdSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		IdMappingMappedByRelationshipPane2_0 mappedByRelationshipPane = new IdMappingMappedByRelationshipPane2_0(this, getSubjectHolder(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		mappedByRelationshipPane.getControl().setLayoutData(gridData);

		// Column widgets
		ColumnComposite columnComposite = new ColumnComposite(this, buildColumnModel(), container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		columnComposite.getControl().setLayoutData(gridData);

		// Name widgets
		this.addLabel(container, JptJpaUiDetailsOrmMessages.ORM_MAPPING_NAME_CHOOSER_NAME);
		new OrmMappingNameText(this, getSubjectHolder(), container);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.ACCESS_TYPE_COMPOSITE_ACCESS);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		return container;
	}
	
	@Override
	protected void initializeGenerationCollapsibleSection(Composite container) {
		new IdMappingGenerationComposite2_0(this, container);
	}
}
