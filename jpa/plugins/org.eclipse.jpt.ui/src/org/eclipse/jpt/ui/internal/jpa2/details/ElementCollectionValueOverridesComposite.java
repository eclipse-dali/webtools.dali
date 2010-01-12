/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.ui.internal.details.AbstractOverridesComposite;
import org.eclipse.jpt.ui.internal.details.AssociationOverrideComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * Here is the layout of this pane:
 * <pre>
 * --------------------------------------------------------------------------------
 * |                                                                              |
 * | - Attribute Overrides ------------------------------------------------------ |
 * | | ------------------------------------------------------------------------ | |
 * | | |                                                                      | | |
 * | | | AddRemoveListPane                                                    | | |
 * | | |                                                                      | | |
 * | | ------------------------------------------------------------------------ | |
 * | |                                                                          | |
 * | |   x Override Default                                                     | |
 * | |                                                                          | |
 * | | ------------------------------------------------------------------------ | |
 * | | |                                                                      | | |
 * | | | PageBook (AttributeOverrideComposite/AssociationOverride2_0Composite)| | |
 * | | |                                                                      | | |
 * | | ------------------------------------------------------------------------ | |
 * | ---------------------------------------------------------------------------- |
 * --------------------------------------------------------------------------------</pre>
 *
 * @see ElementCollectionMapping2_0
 * @see AbstractElementCollectionMapping2_0Composite - The parent container
 * @see AttributeOverrideComposite
 * @see AssociationOverride2_0Composite
 *
 * @version 3.0
 * @since 3.0
 */
public final class ElementCollectionValueOverridesComposite extends AbstractOverridesComposite<ElementCollectionMapping2_0>
{

	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public ElementCollectionValueOverridesComposite(Pane<? extends ElementCollectionMapping2_0> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}
	
	@Override
	protected boolean supportsAssociationOverrides() {
		return true;
	}
	
	@Override
	protected Pane<AssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<AssociationOverride> associationOverrideHolder) {
		return new AssociationOverrideComposite(this, associationOverrideHolder, pageBook);
	}
	
	@Override
	protected PropertyValueModel<AttributeOverrideContainer> buildAttributeOverrideContainerHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, AttributeOverrideContainer>(getSubjectHolder()) {
			@Override
			protected AttributeOverrideContainer buildValue_() {
				return this.subject.getValueAttributeOverrideContainer();
			}
		};
	}

	@Override
	protected PropertyValueModel<AssociationOverrideContainer> buildAssociationOverrideContainerHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, AssociationOverrideContainer>(getSubjectHolder()) {
			@Override
			protected AssociationOverrideContainer buildValue_() {
				return this.subject.getValueAssociationOverrideContainer();
			}
		};
	}

}