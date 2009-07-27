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

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
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
 * This composite simply shows a tri-state check box for the Refresh Only If Newer option.
 * 
 * @see EclipseLinkCaching
 * @see EclipseLinkCachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkRefreshOnlyIfNewerComposite extends FormPane<EclipseLinkCaching>
{
	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkRefreshOnlyIfNewerComposite(FormPane<? extends EclipseLinkCaching> parentPane,
	                         Composite parent)
	{
		super(parentPane, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkRefreshOnlyIfNewerComposite_refreshOnlyIfNewerLabel,
			buildRefreshOnlyIfNewerHolder(),
			buildRefreshOnlyIfNewerStringHolder(),
			EclipseLinkHelpContextIds.CACHING_REFRESH_ONLY_IF_NEWER
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildRefreshOnlyIfNewerHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedRefreshOnlyIfNewer();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedRefreshOnlyIfNewer(value);
			}
		};
	}

	private PropertyValueModel<String> buildRefreshOnlyIfNewerStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultRefreshOnlyIfNewerHolder()) {

			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiMappingsMessages.Boolean_True : JptUiMappingsMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMappingsMessages.EclipseLinkRefreshOnlyIfNewerComposite_refreshOnlyIfNewerDefault, defaultStringValue);
				}
				return EclipseLinkUiMappingsMessages.EclipseLinkRefreshOnlyIfNewerComposite_refreshOnlyIfNewerLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultRefreshOnlyIfNewerHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
			getSubjectHolder(),
			EclipseLinkCaching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY,
			EclipseLinkCaching.DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedRefreshOnlyIfNewer() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultRefreshOnlyIfNewer());
			}
		};
	}
}