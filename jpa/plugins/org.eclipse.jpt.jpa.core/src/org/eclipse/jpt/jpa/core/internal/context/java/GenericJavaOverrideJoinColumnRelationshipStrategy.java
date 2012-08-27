/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Table;

public class GenericJavaOverrideJoinColumnRelationshipStrategy
	extends AbstractJavaJoinColumnRelationshipStrategy
{
	public GenericJavaOverrideJoinColumnRelationshipStrategy(JavaOverrideRelationship parent) {
		super(parent);
	}


	// ********** join column annotations **********

	@Override
	protected ListIterable<JoinColumnAnnotation> getJoinColumnAnnotations() {
		return this.getOverrideAnnotation().getJoinColumns();
	}

	@Override
	protected JoinColumnAnnotation addJoinColumnAnnotation(int index) {
		return this.getOverrideAnnotation().addJoinColumn(index);
	}

	@Override
	protected void removeJoinColumnAnnotation(int index) {
		this.getOverrideAnnotation().removeJoinColumn(index);
	}

	@Override
	protected void moveJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getOverrideAnnotation().moveJoinColumn(targetIndex, sourceIndex);
	}

	@Override
	protected JoinColumnAnnotation buildNullJoinColumnAnnotation() {
		return new NullJoinColumnAnnotation(this.getOverrideAnnotation());
	}

	protected AssociationOverrideAnnotation getOverrideAnnotation() {
		return this.getRelationship().getOverrideAnnotation();
	}


	// ********** misc **********

	@Override
	protected ReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	public boolean isTargetForeignKey() {
		RelationshipMapping relationshipMapping = this.getRelationshipMapping();
		return (relationshipMapping != null) &&
				relationshipMapping.getRelationship().isTargetForeignKey();
	}

	public TypeMapping getRelationshipSource() {
		return this.isTargetForeignKey() ?
				this.getRelationshipMapping().getResolvedTargetEntity() :
				this.getRelationship().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return this.isTargetForeignKey() ?
				this.getRelationship().getTypeMapping() :
				this.getRelationshipMappingTargetEntity();
	}

	protected TypeMapping getRelationshipMappingTargetEntity() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return (mapping == null) ? null : mapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping target = this.getRelationshipTarget();
		return (target instanceof Entity) ? (Entity) target : null;
	}

	@Override
	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	protected String getAttributeName() {
		return this.getRelationship().getAttributeName();
	}

	@Override
	public String getTableName() {
		return this.isTargetForeignKey() ?
				super.getTableName() :
				this.getRelationship().getDefaultTableName();
	}

	@Override
	public Table resolveDbTable(String tableName) {
		return this.isTargetForeignKey() ?
				super.resolveDbTable(tableName) :
				this.getRelationship().resolveDbTable(tableName);
	}

	@Override
	public boolean tableNameIsInvalid(String tableName) {
		return this.isTargetForeignKey() ?
				super.tableNameIsInvalid(tableName) :
				this.getRelationship().tableNameIsInvalid(tableName);
	}

	@Override
	public Iterable<String> getCandidateTableNames() {
		return this.isTargetForeignKey() ?
				super.getCandidateTableNames() :
				this.getRelationship().getCandidateTableNames();
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}

	public boolean isOverridable() {
		return false;
	}

	@Override
	public JavaOverrideRelationship getRelationship() {
		return (JavaOverrideRelationship) super.getRelationship();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}


	// ********** join column owner adapter **********

	protected class JoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((ReadOnlyJoinColumn) column, this);
		}

		public String getAttributeName() {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getAttributeName();
		}

		public PersistentAttribute getPersistentAttribute() {
			RelationshipMapping relationshipMapping = GenericJavaOverrideJoinColumnRelationshipStrategy.this.getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getPersistentAttribute();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getCandidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getReferencedColumnDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return GenericJavaOverrideJoinColumnRelationshipStrategy.this.getRelationship().buildColumnValidator((ReadOnlyBaseColumn) column, this);
		}
	}
}
