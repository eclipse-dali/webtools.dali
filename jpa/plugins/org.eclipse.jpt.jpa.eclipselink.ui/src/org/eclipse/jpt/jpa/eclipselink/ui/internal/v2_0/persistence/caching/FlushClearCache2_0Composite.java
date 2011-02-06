/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.caching;

import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.FlushClearCacheComposite;
import org.eclipse.swt.widgets.Composite;

/**
 *  FlushClearCache2_0Composite
 */
public class FlushClearCache2_0Composite extends FlushClearCacheComposite
{
	/**
	 * Creates a new <code>FlushClearCache2_0Composite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public FlushClearCache2_0Composite(
				Pane<? extends Caching> parentComposite, 
				Composite parent) {
		
		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		super.initializeLayout(parent);

		this.installPaneEnabler();
	}

	// ********** private methods **********

	private void installPaneEnabler() {
		new PaneEnabler(this.buildPaneEnablerHolder(), this);
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
}
