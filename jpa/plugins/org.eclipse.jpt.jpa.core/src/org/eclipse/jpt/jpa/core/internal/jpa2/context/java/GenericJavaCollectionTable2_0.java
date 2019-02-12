/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.CollectionTableTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaReferenceTable;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTableAnnotation2_0;

/**
 * Java collection table
 */
public class GenericJavaCollectionTable2_0
	extends GenericJavaReferenceTable<JavaElementCollectionMapping2_0, JavaCollectionTable2_0.ParentAdapter, CollectionTableAnnotation2_0>
	implements JavaCollectionTable2_0
{
	public GenericJavaCollectionTable2_0(JavaCollectionTable2_0.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}

	@Override
	protected JoinColumn.ParentAdapter buildJoinColumnParentAdapter() {
		return new JoinColumnParentAdapter();
	}


	// ********** table annotation **********

	@Override
	public CollectionTableAnnotation2_0 getTableAnnotation() {
		return (CollectionTableAnnotation2_0) this.getElementCollectionMapping().getResourceAttribute().getNonNullAnnotation(CollectionTableAnnotation2_0.ANNOTATION_NAME);
	}

	@Override
	protected void removeTableAnnotation() {
		this.getElementCollectionMapping().getResourceAttribute().removeAnnotation(CollectionTableAnnotation2_0.ANNOTATION_NAME);
	}


	// ********** misc **********

	public SpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getElementCollectionMapping().getPersistentAttribute();
	}

	protected JavaElementCollectionMapping2_0 getElementCollectionMapping() {
		return this.parent;
	}

	@Override
	protected String buildDefaultName() {
		return MappingTools.buildCollectionTableDefaultName(this.getElementCollectionMapping());
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.getElementCollectionMapping().validatesAgainstDatabase();
	}


	// ********** join column parent adapter **********

	/**
	 * parent adapter for "back-pointer" join columns;
	 * these point at the source/owning entity
	 */
	public class JoinColumnParentAdapter
		implements JoinColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return GenericJavaCollectionTable2_0.this;
		}

		protected TypeMapping getTypeMapping() {
			return GenericJavaCollectionTable2_0.this.getElementCollectionMapping().getTypeMapping();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return ObjectTools.equals(GenericJavaCollectionTable2_0.this.getName(), tableName) ?
					GenericJavaCollectionTable2_0.this.getDbTable() :
					null;
		}

		public String getDefaultColumnName(NamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((JoinColumn) column, this);
		}

		/**
		 * by default, the join column is, obviously, in the collection table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericJavaCollectionTable2_0.this.getName();
		}

		public TextRange getValidationTextRange() {
			return GenericJavaCollectionTable2_0.this.getValidationTextRange();
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name. The table is always the collection table.
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return ! ObjectTools.equals(this.getDefaultTableName(), tableName);
		}

		/**
		 * the join column can only be on the collection table itself
		 */
		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaCollectionTable2_0.this.getElementCollectionMapping().getEntity();
		}

		public String getAttributeName() {
			return null; //I *think* this is correct
			//return GenericJavaCollectionTable2_0.this.getParent().getName();
		}

		protected SpecifiedPersistentAttribute getPersistentAttribute() {
			return GenericJavaCollectionTable2_0.this.getElementCollectionMapping().getPersistentAttribute();
		}

		public int getJoinColumnsSize() {
			return GenericJavaCollectionTable2_0.this.getJoinColumnsSize();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new JoinColumnValidator(this.getPersistentAttribute(), (JoinColumn) column, this, new CollectionTableTableDescriptionProvider());
		}
	}
}
