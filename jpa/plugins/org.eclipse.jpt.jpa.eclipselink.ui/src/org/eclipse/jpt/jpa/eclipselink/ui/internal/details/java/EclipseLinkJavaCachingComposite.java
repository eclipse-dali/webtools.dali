/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheSizeCombo;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCacheTypeComboViewer;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkCachingComposite;
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
 * @see EclipseLinkJavaEntityComposite - The parent container
 * @see EclipseLinkCacheTypeComboViewer
 * @see EclipseLinkCacheSizeCombo
 * @see EclipseLinkAlwaysRefreshComposite
 * @see EclipseLinkRefreshOnlyIfNewerComposite
 * @see EclipseLinkDisableHitsComposite
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkJavaCachingComposite extends EclipseLinkCachingComposite<EclipseLinkJavaCaching>
{

	public EclipseLinkJavaCachingComposite(Pane<?> parentPane,
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
		return new PropertyAspectAdapter<EclipseLinkJavaCaching, Boolean>(getSubjectHolder(), EclipseLinkJavaCaching.EXISTENCE_CHECKING_PROPERTY) {
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
