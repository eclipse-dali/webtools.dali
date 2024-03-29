/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
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
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_JOIN_FETCH_COMPOSITE_INNER;
			case OUTER :
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_JOIN_FETCH_COMPOSITE_OUTER;
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
