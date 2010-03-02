/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.jpa2.context.EmbeddedMapping2_0;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddedMappingOverridesComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * |   x Override Default                                                      |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ColumnComposite                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EmbeddedMapping
 * @see ColumnComposite
 *
 * @version 2.3
 * @since 2.3
 */
public class EmbeddedMapping2_0OverridesComposite extends AbstractEmbeddedMappingOverridesComposite<EmbeddedMapping>
{
	
	/**
	 * Creates a new <code>EmbeddedAttributeOverridesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EmbeddedMapping2_0OverridesComposite(Pane<? extends EmbeddedMapping> parentPane,
	                                           Composite parent) {

		super(parentPane, parent);
	}
	
	@Override
	protected boolean supportsAssociationOverrides() {
		return true;
	}	
	
	@Override
	protected PropertyValueModel<AttributeOverrideContainer> buildAttributeOverrideContainerHolder() {
		return new PropertyAspectAdapter<EmbeddedMapping, AttributeOverrideContainer>(getSubjectHolder()) {
			@Override
			protected AttributeOverrideContainer buildValue_() {
				return this.subject.getAttributeOverrideContainer();
			}
		};
	}

	@Override
	protected Pane<AssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<AssociationOverride> associationOverrideHolder) {
		return new AssociationOverride2_0Composite(this, associationOverrideHolder, pageBook);
	}
	
	@Override
	protected PropertyValueModel<AssociationOverrideContainer> buildAssociationOverrideContainerHolder() {
		return new PropertyAspectAdapter<EmbeddedMapping, AssociationOverrideContainer>(getSubjectHolder()) {
			@Override
			protected AssociationOverrideContainer buildValue_() {
				return ((EmbeddedMapping2_0) this.subject).getAssociationOverrideContainer();
			}
		};
	}

}