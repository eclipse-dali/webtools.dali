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
import java.util.StringTokenizer;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;

public class MappingTools
{	
	public static boolean targetEntityIsValid(String targetEntity) {
		if (targetEntity == null) {
			return true;
		}
		// balance is # of name tokens - # of period tokens seen so far
		// initially 0; finally 1; should never drop < 0 or > 1
		int balance = 0;
		for (StringTokenizer t = new StringTokenizer(targetEntity, ".", true); t.hasMoreTokens();) {
			String s = t.nextToken();
			if (s.indexOf('.') >= 0) {
				// this is a delimiter
				if (s.length() > 1) {
					// too many periods in a row
					return false;
				}
				balance--;
				if (balance < 0) {
					return false;
				}
			} else {
				// this is an identifier segment
				balance++;
			}
		}
		return (balance == 1);
	}
	
	/**
	 * Default join table name from the JPA spec:
	 * 	The concatenated names of the two associated primary
	 * 	entity tables, separated by a underscore.
	 * 
	 * [owning table name]_[target table name]
	 */
	public static String buildJoinTableDefaultName(RelationshipMapping relationshipMapping) {
		if (!relationshipMapping.isRelationshipOwner()) {
			return null;
		}
		String owningTableName = relationshipMapping.getTypeMapping().getTableName();
		if (owningTableName == null) {
			return null;
		}
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		String targetTableName = targetEntity.getTableName();
		if (targetTableName == null) {
			return null;
		}
		return owningTableName + "_" + targetTableName;
	}

	/**
	 * return the join column's default name;
	 * which is typically &lt;attribute name&gt;_&lt;referenced column name&gt;
	 * but, if we don't have an attribute name (e.g. in a unidirectional
	 * OneToMany or ManyToMany) is
	 * &lt;target entity name&gt;_&lt;referenced column name&gt;
	 */
	// <attribute name>_<referenced column name>
	//     or
	// <target entity name>_<referenced column name>
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
		// String targetColumn = this.targetPrimaryKeyColumnName();
		String targetColumn = joinColumn.getReferencedColumnName();
		if (targetColumn == null) {
			return null;
		}
		return prefix + "_" + targetColumn;
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
