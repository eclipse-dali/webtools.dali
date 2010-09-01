/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn.Owner;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;

public abstract class AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy 
	extends AbstractOrmJoinColumnJoiningStrategy
{
	
	protected AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy(
			OrmJoinColumnEnabledRelationshipReference parent,
			XmlJoinColumnsMapping resource) {
		super(parent, resource);
	}
	
	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	@Override
	public OrmJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return (OrmJoinColumnEnabledRelationshipReference) super.getRelationshipReference();
	}

	protected abstract Entity getRelationshipTargetEntity();

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	public boolean isOverridableAssociation() {
		return true;
	}
		
	public TextRange getValidationTextRange() {
		return this.getRelationshipReference().getValidationTextRange();
	}

	// ********** join column owner adapter **********

	protected class JoinColumnOwner
		implements OrmJoinColumn.Owner 
	{
		protected JoinColumnOwner() {
			super();
		}
		
		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return getRelationshipMapping().getName();
		}

		public PersistentAttribute getPersistentAttribute() {
			return getRelationshipMapping().getPersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.candidateTableNames();
		}
		
		public Table getDbTable(String tableName) {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getDbTable(tableName);
		}
		
		public Table getReferencedColumnDbTable() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getReferencedColumnDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new JoinColumnValidator(getPersistentAttribute(), (JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
		}
	}

}
