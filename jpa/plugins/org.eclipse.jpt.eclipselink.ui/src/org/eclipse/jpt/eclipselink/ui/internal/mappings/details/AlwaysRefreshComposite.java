/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
 * This composite simply shows a tri-state check box for the Always Refresh option.
 * 
 * @see EclipseLinkCaching
 * @see CachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class AlwaysRefreshComposite extends FormPane<Caching>
{
	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public AlwaysRefreshComposite(FormPane<? extends Caching> parentPane,
	                         Composite parent)
	{
		super(parentPane, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMappingsMessages.AlwaysRefreshComposite_alwaysRefreshLabel,
			buildAlwaysRefreshHolder(),
			buildAlwaysRefreshStringHolder(),
			EclipseLinkHelpContextIds.CACHING_ALWAYS_REFRESH
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildAlwaysRefreshHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(getSubjectHolder(), Caching.SPECIFIED_ALWAYS_REFRESH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedAlwaysRefresh();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedAlwaysRefresh(value);
			}
		};
	}

	private PropertyValueModel<String> buildAlwaysRefreshStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultAlwaysRefreshHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiMappingsMessages.Boolean_True : JptUiMappingsMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMappingsMessages.AlwaysRefreshComposite_alwaysRefreshDefault, defaultStringValue);
				}
				return EclipseLinkUiMappingsMessages.AlwaysRefreshComposite_alwaysRefreshLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultAlwaysRefreshHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(
			getSubjectHolder(),
			Caching.SPECIFIED_ALWAYS_REFRESH_PROPERTY,
			Caching.DEFAULT_ALWAYS_REFRESH_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedAlwaysRefresh() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultAlwaysRefresh());
			}
		};
	}
}