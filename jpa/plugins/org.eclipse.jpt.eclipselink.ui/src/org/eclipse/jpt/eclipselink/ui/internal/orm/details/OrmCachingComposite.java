/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.CachingComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
 * @see EclipseLinkJavaEntityComposite - The parent container
 * @see CacheTypeComposite
 * @see CacheSizeComposite
 * @see AlwaysRefreshComposite
 * @see RefreshOnlyIfNewerComposite
 * @see DisableHitsComposite
 *
 * @version 2.1
 * @since 2.1
 */
public class OrmCachingComposite extends CachingComposite<EclipseLinkCaching>
{

	public OrmCachingComposite(FormPane<?> parentPane,
        PropertyValueModel<EclipseLinkCaching> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeExistenceCheckingComposite(Composite parent) {
		new OrmExistenceCheckingComposite(this, parent);
	}
}