/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneMapping2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.ManyToOneJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.AbstractManyToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.DerivedId2_0Pane;
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
 * | | DerivedId2_0Pane                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ManyToOneJoiningStrategyPane                                          | |
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
 * @see {@link JavaManyToOneMapping2_0}
 * @see {@link TargetEntityComposite}
 * @see {@link DerivedId2_0Pane}
 * @see {@link ManyToOneJoiningStrategyPane}
 * @see {@link FetchTypeComposite}
 * @see {@link OptionalComposite}
 * @see {@link CascadeComposite}
 */
public class OrmEclipseLinkManyToOneMapping2_0Composite<T extends OrmManyToOneMapping2_0>
	extends AbstractManyToOneMapping2_0Composite<T>
{
	public OrmEclipseLinkManyToOneMapping2_0Composite(
			PropertyValueModel<T> subjectHolder,
			Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		
		new TargetEntityComposite(this, addPane(container, groupBoxMargin));
		new DerivedId2_0Pane(this, buildDerivedIdHolder(), addPane(container, groupBoxMargin));
		new ManyToOneJoiningStrategyPane(this, buildJoiningHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), addPane(container, groupBoxMargin));
		new FetchTypeComposite(this, addPane(container, groupBoxMargin));
		new OptionalComposite(this, addPane(container, groupBoxMargin));
		new CascadeComposite(this, buildCascadeHolder(),  addSubPane(container, 5));
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<OrmManyToOneMapping2_0, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}

}
