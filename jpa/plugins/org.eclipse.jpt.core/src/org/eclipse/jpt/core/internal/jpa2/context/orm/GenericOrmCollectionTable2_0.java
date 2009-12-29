/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmReferenceTable;
import org.eclipse.jpt.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * orm.xml collection table
 */
public class GenericOrmCollectionTable2_0
	extends GenericOrmReferenceTable
	implements OrmCollectionTable2_0
{

	public GenericOrmCollectionTable2_0(OrmElementCollectionMapping2_0 parent, XmlCollectionTable xmlCollectionTable) {
		super(parent);
		this.initialize(xmlCollectionTable);
	}

	@Override
	protected OrmJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	public void initializeFrom(CollectionTable2_0 oldCollectionTable) {
		super.initializeFrom(oldCollectionTable);
	}
	
	// ********** AbstractOrmTable implementation **********

	public PersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}

	@Override
	public OrmElementCollectionMapping2_0 getParent() {
		return (OrmElementCollectionMapping2_0) super.getParent();
	}

	@Override
	protected String buildDefaultName() {
		return MappingTools.buildCollectionTableDefaultName(getParent());
	}

	@Override
	protected XmlCollectionTable getResourceTable() {
		return this.getParent().getResourceAttributeMapping().getCollectionTable();
	}

	@Override
	protected XmlCollectionTable addResourceTable() {
		XmlCollectionTable xmlCollectionTable = OrmFactory.eINSTANCE.createXmlCollectionTable();
		getParent().getResourceAttributeMapping().setCollectionTable(xmlCollectionTable);
		return xmlCollectionTable;
	}

	@Override
	protected void removeResourceTable() {
		getParent().getResourceAttributeMapping().setCollectionTable(null);
	}


	// ********** validation **********

	@Override
	protected boolean shouldValidateAgainstDatabase() {
		return getParent().shouldValidateAgainstDatabase();
	}

	// ********** join column owner adapters **********

	/**
	 * just a little common behavior
	 */
	protected abstract class AbstractJoinColumnOwner
		implements OrmJoinColumn.Owner
	{
		protected AbstractJoinColumnOwner() {
			super();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmCollectionTable2_0.this.getParent().getTypeMapping();
		}

		public PersistentAttribute getPersistentAttribute() {
			return GenericOrmCollectionTable2_0.this.getParent().getPersistentAttribute();
		}

		/**
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		/**
		 * the join column can only be on the collection table itself
		 */
		public boolean tableIsAllowed() {
			return false;
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			String collectionTableName = GenericOrmCollectionTable2_0.this.getName();
			return (collectionTableName == null) ? null : (collectionTableName.equals(tableName)) ? GenericOrmCollectionTable2_0.this.getDbTable() : null;
		}

		/**
		 * by default, the join column is, obviously, in the collection table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericOrmCollectionTable2_0.this.getName();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmCollectionTable2_0.this.getValidationTextRange();
		}
	}


	/**
	 * owner for "back-pointer" JoinColumns;
	 * these point at the source/owning entity
	 */
	protected class JoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected JoinColumnOwner() {
			super();
		}

		public Entity getTargetEntity() {
			return GenericOrmCollectionTable2_0.this.getParent().getEntity();
		}

		public String getAttributeName() {
			//TODO
			return null; //I *think* this is correct
//			//return GenericJavaCollectionTable2_0.this.getParent().getName();
//			Entity targetEntity = GenericOrmCollectionTable2_0.this.getRelationshipMapping().getResolvedTargetEntity();
//			if (targetEntity == null) {
//				return null;
//			}
//			for (PersistentAttribute each : CollectionTools.iterable(targetEntity.getPersistentType().allAttributes())) {
//				if (each.getMapping().isOwnedBy(getRelationshipMapping())) {
//					return each.getName();
//				}
//			}
//			return null;
		}

		@Override
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			org.eclipse.jpt.db.Table dbTable = super.getDbTable(tableName);
			return (dbTable != null) ? dbTable : this.getTypeMapping().getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			return getTypeMapping().getPrimaryDbTable();
		}

		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericOrmCollectionTable2_0.this.defaultJoinColumn == joinColumn;
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public int joinColumnsSize() {
			return GenericOrmCollectionTable2_0.this.joinColumnsSize();
		}
	}
}
