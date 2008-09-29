/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchable;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                ---------------------------------------------------------- |
 * | X  Join Fetch: |                                                      |v| |
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
 * @version 2.0
 * @since 1.0
 */
public class JoinFetchComposite extends FormPane<JoinFetchable> {

	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public JoinFetchComposite(FormPane<?> parentPane, 
								PropertyValueModel<? extends JoinFetchable> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Composite subPane = addSubPane(container, 2, 8, 0, 0, 0);
		addCheckBox(
			subPane, 
			EclipseLinkUiMappingsMessages.JoinFetchComposite_label, 
			buildJoinFetchHolder(),
			null);
		
		addJoinFetchTypeCombo(subPane);
	}
	

	private EnumFormComboViewer<JoinFetchable, JoinFetchType> addJoinFetchTypeCombo(Composite container) {

		return new EnumFormComboViewer<JoinFetchable, JoinFetchType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(JoinFetchable.DEFAULT_JOIN_FETCH_PROPERTY);
				propertyNames.add(JoinFetchable.SPECIFIED_JOIN_FETCH_PROPERTY);
			}

			@Override
			protected JoinFetchType[] getChoices() {
				return JoinFetchType.values();
			}

			@Override
			protected JoinFetchType getDefaultValue() {
				return getSubject().getDefaultJoinFetch();
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
			protected JoinFetchType getValue() {
				return getSubject().getSpecifiedJoinFetch();
			}

			@Override
			protected void setValue(JoinFetchType value) {
				getSubject().setSpecifiedJoinFetch(value);
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildJoinFetchHolder() {
		return new PropertyAspectAdapter<JoinFetchable, Boolean>(getSubjectHolder(), JoinFetchable.JOIN_FETCH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.hasJoinFetch());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setJoinFetch(value.booleanValue());
			}
		};
	}
}
