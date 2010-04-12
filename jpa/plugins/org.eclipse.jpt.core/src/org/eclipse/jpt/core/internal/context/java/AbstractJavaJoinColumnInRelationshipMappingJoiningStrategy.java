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
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy 
	extends AbstractJavaJoinColumnJoiningStrategy
{

	protected AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy(JavaJoinColumnEnabledRelationshipReference parent) {
		super(parent);
	}

	@Override
	protected JavaJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	@Override
	public JavaJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return (JavaJoinColumnEnabledRelationshipReference) super.getRelationshipReference();
	}

	@Override
	public JavaRelationshipMapping getRelationshipMapping() {
		return getRelationshipReference().getRelationshipMapping();
	}

	protected abstract Entity getRelationshipTargetEntity();

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	public boolean isOverridableAssociation() {
		return true;
	}

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return getRelationshipMapping().getPersistentAttribute().getResourcePersistentAttribute();
	}

	@Override
	protected Iterator<JoinColumnAnnotation> joinColumnAnnotations() {
		return new TransformationIterator<NestableAnnotation, JoinColumnAnnotation>(
			this.getResourcePersistentAttribute().annotations(
					JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME)) {
				@Override
				protected JoinColumnAnnotation transform(NestableAnnotation next) {
					return (JoinColumnAnnotation) next;
				}
			};
	}

	@Override
	protected JoinColumnAnnotation buildNullJoinColumnAnnotation() {
		return new NullJoinColumnAnnotation(this.getResourcePersistentAttribute());
	}

	@Override
	protected JoinColumnAnnotation addAnnotation(int index) {
		return (JoinColumnAnnotation) this.getResourcePersistentAttribute().
			addAnnotation(
				index, 
				JoinColumnAnnotation.ANNOTATION_NAME, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void removeAnnotation(int index) {
		this.getResourcePersistentAttribute().
			removeAnnotation(
				index, 
				JoinColumnAnnotation.ANNOTATION_NAME, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void moveAnnotation(int targetIndex, int sourceIndex) {
		this.getResourcePersistentAttribute().
			moveAnnotation(
				targetIndex, 
				sourceIndex, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationshipReference().getValidationTextRange(astRoot);
	}


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
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.getTableName();
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
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public Iterator<String> candidateTableNames() {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.candidateTableNames();
		}

		public Table getDbTable(String tableName) {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.getDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.getReferencedColumnDbTable();
		}

		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.joinColumnsSize();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaJoinColumnInRelationshipMappingJoiningStrategy.this.getValidationTextRange(astRoot);
		}

		public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] {
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY}, 
				column,
				textRange
			);
		}

		public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}

		public IMessage buildUnresolvedReferencedColumnNameMessage(BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column, 
				textRange
			);
		}

		public IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column, 
				textRange
			);
		}
	}
}
