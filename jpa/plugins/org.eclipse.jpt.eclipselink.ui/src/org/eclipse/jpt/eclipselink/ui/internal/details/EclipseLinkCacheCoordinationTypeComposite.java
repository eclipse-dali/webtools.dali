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

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------
 * |                    ----------------------------------------------------- |
 * | Coordination Type: |                                                 |v| |
 * |                    ----------------------------------------------------- |
 * ----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkCaching
 * @see EclipseLinkCachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkCacheCoordinationTypeComposite extends Pane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkCacheCoordinationTypeComposite(Pane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<EclipseLinkCaching, EclipseLinkCacheCoordinationType> addCacheCoordinationTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkCaching, EclipseLinkCacheCoordinationType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_COORDINATION_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_COORDINATION_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkCacheCoordinationType[] getChoices() {
				return EclipseLinkCacheCoordinationType.values();
			}

			@Override
			protected EclipseLinkCacheCoordinationType getDefaultValue() {
				return getSubject().getDefaultCoordinationType();
			}

			@Override
			protected String displayString(EclipseLinkCacheCoordinationType value) {
				return buildDisplayString(
					EclipseLinkUiDetailsMessages.class,
					EclipseLinkCacheCoordinationTypeComposite.this,
					value
				);
			}

			@Override
			protected EclipseLinkCacheCoordinationType getValue() {
				return getSubject().getSpecifiedCoordinationType();
			}

			@Override
			protected void setValue(EclipseLinkCacheCoordinationType value) {
				getSubject().setSpecifiedCoordinationType(value);
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
			EclipseLinkUiDetailsMessages.EclipseLinkCacheCoordinationTypeComposite_label,
			addCacheCoordinationTypeCombo(container),
			EclipseLinkHelpContextIds.CACHING_CACHE_COORDINATION_TYPE
		);
	}
}
