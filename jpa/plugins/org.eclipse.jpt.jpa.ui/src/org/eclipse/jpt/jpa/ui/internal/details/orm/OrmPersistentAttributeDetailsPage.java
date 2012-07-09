/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.jpa.ui.internal.details.PersistentAttributeMapAsComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * The default implementation of the details page used for the XML persistent
 * attribute.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmPersistentAttributeMapAsComposite                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Attribute mapping pane                                                | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OrmPersistentAttribute
 * @see OrmPersistentAttributeMapAsComposite
 *
 * @version 2.3
 * @since 2.0
 */
public class OrmPersistentAttributeDetailsPage
	extends PersistentAttributeDetailsPage<OrmReadOnlyPersistentAttribute>
{
	
	private PropertyValueModel<Boolean> virtualAttributeEnabledModel;

	/**
	 * Creates a new <code>OrmPersistentAttributeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmPersistentAttributeDetailsPage(Composite parent,
	                                         WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.virtualAttributeEnabledModel = this.buildVirtualAttributeEnabledModel();
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Map As composite
		this.buildMapAsPane(container);

		this.buildMappingPageBook(container);
	}

	protected Pane<ReadOnlyPersistentAttribute> buildMapAsPane(Composite parent) {
		return new PersistentAttributeMapAsComposite(this, parent, this.getMappingCompositeEnabledModel());		
	}
	
	private PropertyValueModel<Boolean> buildVirtualAttributeEnabledModel() {
		return new TransformationPropertyValueModel<OrmReadOnlyPersistentAttribute, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform_(OrmReadOnlyPersistentAttribute value) {
				return Boolean.valueOf(!value.isVirtual());
			}
		};
	}

	@Override
	protected PropertyValueModel<Boolean> getMappingCompositeEnabledModel() {
		return this.virtualAttributeEnabledModel;
	}
}