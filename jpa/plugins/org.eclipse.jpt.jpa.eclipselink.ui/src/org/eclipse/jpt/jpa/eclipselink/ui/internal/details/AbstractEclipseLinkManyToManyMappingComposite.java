/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.ManyToManyRelationship;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractManyToManyMappingComposite;
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

public abstract class AbstractEclipseLinkManyToManyMappingComposite<T extends EclipseLinkManyToManyMapping, C extends Cascade>
	extends AbstractManyToManyMappingComposite<T, ManyToManyRelationship, C>
{
	protected AbstractEclipseLinkManyToManyMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Control initializeManyToManySection(Composite container) {
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

		// Cascade widgets
		CascadePane cascadeComposite = new CascadePane(this, buildCascadeModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		cascadeComposite.getControl().setLayoutData(gridData);

		return container;
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
		return new EclipseLinkConvertersComposite(this, this.buildConverterContainerModel(), container).getControl();
	}

	protected PropertyValueModel<EclipseLinkConverterContainer> buildConverterContainerModel() {
		return new PropertyAspectAdapter<T, EclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterContainer buildValue_() {
				return this.subject.getConverterContainer();
			}
		};
	}

}