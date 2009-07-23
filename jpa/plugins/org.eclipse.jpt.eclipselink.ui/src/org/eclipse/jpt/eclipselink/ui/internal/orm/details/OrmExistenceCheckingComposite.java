/*******************************************************************************
 *  Copyright (c) 2008, 2009 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
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
public class OrmExistenceCheckingComposite extends FormPane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>ExistenceCheckingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OrmExistenceCheckingComposite(FormPane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		addLabeledComposite( 
            container, 
            EclipseLinkUiMappingsMessages.ExistenceCheckingComposite_label,
            addExistenceCheckingTypeCombo(container).getControl(), 
            null 
       );
	}

	private EnumFormComboViewer<EclipseLinkCaching, EclipseLinkExistenceType> addExistenceCheckingTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkCaching, EclipseLinkExistenceType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_EXISTENCE_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_EXISTENCE_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkExistenceType[] getChoices() {
				return EclipseLinkExistenceType.values();
			}

			@Override
			protected EclipseLinkExistenceType getDefaultValue() {
				return getSubject().getDefaultExistenceType();
			}

			@Override
			protected String displayString(EclipseLinkExistenceType value) {
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					OrmExistenceCheckingComposite.this,
					value
				);
			}

			@Override
			protected EclipseLinkExistenceType getValue() {
				return getSubject().getSpecifiedExistenceType();
			}

			@Override
			protected void setValue(EclipseLinkExistenceType value) {
				getSubject().setSpecifiedExistenceType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}

}
