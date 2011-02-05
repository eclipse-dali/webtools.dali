/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Entity;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * CacheTypeComposite
 */
public class CacheTypeComposite extends Pane<Entity>
{
	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public CacheTypeComposite(Pane<Entity> parentComposite,
	                          Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_cacheTypeLabel,
			new CacheTypeCombo(container),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);
	}

	private class CacheTypeCombo extends EnumFormComboViewer<Entity, CacheType> {

		private CacheTypeCombo(Composite parent) {
			super(CacheTypeComposite.this, parent);
		}

		@Override
		protected void addPropertyNames(Collection<String> propertyNames) {
			super.addPropertyNames(propertyNames);
			propertyNames.add(Entity.CACHE_TYPE_PROPERTY);
		}

		private PropertyValueModel<Caching> buildCachingHolder() {
			return new TransformationPropertyValueModel<Entity, Caching>(getSubjectHolder()) {
				@Override
				protected Caching transform_(Entity value) {
					return value.getParent();
				}
			};
		}

		private PropertyValueModel<CacheType> buildDefaultCacheTypeHolder() {
			return new PropertyAspectAdapter<Caching, CacheType>(buildCachingHolder(), Caching.CACHE_TYPE_DEFAULT_PROPERTY) {
				@Override
				protected CacheType buildValue_() {
					CacheType cacheType = subject.getCacheTypeDefault();
					if (cacheType == null) {
						cacheType = subject.getDefaultCacheTypeDefault();
					}
					return cacheType;
				}
			};
		}

		private PropertyChangeListener buildDefaultCachingTypePropertyChangeListener() {
			return new SWTPropertyChangeListenerWrapper(
				buildDefaultCachingTypePropertyChangeListener_()
			);
		}

		private PropertyChangeListener buildDefaultCachingTypePropertyChangeListener_() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent e) {
					if ((e.getNewValue() != null) && !getCombo().isDisposed()) {
						CacheTypeCombo.this.doPopulate();
					}
				}
			};
		}

		@Override
		protected CacheType[] getChoices() {
			return CacheType.values();
		}

		@Override
		protected CacheType getDefaultValue() {
			return getSubjectParent().getDefaultCacheType();
		}

		@Override
		protected String displayString(CacheType value) {
			return buildDisplayString(
				EclipseLinkUiMessages.class,
				CacheTypeComposite.this,
				value
			);
		}

		@Override
		protected void doPopulate() {
			// This is required to allow the class loader to let the listener
			// written above to access this method
			super.doPopulate();
		}

		@Override
		protected CacheType getValue() {
			return getSubjectParent().getCacheTypeOf(getSubjectName());
		}

		@Override
		protected void initialize() {
			super.initialize();

			PropertyValueModel<CacheType> defaultCacheTypeHolder =
				buildDefaultCacheTypeHolder();

			defaultCacheTypeHolder.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				buildDefaultCachingTypePropertyChangeListener()
			);
		}

		@Override
		protected void setValue(CacheType value) {
			getSubjectParent().setCacheTypeOf(getSubjectName(), value);
		}

		@Override
		protected boolean sortChoices() {
			return false;
		}
	}
	
	private String getSubjectName() {
		return this.getSubjectHolder().getValue().getName();
	}

	private Caching getSubjectParent() {
		return this.getSubjectHolder().getValue().getParent();
	}
}