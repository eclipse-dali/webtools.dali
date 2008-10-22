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
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.BasicMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToManyMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToOneMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToManyMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToOneMappingComposite;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
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
 * @see Fetchable
 * @see BasicMappingComposite - A container of this widget
 * @see ManyToManyMappingComposite - A container of this widget
 * @see ManyToOneMappingComposite - A container of this widget
 * @see OneToManyMappingComposite - A container of this widget
 * @see OneToOneMappingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class JoinFetchComposite extends FormPane<JoinFetch> {

	/**
	 * Creates a new <code>JoinFetchComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public JoinFetchComposite(FormPane<?> parentPane, 
								PropertyValueModel<? extends JoinFetch> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addLabeledComposite( 
            container, 
            addLabel( 
                 container, 
                 EclipseLinkUiMappingsMessages.JoinFetchComposite_label), 
            addJoinFetchTypeCombo(container).getControl(),
            null 
       );
	}
	

	private EnumFormComboViewer<JoinFetch, JoinFetchType> addJoinFetchTypeCombo(Composite container) {

		return new EnumFormComboViewer<JoinFetch, JoinFetchType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(JoinFetch.VALUE_PROPERTY);
			}

			@Override
			protected JoinFetchType[] getChoices() {
				return JoinFetchType.values();
			}
			
			@Override
			protected JoinFetchType getDefaultValue() {
				return null;
			}
			
			@Override
			protected String displayString(JoinFetchType value) {
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					JoinFetchComposite.this,
					value
				);
			}
			
			@Override
			protected String nullDisplayString() {
				return JptCoreMessages.NONE;
			}

			@Override
			protected JoinFetchType getValue() {
				return getSubject().getValue();
			}

			@Override
			protected void setValue(JoinFetchType value) {
				getSubject().setValue(value);
			}
		};
	}
}
