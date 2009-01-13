/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.AbstractEntityComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an ORM entity.
 *
 * @see OrmEntity
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see OrmSecondaryTablesComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmEntityComposite extends AbstractEntityComposite<OrmEntity>
{
	/**
	 * Creates a new <code>OrmEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>OrmEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEntityComposite(PropertyValueModel<? extends OrmEntity> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		new OrmJavaClassChooser(this, getSubjectHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		new AccessTypeComposite(this, getSubjectHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		new MetadataCompleteComposite(this, getSubjectHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		
		initializeGeneralPane(container);
		initializeQueriesPane(container);
		initializeInheritancePane(container);
		initializeAttributeOverridesPane(container);
		initializeGeneratorsPane(container);
		initializeSecondaryTablesPane(container);
	}
	
	
	@Override
	protected void addSecondaryTablesComposite(Composite container) {
		new OrmSecondaryTablesComposite(this, container);
	}
	
	@Override
	protected void addInheritanceComposite(Composite container) {
		new OrmInheritanceComposite(this, container);
	}
}