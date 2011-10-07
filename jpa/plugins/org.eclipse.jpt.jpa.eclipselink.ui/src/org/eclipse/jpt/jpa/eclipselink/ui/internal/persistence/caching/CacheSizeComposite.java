/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CachingEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * CacheSizeComposite
 */
public class CacheSizeComposite extends Pane<CachingEntity>
{
	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public CacheSizeComposite(Pane<CachingEntity> parentComposite,
	                          Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addCacheSizeCombo(container);
	}	
	
	private void addCacheSizeCombo(Composite container) {
		new IntegerCombo<CachingEntity>(this, container) {
			
			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.CacheSizeComposite_cacheSize;
			}
		
			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
			}
			
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Caching, Integer>(buildCachingHolder(), Caching.CACHE_SIZE_DEFAULT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						Integer value = this.subject.getCacheSizeDefault();
						if (value == null) {
							value = this.subject.getDefaultCacheSizeDefault();
						}
						return value;
					}
				};
			}
		
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<CachingEntity, Integer>(this.getSubjectHolder(), CachingEntity.CACHE_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return getSubjectParent().getCacheSizeOf(getSubjectName());
					}

					@Override
					protected void setValue_(Integer value) {
						getSubjectParent().setCacheSizeOf(getSubjectName(), value);
					}
				};
			}
		};
	}
	
	private String getSubjectName() {
		return this.getSubjectHolder().getValue().getName();
	}
	
	private Caching getSubjectParent() {
		return this.getSubjectHolder().getValue().getParent();
	}
	
	private PropertyValueModel<Caching> buildCachingHolder() {
		return new TransformationPropertyValueModel<CachingEntity, Caching>(this.getSubjectHolder()) {
			@Override
			protected Caching transform_(CachingEntity value) {
				return value.getParent();
			}
		};
	}

}