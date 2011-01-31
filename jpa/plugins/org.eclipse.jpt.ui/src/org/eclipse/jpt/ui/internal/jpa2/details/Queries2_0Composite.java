/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryContainer;
import org.eclipse.jpt.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.ui.internal.details.NamedQueryPropertyComposite;
import org.eclipse.jpt.ui.internal.details.QueriesComposite;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
	protected NamedQueryPropertyComposite<NamedQuery2_0> buildNamedQueryPropertyComposite(PageBook pageBook) {
		return new NamedQueryProperty2_0Composite(
			this,
			this.buildNamedQuery2_0Holder(),
			pageBook);
	}
	
	protected PropertyValueModel<NamedQuery2_0> buildNamedQuery2_0Holder() {
		return new TransformationPropertyValueModel<Query, NamedQuery2_0>(this.getQueryHolder()) {
			@Override
			protected NamedQuery2_0 transform_(Query value) {
				return (value instanceof NamedQuery) ? (NamedQuery2_0) value : null;
			}
		};
	}
}
