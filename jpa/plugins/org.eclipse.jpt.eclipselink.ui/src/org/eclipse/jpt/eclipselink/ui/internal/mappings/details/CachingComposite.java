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

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This pane shows the caching options.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | x Shared                                                                  |
 * |    CacheTypeComposite                                                     |
 * |    AlwaysRefreshComposite                                                 |
 * |    RefreshOnlyIfNewerComposite                                            |
 * |    DisableHitsComposite                                                   |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see EclipseLinkCaching
 * @see EclipseLinkJavaEntityComposite - The parent container
 * @see CacheTypeComposite
 * @see AlwaysRefreshComposite
 * @see RefreshOnlyIfNewerComposite
 * @see DisableHitsComposite
 *
 * @version 2.1
 * @since 2.1
 */
public class CachingComposite extends FormPane<EclipseLinkCaching>
{

	public CachingComposite(FormPane<?> parentPane,
        PropertyValueModel<EclipseLinkCaching> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		//Shared Check box, uncheck this and the rest of the panel is disabled
		addTriStateCheckBoxWithDefault(
			addSubPane(container, 8),
			EclipseLinkUiMappingsMessages.CachingComposite_sharedLabel,
			buildSharedHolder(),
			buildSharedStringHolder(),
			EclipseLinkHelpContextIds.CACHING_SHARED
		);

		Composite subPane = addSubPane(container, 0, 16);

		Collection<Pane<?>> panes = new ArrayList<Pane<?>>();
		
		panes.add(new CacheTypeComposite(this, subPane));
		panes.add(new CacheSizeComposite(this, subPane));
		
		// Advanced sub-pane
		Composite advancedSection = addCollapsableSubSection(
			subPane,
			EclipseLinkUiMappingsMessages.CachingComposite_advanced,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);

		initializeAdvancedPane(addSubPane(advancedSection, 0, 16), panes);
			
		new PaneEnabler(buildSharedCacheEnabler(), panes);
			
		new ExistenceCheckingComposite(this, addSubPane(container, 8));
	}
	
	private void initializeAdvancedPane(Composite container, Collection<Pane<?>> panes) {
		panes.add(new AlwaysRefreshComposite(this, container));
		panes.add(new RefreshOnlyIfNewerComposite(this, container));
		panes.add(new DisableHitsComposite(this, container));
		panes.add(new CacheCoordinationTypeComposite(this, container));
	}
	
	private PropertyValueModel<Boolean> buildSharedCacheEnabler() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getShared();
			}
		};
	}	
	private WritablePropertyValueModel<Boolean> buildSharedHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedShared();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedShared(value);
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

	private PropertyValueModel<String> buildSharedStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildSharedHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((getSubject() != null) && (value == null)) {

					Boolean defaultValue = getSubject().getDefaultShared();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							EclipseLinkUiMappingsMessages.CachingComposite_sharedLabelDefault,
							defaultStringValue
						);
					}
				}

				return EclipseLinkUiMappingsMessages.CachingComposite_sharedLabel;
			}
		};
	}

}