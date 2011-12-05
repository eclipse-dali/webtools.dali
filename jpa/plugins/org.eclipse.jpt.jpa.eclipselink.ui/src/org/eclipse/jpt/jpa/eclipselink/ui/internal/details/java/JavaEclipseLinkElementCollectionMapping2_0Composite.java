/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkConvertComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkElementCollectionMapping2_0Composite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.CollectionTable2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.TargetClassComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

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
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeElementCollectionSection(Composite container) {
		new TargetClassComposite(this, container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchHolder(), container);
		new CollectionTable2_0Composite(this, buildCollectionTableHolder(), container);
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
	protected void initializeBasicValueTypeSection(Composite typeSection) {
		super.initializeBasicValueTypeSection(typeSection);
		PropertyValueModel<Converter> converterHolder = buildConverterHolder();

		// EclipseLink Converter
		Button elConverterButton = addRadioButton(
			typeSection, 
			EclipseLinkUiDetailsMessages.TypeSection_converted, 
			buildConverterBooleanHolder(EclipseLinkConvert.class), 
			null);
		((GridData) elConverterButton.getLayoutData()).horizontalSpan = 2;

		Pane<EclipseLinkConvert> convertComposite = buildConvertComposite(buildEclipseLinkConverterHolder(converterHolder), typeSection);
		GridData gridData = (GridData) convertComposite.getControl().getLayoutData();
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 20;
		registerSubPane(convertComposite);
	}

	protected PropertyValueModel<Converter> buildConverterHolder() {
		return new PropertyAspectAdapter<ConvertibleMapping, Converter>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}

	protected WritablePropertyValueModel<Boolean> buildConverterBooleanHolder(final Class<? extends Converter> converterType) {
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

	protected Pane<EclipseLinkConvert> buildConvertComposite(PropertyValueModel<EclipseLinkConvert> convertHolder, Composite container) {
		return new EclipseLinkConvertComposite(convertHolder, container, getWidgetFactory());
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