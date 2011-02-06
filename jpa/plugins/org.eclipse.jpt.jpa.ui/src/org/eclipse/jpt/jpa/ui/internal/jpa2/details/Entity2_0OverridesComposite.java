/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityOverridesComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

public class Entity2_0OverridesComposite
	extends AbstractEntityOverridesComposite
{
	public Entity2_0OverridesComposite(
			Pane<? extends Entity> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	@Override
	protected Pane<ReadOnlyAssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<ReadOnlyAssociationOverride> associationOverrideHolder) {
		return new AssociationOverride2_0Composite(this, associationOverrideHolder, pageBook);
	}
}
