/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.CollectionTableTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaReferenceTable;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * Java collection table
 */
public class GenericJavaCollectionTable2_0
	extends GenericJavaReferenceTable
	implements JavaCollectionTable2_0
{

	public GenericJavaCollectionTable2_0(JavaElementCollectionMapping2_0 parent) {
		super(parent);
	}

	@Override
	protected JavaJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	@Override
	public JavaElementCollectionMapping2_0 getParent() {
		return (JavaElementCollectionMapping2_0) super.getParent();
	}

	public PersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}
	
	public void initialize(CollectionTable2_0Annotation collectionTable) {
		super.initialize(collectionTable);
	}

	public void update(CollectionTable2_0Annotation collectionTable) {
		super.update(collectionTable);
	}

	// ********** AbstractJavaTable implementation **********

	@Override
	protected String getAnnotationName() {
		return CollectionTable2_0Annotation.ANNOTATION_NAME;
	}

	@Override
	protected String buildDefaultName() {
		return MappingTools.buildCollectionTableDefaultName(getParent());
	}

	@Override
	protected CollectionTable2_0Annotation getAnnotation() {
		return this.getParent().getCollectionTableAnnotation();
	}



	// ********** validation **********

	@Override
	protected boolean shouldValidateAgainstDatabase() {
		return getParent().shouldValidateAgainstDatabase();
	}

	@Override
	protected String getUnresolvedCatalogMessageId() {
		return JpaValidationMessages.COLLECTION_TABLE_UNRESOLVED_CATALOG;
	}
	
	@Override
	protected String getUnresolvedSchemaMessageId() {
		return JpaValidationMessages.COLLECTION_TABLE_UNRESOLVED_SCHEMA;
	}
	
	@Override
	protected String getUnresolvedNameMessageId() {
		return JpaValidationMessages.COLLECTION_TABLE_UNRESOLVED_NAME;
	}


	// ********** join column owner adapter **********

	/**
	 * owner for "back-pointer" JoinColumns;
	 * these point at the source/owning entity
	 */
	protected class JoinColumnOwner
		implements JavaJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		//***** NamedColumn.Owner implementation *******
		public TypeMapping getTypeMapping() {
			return GenericJavaCollectionTable2_0.this.getParent().getTypeMapping();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			String collectionTableName = GenericJavaCollectionTable2_0.this.getName();
			return (collectionTableName == null) ? null : (collectionTableName.equals(tableName)) ? GenericJavaCollectionTable2_0.this.getDbTable() : null;
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}
		
		//***** JavaNamedColumn.Owner implementation *******
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaCollectionTable2_0.this.getValidationTextRange(astRoot);
		}

		//***** BaseColumn.Owner implementation *******
		/**
		 * by default, the join column is, obviously, in the collection table
		 */
		public String getDefaultTableName() {
			return GenericJavaCollectionTable2_0.this.getName();
		}
		

		//***** BaseJoinColumn.Owner implementation *******
		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			return getTypeMapping().getPrimaryDbTable();
		}

		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericJavaCollectionTable2_0.this.defaultJoinColumn == joinColumn;
		}

		//***** JoinColumn.Owner implementation *******
		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return !StringTools.stringsAreEqual(getDefaultTableName(), tableName);
		}

		/**
		 * the join column can only be on the collection table itself
		 */
		public Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaCollectionTable2_0.this.getParent().getEntity();
		}

		public String getAttributeName() {
			return null; //I *think* this is correct
			//return GenericJavaCollectionTable2_0.this.getParent().getName();
		}

		public PersistentAttribute getPersistentAttribute() {
			return GenericJavaCollectionTable2_0.this.getParent().getPersistentAttribute();
		}

		public int joinColumnsSize() {
			return GenericJavaCollectionTable2_0.this.joinColumnsSize();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new JoinColumnValidator((JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}
}
