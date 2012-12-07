/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessHolder;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkElementCollectionMapping2_0Composite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkJoinFetchComboViewer;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.JptUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameText;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.CollectionTable2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.JptUiDetailsMessages2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.TargetClassChooser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class OrmEclipseLinkElementCollectionMapping2_1Composite
	extends EclipseLinkElementCollectionMapping2_0Composite<ElementCollectionMapping2_0>
{
	public OrmEclipseLinkElementCollectionMapping2_1Composite(
			PropertyValueModel<? extends ElementCollectionMapping2_0> mappingModel,
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
		Hyperlink hyperlink = this.addHyperlink(container, JptUiDetailsMessages2_0.TargetClassComposite_label);
		new TargetClassChooser(this, container, hyperlink);

		// Name widgets
		this.addLabel(container, JptUiDetailsOrmMessages.OrmMappingNameChooser_name);
		new OrmMappingNameText(this, getSubjectHolder(), container);

		// Attribute type widgets
		Hyperlink attributeTypeHyperlink = this.addHyperlink(container, EclipseLinkUiDetailsMessages.OrmAttributeTypeComposite_attributeType);
		new OrmAttributeTypeClassChooser(this, getSubjectHolder(), container, attributeTypeHyperlink);

		// Access type widgets
		this.addLabel(container, JptUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessHolderHolder(), container);

		// Fetch type widgets
		this.addLabel(container, JptUiDetailsMessages.BasicGeneralSection_fetchLabel);
		new FetchTypeComboViewer(this, container);

		// Join fetch widgets
		this.addLabel(container, EclipseLinkUiDetailsMessages.EclipseLinkJoinFetchComposite_label);
		new EclipseLinkJoinFetchComboViewer(this, buildJoinFetchHolder(), container);

		// Collection table widgets
		CollectionTable2_0Composite collectionTableComposite = new CollectionTable2_0Composite(this, buildCollectionTableHolder(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		collectionTableComposite.getControl().setLayoutData(gridData);

		return container;
	}

	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}

	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((EclipseLinkElementCollectionMapping2_0) this.subject).getJoinFetch();
			}
		};
	}
}
