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

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.ui.internal.details.PersistentAttributeDetailsPageManager;
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
 * | | PersistentAttributeMapAsComposite                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Attribute mapping pane                                                | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OrmModifiablePersistentAttribute
 * @see PersistentAttributeMapAsComposite
 */
public class OrmPersistentAttributeDetailsPageManager
	extends PersistentAttributeDetailsPageManager<OrmPersistentAttribute>
{
	private PropertyValueModel<Boolean> virtualAttributeEnabledModel;

	public OrmPersistentAttributeDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		super(parent, widgetFactory, resourceManager);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.virtualAttributeEnabledModel = this.buildVirtualAttributeEnabledModel();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.buildMapAsPane(container);
		super.initializeLayout(container);
	}

	protected Pane<PersistentAttribute> buildMapAsPane(Composite parent) {
		return new PersistentAttributeMapAsComposite(this, parent, this.getMappingCompositeEnabledModel());		
	}
	
	private PropertyValueModel<Boolean> buildVirtualAttributeEnabledModel() {
		return new TransformationPropertyValueModel<OrmPersistentAttribute, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform_(OrmPersistentAttribute attribute) {
				return Boolean.valueOf( ! attribute.isVirtual());
			}
		};
	}

	@Override
	protected PropertyValueModel<Boolean> getMappingCompositeEnabledModel() {
		return this.virtualAttributeEnabledModel;
	}
}
