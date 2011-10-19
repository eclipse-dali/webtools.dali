/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import java.util.ArrayList;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
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
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Converter: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkConvert
 * @see EclipseLinkBasicMappingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */

//Removes the Define Converters section from orm basic, id, version mapping.
//This is supported in EclipseLink in version 1.1, but not 1.0
public class OrmEclipseLinkConvert1_0Composite extends Pane<EclipseLinkConvert>
{

	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to <code>null</code>. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * <code>null</code> when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?"; //$NON-NLS-1$
	
	
	public OrmEclipseLinkConvert1_0Composite(PropertyValueModel<? extends EclipseLinkConvert> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Combo combo = addLabeledEditableCombo(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_converterNameLabel,
			buildConvertNameListHolder(),
			buildConvertNameHolder(),
			buildNameConverter(),
			null
		);
		SWTUtil.attachDefaultValueHandler(combo);
				
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected final WritablePropertyValueModel<String> buildConvertNameHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, String>(getSubjectHolder(), EclipseLinkConvert.SPECIFIED_CONVERTER_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getSpecifiedConverterName();
			}

			@Override
			protected void setValue_(String value) {
				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
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

	private WritablePropertyValueModel<String> buildDefaultNameHolder() {
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
						value = JptCommonUiMessages.DefaultEmpty;
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
				return (EclipseLinkPersistenceUnit) getSubject().getPersistenceUnit();
			}
		};
	}

	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConvert, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(EclipseLinkConvert value) {
				if (getSubject() != null && getSubject().getParent().getPersistentAttribute().isVirtual()) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(value != null);
			}
		};
	}
}