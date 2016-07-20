/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Comparator;
import java.util.List;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import com.ibm.icu.text.Collator;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |           --------------------------------------------------------------- |
 * | Temporal: |                                                           |v| |
 * |           --------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 1.0
 */
public class TemporalTypeCombo extends Pane<BaseTemporalConverter> {

	private Control combo;


	public TemporalTypeCombo(Pane<?> parentPane, PropertyValueModel<? extends BaseTemporalConverter> subjectModel, Composite parentComposite) {
		super(parentPane, subjectModel, parentComposite);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.combo;
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.combo = this.addCombo(
			container,
			buildTemporalTypeListHolder(),
			buildTemporalTypeHolder(),
			buildTemporalTypeConverter(),
			this.buildSubjectIsNotNullModel()
		);
	}

	private ModifiablePropertyValueModel<TemporalType> buildTemporalTypeHolder() {
		return new PropertyAspectAdapterXXXX<BaseTemporalConverter, TemporalType>(getSubjectHolder(), BaseTemporalConverter.TEMPORAL_TYPE_PROPERTY) {
			@Override
			protected TemporalType buildValue_() {
				return this.subject.getTemporalType();
			}

			@Override
			protected void setValue_(TemporalType value) {
				this.subject.setTemporalType(value);
			}
		};
	}

	private ListValueModel<TemporalType> buildTemporalTypeListHolder() {
		return new SimpleListValueModel<TemporalType>(
			buildSortedTemporalTypeList()
		);
	}

	private List<TemporalType> buildSortedTemporalTypeList() {
		return ListTools.sort(ListTools.arrayList(TemporalType.values()), this.buildTemporalTypeComparator());
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

	private Transformer<TemporalType, String> buildTemporalTypeConverter() {
		return new TemporalTypeLabelTransformer();
	}

	class TemporalTypeLabelTransformer
		extends TransformerAdapter<TemporalType, String>
	{
		@Override
		public String transform(TemporalType temporalType) {
			return (temporalType == null) ? null : displayString(temporalType);
		}
	}

	String displayString(TemporalType temporalType) {
		switch (temporalType) {
			case DATE :
				return JptJpaUiDetailsMessages.TEMPORAL_TYPE_COMPOSITE_DATE;
			case TIME :
				return JptJpaUiDetailsMessages.TEMPORAL_TYPE_COMPOSITE_TIME;
			case TIMESTAMP :
				return JptJpaUiDetailsMessages.TEMPORAL_TYPE_COMPOSITE_TIMESTAMP;
			default :
				throw new IllegalStateException();
		}
	}
}
