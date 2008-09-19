/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticListValueModel;
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
 * |            -------------------------------------------------------------- |
 * | Enum Type: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkConvert
 * @see EclipseLinkBasicMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class ConvertComposite extends FormPane<EclipseLinkConvert>
{
	private CCombo combo;

	/**
	 * Creates a new <code>EnumTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ConvertComposite(PropertyValueModel<? extends EclipseLinkConvert> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.combo = addLabeledEditableCCombo(
			container,
			EclipseLinkUiMappingsMessages.ConvertComposite_convertNameLabel,
			buildConvertNameListHolder(),
			buildConvertNameHolder(),
			null
		);

		
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected final WritablePropertyValueModel<String> buildConvertNameHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, String>(getSubjectHolder(), EclipseLinkConvert.SPECIFIED_CONVERTER_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getSpecifiedConverterName();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setSpecifiedConverterName(value);
			}
		};
	}

	//TODO converter name repository, have another ListValueModel for these
	protected ListValueModel<String> buildConvertNameListHolder() {
		return new StaticListValueModel<String>(CollectionTools.list(EclipseLinkConvert.RESERVED_CONVERTER_NAMES));
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
