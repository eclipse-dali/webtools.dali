/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkElementCollectionMappingComposite2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkJoinFetchComboViewer;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameText;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.CollectionTableComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.TargetClassChooser2_0;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class EclipseLinkOrmElementCollectionMappingComposite2_1
	extends EclipseLinkElementCollectionMappingComposite2_0<EclipseLinkElementCollectionMapping2_0>
{
	public EclipseLinkOrmElementCollectionMappingComposite2_1(
			PropertyValueModel<? extends EclipseLinkElementCollectionMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control buildElementCollectionSectionClient(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Target class widgets
		Hyperlink hyperlink = this.addHyperlink(container, JptJpaUiDetailsMessages2_0.TARGET_CLASS_COMPOSITE_LABEL);
		new TargetClassChooser2_0(this, container, hyperlink);

		// Name widgets
		this.addLabel(container, JptJpaUiDetailsOrmMessages.ORM_MAPPING_NAME_CHOOSER_NAME);
		new OrmMappingNameText(this, getSubjectHolder(), container);

		// Attribute type widgets
		Hyperlink attributeTypeHyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiDetailsMessages.ORM_ATTRIBUTE_TYPE_COMPOSITE_ATTRIBUTE_TYPE);
		new EclipseLinkOrmAttributeTypeClassChooser(this, getSubjectHolder(), container, attributeTypeHyperlink);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.ACCESS_TYPE_COMPOSITE_ACCESS);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Fetch type widgets
		this.addLabel(container, JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_FETCH_LABEL);
		new FetchTypeComboViewer(this, container);

		// Join fetch widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_JOIN_FETCHCOMPOSITE_LABEL);
		new EclipseLinkJoinFetchComboViewer(this, buildJoinFetchModel(), container);

		// Collection table widgets
		CollectionTableComposite2_0 collectionTableComposite = new CollectionTableComposite2_0(this, buildCollectionTableModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		collectionTableComposite.getControl().setLayoutData(gridData);

		return container;
	}

	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchModel() {
		return new PropertyAspectAdapter<EclipseLinkElementCollectionMapping2_0, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return this.subject.getJoinFetch();
			}
		};
	}
}
