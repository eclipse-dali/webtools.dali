/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkMultitenancyComposite;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.QueriesComposite2_1;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkJavaMappedSuperclassComposite2_5
	extends EclipseLinkAbstractJavaMappedSuperclassComposite<EclipseLinkJavaMappedSuperclass>
{

	protected EclipseLinkJavaMappedSuperclassComposite2_5(
			PropertyValueModel<? extends EclipseLinkJavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite, WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);		
		this.initializeCachingCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeMultitenancyCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}

	@Override
	protected Control initializeMappedSuperclassSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptJpaUiDetailsMessages.ID_CLASS_COMPOSITE_LABEL);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		return container;
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new QueriesComposite2_1(this, this.buildQueryContainerModel(), container).getControl();
	}
	
	@Override
	protected Control initializeCachingSection(Composite container) {
		return new EclipseLinkJavaCachingComposite2_0(this, this.buildCachingModel(), container).getControl();
	}
	
	protected void initializeMultitenancyCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_MAPPING_COMPOSITE_MULTITENANCY);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeMultitenancySection(section));
				}
			}
		});
	}

	protected Control initializeMultitenancySection(Composite container) {
		return new EclipseLinkMultitenancyComposite(this, this.buildMultitenancyModel(), container).getControl();
	}

	private PropertyAspectAdapter<EclipseLinkJavaMappedSuperclass, EclipseLinkJavaMultitenancy2_3> buildMultitenancyModel() {
		return new PropertyAspectAdapter<EclipseLinkJavaMappedSuperclass, EclipseLinkJavaMultitenancy2_3>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJavaMultitenancy2_3 buildValue_() {
				return this.subject.getMultitenancy();
			}
		};
	}

}
