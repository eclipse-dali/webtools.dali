/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.Pane;
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
public class JavaEclipseLinkExistenceCheckingComposite extends Pane<JavaEclipseLinkCaching> {

	/**
	 * Creates a new <code>ExistenceCheckingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public JavaEclipseLinkExistenceCheckingComposite(Pane<? extends JavaEclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		addLabeledComposite( 
            container, 
            addCheckBox( 
                 container, 
                 EclipseLinkUiDetailsMessages.EclipseLinkExistenceCheckingComposite_label, 
                 buildExistenceCheckingHolder(), 
                 null 
            ), 
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
					EclipseLinkUiDetailsMessages.class,
					JavaEclipseLinkExistenceCheckingComposite.this,
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
	
	private WritablePropertyValueModel<Boolean> buildExistenceCheckingHolder() {
		return new PropertyAspectAdapter<JavaEclipseLinkCaching, Boolean>(getSubjectHolder(), JavaEclipseLinkCaching.EXISTENCE_CHECKING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isExistenceChecking());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setExistenceChecking(value.booleanValue());
			}
		};
	}

}
