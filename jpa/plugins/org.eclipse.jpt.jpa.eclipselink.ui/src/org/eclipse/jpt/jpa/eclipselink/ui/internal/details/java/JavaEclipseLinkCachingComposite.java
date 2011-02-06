/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkAlwaysRefreshComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheSizeComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheTypeComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCachingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkDisableHitsComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkRefreshOnlyIfNewerComposite;
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
 * @see EclipseLinkCaching2_0
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
public class JavaEclipseLinkCachingComposite extends EclipseLinkCachingComposite<JavaEclipseLinkCaching>
{

	public JavaEclipseLinkCachingComposite(Pane<?> parentPane,
        PropertyValueModel<JavaEclipseLinkCaching> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeExistenceCheckingComposite(Composite parent) {
		new JavaEclipseLinkExistenceCheckingComposite(this, parent);
	}
}