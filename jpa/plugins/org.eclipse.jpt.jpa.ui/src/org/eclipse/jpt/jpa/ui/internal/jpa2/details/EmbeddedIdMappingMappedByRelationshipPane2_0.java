/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivableIdMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

public class EmbeddedIdMappingMappedByRelationshipPane2_0
	extends Pane<EmbeddedIdMapping2_0>
{
	Label mappedByRelationshipLabel;

	public EmbeddedIdMappingMappedByRelationshipPane2_0(
			Pane<?> parentPane,
			PropertyValueModel<? extends EmbeddedIdMapping2_0> subjectModel,
			Composite parent) {
		
		super(parentPane, subjectModel, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		SWTBindingTools.bind(buildDerivedModel(), this.buildPaneTransformer(pageBook), pageBook);
	}

	protected Label getMappedByRelationshipLabel(Composite container) {
		if (this.mappedByRelationshipLabel == null) {
			this.mappedByRelationshipLabel = this.addLabel(container, JptJpaUiDetailsMessages2_0.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_PANE_LABEL);
		}
		return this.mappedByRelationshipLabel;
	}

	protected PropertyValueModel<Boolean> buildDerivedModel() {
		return PropertyValueModelTools.subjectModelAspectAdapter(
				this.getSubjectHolder(),
				DerivableIdMapping2_0.DERIVED_PROPERTY,
				m -> Boolean.valueOf(m.isDerived())
			);
	}

	private Transformer<Boolean, Control> buildPaneTransformer(Composite container) {
		return new PaneTransformer(container);
	}

	protected class PaneTransformer
		extends TransformerAdapter<Boolean, Control>
	{
		private final Composite container;

		protected PaneTransformer(Composite container) {
			this.container = container;
		}

		@Override
		public Control transform(Boolean converter) {
			if (converter == null || converter == Boolean.FALSE) {
				return null;
			}
			return EmbeddedIdMappingMappedByRelationshipPane2_0.this.getMappedByRelationshipLabel(this.container); 
		}
	}
}
