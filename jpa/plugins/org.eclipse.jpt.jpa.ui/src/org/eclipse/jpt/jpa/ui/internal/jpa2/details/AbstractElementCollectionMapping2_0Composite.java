/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.EnumTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TemporalTypeComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ColumnComposite                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TemporalTypeComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EnumTypeComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | LobComposite                                                          | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see BasicMapping
 * @see OrderColumnComposite
 * @see EnumTypeComposite
 * @see FetchTypeComposite
 * @see LobComposite
 * @see OptionalComposite
 * @see TemporalTypeComposite
 *
 * @version 2.3
 * @since 2.3
 */
public abstract class AbstractElementCollectionMapping2_0Composite<T extends ElementCollectionMapping2_0> 
	extends Pane<T>
	implements JpaComposite
{
		
	private Composite basicValueComposite;
	
	private Composite embeddableValueComposite;
	
	/**
	 * Creates a new <code>BasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IBasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractElementCollectionMapping2_0Composite(PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent,
	                             WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	@Override
	protected void initializeLayout(Composite container) {
		initializeElementCollectionCollapsibleSection(container);
		initializeValueCollapsibleSection(container);
		initializeKeyCollapsibleSection(container);
		initializeOrderingCollapsibleSection(container);
	}
	
	protected void initializeElementCollectionCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages2_0.ElementCollectionSection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeElementCollectionSection(container);
	}

	protected void initializeElementCollectionSection(Composite container) {
		new TargetClassComposite(this, container);
		new FetchTypeComposite(this, container);
		new CollectionTable2_0Composite(this, buildCollectionTableHolder(), container);
	}

	protected void initializeOrderingCollapsibleSection(Composite container) {
		new Ordering2_0Composite(this, container);
	}
	
	protected void initializeValueCollapsibleSection(Composite container) {
		Composite valueSection = addCollapsibleSection(
			container,
			JptUiDetailsMessages2_0.AbstractElementCollectionMapping2_0_Composite_valueSectionTitle
		);
		initializeValueSection(valueSection);
	}
	
	protected void initializeKeyCollapsibleSection(Composite container) {
		
	}

	protected void initializeValueSection(Composite container) {
		PageBook pageBook = new PageBook(container, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		pageBook.setLayoutData(gd);

		this.initializeBasicValueSection(pageBook);
		this.initializeEmbeddableValueSection(pageBook);
		
		installValueControlSwitcher(pageBook);
	}

	protected void initializeBasicValueSection(Composite container) {
		this.basicValueComposite = addSubPane(container);

		new ColumnComposite(this, buildValueColumnHolder(), this.basicValueComposite);
		// type section
		Composite converterSection = addCollapsibleSubSection(
			this.basicValueComposite,
			JptUiDetailsMessages.TypeSection_type,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);
		((GridLayout) converterSection.getLayout()).numColumns = 2;

		this.initializeBasicValueTypeSection(converterSection);
	}

	protected void initializeBasicValueTypeSection(Composite typeSection) {
		// No converter
		Button noConverterButton = addRadioButton(
			typeSection, 
			JptUiDetailsMessages.TypeSection_default, 
			buildNoConverterHolder(), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;

		// Lob
		Button lobButton = addRadioButton(
			typeSection, 
			JptUiDetailsMessages.TypeSection_lob, 
			buildLobConverterHolder(), 
			null);
		((GridData) lobButton.getLayoutData()).horizontalSpan = 2;


		PropertyValueModel<Converter> converterHolder = buildConverterHolder();
		// Temporal
		addRadioButton(
			typeSection, 
			JptUiDetailsMessages.TypeSection_temporal, 
			buildTemporalBooleanHolder(), 
			null);
		registerSubPane(new TemporalTypeComposite(buildTemporalConverterHolder(converterHolder), typeSection, getWidgetFactory()));


		// Enumerated
		addRadioButton(
			typeSection, 
			JptUiDetailsMessages.TypeSection_enumerated, 
			buildEnumeratedBooleanHolder(), 
			null);
		registerSubPane(new EnumTypeComposite(buildEnumeratedConverterHolder(converterHolder), typeSection, getWidgetFactory()));
	}

	protected void initializeEmbeddableValueSection(Composite container) {
		this.embeddableValueComposite = new ElementCollectionValueOverridesComposite(this, container).getControl();
	}

	private void installValueControlSwitcher(PageBook pageBook) {

		new ControlSwitcher(
			buildValueHolder(),
			buildPaneTransformer(),
			pageBook
		);
	}
	
	protected PropertyValueModel<ElementCollectionMapping2_0.Type> buildValueHolder() {
		return new PropertyAspectAdapter<T, ElementCollectionMapping2_0.Type>(
				this.getSubjectHolder(), ElementCollectionMapping2_0.VALUE_TYPE_PROPERTY) {
			@Override
			protected ElementCollectionMapping2_0.Type buildValue_() {
				return this.subject.getValueType();
			}
		};
	}

	private Transformer<ElementCollectionMapping2_0.Type, Control> buildPaneTransformer() {
		return new Transformer<ElementCollectionMapping2_0.Type, Control>() {
			public Control transform(ElementCollectionMapping2_0.Type type) {
				return AbstractElementCollectionMapping2_0Composite.this.transformValueType(type);
			}
		};
	}

	/**
	 * Given the selected override, return the control that will be displayed
	 */
	protected Control transformValueType(ElementCollectionMapping2_0.Type type) {
		if (type == null) {
			return null;
		}
		switch (type) {
			case BASIC_TYPE :
				return this.basicValueComposite;
			case EMBEDDABLE_TYPE :
				return this.embeddableValueComposite;
			default :
				return null;
		}
	}
	
	protected PropertyValueModel<CollectionTable2_0> buildCollectionTableHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, CollectionTable2_0>(getSubjectHolder()) {
			@Override
			protected CollectionTable2_0 buildValue_() {
				return this.subject.getCollectionTable();
			}
		};
	}
	
	protected PropertyValueModel<Column> buildValueColumnHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, Column>(getSubjectHolder()) {
			@Override
			protected Column buildValue_() {
				return this.subject.getValueColumn();
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildNoConverterHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getConverter().getType() == null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(null);
				}
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildLobConverterHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getType() == LobConverter.class);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(LobConverter.class);
				}
			}
		};
	}
	
	private PropertyValueModel<Converter> buildConverterHolder() {
		return new PropertyAspectAdapter<T, Converter>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}
	
	private PropertyValueModel<BaseTemporalConverter> buildTemporalConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, BaseTemporalConverter>(converterHolder) {
			@Override
			protected BaseTemporalConverter transform_(Converter converter) {
				return converter.getType() == BaseTemporalConverter.class ? (BaseTemporalConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<BaseEnumeratedConverter> buildEnumeratedConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, BaseEnumeratedConverter>(converterHolder) {
			@Override
			protected BaseEnumeratedConverter transform_(Converter converter) {
				return converter.getType() == BaseEnumeratedConverter.class ? (BaseEnumeratedConverter) converter : null;
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildTemporalBooleanHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getType() == BaseTemporalConverter.class);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(BaseTemporalConverter.class);
				}
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildEnumeratedBooleanHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getType() == BaseEnumeratedConverter.class);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(BaseEnumeratedConverter.class);
				}
			}
		};
	}
	protected Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}

}