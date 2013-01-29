/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.FetchableMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.jpa.ui.internal.details.BasicMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmManyToOneMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmOneToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmOneToOneMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.java.JavaManyToManyMapping2_0Composite;
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
public class EclipseLinkJoinFetchComboViewer extends EnumFormComboViewer<EclipseLinkJoinFetch, EclipseLinkJoinFetchType> {

	public EclipseLinkJoinFetchComboViewer(Pane<?> parentPane, 
								PropertyValueModel<? extends EclipseLinkJoinFetch> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

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
		switch (value) {
			case INNER :
				return EclipseLinkUiDetailsMessages.EclipseLinkJoinFetchComposite_inner;
			case OUTER :
				return EclipseLinkUiDetailsMessages.EclipseLinkJoinFetchComposite_outer;
			default :
				throw new IllegalStateException();
		}
	}
	
	@Override
	protected String nullDisplayString() {
		return JptCommonUiMessages.NONE_SELECTED;
	}

	@Override
	protected EclipseLinkJoinFetchType getValue() {
		return getSubject().getValue();
	}

	@Override
	protected void setValue(EclipseLinkJoinFetchType value) {
		getSubject().setValue(value);
	}
}
