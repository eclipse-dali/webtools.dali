/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
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
 * @see EclipseLinkCachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkCacheTypeComposite extends Pane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkCacheTypeComposite(Pane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<EclipseLinkCaching, EclipseLinkCacheType> addCacheTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkCaching, EclipseLinkCacheType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkCacheType[] getChoices() {
				return EclipseLinkCacheType.values();
			}

			@Override
			protected EclipseLinkCacheType getDefaultValue() {
				return getSubject().getDefaultType();
			}

			@Override
			protected String displayString(EclipseLinkCacheType value) {
				return buildDisplayString(
					EclipseLinkUiDetailsMessages.class,
					EclipseLinkCacheTypeComposite.this,
					value
				);
			}

			@Override
			protected EclipseLinkCacheType getValue() {
				return getSubject().getSpecifiedType();
			}

			@Override
			protected void setValue(EclipseLinkCacheType value) {
				getSubject().setSpecifiedType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		addLabeledComposite(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkCacheTypeComposite_label,
			addCacheTypeCombo(container),
			EclipseLinkHelpContextIds.CACHING_CACHE_TYPE
		);
	}
}
