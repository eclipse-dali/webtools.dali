/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToManyRelationship;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
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
 * | | JoiningStrategyComposite                                              | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrderingComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link ManyToManyMapping}
 * @see {@link TargetEntityComposite}
 * @see {@link ManyToManyJoiningStrategyPane}
 * @see {@link FetchTypeComposite}
 * @see {@link CascadeComposite}
 * @see {@link OrderingComposite}
 *
 * @version 2.3
 * @since 1.0
 */
public abstract class AbstractManyToManyMappingComposite<T extends ManyToManyMapping, R extends ManyToManyRelationship> 
	extends Pane<T>
    implements JpaComposite
{
	/**
	 * Creates a new <code>ManyToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IManyToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractManyToManyMappingComposite(PropertyValueModel<? extends T> subjectHolder,
	                                  Composite parent,
	                                  WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeManyToManyCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
		initializeOrderingCollapsibleSection(container);
	}
	
	protected void initializeManyToManyCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.ManyToManySection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeManyToManySection(container);
	}

	protected abstract void initializeManyToManySection(Composite container);

	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new ManyToManyJoiningStrategyPane(this, buildJoiningHolder(), container);
	}

	protected void initializeOrderingCollapsibleSection(Composite container) {
		new OrderingComposite(this, container);
	}

	
	protected PropertyValueModel<R> buildJoiningHolder() {
		return new TransformationPropertyValueModel<T, R>(
				getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected R transform_(T value) {
				return (R) value.getRelationship();
			}
		};
	}
	
	protected PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<T, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(T value) {
				return value.getCascade();
			}
		};
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<T, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}
