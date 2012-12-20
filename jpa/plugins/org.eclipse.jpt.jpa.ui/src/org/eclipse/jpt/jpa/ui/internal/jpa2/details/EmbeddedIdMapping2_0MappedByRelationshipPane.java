/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivableIdMapping2_0;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

public class EmbeddedIdMapping2_0MappedByRelationshipPane
	extends Pane<EmbeddedIdMapping2_0>
{
	Label mappedByRelationshipLabel;

	public EmbeddedIdMapping2_0MappedByRelationshipPane(
			Pane<?> parentPane,
			PropertyValueModel<? extends EmbeddedIdMapping2_0> subjectModel,
			Composite parent) {
		
		super(parentPane, subjectModel, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new ControlSwitcher(buildDerivedModel(), this.buildPaneTransformer(pageBook), pageBook);
	}

	protected Label getMappedByRelationshipLabel(Composite container) {
		if (this.mappedByRelationshipLabel == null) {
			this.mappedByRelationshipLabel = this.addLabel(container, JptUiDetailsMessages2_0.EmbeddedIdMapping2_0MappedByRelationshipPane_label);
		}
		return this.mappedByRelationshipLabel;
	}

	protected ModifiablePropertyValueModel<Boolean> buildDerivedModel() {
		return new PropertyAspectAdapter<EmbeddedIdMapping2_0, Boolean>(getSubjectHolder(), DerivableIdMapping2_0.DERIVED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isDerived());
			}
		};
	}

	private Transformer<Boolean, Control> buildPaneTransformer(final Composite container) {
		return new Transformer<Boolean, Control>() {
			public Control transform(Boolean converter) {
				if (converter == null || converter == Boolean.FALSE) {
					return null;
				}
				return EmbeddedIdMapping2_0MappedByRelationshipPane.this.getMappedByRelationshipLabel(container); 
			}
		};
	}
}
