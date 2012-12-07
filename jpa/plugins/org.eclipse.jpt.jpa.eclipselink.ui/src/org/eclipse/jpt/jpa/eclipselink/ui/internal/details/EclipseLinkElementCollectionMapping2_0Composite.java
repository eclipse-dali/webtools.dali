/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.NotNullObjectTransformer;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.AbstractElementCollectionMapping2_0Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class EclipseLinkElementCollectionMapping2_0Composite<T extends ElementCollectionMapping2_0>
	extends AbstractElementCollectionMapping2_0Composite<T>
{
	protected EclipseLinkElementCollectionMapping2_0Composite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
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
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(EclipseLinkElementCollectionMapping2_0Composite.this.initializeConvertersSection(section));
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
				return ((EclipseLinkElementCollectionMapping2_0) this.subject).getConverterContainer();
			}
		};
	}

	@Override
	protected Composite buildBasicValueTypeSectionClient(Section section) {
		Composite container = super.buildBasicValueTypeSectionClient(section);
		PropertyValueModel<Converter> converterHolder = buildConverterModel();

		// EclipseLink Converter
		Button elConverterButton = addRadioButton(
			container, 
			EclipseLinkUiDetailsMessages.TypeSection_converted, 
			buildConverterBooleanHolder(EclipseLinkConvert.class), 
			null);
		((GridData) elConverterButton.getLayoutData()).horizontalSpan = 2;

		PropertyValueModel<EclipseLinkConvert> convertModel = this.buildEclipseLinkConvertModel(converterHolder);
		PropertyValueModel<Boolean> convertEnabledModel = this.buildNotNullModel(convertModel);
		Label convertLabel = this.addLabel(container, EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_converterNameLabel, convertEnabledModel);
		GridData gridData = new GridData();
		gridData.horizontalIndent = 20;
		convertLabel.setLayoutData(gridData);
		new EclipseLinkConvertCombo(this, convertModel, convertEnabledModel, container);

		return container;
	}

	protected PropertyValueModel<Converter> buildConverterModel() {
		return new PropertyAspectAdapter<ConvertibleMapping, Converter>(this.getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}

	protected ModifiablePropertyValueModel<Boolean> buildConverterBooleanHolder(final Class<? extends Converter> converterType) {
		return new PropertyAspectAdapter<ConvertibleMapping, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getType() == converterType);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(converterType);
				}
			}
		};
	}
	protected PropertyValueModel<EclipseLinkConvert> buildEclipseLinkConvertModel(PropertyValueModel<Converter> converterModel) {
		return new TransformationPropertyValueModel<Converter, EclipseLinkConvert>(converterModel, EclipseLinkConvert.CONVERTER_TRANSFORMER);
	}

	protected <M> PropertyValueModel<Boolean> buildNotNullModel(PropertyValueModel<M> valueModel) {
		return new TransformationPropertyValueModel<M, Boolean>(valueModel, NotNullObjectTransformer.<M>instance());
	}
}