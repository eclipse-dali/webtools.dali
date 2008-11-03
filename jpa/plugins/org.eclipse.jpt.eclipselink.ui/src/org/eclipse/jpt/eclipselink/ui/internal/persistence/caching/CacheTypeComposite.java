/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * CacheTypeComposite
 */
public class CacheTypeComposite extends Pane<EntityCacheProperties>
{
	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public CacheTypeComposite(Pane<EntityCacheProperties> parentComposite,
	                          Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_cacheTypeLabel,
			new CacheTypeCombo(container),
			null		// TODO IJpaHelpContextIds.
		);
	}

	private class CacheTypeCombo extends EnumFormComboViewer<EntityCacheProperties, CacheType> {

		private CacheTypeCombo(Composite parent) {
			super(CacheTypeComposite.this, parent);
		}

		@Override
		protected void addPropertyNames(Collection<String> propertyNames) {
			super.addPropertyNames(propertyNames);
			propertyNames.add(EntityCacheProperties.CACHE_TYPE_PROPERTY);
		}

		private PropertyValueModel<Caching> buildCachingHolder() {
			return new TransformationPropertyValueModel<EntityCacheProperties, Caching>(getSubjectHolder()) {
				@Override
				protected Caching transform_(EntityCacheProperties value) {
					return value.getCaching();
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
			return this.getSubject().getDefaultCacheType();
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
			return this.getSubject().getCacheType();
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
			this.getSubject().setCacheType(value);
		}

		@Override
		protected boolean sortChoices() {
			return false;
		}
	}
}