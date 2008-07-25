/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.context.CacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------
 * |       ------------------------------------------------------------------ |
 * | Type: |                                                              |v| |
 * |       ------------------------------------------------------------------ |
 * ----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkCaching
 * @see CachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class CacheTypeComposite extends AbstractFormPane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CacheTypeComposite(AbstractFormPane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<EclipseLinkCaching, CacheType> buildCacheTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkCaching, CacheType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_CACHE_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_CACHE_TYPE_PROPERTY);
			}

			@Override
			protected CacheType[] choices() {
				return CacheType.values();
			}

			@Override
			protected CacheType defaultValue() {
				return subject().getDefaultCacheType();
			}

			@Override
			protected String displayString(CacheType value) {
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					CacheTypeComposite.this,
					value
				);
			}

			@Override
			protected CacheType getValue() {
				return subject().getSpecifiedCacheType();
			}

			@Override
			protected void setValue(CacheType value) {
				subject().setSpecifiedCacheType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		buildLabeledComposite(
			container,
			EclipseLinkUiMappingsMessages.CacheTypeComposite_label,
			buildCacheTypeCombo(container),
			EclipseLinkHelpContextIds.CACHING_CACHE_TYPE
		);
	}
}
