/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractPrimaryKeyJoinColumnsComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * @see OrmEntity
 * @see OrmInheritanceComposite - The container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class OrmPrimaryKeyJoinColumnsComposite extends AbstractPrimaryKeyJoinColumnsComposite<OrmEntity>
{
	public OrmPrimaryKeyJoinColumnsComposite(Pane<? extends OrmEntity> subjectHolder,
	                                      Composite parent) {

		super(subjectHolder, parent);
	}
	
	@Override
	protected ListValueModel<ReadOnlyPrimaryKeyJoinColumn> buildDefaultJoinColumnsListHolder() {
		return new ListAspectAdapter<OrmEntity, ReadOnlyPrimaryKeyJoinColumn>(
			getSubjectHolder(),
			OrmEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST)
		{
			@Override
			protected ListIterable<ReadOnlyPrimaryKeyJoinColumn> getListIterable() {
				return subject.getDefaultPrimaryKeyJoinColumns();
			}
			@Override
			protected int size_() {
				return subject.getDefaultPrimaryKeyJoinColumnsSize();
			}
		};
	}
	
	@Override
	protected void switchDefaultToSpecified() {
		for (ReadOnlyPrimaryKeyJoinColumn defaultJoinColumn : getSubject().getDefaultPrimaryKeyJoinColumns()) {
			String columnName = defaultJoinColumn.getName();
			String referencedColumnName = defaultJoinColumn.getReferencedColumnName();

			PrimaryKeyJoinColumn pkJoinColumn = getSubject().addSpecifiedPrimaryKeyJoinColumn();
			pkJoinColumn.setSpecifiedName(columnName);
			pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}
	
}