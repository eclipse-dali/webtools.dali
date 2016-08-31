/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToOneMappingComposite;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOneToOneMappingComposite2_0<T extends OneToOneMapping2_0, R extends OneToOneRelationship2_0, C extends Cascade2_0>
	extends AbstractOneToOneMappingComposite<T, R, C>
{
	protected AbstractOneToOneMappingComposite2_0(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
	        WidgetFactory widgetFactory,
	        ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeOneToOneCollapsibleSection(container);
		initializeDerivedIdentityCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
	}

	protected void initializeDerivedIdentityCollapsibleSection(Composite container) {
		new DerivedIdentityPane2_0(this, buildDerivedIdentityModel(), container);
	}
	
	protected PropertyValueModel<DerivedIdentity2_0> buildDerivedIdentityModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getDerivedIdentity());
	}

	protected PropertyValueModel<OrphanRemovable2_0> buildOrphanRemovableModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getOrphanRemoval());
	}
}
