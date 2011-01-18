/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import java.util.Collection;

import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                ---------------------------------------------------------- |
 * | Join Fetch:    |                                                      |v| |
 * |                ---------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see FetchableMapping
 * @see BasicMappingComposite - A container of this widget
 * @see JavaManyToManyMapping2_0Composite - A container of this widget
 * @see OrmManyToOneMappingComposite - A container of this widget
 * @see OrmOneToManyMappingComposite - A container of this widget
 * @see OrmOneToOneMappingComposite - A container of this widget
 *
 * @version 2.3
 * @since 2.1
 */
public class EclipseLinkJoinFetchComposite extends Pane<EclipseLinkJoinFetch> {

	/**
	 * Creates a new <code>JoinFetchComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkJoinFetchComposite(Pane<?> parentPane, 
								PropertyValueModel<? extends EclipseLinkJoinFetch> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addLabeledComposite( 
            container, 
            addLabel( 
                 container, 
                 EclipseLinkUiDetailsMessages.EclipseLinkJoinFetchComposite_label), 
            addJoinFetchTypeCombo(container).getControl(),
            null 
       );
	}
	

	private EnumFormComboViewer<EclipseLinkJoinFetch, EclipseLinkJoinFetchType> addJoinFetchTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkJoinFetch, EclipseLinkJoinFetchType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkJoinFetch.VALUE_PROPERTY);
			}

			@Override
			protected EclipseLinkJoinFetchType[] getChoices() {
				return EclipseLinkJoinFetchType.values();
			}
			
			@Override
			protected EclipseLinkJoinFetchType getDefaultValue() {
				return null;
			}
			
			@Override
			protected String displayString(EclipseLinkJoinFetchType value) {
				return buildDisplayString(
					EclipseLinkUiDetailsMessages.class,
					EclipseLinkJoinFetchComposite.this,
					value
				);
			}
			
			@Override
			protected String nullDisplayString() {
				return JptCoreMessages.NONE;
			}

			@Override
			protected EclipseLinkJoinFetchType getValue() {
				return getSubject().getValue();
			}

			@Override
			protected void setValue(EclipseLinkJoinFetchType value) {
				getSubject().setValue(value);
			}
		};
	}
}
