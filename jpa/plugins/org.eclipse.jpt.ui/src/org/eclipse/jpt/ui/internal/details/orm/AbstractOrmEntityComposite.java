/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractEntityComposite;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.EntityNameComposite;
import org.eclipse.jpt.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.details.TableComposite;
import org.eclipse.jpt.ui.internal.details.java.BaseJavaUiFactory;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an ORM entity.
 *
 * @see OrmEntity
 * @see BaseJavaUiFactory - The factory creating this pane
 *
 * @version 2.3
 * @since 1.0
 */
public abstract class AbstractOrmEntityComposite extends AbstractEntityComposite<OrmEntity>
{
	/**
	 * Creates a new <code>OrmEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>OrmEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractOrmEntityComposite(
			PropertyValueModel<? extends OrmEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeInheritanceCollapsibleSection(container);
		this.initializeAttributeOverridesCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeSecondaryTablesCollapsibleSection(container);
	}

	@Override
	protected void initializeEntitySection(Composite container) {
		new OrmJavaClassChooser(this, getSubjectHolder(), container, false);
		new TableComposite(this, container);
		new EntityNameComposite(this, container);
		new AccessTypeComposite(this, buildAccessHolder(), container);
		new IdClassComposite(this, buildIdClassReferenceHolder(), container, false);
		new MetadataCompleteComposite(this, getSubjectHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<OrmEntity, AccessHolder>(getSubjectHolder()){
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}

	@Override
	protected void initializeSecondaryTablesSection(Composite container) {
		new OrmSecondaryTablesComposite(this, container);
	}

	@Override
	protected void initializeInheritanceSection(Composite container) {
		new OrmInheritanceComposite(this, container);
	}
}