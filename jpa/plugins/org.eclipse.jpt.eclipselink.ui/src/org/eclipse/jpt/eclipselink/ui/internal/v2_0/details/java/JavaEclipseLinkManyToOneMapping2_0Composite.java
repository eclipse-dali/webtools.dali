/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneRelationshipReference2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkManyToOneMapping2_0;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.ManyToOneJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.AbstractManyToOneMapping2_0Composite;
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
public class JavaEclipseLinkManyToOneMapping2_0Composite
	extends AbstractManyToOneMapping2_0Composite<JavaManyToOneMapping, JavaManyToOneRelationshipReference2_0>
{
	public JavaEclipseLinkManyToOneMapping2_0Composite(
			PropertyValueModel<? extends JavaManyToOneMapping> subjectHolder,
			Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeManyToOneSection(Composite container) {
		new TargetEntityComposite(this, container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchableHolder(), container);
		new OptionalComposite(this, container);
		new CascadeComposite(this, buildCascadeHolder(),  addSubPane(container, 5));
	}
	
	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<JavaManyToOneMapping, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((JavaEclipseLinkManyToOneMapping2_0) this.subject).getJoinFetch();
			}
		};
	}

}
