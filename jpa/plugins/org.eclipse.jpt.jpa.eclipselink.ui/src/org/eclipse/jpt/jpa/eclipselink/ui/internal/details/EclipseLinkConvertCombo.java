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

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.NonEmptyStringFilter;
import org.eclipse.jpt.common.utility.internal.StringConverter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Enum Type: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkConvert
 * @see EclipseLinkBasicMappingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkConvertCombo
	extends Pane<EclipseLinkConvert>
{

	private Combo combo;

	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to an empty string. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * an empty string when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?"; //$NON-NLS-1$


	/**
	 * Creates a new <code>EclipseLinkConvertComposite</code>.
	 */
	public EclipseLinkConvertCombo(PropertyValueModel<? extends EclipseLinkConvert> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, enabledModel, parent, widgetFactory);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Combo getControl() {
		return this.combo;
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.combo = this.addEditableCombo(
			container,
			buildConvertNameListHolder(),
			buildConvertNameHolder(),
			buildNameConverter(),
			(String) null
		);
		SWTUtil.attachDefaultValueHandler(this.combo);
	}

	protected final ModifiablePropertyValueModel<String> buildConvertNameHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, String>(getSubjectHolder(), EclipseLinkConvert.SPECIFIED_CONVERTER_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getSpecifiedConverterName();
			}

			@Override
			protected void setValue_(String value) {
				// Convert the default value to the default converter
				if ((value != null) && (value.startsWith(DEFAULT_KEY))) {
					value = EclipseLinkConvert.DEFAULT_CONVERTER_NAME;
				}
				this.subject.setSpecifiedConverterName(value);
			}
		};
	}

	private ListValueModel<String> buildConvertNameListHolder() {
		java.util.List<ListValueModel<String>> list = new ArrayList<ListValueModel<String>>();
		list.add(buildDefaultNameListHolder());
		list.add(buildReservedConverterNameListHolder());
		list.add(buildSortedConverterNamesModel());
		return new CompositeListValueModel<ListValueModel<String>, String>(list);
	}

	protected ListValueModel<String> buildDefaultNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultNameHolder()
		);
	}

	private ModifiablePropertyValueModel<String> buildDefaultNameHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, String>(getSubjectHolder(), EclipseLinkConvert.DEFAULT_CONVERTER_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				String name = this.subject.getDefaultConverterName();

				if (name == null) {
					name = DEFAULT_KEY;
				}
				else {
					name = DEFAULT_KEY + name;
				}

				return name;
			}
		};
	}

	private StringConverter<String> buildNameConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {

				if (getSubject() == null) {
					return value;
				}

				if (value == null) {
					value = getSubject().getDefaultConverterName();

					if (value != null) {
						value = DEFAULT_KEY + value;
					}
					else {
						value = DEFAULT_KEY;
					}
				}

				if (value.startsWith(DEFAULT_KEY)) {
					String defaultName = value.substring(DEFAULT_KEY.length());

					if (defaultName.length() > 0) {
						value = NLS.bind(
							JptCommonUiMessages.DefaultWithOneParam,
							defaultName
						);
					}
					else {
						value = NLS.bind(
							JptCommonUiMessages.DefaultWithOneParam,
							EclipseLinkConvert.NO_CONVERTER
						);
					}
				}

				return value;
			}
		};
	}

	protected ListValueModel<String> buildReservedConverterNameListHolder() {
		return new StaticListValueModel<String>(EclipseLinkConvert.RESERVED_CONVERTER_NAMES);
	}

	protected ListValueModel<String> buildSortedConverterNamesModel() {
		return new SortedListValueModelAdapter<String>(this.buildUniqueConverterNamesModel());
	}

	protected CollectionValueModel<String> buildUniqueConverterNamesModel() {
		return new SetCollectionValueModel<String>(this.buildConverterNamesModel());
	}

	protected CollectionValueModel<String> buildConverterNamesModel() {
		return new FilteringCollectionValueModel<String>(this.buildConverterNamesModel_(), NonEmptyStringFilter.instance());
	}

	protected ListValueModel<String> buildConverterNamesModel_() {
		return new TransformationListValueModel<EclipseLinkConverter, String>(this.buildConvertersModel()) {
			@Override
			protected String transformItem_(EclipseLinkConverter converter) {
				return converter.getName();
			}
		};
	}

	protected ListValueModel<EclipseLinkConverter> buildConvertersModel() {
		return new ItemPropertyListValueModelAdapter<EclipseLinkConverter>(this.buildConvertersModel_(), JpaNamedContextNode.NAME_PROPERTY);
	}

	protected CollectionValueModel<EclipseLinkConverter> buildConvertersModel_() {
		return new CollectionAspectAdapter<EclipseLinkPersistenceUnit, EclipseLinkConverter>(this.buildPersistenceUnitHolder(), EclipseLinkPersistenceUnit.CONVERTERS_COLLECTION) {
			@Override
			protected Iterable<EclipseLinkConverter> getIterable() {
				return this.subject.getConverters();
			}
			@Override
			protected int size_() {
				return this.subject.getConvertersSize();
			}
		};
	}

	protected PropertyValueModel<EclipseLinkPersistenceUnit> buildPersistenceUnitHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, EclipseLinkPersistenceUnit>(getSubjectHolder()) {
			@Override
			protected EclipseLinkPersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
	}
}
