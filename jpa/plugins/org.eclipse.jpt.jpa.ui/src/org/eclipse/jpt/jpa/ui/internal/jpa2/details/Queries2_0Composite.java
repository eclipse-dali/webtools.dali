/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.ui.internal.details.QueriesComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 *  Queries2_0Composite
 */
public class Queries2_0Composite
	extends QueriesComposite
{
	public Queries2_0Composite(
			Pane<?> parentPane, 
			PropertyValueModel<? extends QueryContainer> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	@Override
	protected Pane<NamedQuery2_0> buildNamedQueryPropertyComposite(PageBook pageBook) {
		return new NamedQueryProperty2_0Composite(
			this,
			this.buildNamedQuery2_0Holder(),
			pageBook);
	}
	
	protected PropertyValueModel<NamedQuery2_0> buildNamedQuery2_0Holder() {
		return new TransformationPropertyValueModel<Query, NamedQuery2_0>(this.getQueryHolder()) {
			@Override
			protected NamedQuery2_0 transform_(Query value) {
				return (value instanceof NamedQuery2_0) ? (NamedQuery2_0) value : null;
			}
		};
	}
}