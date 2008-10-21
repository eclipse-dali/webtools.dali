/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows a tri-state check box for the Disable Hits option.
 * 
 * @see EclipseLinkCaching
 * @see CachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class DisableHitsComposite extends FormPane<Caching>
{
	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public DisableHitsComposite(FormPane<? extends Caching> parentPane,
	                         Composite parent)
	{
		super(parentPane, parent);
	}

	private WritablePropertyValueModel<Boolean> buildDisableHitsHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(getSubjectHolder(), Caching.SPECIFIED_DISABLE_HITS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedDisableHits();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedDisableHits(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildDisableHitsStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDisableHitsHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((getSubject() != null) && (value == null)) {

					Boolean defaultValue = getSubject().isDefaultDisableHits();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							EclipseLinkUiMappingsMessages.DisableHitsComposite_disableHitsDefault,
							defaultStringValue
						);
					}
				}

				return EclipseLinkUiMappingsMessages.DisableHitsComposite_disableHitsLabel;
			}
		};
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMappingsMessages.DisableHitsComposite_disableHitsLabel,
			buildDisableHitsHolder(),
			buildDisableHitsStringHolder(),
			EclipseLinkHelpContextIds.CACHING_DISABLE_HITS
		);
	}
}