/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkAlwaysRefreshComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkCacheSizeComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkCacheTypeComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkDisableHitsComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkRefreshOnlyIfNewerComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.JavaEclipseLinkEntityComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.JavaEclipseLinkExistenceCheckingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.EclipseLinkCaching2_0Composite;
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
public class JavaEclipseLinkCaching2_0Composite extends EclipseLinkCaching2_0Composite<JavaEclipseLinkCaching>
{

	public JavaEclipseLinkCaching2_0Composite(Pane<?> parentPane,
        PropertyValueModel<JavaEclipseLinkCaching> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeExistenceCheckingComposite(Composite parent) {
		new JavaEclipseLinkExistenceCheckingComposite(this, parent);
	}
}