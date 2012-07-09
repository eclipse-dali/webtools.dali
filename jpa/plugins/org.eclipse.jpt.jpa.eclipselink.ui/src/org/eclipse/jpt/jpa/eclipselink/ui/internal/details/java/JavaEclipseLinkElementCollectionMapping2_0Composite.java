/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeBooleanPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkConvertCombo;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkElementCollectionMapping2_0Composite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkJoinFetchComboViewer;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.CollectionTable2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.JptUiDetailsMessages2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.TargetClassChooser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class JavaEclipseLinkElementCollectionMapping2_0Composite
	extends EclipseLinkElementCollectionMapping2_0Composite<JavaElementCollectionMapping2_0>
{
	/**
	 * Creates a new <code>EclipseLink1_1OrmBasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkElementCollectionMapping2_0Composite(PropertyValueModel<? extends JavaElementCollectionMapping2_0> subjectHolder,
									PropertyValueModel<Boolean> enabledModel,
									Composite parent,
									WidgetFactory widgetFactory) {

		super(subjectHolder, enabledModel, parent, widgetFactory);
	}

	@Override
	protected Control initializeElementCollectionSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Target class widgets
		Hyperlink hyperlink = this.addHyperlink(container, JptUiDetailsMessages2_0.TargetClassComposite_label);
		new TargetClassChooser(this, container, hyperlink);

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

	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchHolder() {
		return new PropertyAspectAdapter<JavaElementCollectionMapping2_0, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((EclipseLinkElementCollectionMapping2_0) this.subject).getJoinFetch();
			}
		};
	}


	@Override
	protected void initializeBasicValueTypeSection(Composite container) {
		super.initializeBasicValueTypeSection(container);
		PropertyValueModel<Converter> converterHolder = buildConverterHolder();

		// EclipseLink Converter
		Button elConverterButton = addRadioButton(
			container, 
			EclipseLinkUiDetailsMessages.TypeSection_converted, 
			buildConverterBooleanHolder(EclipseLinkConvert.class), 
			null);
		((GridData) elConverterButton.getLayoutData()).horizontalSpan = 2;

		PropertyValueModel<EclipseLinkConvert> convertHolder = buildEclipseLinkConverterHolder(converterHolder);
		PropertyValueModel<Boolean> convertEnabledModel = CompositeBooleanPropertyValueModel.and(getEnabledModel(), buildEclipseLinkConvertBooleanHolder(convertHolder));
		Label convertLabel = this.addLabel(container, EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_converterNameLabel, convertEnabledModel);
		GridData gridData = new GridData();
		gridData.horizontalIndent = 20;
		convertLabel.setLayoutData(gridData);
		registerSubPane(new EclipseLinkConvertCombo(convertHolder, convertEnabledModel, container, getWidgetFactory()));
	}

	protected PropertyValueModel<Converter> buildConverterHolder() {
		return new PropertyAspectAdapter<ConvertibleMapping, Converter>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
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

	protected PropertyValueModel<EclipseLinkConvert> buildEclipseLinkConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, EclipseLinkConvert>(converterHolder) {
			@Override
			protected EclipseLinkConvert transform_(Converter converter) {
				return converter.getType() == EclipseLinkConvert.class ? (EclipseLinkConvert) converter : null;
			}
		};
	}
}