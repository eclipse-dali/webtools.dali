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
public class CacheCoordinationTypeComposite extends AbstractFormPane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CacheCoordinationTypeComposite(AbstractFormPane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<EclipseLinkCaching, CacheCoordinationType> buildCacheCoordinationTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkCaching, CacheCoordinationType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_COORDINATION_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_COORDINATION_TYPE_PROPERTY);
			}

			@Override
			protected CacheCoordinationType[] choices() {
				return CacheCoordinationType.values();
			}

			@Override
			protected CacheCoordinationType defaultValue() {
				return subject().getDefaultCoordinationType();
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
				return subject().getSpecifiedCoordinationType();
			}

			@Override
			protected void setValue(CacheCoordinationType value) {
				subject().setSpecifiedCoordinationType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		buildLabeledComposite(
			container,
			EclipseLinkUiMappingsMessages.CacheCoordinationTypeComposite_label,
			buildCacheCoordinationTypeCombo(container),
			EclipseLinkHelpContextIds.CACHING_CACHE_COORDINATION_TYPE
		);
	}
}
