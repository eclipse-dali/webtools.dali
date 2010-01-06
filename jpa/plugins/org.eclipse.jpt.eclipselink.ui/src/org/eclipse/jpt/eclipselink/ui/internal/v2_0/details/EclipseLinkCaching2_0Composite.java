/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkAlwaysRefreshComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkCacheCoordinationTypeComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkCacheSizeComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkCacheTypeComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkDisableHitsComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkExpiryComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkRefreshOnlyIfNewerComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.jpa2.details.JptUiDetailsMessages2_0;
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
 * @version 3.0
 * @since 3.0
 */
public abstract class EclipseLinkCaching2_0Composite<T extends EclipseLinkCaching> extends Pane<T>
{
	
	protected EclipseLinkCaching2_0Composite(Pane<?> parentPane,
        PropertyValueModel<T> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Cacheable2_0> cacheableHolder = buildCacheableHolder();
		
		//Shared Check box, uncheck this and the rest of the panel is disabled
		addTriStateCheckBoxWithDefault(
			addSubPane(container, 8),
			JptUiDetailsMessages2_0.Entity_cacheableLabel,
			buildSpecifiedCacheableHolder(cacheableHolder),
			buildCacheableStringHolder(cacheableHolder),
			JpaHelpContextIds.ENTITY_CACHEABLE
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
			
		new PaneEnabler(buildCacheableEnabler(cacheableHolder), panes);
		
		initializeExistenceCheckingComposite(addSubPane(container, 8));
	}
	
	protected PropertyValueModel<Cacheable2_0> buildCacheableHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Cacheable2_0>(getSubjectHolder()) {
			@Override
			protected Cacheable2_0 buildValue_() {
				return ((CacheableHolder2_0) this.subject).getCacheable();
			}
		};
	}

	protected void initializeAdvancedPane(Composite container, Collection<Pane<?>> panes) {
		panes.add(new EclipseLinkExpiryComposite(this, container));
		panes.add(new EclipseLinkAlwaysRefreshComposite(this, container));
		panes.add(new EclipseLinkRefreshOnlyIfNewerComposite(this, container));
		panes.add(new EclipseLinkDisableHitsComposite(this, container));
		panes.add(new EclipseLinkCacheCoordinationTypeComposite(this, container));
	}
	
	protected abstract void initializeExistenceCheckingComposite(Composite parent);
	
	private PropertyValueModel<Boolean> buildCacheableEnabler(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(
				cacheableHolder,
				Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY, 
				Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isCacheable());
			}
		};
	}	
	
	private WritablePropertyValueModel<Boolean> buildSpecifiedCacheableHolder(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(cacheableHolder, Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedCacheable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedCacheable(value);
			}
		};
	}

	private PropertyValueModel<String> buildCacheableStringHolder(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultCacheableHolder(cacheableHolder)) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages2_0.Entity_cacheableWithDefaultLabel, defaultStringValue);
				}
				return JptUiDetailsMessages2_0.Entity_cacheableLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultCacheableHolder(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(
			cacheableHolder,
			Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY,
			Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedCacheable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultCacheable());
			}
		};
	}
}