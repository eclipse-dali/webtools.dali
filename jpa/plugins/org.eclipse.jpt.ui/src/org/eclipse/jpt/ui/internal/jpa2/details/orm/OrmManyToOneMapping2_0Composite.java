/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneMapping2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.ManyToOneJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.AbstractManyToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.DerivedIdentity2_0Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TargetEntityComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | DerivedIdentity2_0Pane                                                | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ManyToOneJoiningStrategyPane                                          | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AccessTypeComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link OrmManyToOneMapping2_0}
 * @see {@link OrmManyToOneMapping2_0UiProvider}
 * @see {@link TargetEntityComposite}
 * @see {@link DerivedId2_0Pane}
 * @see {@link ManyToOneJoiningStrategyPane}
 * @see {@link AccessTypeComposite}
 * @see {@link FetchTypeComposite}
 * @see {@link OptionalComposite}
 * @see {@link CascadeComposite}
 */
public class OrmManyToOneMapping2_0Composite<T extends OrmManyToOneMapping2_0>
	extends AbstractManyToOneMapping2_0Composite<T>
{
	public OrmManyToOneMapping2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		
		new TargetEntityComposite(this, addPane(container, groupBoxMargin));
		new DerivedIdentity2_0Pane(this, buildDerivedIdentityHolder(), container);
		new ManyToOneJoiningStrategyPane(this, buildJoiningHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), addPane(container, groupBoxMargin));
		new FetchTypeComposite(this, addPane(container, groupBoxMargin));
		new OptionalComposite(this, addPane(container, groupBoxMargin));
		new CascadeComposite(this, buildCascadeHolder(),  addSubPane(container, 5));
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<OrmManyToOneMapping, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}
