/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheSizeCombo;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheTypeComboViewer;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCachingComposite2_0;
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
 * @see EclipseLinkCacheTypeComboViewer
 * @see EclipseLinkCacheSizeCombo
 * @see EclipseLinkAlwaysRefreshComposite
 * @see EclipseLinkRefreshOnlyIfNewerComposite
 * @see EclipseLinkDisableHitsComposite
 *
 * @version 3.0
 * @since 3.0
 */
public class EclipseLinkJavaCachingComposite2_0 extends EclipseLinkCachingComposite2_0<EclipseLinkJavaCaching>
{

	public EclipseLinkJavaCachingComposite2_0(Pane<?> parentPane,
        PropertyValueModel<EclipseLinkJavaCaching> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeExistenceCheckingComposite(Composite container) {
		this.addCheckBox( 
                 container, 
                 JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXISTENCE_CHECKING_COMPOSITE_LABEL, 
                 buildExistenceCheckingHolder(), 
                 null);
		this.addExistenceCheckingTypeCombo(container);
	}

	private ModifiablePropertyValueModel<Boolean> buildExistenceCheckingHolder() {
		return new PropertyAspectAdapterXXXX<EclipseLinkJavaCaching, Boolean>(getSubjectHolder(), EclipseLinkJavaCaching.EXISTENCE_CHECKING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isExistenceChecking());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setExistenceChecking(value.booleanValue());
			}
		};
	}
}
