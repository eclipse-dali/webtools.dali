/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import java.util.Iterator;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;

public class MappingTools
{
	/**
	 * Default join table name from the JPA spec:
	 * 	The concatenated names of the two associated primary
	 * 	entity tables, separated by a underscore.
	 * 
	 * [owning table name]_[target table name]
	 * 
	 * NB: The *names* are concatenated, *not* the *identifiers*.
	 * E.g. the join table for "Foo" and "baR" (where the "delimited" identifier
	 * is required) is
	 *     "Foo_baR"
	 * not
	 *     "Foo"_"baR"
	 * As a result, we cannot honestly calculate the default name without a
	 * database connection.
	 */
	public static String buildJoinTableDefaultName(RelationshipMapping relationshipMapping) {
		if ( ! relationshipMapping.isRelationshipOwner()) {
			return null;
		}
		Table owningTable = relationshipMapping.getTypeMapping().getPrimaryDbTable();
		if (owningTable == null) {
			return null;
		}
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		Table targetTable = targetEntity.getPrimaryDbTable();
		if (targetTable == null) {
			return null;
		}
		String name = owningTable.getName() + '_' + targetTable.getName();
		return owningTable.getDatabase().convertNameToIdentifier(name);
	}

	/**
	 * Return the join column's default name;
	 * which is typically
	 *     [attribute name]_[referenced column name]
	 * But, if we don't have an attribute name (e.g. in a unidirectional
	 * OneToMany or ManyToMany) is
	 *     [target entity name]_[referenced column name]
	 * 
	 * See the comments in #buildJoinTableDefaultName(RelationshipMapping)
	 */
	public static String buildJoinColumnDefaultName(JoinColumn joinColumn) {
		if (joinColumn.getOwner().joinColumnsSize() != 1) {
			return null;
		}
		String prefix = joinColumn.getOwner().getAttributeName();
		if (prefix == null) {
			prefix = targetEntityName(joinColumn);
		}
		if (prefix == null) {
			return null;
		}
		// TODO not sure which of these is correct...
		// (the spec implies that the referenced column is always the
		// primary key column of the target entity)
		// Column targetColumn = joinColumn.getTargetPrimaryKeyDbColumn();
		Column targetColumn = joinColumn.getReferencedDbColumn();
		if (targetColumn == null) {
			return null;
		}
		String name = prefix + '_' + targetColumn.getName();
		return targetColumn.getDatabase().convertNameToIdentifier(name);
	}

	/**
	 * return the name of the target entity
	 */
	protected static String targetEntityName(JoinColumn joinColumn) {
		Entity targetEntity = joinColumn.getOwner().getTargetEntity();
		return (targetEntity == null) ? null : targetEntity.getName();
	}

	public static String buildJoinColumnDefaultReferencedColumnName(JoinColumn joinColumn) {
		if (joinColumn.getOwner().joinColumnsSize() != 1) {
			return null;
		}
		return targetPrimaryKeyColumnName(joinColumn);
	}

	/**
	 * return the name of the single primary key column of the target entity
	 */
	protected static String targetPrimaryKeyColumnName(JoinColumn joinColumn) {
		Entity targetEntity = joinColumn.getOwner().getTargetEntity();
		return (targetEntity == null) ? null : targetEntity.getPrimaryKeyColumnName();
	}
	
	
	public static Embeddable getEmbeddableFor(JavaPersistentAttribute persistentAttribute) {
		String qualifiedTypeName = persistentAttribute.getResourcePersistentAttribute().getQualifiedTypeName();
		if (qualifiedTypeName == null) {
			return null;
		}
		PersistentType persistentType = persistentAttribute.getPersistenceUnit().getPersistentType(qualifiedTypeName);
		if (persistentType != null) {
			if (persistentType.getMappingKey() == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
				return (Embeddable) persistentType.getMapping();
			}
		}
		return null;
	}
	
	public static ColumnMapping getColumnMapping(String attributeName, Embeddable embeddable) {
		if (attributeName == null || embeddable == null) {
			return null;
		}
		for (Iterator<PersistentAttribute> stream = embeddable.getPersistentType().allAttributes(); stream.hasNext();) {
			PersistentAttribute persAttribute = stream.next();
			if (attributeName.equals(persAttribute.getName())) {
				if (persAttribute.getMapping() instanceof ColumnMapping) {
					return (ColumnMapping) persAttribute.getMapping();
				}
			}
		}
		return null;		
	}
}
