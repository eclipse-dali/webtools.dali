/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.db.Table;

public class GenericJavaMappingJoinColumnRelationshipStrategy
	extends AbstractJavaJoinColumnRelationshipStrategy<JavaMappingJoinColumnRelationship>
	implements MappingRelationshipStrategy2_0
{
	protected final boolean targetForeignKey;


	/**
	 * The default strategy is for a "source" foreign key.
	 */
	public GenericJavaMappingJoinColumnRelationshipStrategy(JavaMappingJoinColumnRelationship parent) {
		this(parent, false);
	}

	public GenericJavaMappingJoinColumnRelationshipStrategy(JavaMappingJoinColumnRelationship parent, boolean targetForeignKey) {
		super(parent);
		this.targetForeignKey = targetForeignKey;
	}


	// ********** join column annotations **********

	@Override
	protected ListIterable<JoinColumnAnnotation> getJoinColumnAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, JoinColumnAnnotation>(this.getNestableJoinColumnAnnotations());
	}

	protected ListIterable<NestableAnnotation> getNestableJoinColumnAnnotations() {
		return this.getResourceAttribute().getAnnotations(JoinColumnAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected JoinColumnAnnotation addJoinColumnAnnotation(int index) {
		return (JoinColumnAnnotation) this.getResourceAttribute().addAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void removeJoinColumnAnnotation(int index) {
		this.getResourceAttribute().removeAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void moveJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getResourceAttribute().moveAnnotation(targetIndex, sourceIndex, JoinColumnAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected JoinColumnAnnotation buildNullJoinColumnAnnotation() {
		return new NullJoinColumnAnnotation(this.getResourceAttribute());
	}


	// ********** misc **********

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getRelationship().getMapping().getResourceAttribute();
	}

	@Override
	public JavaMappingJoinColumnRelationship getRelationship() {
		return (JavaMappingJoinColumnRelationship) super.getRelationship();
	}

	@Override
	protected ReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
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

	public ReadOnlyRelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship) {
		return overrideRelationship.getJoinColumnStrategy();
	}


	// ********** validation **********

	public String getColumnTableNotValidDescription() {
		return JptJpaCoreValidationArgumentMessages.NOT_VALID_FOR_THIS_ENTITY;
	}


	// ********** join column owner **********

	protected class JoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((ReadOnlyJoinColumn) column, this);
		}

		public String getAttributeName() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getRelationshipMapping().getName();
		}

		protected ModifiablePersistentAttribute getPersistentAttribute() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getRelationshipMapping().getPersistentAttribute();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public Iterable<String> getCandidateTableNames() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getCandidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getReferencedColumnDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericJavaMappingJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column) {
			return new JoinColumnValidator(this.getPersistentAttribute(), (ReadOnlyJoinColumn) column, this, new EntityTableDescriptionProvider());
		}
	}
}
