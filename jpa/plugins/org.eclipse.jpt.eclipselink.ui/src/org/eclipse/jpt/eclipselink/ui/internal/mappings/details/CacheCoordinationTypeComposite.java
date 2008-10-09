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
import org.eclipse.jpt.eclipselink.core.context.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
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
 * @see CachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class CacheCoordinationTypeComposite extends FormPane<Caching> {

	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CacheCoordinationTypeComposite(FormPane<? extends Caching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<Caching, CacheCoordinationType> addCacheCoordinationTypeCombo(Composite container) {

		return new EnumFormComboViewer<Caching, CacheCoordinationType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Caching.DEFAULT_COORDINATION_TYPE_PROPERTY);
				propertyNames.add(Caching.SPECIFIED_COORDINATION_TYPE_PROPERTY);
			}

			@Override
			protected CacheCoordinationType[] getChoices() {
				return CacheCoordinationType.values();
			}

			@Override
			protected CacheCoordinationType getDefaultValue() {
				return getSubject().getDefaultCoordinationType();
			}

			@Override
			protected String displayString(CacheCoordinationType value) {
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					CacheCoordinationTypeComposite.this,
					value
				);
			}

			@Override
			protected CacheCoordinationType getValue() {
				return getSubject().getSpecifiedCoordinationType();
			}

			@Override
			protected void setValue(CacheCoordinationType value) {
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
			EclipseLinkUiMappingsMessages.CacheCoordinationTypeComposite_label,
			addCacheCoordinationTypeCombo(container),
			EclipseLinkHelpContextIds.CACHING_CACHE_COORDINATION_TYPE
		);
	}
}
