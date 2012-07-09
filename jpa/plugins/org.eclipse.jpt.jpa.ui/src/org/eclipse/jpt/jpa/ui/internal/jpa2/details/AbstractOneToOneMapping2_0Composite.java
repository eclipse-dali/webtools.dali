/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneRelationship2_0;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToOneMappingComposite;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOneToOneMapping2_0Composite<T extends OneToOneMapping, R extends OneToOneRelationship2_0>
	extends AbstractOneToOneMappingComposite<T, R>
{
	protected AbstractOneToOneMapping2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
	        WidgetFactory widgetFactory) {

		super(subjectHolder, enabledModel, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeOneToOneCollapsibleSection(container);
		initializeDerivedIdentityCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
	}

	protected void initializeDerivedIdentityCollapsibleSection(Composite container) {
		new DerivedIdentity2_0Pane(this, buildDerivedIdentityHolder(), container);
	}
	
	protected PropertyValueModel<DerivedIdentity2_0> buildDerivedIdentityHolder() {
		return new PropertyAspectAdapter<T, DerivedIdentity2_0>(getSubjectHolder()) {
			@Override
			protected DerivedIdentity2_0 buildValue_() {
				return ((OneToOneMapping2_0) this.subject).getDerivedIdentity();
			}
		};
	}
}
