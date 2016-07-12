/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.ui.internal.details.QueriesComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 *  Queries2_0Composite
 */
public class QueriesComposite2_0
	extends QueriesComposite
{
	public QueriesComposite2_0(
			Pane<?> parentPane, 
			PropertyValueModel<? extends QueryContainer> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	@Override
	protected Pane<NamedQuery2_0> buildNamedQueryPropertyComposite(PageBook pageBook) {
		return new NamedQueryPropertyComposite2_0(
			this,
			this.buildSelectedNamedQuery2_0Model(),
			pageBook);
	}
	
	protected PropertyValueModel<NamedQuery2_0> buildSelectedNamedQuery2_0Model() {
		return PropertyValueModelTools.filter(this.getSelectedQueryModel(), NamedQuery2_0.class);
	}
}
