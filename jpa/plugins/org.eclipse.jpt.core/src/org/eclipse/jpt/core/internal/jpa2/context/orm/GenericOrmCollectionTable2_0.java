/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.CollectionTableTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmReferenceTable;
import org.eclipse.jpt.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * <code>orm.xml</code> collection table
 */
public class GenericOrmCollectionTable2_0
	extends GenericOrmReferenceTable<XmlCollectionTable>
	implements OrmCollectionTable2_0
{
	public GenericOrmCollectionTable2_0(OrmElementCollectionMapping2_0 parent, Owner owner) {
		super(parent, owner);
	}

	@Override
	protected OrmJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}


	// ********** XML table **********

	@Override
	protected XmlCollectionTable getXmlTable() {
		return this.getXmlAttributeMapping().getCollectionTable();
	}

	@Override
	protected XmlCollectionTable buildXmlTable() {
		XmlCollectionTable xmlCollectionTable = OrmFactory.eINSTANCE.createXmlCollectionTable();
		this.getXmlAttributeMapping().setCollectionTable(xmlCollectionTable);
		return xmlCollectionTable;
	}

	@Override
	protected void removeXmlTable() {
		this.getXmlAttributeMapping().setCollectionTable(null);
	}

	protected XmlElementCollection getXmlAttributeMapping() {
		return this.getElementCollectionMapping().getXmlAttributeMapping();
	}


	// ********** misc **********

	public PersistentAttribute getPersistentAttribute() {
		return this.getElementCollectionMapping().getPersistentAttribute();
	}

	@Override
	public OrmElementCollectionMapping2_0 getParent() {
		return (OrmElementCollectionMapping2_0) super.getParent();
	}

	protected OrmElementCollectionMapping2_0 getElementCollectionMapping() {
		return this.getParent();
	}

	@Override
	protected String buildDefaultName() {
		return MappingTools.buildCollectionTableDefaultName(this.getElementCollectionMapping());
	}

	public void initializeFrom(CollectionTable2_0 oldCollectionTable) {
		super.initializeFrom(oldCollectionTable);
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getElementCollectionMapping().validatesAgainstDatabase();
	}


	// ********** join column owner **********

	/**
	 * owner for "back-pointer" join columns;
	 * these point at the source/owning entity
	 */
	protected class JoinColumnOwner
		implements OrmJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public TypeMapping getTypeMapping() {
			return this.getElementCollectionMapping().getTypeMapping();
		}

		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			return Tools.valuesAreEqual(GenericOrmCollectionTable2_0.this.getName(), tableName) ?
					GenericOrmCollectionTable2_0.this.getDbTable() :
					null;
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
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

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmCollectionTable2_0.this.getDefaultJoinColumn() == joinColumn;
		}

		/**
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		public Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		public Entity getRelationshipTarget() {
			return this.getElementCollectionMapping().getEntity();
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

		protected PersistentAttribute getPersistentAttribute() {
			return GenericOrmCollectionTable2_0.this.getPersistentAttribute();
		}

		public int joinColumnsSize() {
			return GenericOrmCollectionTable2_0.this.joinColumnsSize();
		}

		protected OrmElementCollectionMapping2_0 getElementCollectionMapping() {
			return GenericOrmCollectionTable2_0.this.getElementCollectionMapping();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new JoinColumnValidator(this.getPersistentAttribute(), (JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}
}
