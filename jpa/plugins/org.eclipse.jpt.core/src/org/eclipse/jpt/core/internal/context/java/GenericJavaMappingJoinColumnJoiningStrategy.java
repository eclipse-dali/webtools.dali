/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaMappingJoinColumnRelationship;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.SubIteratorWrapper;

public class GenericJavaMappingJoinColumnJoiningStrategy
	extends AbstractJavaJoinColumnJoiningStrategy
{
	protected final boolean targetForeignKey;


	/**
	 * The default strategy is for a "source" foreign key.
	 */
	public GenericJavaMappingJoinColumnJoiningStrategy(JavaMappingJoinColumnRelationship parent) {
		this(parent, false);
	}

	public GenericJavaMappingJoinColumnJoiningStrategy(JavaMappingJoinColumnRelationship parent, boolean targetForeignKey) {
		super(parent);
		this.targetForeignKey = targetForeignKey;
	}


	// ********** join column annotations **********

	@Override
	protected Iterator<JoinColumnAnnotation> joinColumnAnnotations() {
		return new SubIteratorWrapper<NestableAnnotation, JoinColumnAnnotation>(this.joinColumnAnnotations_());
	}

	protected Iterator<NestableAnnotation> joinColumnAnnotations_() {
		return this.getResourcePersistentAttribute().annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected JoinColumnAnnotation addJoinColumnAnnotation(int index) {
		return (JoinColumnAnnotation) this.getResourcePersistentAttribute().addAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void removeJoinColumnAnnotation(int index) {
		this.getResourcePersistentAttribute().removeAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void moveJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getResourcePersistentAttribute().moveAnnotation(targetIndex, sourceIndex, JoinColumnsAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected JoinColumnAnnotation buildNullJoinColumnAnnotation() {
		return new NullJoinColumnAnnotation(this.getResourcePersistentAttribute());
	}


	// ********** misc **********

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getRelationshipReference().getMapping().getResourcePersistentAttribute();
	}

	@Override
	public JavaMappingJoinColumnRelationship getRelationshipReference() {
		return (JavaMappingJoinColumnRelationship) super.getRelationshipReference();
	}

	@Override
	protected JavaJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	public boolean isOverridable() {
		return true;
	}

	public TypeMapping getRelationshipSource() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return this.targetForeignKey ?
				mapping.getResolvedTargetEntity() :
				mapping.getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return this.targetForeignKey ?
				mapping.getTypeMapping() :
				mapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping target = this.getRelationshipTarget();
		return (target instanceof Entity) ? (Entity) target : null;
	}

	public boolean isTargetForeignKey() {
		return this.targetForeignKey;
	}


	// ********** validation **********

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationshipReference().getValidationTextRange(astRoot);
	}


	// ********** join column owner **********

	protected class JoinColumnOwner
		implements JavaJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.getRelationshipMapping().getName();
		}

		public PersistentAttribute getPersistentAttribute() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.getRelationshipMapping().getPersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public Iterator<String> candidateTableNames() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.candidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.getReferencedColumnDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.joinColumnsSize();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaMappingJoinColumnJoiningStrategy.this.getValidationTextRange(astRoot);
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new JoinColumnValidator((JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
		}
	}
}
