/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
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
 * |    CacheSizeComposite                                                     |
 * |    > Advanced   	                                                       |
 * |    	ExpiryComposite                                                    |
 * |    	AlwaysRefreshComposite                                             |
 * |   		RefreshOnlyIfNewerComposite                                        |
 * |    	DisableHitsComposite                                               |
 * |    	CacheCoordinationComposite                                         |
 * | ExistenceTypeComposite                                                    |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see EclipseLinkCaching
 * @see JavaEclipseLinkEntityComposite - The parent container
 * @see EclipseLinkCacheTypeComposite
 * @see EclipseLinkCacheSizeComposite
 * @see EclipseLinkAlwaysRefreshComposite
 * @see EclipseLinkRefreshOnlyIfNewerComposite
 * @see EclipseLinkDisableHitsComposite
 *
 * @version 2.1
 * @since 2.1
 */
public abstract class EclipseLinkCachingComposite<T extends EclipseLinkCaching> extends Pane<T>
{

	protected EclipseLinkCachingComposite(Pane<?> parentPane,
        PropertyValueModel<T> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		//Shared Check box, uncheck this and the rest of the panel is disabled
		addTriStateCheckBoxWithDefault(
			addSubPane(container, 8),
			EclipseLinkUiDetailsMessages.EclipseLinkCachingComposite_sharedLabel,
			buildSpecifiedSharedHolder(),
			buildSharedStringHolder(),
			EclipseLinkHelpContextIds.CACHING_SHARED
		);

		Composite subPane = addSubPane(container, 0, 16);

		Collection<Pane<?>> panes = new ArrayList<Pane<?>>();
		
		panes.add(new EclipseLinkCacheTypeComposite(this, subPane));
		panes.add(new EclipseLinkCacheSizeComposite(this, subPane));
		
		// Advanced sub-pane
		Composite advancedSection = addCollapsibleSubSection(
			subPane,
			EclipseLinkUiDetailsMessages.EclipseLinkCachingComposite_advanced,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);

		initializeAdvancedPane(addSubPane(advancedSection, 0, 16), panes);
			
		new PaneEnabler(buildSharedCacheEnabler(), panes);
		
		initializeExistenceCheckingComposite(addSubPane(container, 8));
	}
	
	protected void initializeAdvancedPane(Composite container, Collection<Pane<?>> panes) {
		panes.add(new EclipseLinkExpiryComposite(this, container));
		panes.add(new EclipseLinkAlwaysRefreshComposite(this, container));
		panes.add(new EclipseLinkRefreshOnlyIfNewerComposite(this, container));
		panes.add(new EclipseLinkDisableHitsComposite(this, container));
		panes.add(new EclipseLinkCacheCoordinationTypeComposite(this, container));
	}
	
	protected abstract void initializeExistenceCheckingComposite(Composite parent);
	
	private PropertyValueModel<Boolean> buildSharedCacheEnabler() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
				getSubjectHolder(), 
				EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY, 
				EclipseLinkCaching.DEFAULT_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isShared());
			}
		};
	}	
	
	private WritablePropertyValueModel<Boolean> buildSpecifiedSharedHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedShared();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedShared(value);
			}
		};
	}

	private PropertyValueModel<String> buildSharedStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultSharedHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiDetailsMessages.EclipseLinkCachingComposite_sharedLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiDetailsMessages.EclipseLinkCachingComposite_sharedLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultSharedHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
			getSubjectHolder(),
			EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY,
			EclipseLinkCaching.DEFAULT_SHARED_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedShared() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultShared());
			}
		};
	}
}