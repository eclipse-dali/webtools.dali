/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.EnumTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.TemporalTypeCombo;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.PageBook;

public abstract class AbstractElementCollectionMapping2_0Composite<T extends ElementCollectionMapping2_0> 
	extends Pane<T>
	implements JpaComposite
{
	private Control basicValueComposite;
	
	private Control embeddableValueComposite;
	
	protected AbstractElementCollectionMapping2_0Composite(
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
		initializeOrderingCollapsibleSection(container);
	}
	
	protected void initializeElementCollectionCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_SECTION_TITLE);
		section.setClient(this.buildElementCollectionSectionClient(section));
	}

	protected Control buildElementCollectionSectionClient(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Target class widgets
		Hyperlink hyperlink = this.addHyperlink(container, JptJpaUiDetailsMessages2_0.TARGET_CLASS_COMPOSITE_LABEL);
		new TargetClassChooser2_0(this, container, hyperlink);

		// Fetch type widgets
		this.addLabel(container, JptJpaUiDetailsMessages.BasicGeneralSection_fetchLabel);
		new FetchTypeComboViewer(this, container);

		// Collection table widgets
		CollectionTableComposite2_0 collectionTableComposite = new CollectionTableComposite2_0(this, buildCollectionTableModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		collectionTableComposite.getControl().setLayoutData(gridData);

		return container;
	}
	
	protected void initializeOrderingCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.OrderingComposite_orderingGroup);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(AbstractElementCollectionMapping2_0Composite.this.initializeOrderingSection(section));
				}
			}
		});
	}
	
	protected Control initializeOrderingSection(Composite container) {
		return new OrderingComposite2_0(this, container).getControl();
	}
	
	protected void initializeValueCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages2_0.ABSTRACT_ELEMENT_COLLECTION_MAPPING_COMPOSITE_VALUE_SECTION_TITLE);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(AbstractElementCollectionMapping2_0Composite.this.initializeValueSection(section));
				}
			}
		});
	}
	
	protected void initializeKeyCollapsibleSection(Composite container) {
		//nothing yet
	}

	protected Control initializeValueSection(Composite container) {
		PageBook pageBook = new PageBook(container, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		pageBook.setLayoutData(gd);
		
		installValueControlSwitcher(pageBook);
		return pageBook;
	}

	protected Control getBasicValueComposite(Composite container) {
		if (this.basicValueComposite == null) {
			this.basicValueComposite = buildBasicValueSection(container);
		}
		return this.basicValueComposite;
	}

	protected Control buildBasicValueSection(Composite container) {
		Composite basicComposite = addSubPane(container);

		new ColumnComposite(this, buildValueColumnModel(), basicComposite);

		// type section
		final Section section = this.getWidgetFactory().createSection(basicComposite, ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.TypeSection_type);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					Composite converterClient = buildBasicValueTypeSectionClient(section);
					converterClient.setLayoutData(new GridData(GridData.FILL_BOTH));
					section.setClient(converterClient);
				}
			}
		});

		return basicComposite;
	}

	protected Composite buildBasicValueTypeSectionClient(Section section) {
		Composite container = this.getWidgetFactory().createComposite(section);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TypeSection_default, 
			buildNoConverterHolder(), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;

		// Lob
		Button lobButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TypeSection_lob, 
			buildLobConverterHolder(), 
			null);
		((GridData) lobButton.getLayoutData()).horizontalSpan = 2;


		PropertyValueModel<Converter> converterHolder = buildConverterHolder();
		// Temporal
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TypeSection_temporal, 
			buildTemporalBooleanHolder(), 
			null);
		new TemporalTypeCombo(this, this.buildTemporalConverterHolder(converterHolder), container);


		// Enumerated
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TypeSection_enumerated, 
			buildEnumeratedBooleanHolder(), 
			null);
		new EnumTypeComboViewer(this, this.buildEnumeratedConverterHolder(converterHolder), container);

		return container;
	}

	protected Control getEmbeddableValueComposite(Composite container) {
		if (this.embeddableValueComposite == null) {
			this.embeddableValueComposite = buildEmbeddableValueSection(container);
		}
		return this.embeddableValueComposite;
	}

	protected Control buildEmbeddableValueSection(Composite container) {
		return new ElementCollectionValueOverridesComposite2_0(this, container).getControl();
	}

	private void installValueControlSwitcher(PageBook pageBook) {

		new ControlSwitcher(
			buildValueHolder(),
			buildPaneTransformer(pageBook),
			pageBook
		);
	}
	
	protected PropertyValueModel<T.Type> buildValueHolder() {
		return new PropertyAspectAdapter<T, T.Type>(
				this.getSubjectHolder(), CollectionMapping.VALUE_TYPE_PROPERTY) {
			@Override
			protected ElementCollectionMapping2_0.Type buildValue_() {
				return this.subject.getValueType();
			}
		};
	}

	private Transformer<T.Type, Control> buildPaneTransformer(Composite container) {
		return new PaneTransformer(container);
	}

	protected class PaneTransformer
		extends TransformerAdapter<T.Type, Control>
	{
		private final Composite container;

		protected PaneTransformer(Composite container) {
			this.container = container;
		}

		@Override
		public Control transform(T.Type type) {
			return AbstractElementCollectionMapping2_0Composite.this.transformValueType(type, this.container);
		}
	}

	/**
	 * Given the selected override, return the control that will be displayed
	 */
	protected Control transformValueType(T.Type type, Composite container) {
		if (type == null) {
			return null;
		}
		switch (type) {
			case BASIC_TYPE :
				return this.getBasicValueComposite(container);
			case EMBEDDABLE_TYPE :
				return this.getEmbeddableValueComposite(container);
			default :
				return null;
		}
	}
	
	protected PropertyValueModel<CollectionTable2_0> buildCollectionTableModel() {
		return new PropertyAspectAdapter<T, CollectionTable2_0>(getSubjectHolder()) {
			@Override
			protected CollectionTable2_0 buildValue_() {
				return this.subject.getCollectionTable();
			}
		};
	}
	
	protected PropertyValueModel<SpecifiedColumn> buildValueColumnModel() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, SpecifiedColumn>(getSubjectHolder()) {
			@Override
			protected SpecifiedColumn buildValue_() {
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

	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return new PropertyAspectAdapter<T, SpecifiedAccessReference>(getSubjectHolder()) {
			@Override
			protected SpecifiedAccessReference buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}