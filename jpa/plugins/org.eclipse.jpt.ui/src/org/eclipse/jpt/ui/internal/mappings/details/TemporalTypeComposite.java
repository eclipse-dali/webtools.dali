/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |           --------------------------------------------------------------- |
 * | Temporal: |                                                           |v| |
 * |           --------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see ColumnMapping
 * @see TemporalType
 * @see BasicMappingComposite - A container of this pane
 * @see IdMappingComposite - A container of this pane
 * @see VersionMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class TemporalTypeComposite extends FormPane<TemporalConverter> {

	/**
	 * Creates a new <code>TemporalTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TemporalTypeComposite(PropertyValueModel<? extends TemporalConverter> subjectHolder,
	                             Composite parent,
	                             WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {

		CCombo combo = addCCombo(
			container,
			buildTemporalTypeListHolder(),
			buildTemporalTypeHolder(),
			buildTemporalTypeConverter()
		);
		
		new ControlEnabler(buildBooleanHolder(), combo);
	}

	private WritablePropertyValueModel<TemporalType> buildTemporalTypeHolder() {
		return new PropertyAspectAdapter<TemporalConverter, TemporalType>(getSubjectHolder(), TemporalConverter.TEMPORAL_TYPE_PROPERTY) {
			@Override
			protected TemporalType buildValue_() {
				return subject.getTemporalType();
			}

			@Override
			protected void setValue_(TemporalType value) {
				subject.setTemporalType(value);
			}
		};
	}

	private ListValueModel<TemporalType> buildTemporalTypeListHolder() {
		return new SimpleListValueModel<TemporalType>(
			buildSortedTemporalTypeList()
		);
	}
	
	private List<TemporalType> buildSortedTemporalTypeList() {
		List<TemporalType> types = CollectionTools.list(TemporalType.values());
		Collections.sort(types, buildTemporalTypeComparator());
		return types;
	}

	private Comparator<TemporalType> buildTemporalTypeComparator() {
		return new Comparator<TemporalType>() {
			public int compare(TemporalType type1, TemporalType type2) {
				String displayString1 = displayString(type1);
				String displayString2 = displayString(type2);
				return Collator.getInstance().compare(displayString1, displayString2);
			}
		};
	}

	private StringConverter<TemporalType> buildTemporalTypeConverter() {
		return new StringConverter<TemporalType>() {
			public String convertToString(TemporalType value) {
				if (value == null) {
					return null;
				}
				return displayString(value);
			}
		};
	}

	private String displayString(TemporalType temporalType) {
		return SWTUtil.buildDisplayString(
			JptUiMappingsMessages.class,
			TemporalTypeComposite.this,
			temporalType.name()
		);
	}
	
	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<TemporalConverter, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(TemporalConverter value) {
				if (getSubject() != null && getSubject().getParent().getPersistentAttribute().isVirtual()) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(value != null);
			}
		};
	}

}