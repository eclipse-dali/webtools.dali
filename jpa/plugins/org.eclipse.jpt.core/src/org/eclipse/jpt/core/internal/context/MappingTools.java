/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.ReferenceTable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * Gather some of the behavior common to the Java and XML models. :-(
 */
public class MappingTools {

	/**
	 * Default join table name from the JPA spec:<br>
	 * 	"The concatenated names of the two associated primary
	 * 	entity tables, separated by a underscore."
	 * <pre>
	 * [owning table name]_[target table name]
	 * </pre>
	 * <strong>NB:</strong> The <em>names</em> are concatenated,
	 * <em>not</em> the <em>identifiers</em>.
	 * E.g. the join table for <code>"Foo"</code> and <code>"baR"</code>
	 * (where the "delimited" identifier is required) is:
	 * <pre>
	 *     "Foo_baR"
	 * </pre>
	 * not
	 * <pre>
	 *     "Foo"_"baR"
	 * </pre>
	 * As a result, we cannot honestly calculate the default name without a
	 * database connection. We need the database to convert the resulting name
	 * to an identifier appropriate to the current database.
	 */
	public static String buildJoinTableDefaultName(RelationshipReference relationshipReference) {
		if (relationshipReference.getJpaProject().getDataSource().connectionProfileIsActive()) {
			return buildDbJoinTableDefaultName(relationshipReference);
		}
		// continue with a "best effort":
		String owningTableName = relationshipReference.getTypeMapping().getPrimaryTableName();
		if (owningTableName == null) {
			return null;
		}
		RelationshipMapping relationshipMapping = relationshipReference.getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		String targetTableName = targetEntity.getPrimaryTableName();
		if (targetTableName == null) {
			return null;
		}
		return owningTableName + '_' + targetTableName;
	}

	/**
	 * Use the database to build a more accurate default name.
	 */
	protected static String buildDbJoinTableDefaultName(RelationshipReference relationshipReference) {
		Table owningTable = relationshipReference.getTypeMapping().getPrimaryDbTable();
		if (owningTable == null) {
			return null;
		}
		RelationshipMapping relationshipMapping = relationshipReference.getRelationshipMapping();
		if (relationshipMapping == null) {
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
	 * Default collection table name from the JPA spec:<br>
	 * 	"The concatenation of the name of the containing entity and 
	 *  the name of the collection attribute, separated by an underscore."
	 * <pre>
	 * [owning entity name]_[attribute name]
	 * </pre>
	 */
	public static String buildCollectionTableDefaultName(ElementCollectionMapping2_0 mapping) {
		Entity entity = mapping.getEntity();
		if (entity == null) {
			return null;
		}
		String owningEntityName = entity.getName();
		String attributeName = mapping.getName();
		return owningEntityName + '_' + attributeName;
	}

	/**
	 * Return the join column's default name;
	 * which is typically
	 *     [attribute name]_[referenced column name]
	 * But, if we don't have an attribute name (e.g. in a unidirectional
	 * OneToMany or ManyToMany) is
	 *     [target entity name]_[referenced column name]
	 * 
	 * @see #buildJoinTableDefaultName(RelationshipMapping)
	 */
	public static String buildJoinColumnDefaultName(JoinColumn joinColumn, JoinColumn.Owner owner) {
		if (owner.joinColumnsSize() != 1) {
			return null;
		}
		String prefix = owner.getAttributeName();
		if (prefix == null) {
			Entity targetEntity = owner.getTargetEntity();
			if (targetEntity == null) {
				return null;
			}
			prefix = targetEntity.getName();
		}
		// not sure which of these is correct...
		// (the spec implies that the referenced column is always the
		// primary key column of the target entity)
		// Column targetColumn = joinColumn.getTargetPrimaryKeyDbColumn();
		String targetColumnName = joinColumn.getReferencedColumnName();
		if (targetColumnName == null) {
			return null;
		}
		String name = prefix + '_' + targetColumnName;
		// not sure which of these is correct...
		// converting the name to an identifier will result in the identifier
		// being delimited nearly every time (at least on non-Sybase/MS
		// databases); but that probably is not the intent of the spec...
		// return targetColumn.getDatabase().convertNameToIdentifier(name);
		return name;
	}

	/**
	 * If appropriate, return the name of the single primary key column of the
	 * target entity.
	 */
	public static String buildJoinColumnDefaultReferencedColumnName(JoinColumn.Owner joinColumnOwner) {
		if (joinColumnOwner.joinColumnsSize() != 1) {
			return null;
		}
		Entity targetEntity = joinColumnOwner.getTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		return targetEntity.getPrimaryKeyColumnName();
	}

	public static ColumnMapping getColumnMapping(String attributeName, PersistentType persistentType) {
		if (attributeName == null || persistentType == null) {
			return null;
		}
		for (Iterator<PersistentAttribute> stream = persistentType.allAttributes(); stream.hasNext(); ) {
			PersistentAttribute persAttribute = stream.next();
			if (attributeName.equals(persAttribute.getName())) {
				if (persAttribute.getMapping() instanceof ColumnMapping) {
					return (ColumnMapping) persAttribute.getMapping();
				}
				// keep looking or return null???
			}
		}
		return null;		
	}
	
	public static RelationshipMapping getRelationshipMapping(String attributeName, TypeMapping typeMapping) {
		if (attributeName == null || typeMapping == null) {
			return null;
		}
		for (Iterator<AttributeMapping> stream = typeMapping.allAttributeMappings(); stream.hasNext(); ) {
			AttributeMapping attributeMapping = stream.next();
			if (attributeName.equals(attributeMapping.getName())) {
				if (attributeMapping instanceof RelationshipMapping) {
					return (RelationshipMapping) attributeMapping;
				}
				// keep looking or return null???
			}
		}
		return null;		
	}

	public static void convertReferenceTableDefaultToSpecifiedJoinColumn(ReferenceTable referenceTable) {
		JoinColumn defaultJoinColumn = referenceTable.getDefaultJoinColumn();
		if (defaultJoinColumn != null) {
			String columnName = defaultJoinColumn.getDefaultName();
			String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();
			JoinColumn joinColumn = referenceTable.addSpecifiedJoinColumn(0);
			joinColumn.setSpecifiedName(columnName);
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	public static void convertJoinTableDefaultToSpecifiedInverseJoinColumn(JoinTable joinTable) {
		JoinColumn defaultInverseJoinColumn = joinTable.getDefaultInverseJoinColumn();
		if (defaultInverseJoinColumn != null) {
			String columnName = defaultInverseJoinColumn.getDefaultName();
			String referencedColumnName = defaultInverseJoinColumn.getDefaultReferencedColumnName();
			JoinColumn joinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
			joinColumn.setSpecifiedName(columnName);
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	public static String getMetamodelFieldMapKeyTypeName(CollectionMapping mapping) {
		PersistentType targetType = mapping.getResolvedTargetType();
		String mapKey = mapping.getMapKey();
		if (mapKey == null || targetType == null) {
			String mapKeyClass = mapping.getMapKeyClass();
			return mapKeyClass != null ? mapKeyClass : MetamodelField.DEFAULT_TYPE_NAME;
		}
		PersistentAttribute mapKeyAttribute = targetType.resolveAttribute(mapKey);
		if (mapKeyAttribute == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		AttributeMapping2_0 mapKeyMapping = (AttributeMapping2_0) mapKeyAttribute.getMapping();
		if (mapKeyMapping == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		return mapKeyMapping.getMetamodelTypeName();
	}

	public static Column resolveOverridenColumn(TypeMapping overridableTypeMapping, String attributeOverrideName) {
		if (overridableTypeMapping != null) {
			for (TypeMapping typeMapping : CollectionTools.iterable(overridableTypeMapping.inheritanceHierarchy())) {
				Column column = typeMapping.resolveOverriddenColumn(attributeOverrideName);
				if (column != null) {
					return column;
				}
			}
		}
		return null;		
	}

	public static RelationshipReference resolveRelationshipReference(TypeMapping overridableTypeMapping, String associationOverrideName) {
		if (overridableTypeMapping != null) {
			for (TypeMapping typeMapping : CollectionTools.iterable(overridableTypeMapping.inheritanceHierarchy())) {
				RelationshipReference relationshipReference = typeMapping.resolveRelationshipReference(associationOverrideName);
				if (relationshipReference != null) {
					return relationshipReference;
				}
			}
		}
		return null;
	}

	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private MappingTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
