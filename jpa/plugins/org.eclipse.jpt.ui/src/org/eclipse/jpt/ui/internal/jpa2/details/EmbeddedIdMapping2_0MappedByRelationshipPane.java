/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.core.jpa2.context.MappedByRelationshipMapping2_0;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

public class EmbeddedIdMapping2_0MappedByRelationshipPane<T extends EmbeddedIdMapping2_0>
	extends Pane<T>
{
	Label mappedByRelationshipLabel;

	public EmbeddedIdMapping2_0MappedByRelationshipPane(
			Pane<?> parentPane,
			PropertyValueModel<T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.mappedByRelationshipLabel = addLabel(pageBook, JptUiDetailsMessages2_0.EmbeddedIdMapping2_0MappedByRelationshipPane_label);

		new ControlSwitcher(buildIsMappedByRelationshipHolder(), buildPaneTransformer(), pageBook);
	}

	protected WritablePropertyValueModel<Boolean> buildIsMappedByRelationshipHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), MappedByRelationshipMapping2_0.MAPPED_BY_RELATIONSHIP_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isMappedByRelationship());
			}
		};
	}

	private Transformer<Boolean, Control> buildPaneTransformer() {
		return new Transformer<Boolean, Control>() {
			public Control transform(Boolean converter) {
				if (converter == null || converter == Boolean.FALSE) {
					return null;
				}
				return EmbeddedIdMapping2_0MappedByRelationshipPane.this.mappedByRelationshipLabel; 
			}
		};
	}
}
