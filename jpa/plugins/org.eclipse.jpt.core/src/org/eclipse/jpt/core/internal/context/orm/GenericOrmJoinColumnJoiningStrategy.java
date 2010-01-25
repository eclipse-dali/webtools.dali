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
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn.Owner;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;

public class GenericOrmJoinColumnJoiningStrategy 
	extends AbstractOrmJoinColumnJoiningStrategy
{
	
	public GenericOrmJoinColumnJoiningStrategy(
			OrmJoinColumnEnabledRelationshipReference parent,
			XmlJoinColumnsMapping resource) {
		super(parent, resource);
	}
	
	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	public TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}

	public String getTableName() {
		return getTypeMapping().getPrimaryTableName();
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}

	public boolean isOverridableAssociation() {
		return true;
	}
	
	@Override
	public OrmJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return (OrmJoinColumnEnabledRelationshipReference) super.getRelationshipReference();
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
			return GenericOrmJoinColumnJoiningStrategy.this.getTableName();
		}
		
		public Entity getTargetEntity() {
			return getRelationshipMapping().getResolvedTargetEntity();
		}
		
		public String getAttributeName() {
			return getRelationshipMapping().getName();
		}
		
		public PersistentAttribute getPersistentAttribute() {
			return getRelationshipMapping().getPersistentAttribute();
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return getTypeMapping().associatedTableNamesIncludingInherited();
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmJoinColumnJoiningStrategy.this.getTypeMapping();
		}
		
		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericOrmJoinColumnJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public int joinColumnsSize() {
			return GenericOrmJoinColumnJoiningStrategy.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return GenericOrmJoinColumnJoiningStrategy.this.getValidationTextRange();
		}

	}

}
