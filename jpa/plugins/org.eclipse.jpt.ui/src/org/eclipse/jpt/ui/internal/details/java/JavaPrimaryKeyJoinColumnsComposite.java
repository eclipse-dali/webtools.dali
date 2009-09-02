/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.ui.internal.details.AbstractPrimaryKeyJoinColumnsComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * @see JavaEntity
 * @see JavaInheritanceComposite - The container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class JavaPrimaryKeyJoinColumnsComposite extends AbstractPrimaryKeyJoinColumnsComposite<JavaEntity>
{

	/**
	 * Creates a new <code>JavaPrimaryKeyJoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public JavaPrimaryKeyJoinColumnsComposite(Pane<? extends JavaEntity> subjectHolder,
	                                      Composite parent) {

		super(subjectHolder, parent);
	}
	
	@Override
	protected ListValueModel<PrimaryKeyJoinColumn> buildDefaultJoinColumnsListHolder() {
		return new PropertyListValueModelAdapter<PrimaryKeyJoinColumn>(buildDefaultJoinColumnHolder());
	}
	
	private PropertyValueModel<PrimaryKeyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<Entity, PrimaryKeyJoinColumn>(getSubjectHolder(), Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN) {
			@Override
			protected PrimaryKeyJoinColumn buildValue_() {
				return subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}
	
	@Override
	protected void switchDefaultToSpecified() {
		PrimaryKeyJoinColumn defaultJoinColumn = getSubject().getDefaultPrimaryKeyJoinColumn();

		if (defaultJoinColumn != null) {
			String columnName = defaultJoinColumn.getDefaultName();
			String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

			PrimaryKeyJoinColumn pkJoinColumn = getSubject().addSpecifiedPrimaryKeyJoinColumn(0);
			pkJoinColumn.setSpecifiedName(columnName);
			pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);

			this.joinColumnHolder.setValue(pkJoinColumn);
		}
	}

}