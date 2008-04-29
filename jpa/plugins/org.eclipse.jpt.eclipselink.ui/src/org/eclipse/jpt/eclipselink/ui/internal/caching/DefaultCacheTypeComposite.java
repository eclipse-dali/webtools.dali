/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.caching;

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * DefaultCacheTypeComposite
 */
public class DefaultCacheTypeComposite extends AbstractFormPane<Caching>
{
	/**
	 * Creates a new <code>DefaultCacheTypeComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public DefaultCacheTypeComposite(
					AbstractFormPane<? extends Caching> parentComposite, 
					Composite parent) {

		super( parentComposite, parent);
	}

	private EnumFormComboViewer<Caching, CacheType> buildDefaultCacheTypeCombo(Composite container) {
		return new EnumFormComboViewer<Caching, CacheType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Caching.CACHE_TYPE_DEFAULT_PROPERTY);
			}

			@Override
			protected CacheType[] choices() {
				return CacheType.values();
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected CacheType defaultValue() {
				return subject().getDefaultCacheTypeDefault();
			}

			@Override
			protected String displayString(CacheType value) {
				return buildDisplayString(EclipseLinkUiMessages.class, DefaultCacheTypeComposite.this, value);
			}

			@Override
			protected CacheType getValue() {
				return subject().getCacheTypeDefault();
			}

			@Override
			protected void setValue(CacheType value) {
				subject().setCacheTypeDefault(value);
			}
		};
	}
	
	@Override
	protected void initializeLayout( Composite container) {

		this.buildLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultCacheTypeLabel,
			this.buildDefaultCacheTypeCombo( container),
			null		// TODO IJpaHelpContextIds.
		);
	}
}
