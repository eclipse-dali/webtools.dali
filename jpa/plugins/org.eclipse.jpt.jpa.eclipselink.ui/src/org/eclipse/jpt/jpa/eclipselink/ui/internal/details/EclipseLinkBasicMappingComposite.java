/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractBasicMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.EnumTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.TemporalTypeCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class EclipseLinkBasicMappingComposite<T extends EclipseLinkBasicMapping>
	extends AbstractBasicMappingComposite<T>
{
	protected EclipseLinkBasicMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	
	@SuppressWarnings("unused")
	@Override
	protected Control initializeTypeSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_DEFAULT, 
			buildConverterBooleanModel(null), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
		
		// Lob
		Button lobButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_LOB, 
			buildConverterBooleanModel(LobConverter.class), 
			null);
		((GridData) lobButton.getLayoutData()).horizontalSpan = 2;
		
		PropertyValueModel<Converter> converterModel = buildConverterModel();
		// Temporal
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_TEMPORAL, 
			buildConverterBooleanModel(BaseTemporalConverter.class), 
			null);
		new TemporalTypeCombo(this, buildTemporalConverterModel(converterModel), container);
		
		
		// Enumerated
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_ENUMERATED, 
			buildConverterBooleanModel(BaseEnumeratedConverter.class), 
			null);
		new EnumTypeComboViewer(this, this.buildEnumeratedConverterModel(converterModel), container);

		// EclipseLink Converter
		Button elConverterButton = addRadioButton(
			container, 
			JptJpaEclipseLinkUiDetailsMessages.TYPE_SECTION_CONVERTED, 
			buildConverterBooleanModel(EclipseLinkConvert.class), 
			null);
		((GridData) elConverterButton.getLayoutData()).horizontalSpan = 2;

		PropertyValueModel<EclipseLinkConvert> convertModel = this.buildEclipseLinkConvertModel(converterModel);
		PropertyValueModel<Boolean> convertEnabledModel = PropertyValueModelTools.valueIsNotNull(convertModel);
		Label convertLabel = this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERT_COMPOSITE_CONVERTER_NAME_LABEL, convertEnabledModel);
		GridData gridData = new GridData();
		gridData.horizontalIndent = 20;
		convertLabel.setLayoutData(gridData);
		new EclipseLinkConvertCombo(this, convertModel, convertEnabledModel, container);

		return container;
	}

	protected PropertyValueModel<EclipseLinkMutable> buildMutableModel() {
		return new PropertyAspectAdapterXXXX<T, EclipseLinkMutable>(getSubjectHolder()) {
			@Override
			protected EclipseLinkMutable buildValue_() {
				return this.subject.getMutable();
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkConvert> buildEclipseLinkConvertModel(PropertyValueModel<Converter> converterModel) {
		return PropertyValueModelTools.transform(converterModel, EclipseLinkConvert.CONVERTER_TRANSFORMER);
	}

	protected void initializeConvertersCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_MAPPING_COMPOSITE_CONVERTERS);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(EclipseLinkBasicMappingComposite.this.initializeConvertersSection(section));
				}
			}
		});
	}

	protected Control initializeConvertersSection(Composite container) {
		return new EclipseLinkConvertersComposite(this, this.buildConverterContainerModel(), container).getControl();
	}

	protected PropertyValueModel<EclipseLinkConverterContainer> buildConverterContainerModel() {
		return new PropertyAspectAdapterXXXX<T, EclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterContainer buildValue_() {
				return this.subject.getConverterContainer();
			}
		};
	}
}