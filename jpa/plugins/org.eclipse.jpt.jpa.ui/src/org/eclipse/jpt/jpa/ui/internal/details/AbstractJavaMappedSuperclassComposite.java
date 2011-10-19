/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | IdClassComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - v Queries ------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | QueriesComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see MappedSuperclass
 * @see IdClassComposite
 * @see QueriesComposite
 *
 * @version 2.3
 * @since 2.3
 */

public abstract class AbstractJavaMappedSuperclassComposite extends
		AbstractMappedSuperclassComposite<JavaMappedSuperclass> {
	/**
	 * Creates a new <code>MappedSuperclassComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AbstractJavaMappedSuperclassComposite(
			PropertyValueModel<? extends JavaMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
	}
	
	
	protected void initializeQueriesCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.MappedSuperclassComposite_queries);
		this.initializeQueriesSection(container, buildQueryContainerHolder());
		
	}

	protected void initializeQueriesSection(Composite container, PropertyValueModel<QueryContainer> queryContainerHolder) {
		new QueriesComposite(this, queryContainerHolder, container);
	}
	
	private PropertyValueModel<QueryContainer> buildQueryContainerHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, QueryContainer>(getSubjectHolder()) {
			@Override
			protected QueryContainer buildValue_() {
				return this.subject.getQueryContainer();
			}
		};
	}
}
