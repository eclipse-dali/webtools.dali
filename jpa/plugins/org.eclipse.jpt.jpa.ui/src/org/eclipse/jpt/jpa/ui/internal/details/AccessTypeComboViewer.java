/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.swt.widgets.Composite;

public final class AccessTypeComboViewer
	extends EnumFormComboViewer<SpecifiedAccessReference, AccessType>
{
	public AccessTypeComboViewer(Pane<?> parentPane, PropertyValueModel<? extends SpecifiedAccessReference> subjectHolder, Composite parent) {
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(SpecifiedAccessReference.DEFAULT_ACCESS_PROPERTY);
		propertyNames.add(SpecifiedAccessReference.SPECIFIED_ACCESS_PROPERTY);
	}

	@Override
	protected AccessType[] getChoices() {
		if (getSubject() == null) {
			return new AccessType[]{};
		}
		return getJpaPlatformVariation().getSupportedAccessTypes(getSubject().getResourceType());
	}

	JpaPlatformVariation getJpaPlatformVariation() {
		return getSubject().getJpaProject().getJpaPlatform().getJpaVariation();
	}

	@Override
	protected AccessType getDefaultValue() {
		return getSubject().getDefaultAccess();
	}

	@Override
	protected String displayString(AccessType value) {
		return value.getDisplayString();
	}

	@Override
	protected AccessType getValue() {
		return getSubject().getSpecifiedAccess();
	}

	@Override
	protected void setValue(AccessType value) {
		getSubject().setSpecifiedAccess(value);
	}
}