/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.caching;

import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.caching.CacheDefaultsComposite;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.caching.DefaultCacheSizeComposite;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.caching.DefaultCacheTypeComposite;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  CacheDefaults2_0Composite
 */
public class CacheDefaults2_0Composite extends CacheDefaultsComposite<Caching>
{
	public CacheDefaults2_0Composite(Pane<Caching> subjectHolder,
        Composite container) {

		super(subjectHolder, container);
	}
		
	@Override
	protected void initializeLayout(Composite parent) {
		this.initializeCacheDefaultsComposites(parent);
		this.installPaneEnabler();
	}

	@Override
	protected void initializeCacheDefaultsComposites(Composite parent) {

		// Default Cache Type
		new DefaultCacheTypeComposite(this, parent);

		// Default Cache Size
		new DefaultCacheSizeComposite<Caching>(this, parent);

	}

	// ********** private methods **********

	private void installPaneEnabler() {
		new PaneEnabler(this.buildPaneEnablerHolder(), this) {
			@Override
			protected void updateState(boolean enable) {
				super.updateState(enable);
				if( ! enable) {
					removeDefaultCacheTypeProperty();
					removeDefaultCacheSizeProperty();
				}
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildPaneEnablerHolder() {
		return new TransformationPropertyValueModel<SharedCacheMode, Boolean>(this.buildSharedCacheModeHolder()) {
			@Override
			protected Boolean transform(SharedCacheMode value) {
				return value != SharedCacheMode.NONE;
			}
		};
	}

	private PropertyValueModel<SharedCacheMode> buildSharedCacheModeHolder() {
		return new PropertyAspectAdapter<PersistenceUnit2_0, SharedCacheMode>(
								this.buildPersistenceUnit2_0Holder(), 
								PersistenceUnit2_0.SPECIFIED_SHARED_CACHE_MODE_PROPERTY, 
								PersistenceUnit2_0.DEFAULT_SHARED_CACHE_MODE_PROPERTY) {
			@Override
			protected SharedCacheMode buildValue_() {
				return this.subject.getSharedCacheMode();
			}
		};
	}

	private PropertyValueModel<PersistenceUnit2_0> buildPersistenceUnit2_0Holder() {
		return new PropertyAspectAdapter<Caching, PersistenceUnit2_0>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit2_0 buildValue_() {
				return (PersistenceUnit2_0) this.subject.getPersistenceUnit();
			}
		};
	}
	
	private void removeDefaultCacheTypeProperty() {
		this.getSubject().setCacheTypeDefault(null);
	}
	
	private void removeDefaultCacheSizeProperty() {
		this.getSubject().setCacheSizeDefault(null);
	}
}
