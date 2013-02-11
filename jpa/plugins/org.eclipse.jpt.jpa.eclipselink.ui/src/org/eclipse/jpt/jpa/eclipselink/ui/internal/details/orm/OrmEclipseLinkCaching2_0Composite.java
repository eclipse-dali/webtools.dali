/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheSizeCombo;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheTypeComboViewer;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCaching2_0Composite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.JavaEclipseLinkEntityComposite;
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
 * @see EclipseLinkCacheTypeComboViewer
 * @see EclipseLinkCacheSizeCombo
 * @see EclipseLinkAlwaysRefreshComposite
 * @see EclipseLinkRefreshOnlyIfNewerComposite
 * @see EclipseLinkDisableHitsComposite
 *
 * @version 3.0
 * @since 3.0
 */
public class OrmEclipseLinkCaching2_0Composite extends EclipseLinkCaching2_0Composite<EclipseLinkCaching>
{

	public OrmEclipseLinkCaching2_0Composite(Pane<?> parentPane,
        PropertyValueModel<EclipseLinkCaching> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeExistenceCheckingComposite(Composite container) {
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXISTENCE_CHECKING_COMPOSITE_LABEL);
		this.addExistenceCheckingTypeCombo(container);
	}
}