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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.ExistenceType;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * -------------------------------------------------------------------------
 * |       			  		---------------------------------------------- |
 * | x Existence Checking:  |                                          |v| |
 * |       					---------------------------------------------- |
 * -------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkCaching
 * @see CachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class ExistenceCheckingComposite extends AbstractFormPane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>ExistenceCheckingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ExistenceCheckingComposite(AbstractFormPane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Composite subPane = buildSubPane(container, 2, 8, 0, 0, 0);
		
		
		buildCheckBox(
			subPane, 
			EclipseLinkUiMappingsMessages.ExistenceCheckingComposite_label, 
			buildExistenceCheckingHolder());
		
		
		buildExistenceCheckingTypeCombo(subPane);
	}

	private EnumFormComboViewer<EclipseLinkCaching, ExistenceType> buildExistenceCheckingTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkCaching, ExistenceType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_EXISTENCE_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_EXISTENCE_TYPE_PROPERTY);
			}

			@Override
			protected ExistenceType[] choices() {
				return ExistenceType.values();
			}

			@Override
			protected ExistenceType defaultValue() {
				return subject().getDefaultExistenceType();
			}

			@Override
			protected String displayString(ExistenceType value) {
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					ExistenceCheckingComposite.this,
					value
				);
			}

			@Override
			protected ExistenceType getValue() {
				return subject().getSpecifiedExistenceType();
			}

			@Override
			protected void setValue(ExistenceType value) {
				subject().setSpecifiedExistenceType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildExistenceCheckingHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.EXISTENCE_CHECKING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.hasExistenceChecking();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setExistenceChecking(value);
			}
		};
	}

}
