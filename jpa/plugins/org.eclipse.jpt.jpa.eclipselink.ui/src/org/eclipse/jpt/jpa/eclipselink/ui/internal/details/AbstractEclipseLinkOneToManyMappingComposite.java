/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyRelationship;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.CascadePane;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.TargetEntityClassChooser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractEclipseLinkOneToManyMappingComposite<T extends EclipseLinkOneToManyMapping, C extends Cascade> 
	extends AbstractOneToManyMappingComposite<T, EclipseLinkOneToManyRelationship, C>
{
	protected AbstractEclipseLinkOneToManyMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeOneToManySection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Target entity widgets
		Hyperlink targetEntityHyperlink = this.addHyperlink(container, JptUiDetailsMessages.TargetEntityChooser_label);
		new TargetEntityClassChooser(this, container, targetEntityHyperlink);

		// Fetch type widgets
		this.addLabel(container, JptUiDetailsMessages.BasicGeneralSection_fetchLabel);
		new FetchTypeComboViewer(this, container);
	
		// Join fetch widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_JOIN_FETCHCOMPOSITE_LABEL);
		new EclipseLinkJoinFetchComboViewer(this, buildJoinFetchModel(), container);

		// Private owned widgets
		EclipseLinkPrivateOwnedCheckBox privateOwnedCheckBox = new EclipseLinkPrivateOwnedCheckBox(this, buildPrivateOwnedModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		privateOwnedCheckBox.getControl().setLayoutData(gridData);

		// Cascade widgets
		CascadePane cascadeComposite = new CascadePane(this, buildCascadeModel(), container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		cascadeComposite.getControl().setLayoutData(gridData);

		return container;
	}

	@Override
	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new EclipseLinkOneToManyJoiningStrategyPane(this, buildRelationshipModel(), container);
	}

	protected PropertyValueModel<EclipseLinkPrivateOwned> buildPrivateOwnedModel() {
		return new PropertyAspectAdapter<T, EclipseLinkPrivateOwned>(getSubjectHolder()) {
			@Override
			protected EclipseLinkPrivateOwned buildValue_() {
				return this.subject.getPrivateOwned();
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchModel() {
		return new PropertyAspectAdapter<T, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return this.subject.getJoinFetch();
			}
		};
	}

	protected void initializeConvertersCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_MAPPING_COMPOSITE_CONVERTERS);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeConvertersSection(section));
				}
			}
		});
	}

	protected Control initializeConvertersSection(Composite container) {
		return new EclipseLinkConvertersComposite(this, this.buildConverterHolderValueModel(), container).getControl();
	}

	protected PropertyValueModel<EclipseLinkConverterContainer> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<T, EclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterContainer buildValue_() {
				return this.subject.getConverterContainer();
			}
		};
	}
}